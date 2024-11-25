<script setup>
import AttractionPanel from "@/components/attractions/AttractionPanel.vue";
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";
const planStore = usePlanStore();
const { isAddMode, isEditMode } = storeToRefs(planStore);

const onAddDestination = () => {
  isAddMode.value = true;
};
</script>
<template>
  <div
    class="bg-light p-4 d-flex justify-content-center align-items-center space-x-4"
    v-if="isEditMode"
  >
    <button class="btn btn-outline-secondary w-40" @click="onAddDestination">
      목적지 추가
    </button>
    <AttractionPanel v-if="isAddMode" />
  </div>
</template>
<style></style>
