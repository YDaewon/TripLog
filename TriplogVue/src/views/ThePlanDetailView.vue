<script setup>
import { deletePlan, getPlan, updatePlan } from "@/api/plan";
import {
  deleteDestination,
  getDestinations,
  updateDestination,
} from "@/api/destination";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import PlanPanel from "@/components/plan/PlanPanel.vue";
import { ref, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import router from "@/router";
import { listContentType } from "@/api/map";
import { getAttractions } from "@/api/attraction";

const attractions = ref([]);
const selectAttraction = ref({});
const plan = ref({});
const planNo = useRoute().params.planNo;
const destinations = ref([]);
const destinationsTemp = ref([]);
const isEditMode = ref(useRoute().params.isEditMode === "true");

onMounted(() => {
  if (planNo) {
    // 계획 정보 불러옴
    getPlan(
      planNo,
      ({ data }) => {
        plan.value = data;
        console.log("Raw plan:", plan);
      },
      (err) => {
        console.log(err);
      }
    );
    // 계획에 포함된 목적지들 불러옴
    getDestinations(
      planNo,
      ({ data }) => {
        destinations.value = data;
        console.log("Raw destinations: ", destinations);
      },
      (err) => {
        console.log("err: ", err);
      }
    );
    // updateAttractions();
  } else {
    console.log("planNo가 전달되지 않음");
  }
});
const onUpdatePlan = (newValue) => {
  plan.value = newValue;
  destinations.value = adjustDates(plan.value, destinations.value);
  // updateAttractions();
};
const onUpdateEditMode = (newValue) => {
  isEditMode.value = newValue;
};

const onSavePlan = () => {
  // 플랜 기본정보 갱신
  updatePlan(
    plan.value,
    (response) => {
      alert("저장되었습니다");
    },
    (error) => {
      alert("저장 중 오류발생:");
    }
  );

  // 수정된 목적지 갱신
  updateDestinations(destinations.value);

  //삭제 예정 목적지 삭제처리
  deleteDestinations(destinationsTemp.value);
};

const updateDestinations = (dests) => {
  for (let i = 0; i < dests.length; i++) {
    const dest = dests[i];
    updateDestination(
      dest,
      () => {},
      (error) => {
        console.error(`목적지 ${dest.destinationNo} 수정 실패:`, error);
      }
    );
  }
  console.log(`목적지 수정 완료`);
};

const deleteDestinations = (dests) => {
  for (let i = 0; i < dests.length; i++) {
    const dest = dests[i];
    deleteDestination(
      dest.destinationNo,
      () => {},
      (error) => {
        console.error(`목적지 ${dest.destinationNo} 삭제 실패:`, error);
      }
    );
  }
  console.log(`목적지 삭제 완료`);
};

const onDeletePlan = () => {
  if (confirm("삭제하시겠습니까?")) {
    deletePlan(
      plan.value.planNo,
      (response) => {
        alert("삭제되었습니다.");
        router.push({ name: "plan" });
      },
      (error) => {
        alert("삭제 중 오류발생:");
      }
    );
  } else {
    alert("삭제 취소");
  }
};

function adjustDates(plan, destinations) {
  if (
    !plan ||
    !destinations ||
    !Array.isArray(destinations) ||
    destinations.length === 0
  ) {
    throw new Error("Invalid input parameters");
  }

  if (!plan.startAt || !plan.endAt) {
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
    const endAt = parseDate(plan.endAt);
    const startAt = parseDate(plan.startAt);

    // destinations를 날짜순으로 정렬
    const sortedDestinations = [...destinations].sort(
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
        originalDate: dest.visitDate, // 원본 날짜 보존
      };
    });

    // 조정된 날짜 기준으로 범위를 벗어나는 목적지 확인
    const outOfRangeDestinations = adjustedDestinations.filter(
      (dest) => parseDate(dest.visitDate) > endAt
    );

    if (outOfRangeDestinations.length > 0) {
      const confirmMessage =
        `조정된 날짜 기준으로 ${outOfRangeDestinations.length}개의 목적지가 종료 날짜(${plan.endAt})를 초과합니다.\n\n` +
        `제외될 목적지:\n${outOfRangeDestinations
          .map(
            (dest) =>
              `- ${dest.memo}\n  원래 날짜: ${dest.originalDate} → 조정된 날짜: ${dest.visitDate}`
          )
          .join("\n")}\n\n` +
        "초과하는 목적지를 제외하고 진행하시겠습니까?";

      const userConfirmed = confirm(confirmMessage);

      if (userConfirmed) {
        destinationsTemp.value = outOfRangeDestinations;
        console.log(destinationsTemp.value);
        // 유효한 범위 내의 목적지만 필터링
        const validDestinations = adjustedDestinations
          .filter((dest) => parseDate(dest.visitDate) <= endAt)
          .map(({ originalDate, ...dest }) => dest); // originalDate 필드 제거

        console.log(
          `${outOfRangeDestinations.length}개의 초과 목적지가 제외되었습니다.`
        );
        return validDestinations;
      } else {
        console.log("작업이 취소되었습니다.");
        return null;
      }
    }

    // 범위를 벗어나는 목적지가 없는 경우
    return adjustedDestinations.map(({ originalDate, ...dest }) => dest); // originalDate 필드 제거
  } catch (error) {
    console.error("Error processing dates:", error.message);
    throw error;
  }
}
// 완성한 함수들 ↑ ===================================
const updateAttractions = () => {
  listContentType(
    (response) => {
      const contentTypes = response.data;
      getAttractions(
        planNo,
        ({ data }) => {
          const updatedAttractions = data.map((attraction) => {
            const match = contentTypes.find(
              (type) => type.content_type_id === attraction.contentTypeId
            );
            return {
              ...attraction,
              contentTypeName: match ? match.content_type_name : null,
            };
          });
          attractions.value = updateAttractions;
        },
        (err) => {
          console.log("Error fetching attractions:", err);
        }
      );
    },
    (err) => {
      console.log("Error fetching content types:", err);
    }
  );
};
</script>

<template>
  <div class="position-relative vh-100 vw-100 overflow-hidden">
    <VKakaoMap
      class="map w-100 h-100"
      :attractions="attractions"
      :selectAttraction="selectAttraction"
    />
    <div class="plan-container p-0 border">
      <PlanPanel
        :plan="plan"
        :destinations="destinations"
        :isEditMode="isEditMode"
        @updatePlan="onUpdatePlan"
        @savePlan="onSavePlan"
        @deletePlan="onDeletePlan"
        @updateEditMode="onUpdateEditMode"
      />
    </div>
  </div>
</template>

<style>
.plan-container {
  position: absolute;
  top: 20px;
  left: 20px;
  background-color: rgba(255, 255, 255, 1);
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  width: 500px;
}
</style>
