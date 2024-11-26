<script setup>
import PlanPanel from "@/components/plan/PlanPanel.vue";
import PlanKaKaoMap from "@/components/common/PlanKaKaoMap.vue";
import { usePlanStore } from "@/stores/plan";
import { useRoute } from "vue-router";
import { storeToRefs } from "pinia";
import { onMounted, onUnmounted } from "vue";
const planStore = usePlanStore();
const { destinations, isArticle } = storeToRefs(planStore);
const route = useRoute();
onMounted(async () => {
  await planStore.loadPlan(route.params.planNo);
  await planStore.loadDestinations(route.params.planNo);
  if (route.params.isArticle === "false") {
    isArticle.value = false;
  } else {
    isArticle.value = true;
  }
});
onUnmounted(() => {
  isArticle.value = false;
});
</script>

<template>
  <div class="position-relative vh-100 vw-100 overflow-hidden">
    <PlanKaKaoMap class="map w-100 h-100" />
    <div class="plan-container p-0 border">
      <PlanPanel />
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
