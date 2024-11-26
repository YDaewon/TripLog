import { computed, ref, watch } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";

import { getPlans, getPlan, updatePlan } from "@/api/plan";
import {
  getDestinationInfo,
  getDestinations,
  updateDestinations,
} from "@/api/destination";

export const usePlanStore = defineStore(
  "planStore",
  () => {
    // plan 관련 ========================================================================================================
    const plans = ref([]);
    const plan = ref({}); // 표시용
    const tempPlan = ref({}); // 수정용

    const commitPlan = () => {
      updatePlan(
        tempPlan.value,
        () => {
          console.log("플랜 업데이트 성공");
          plan.value = tempPlan.value;
        },
        (err) => {
          console.log("플랜 업데이트 실패:", err);
        }
      );
    };
    const loadPlans = async (userNo) => {
      await getPlans(
        { userNo: userNo },
        ({ data }) => {
          plans.value = data;
        },
        (err) => {
          console.log(err);
        }
      );
    };
    const loadPlan = async (planNo) => {
      await getPlan(
        planNo,
        ({ data }) => {
          plan.value = JSON.parse(JSON.stringify(data));
          tempPlan.value = JSON.parse(JSON.stringify(data));
        },
        (err) => {
          console.log(err);
        }
      );
    };
    const rollbackPlan = () => {
      console.log("rollback plan:", tempPlan.value, plan.value);
      tempPlan.value = plan.value;
    };

    // dest 관련 ========================================================================================================
    const destinations = ref([]); //표시용
    const tempDestinations = ref([]); // 수정용
    const selectedDestination = ref({});

    const groupedDestinations = computed(() => {
      // tempDestinations가 undefined이거나 배열이 아닌 경우 빈 배열 반환
      if (!tempDestinations.value || !Array.isArray(tempDestinations.value)) {
        console.log("tempDestinations is not valid:", tempDestinations.value);
        return [];
      }

      try {
        const groups = tempDestinations.value.reduce((result, destination) => {
          // 기존 그룹에서 같은 날짜를 찾음
          const existingGroup = result.find(
            (group) => group.date === destination.visitDate
          );

          if (existingGroup) {
            existingGroup.destinations.push(destination);
            existingGroup.destinations.sort(
              (a, b) => a.destinationOrder - b.destinationOrder
            );
          } else {
            result.push({
              date: destination.visitDate,
              destinations: [destination],
            });
          }

          return result;
        }, []);

        // tempPlan 체크 추가
        if (tempPlan.value && tempPlan.value.startAt && tempPlan.value.endAt) {
          const startDate = new Date(tempPlan.value.startAt);
          const endDate = new Date(tempPlan.value.endAt);
          let currentDate = new Date(startDate);

          while (currentDate <= endDate) {
            const dateString = currentDate.toISOString().split("T")[0];

            if (!groups.find((group) => group.date === dateString)) {
              groups.push({
                date: dateString,
                destinations: [],
              });
            }
            currentDate.setDate(currentDate.getDate() + 1);
          }
        }
        return groups.sort((a, b) => new Date(a.date) - new Date(b.date));
      } catch (error) {
        console.error("Error in groupedDestinations:", error);
        return [];
      }
    });

    const commitDesinations = () => {
      updateDestinations(
        tempDestinations.value,
        tempPlan.value.planNo,
        () => {
          console.log("목적지 업데이트 성공");
          destinations.value = tempDestinations.value;
        },
        (err) => {
          console.log("목적지 업데이트 실패:", err);
        }
      );
    };
    const loadDestinations = async (planNo) => {
      await getDestinations(
        planNo,
        (dests) => {
          destinations.value = JSON.parse(JSON.stringify(dests.data));
          tempDestinations.value = JSON.parse(JSON.stringify(dests.data));
        },
        (err) => {
          console.log(err);
        }
      );
    };
    const rollbackDestinations = () => {
      console.log("rollback dest:", tempDestinations.value, destinations.value);
      tempDestinations.value = destinations.value;
    };

    // state 관련 =======================================================================================================
    const selectedDate = ref("");
    const isEditMode = ref(false);
    const isPlan = ref(false);
    const isAddMode = ref(false);

    // 기타 =============================================================================================================

    const addDestination = (attNo) => {
      getDestinationInfo(
        attNo,
        ({ data }) => {
          console.log(data);
          selectedDestination.value = { ...data, ...selectedDestination.value };
          tempDestinations.value.push(selectedDestination.value);
          selectedDestination.value = {};
          console.log("now dests:", tempDestinations.value, destinations.value);
        },
        (err) => {
          console.log(err);
        }
      );
    };

    const adjustDates = () => {
      console.log(
        "start adjustDates: ",
        tempDestinations.value,
        tempPlan.value
      );
      if (
        !tempPlan.value ||
        !tempDestinations.value ||
        !Array.isArray(tempDestinations.value) ||
        tempDestinations.value.length === 0
      ) {
        throw new Error("Invalid input parameters");
      }

      if (!tempPlan.value.startAt || !tempPlan.value.endAt) {
        throw new Error("Plan must have startAt and endAt dates");
      }

      const parseDate = (dateStr) => {
        const date = new Date(dateStr);
        if (isNaN(date.getTime())) {
          throw new Error(`Invalid date format: ${dateStr}`);
        }
        return date;
      };

      const formatDate = (date) => {
        return date.toISOString().split("T")[0];
      };

      try {
        const endAt = parseDate(tempPlan.value.endAt);
        const startAt = parseDate(tempPlan.value.startAt);

        // destinations를 날짜순으로 정렬
        const sortedDestinations = [...tempDestinations.value].sort(
          (a, b) => parseDate(a.visitDate) - parseDate(b.visitDate)
        );

        // 원본 첫 번째 날짜를 기준으로 새로운 시작일까지의 차이 계산
        const originalFirstDate = parseDate(sortedDestinations[0].visitDate);
        const daysDiff = Math.floor(
          (startAt - originalFirstDate) / (1000 * 60 * 60 * 24)
        );

        // 모든 목적지의 날짜를 새로운 시작일 기준으로 조정
        const adjustedDestinations = sortedDestinations.map((dest) => {
          const originalDate = parseDate(dest.visitDate);
          const newDate = new Date(originalDate);
          newDate.setDate(originalDate.getDate() + daysDiff);
          return {
            ...dest,
            visitDate: formatDate(newDate),
          };
        });

        // 조정된 날짜 기준으로 범위를 벗어나는 목적지 확인
        const outOfRangeDestinations = adjustedDestinations.filter((dest) => {
          return parseDate(dest.visitDate) > endAt;
        });

        if (outOfRangeDestinations.length > 0) {
          const confirmMessage =
            `조정된 날짜 기준으로 ${outOfRangeDestinations.length}개의 목적지가 종료 날짜(${tempPlan.value.endAt})를 초과합니다.\n\n` +
            `제외될 목적지:\n${outOfRangeDestinations
              .map((dest) => `- ${dest.attractionTitle}\n `)
              .join("\n")}\n\n` +
            "초과하는 목적지를 제외하고 진행하시겠습니까?";

          const userConfirmed = confirm(confirmMessage);

          if (userConfirmed) {
            // 유효한 범위 내의 목적지만 필터링
            const validDestinations = adjustedDestinations
              .filter((dest) => parseDate(dest.visitDate) <= endAt)
              .map(({ originalDate, ...dest }) => dest); // originalDate 필드 제거

            console.log(
              `${outOfRangeDestinations.length}개의 초과 목적지가 제외되었습니다.`
            );
            tempDestinations.value = validDestinations;
            console.log("삭제된 결과: ", tempDestinations.value);
            return null;
          } else {
            console.log("작업이 취소되었습니다.");
            return null;
          }
        }

        // 범위를 벗어나는 목적지가 없는 경우
        tempDestinations.value = adjustedDestinations;
        console.log("조정된 결과: ", tempDestinations.value);
      } catch (error) {
        console.error("Error processing dates:", error.message);
        throw error;
      }
    };

    return {
      isEditMode,
      isPlan,
      isAddMode,
      plan,
      tempPlan,
      plans,
      tempPlan,
      selectedDate,
      commitPlan,
      loadPlans,
      rollbackPlan,
      tempDestinations,
      selectedDestination,
      loadDestinations,
      rollbackDestinations,
      groupedDestinations,
      loadPlan,
      adjustDates,
      commitDesinations,
      addDestination,
    };
  },
  { persist: { storage: localStorage } }
);
