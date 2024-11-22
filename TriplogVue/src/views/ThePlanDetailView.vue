<script setup>
  import VKakaoMap from "@/components/common/VKakaoMap.vue";
  import PlanPanel from "@/components/plan/PlanPanel.vue";
  import { ref, onMounted } from "vue";
  const attractions = ref([]);
  const selectAttraction = ref({});
  const plan = ref([]);

</script>

<template>
  <div class="position-relative vh-100 vw-100 overflow-hidden">
    <VKakaoMap
      class="map w-100 h-100"
      :attractions="attractions"
      :selectAttraction="selectAttraction"
    />
    <div class="plan-container">
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
