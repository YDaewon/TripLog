import { computed, ref, watch } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";

import {
  getPlans,
  getPlan,
  createPlan,
  updatePlan,
  deletePlan,
} from "@/api/plan";
import {
  getDestinations,
  createDestination,
  deleteDestination,
  updateDestination,
  getDestinationInfo,
} from "@/api/destination";

export const usePlanStore = defineStore(
  "planStore",
  () => {
    const selectedDate = ref(-1);
    const isEditMode = ref(false);
    const toggleEditMode = () => {
      isEditMode.value = !isEditMode.value;
      console.log("toggled Edit Mode: ", isEditMode.value);
    };
    const isPlan = ref(false);
    const toggleIsPlan = () => {
      isPlan.value = !isPlan.value;
      console.log("toggled Plan Mode: ", isPlan.value);
    };
    const isAddMode = ref(false);

    const plans = ref([]);
    const plan = ref({});
    const tempPlan = ref({});

    const loadPlans = (userNo) => {
      console.log("loadPlans: ", userNo);
      getPlans(
        { userNo: userNo },
        ({ data }) => {
          plans.value = data;
        },
        (err) => {
          console.log(err);
        }
      );
    };
    const loadPlan = (planNo) => {
      getPlan(
        planNo,
        ({ data }) => {
          plan.value = data;
          tempPlan.value = data;
          console.log("plan info loaded: ", data);
        },
        (err) => {
          console.log("plan info load failed: ", err);
        }
      );
    };

    const commitPlan = () => {
      updatePlan(
        tempPlan.value,
        () => {
          loadPlan(tempPlan.value.planNo);
        },
        (err) => {
          console.log(err);
        }
      );
    };

    const rollbackPlan = () => {
      tempPlan.value = plan.value;
    };
    const destinations = ref([]);
    const tempDeleteDestinations = ref([]);
    const tempUpdateDestinations = ref([]);
    const tempCreateDestinations = ref([]);
    const tempDestinations = ref([]);
    const selectedDestination = ref({});

    const loadDestinations = async (planNo) => {
      await getDestinations(
        planNo,
        (dests) => {
          destinations.value = dests.data;
          tempDestinations.value = dests.data;
        },
        (err) => {
          console.log(err);
        }
      );
    };

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

    // watch(groupedDestinations, ()=>{
    //   console.log(groupedDestinations.value);
    // })

    const addTempDestination = (attNo) => {
      getDestinationInfo(
        attNo,
        ({ data }) => {
          console.log("addTempDest_data: ", data);
          console.log("addTempDest_selected: ", selectedDestination.value);
          const mergedDest = { ...data, ...selectedDestination.value };
          console.log(mergedDest);
          tempDestinations.value.push(mergedDest);
          tempCreateDestinations.value.push(mergedDest);
        },
        (err) => {
          console.log(err);
        }
      );
    };
    const subTempDestination = (delDest) => {
      tempDeleteDestinations.value.push(delDest);
      tempDestinations.value = tempDestinations.value.filter(
        (dest) =>
          !(
            dest.planNo === delDest.planNo &&
            dest.attractionNo === delDest.attractionNo &&
            dest.visitDate === delDest.visitDate
          )
      );
      tempCreateDestinations.value = tempCreateDestinations.value.filter(
        (createDest) =>
          !(
            createDest.planNo === delDest.planNo &&
            createDest.attractionNo === delDest.attractionNo &&
            createDest.visitDate === delDest.visitDate
          )
      );
    };
    const commitCreatedDestinations = () => {
      for (let i = 0; i < tempCreateDestinations.value.length; i++) {
        const dest = tempCreateDestinations.value[i];
        console.log("생성된 목적지 저장 요청: ", dest);
        createDestination(
          dest,
          () => {
            console.log(`목적지 추가 완료`);
          },
          (error) => {
            console.error(`목적지 ${dest.destinationNo} 추가 실패:`, error);
          }
        );
      }
      tempCreateDestinations.value = [];
    };
    const commitUpdatedDestinations = () => {
      for (let i = 0; i < tempUpdateDestinations.value.length; i++) {
        const dest = tempUpdateDestinations.value[i];
        updateDestination(
          dest,
          () => {
            console.log(`목적지 수정 완료`);
          },
          (error) => {
            console.error(`목적지 ${dest.destinationNo} 수정 실패:`, error);
          }
        );
      }
      tempUpdateDestinations.value = [];
    };
    const commitDeletedDestinations = () => {
      for (let i = 0; i < tempDeleteDestinations.value.length; i++) {
        const dest = tempDeleteDestinations.value[i];
        console.log("삭제된 목적지 삭제 요청: ", dest);
        deleteDestination(
          dest.destinationNo,
          () => {
            console.log(`목적지 삭제 완료`);
          },
          (error) => {
            console.error(`목적지 ${dest.destinationNo} 삭제 실패:`, error);
          }
        );
      }
      tempDeleteDestinations.value = [];
    };

    const rollbackDestinations = () => {
      tempDeleteDestinations.value = [];
      tempUpdateDestinations.value = [];
      tempCreateDestinations.value = [];
      tempDestinations.value = destinations.value;
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
        console.log(outOfRangeDestinations);

        if (outOfRangeDestinations.length > 0) {
          const confirmMessage =
            `조정된 날짜 기준으로 ${outOfRangeDestinations.length}개의 목적지가 종료 날짜(${tempPlan.value.endAt})를 초과합니다.\n\n` +
            `제외될 목적지:\n${outOfRangeDestinations
              .map((dest) => `- ${dest.attractionTitle}\n `)
              .join("\n")}\n\n` +
            "초과하는 목적지를 제외하고 진행하시겠습니까?";

          const userConfirmed = confirm(confirmMessage);

          if (userConfirmed) {
            tempDeleteDestinations.value.push(outOfRangeDestinations);
            console.log(tempDeleteDestinations.value);
            // 유효한 범위 내의 목적지만 필터링
            const validDestinations = adjustedDestinations
              .filter((dest) => parseDate(dest.visitDate) <= endAt)
              .map(({ originalDate, ...dest }) => dest); // originalDate 필드 제거

            console.log(
              `${outOfRangeDestinations.length}개의 초과 목적지가 제외되었습니다.`
            );
            tempUpdateDestinations.value.push(...validDestinations);
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
        tempUpdateDestinations.value.push(...adjustedDestinations);
        console.log("날짜만 조정한 결과: ", tempUpdateDestinations.value);
      } catch (error) {
        console.error("Error processing dates:", error.message);
        throw error;
      }
    };

    const resetDestinations = () => {
      isEditMode.value = false;
      isPlan.value = false;
      isAddMode.value = false;
      plans.value = [];
      plan.value = {};
      tempPlan.value = {};
      destinations.value = [];
      tempDeleteDestinations.value = [];
      tempUpdateDestinations.value = [];
      tempCreateDestinations.value = [];
      tempDestinations.value = [];
      selectedDestination.value = {};
    };

    return {
      isEditMode,
      toggleEditMode,
      isPlan,
      toggleIsPlan,
      isAddMode,
      plans,
      plan,
      tempPlan,
      commitPlan,
      loadPlans,
      rollbackPlan,
      destinations,
      tempDeleteDestinations,
      tempUpdateDestinations,
      tempCreateDestinations,
      tempDestinations,
      selectedDestination,
      loadDestinations,
      commitCreatedDestinations,
      commitUpdatedDestinations,
      commitDeletedDestinations,
      rollbackDestinations,
      groupedDestinations,
      addTempDestination,
      subTempDestination,
      resetDestinations,
      loadPlan,
      adjustDates,
    };
  },
  { persist: { storage: localStorage } }
);
