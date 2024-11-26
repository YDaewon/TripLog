<script setup>
import { storeToRefs } from "pinia";
import PlanBody from "./PlanBody.vue";
import PlanHeader from "./PlanHeader.vue";
import { usePlanStore } from "@/stores/plan";
import { onMounted, ref } from "vue";
const planStore = usePlanStore();
const { tempDestinations, selectedDate } = storeToRefs(planStore);
const isShow = ref(true);

const disableDailyMarker = () => {
  selectedDate.value = selectedDate.value === "disabled" ? "" : "disabled";
  isShow.value = selectedDate.value != "disabled";
};
onMounted(() => {
  tempDestinations.value = [];
});
</script>
<template>
  <div
    class="bg-white shadow-lg rounded-lg overflow-hidden h-100 d-flex flex-column"
  >
    <PlanHeader />
    <div class="d-flex justify-content-end">
      <button class="btn p-0 pe-4" @click="disableDailyMarker">
        {{ isShow ? "마커 숨기기" : "마커 보이기" }}
      </button>
    </div>
    <PlanBody />
  </div>
</template>
<style></style>
