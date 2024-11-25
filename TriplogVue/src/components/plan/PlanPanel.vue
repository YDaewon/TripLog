<script setup>
import { storeToRefs } from "pinia";
import PlanBody from "./PlanBody.vue";
import PlanHeader from "./PlanHeader.vue";
import { usePlanStore } from "@/stores/plan";
import { onMounted } from "vue";
const planStore = usePlanStore();
const { tempDestinations } = storeToRefs(planStore);
onMounted(() => {
  tempDestinations.value = [];
});
</script>
<template>
  <div
    class="bg-white shadow-lg rounded-lg overflow-hidden h-100 d-flex flex-column"
  >
    <PlanHeader />
    <PlanBody />
  </div>
</template>
<style></style>
