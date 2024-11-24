<script setup>
import { createDestination } from "@/api/destination";

defineProps({
  isEditMode: Boolean,
});

const addMemeo = () => {
  createDestination({});
};
const newPlan = () => {
  createPlan(
    {
      title: "제목없음",
      description: "설명없음",
      userNo: userInfo.value.userNo,
    },
    (planNo) => {
      router.push({ name: "planDetail", params: { planNo: planNo.data } });
    },
    (err) => {
      console.log(err);
    }
  );
};
const addDestination = () => {
  showSearchPanel();
};
const showSearchPanel = () => {};
</script>
<template>
  <div
    class="bg-light p-4 d-flex justify-content-center align-items-center space-x-4"
    v-if="isEditMode"
  >
    <button class="btn btn-outline-secondary w-40" @click="addDestination">
      목적지 추가
    </button>
    <button class="btn btn-outline-secondary w-40" @click="addMemo">
      메모 추가
    </button>
  </div>
</template>
<style></style>
