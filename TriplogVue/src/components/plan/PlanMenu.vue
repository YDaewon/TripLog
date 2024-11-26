<script setup>
import { deletePlan } from "@/api/plan";
import router from "@/router";
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";
const planStore = usePlanStore();
const { isEditMode, isArticle } = storeToRefs(planStore);
const onDeletePlan = () => {
  if (confirm("삭제하시겠습니까?")) {
    deletePlan(
      planStore.plan.planNo,
      (response) => {
        alert("삭제되었습니다.");
        router.push({ name: "plan" });
      },
      (error) => {
        alert("삭제 중 오류발생");
        console.log(error);
      }
    );
  } else {
    alert("삭제 취소");
  }
};

const saveEdit = () => {
  if (planStore.isEditMode) {
    planStore.commitPlan();
    planStore.commitDesinations();
    console.log("수정사항 commit 완료");
  }
  isEditMode.value = !isEditMode.value;
};
const rollback = () => {
  if (confirm("수정을 취소하시겠습니까?")) {
    planStore.rollbackDestinations();
    planStore.rollbackPlan();
    isEditMode.value = false;
  }
};
</script>
<template>
  <div class="d-flex justify-content-between mb-3">
    <button class="btn" @click="$router.go(-1)">◀ 돌아가기</button>
    <div class="d-flex justify-content-end mb-2">
      <button class="btn" @click="saveEdit" v-if="!isArticle">
        {{ planStore.isEditMode ? "저장" : "수정" }}
      </button>
      <button class="btn" v-show="planStore.isEditMode" @click="rollback">
        취소
      </button>
      <button class="btn" v-show="planStore.isEditMode" @click="onDeletePlan">
        삭제
      </button>
    </div>
  </div>
</template>
<style></style>
