<script setup>
import PlanInfo from "./PlanInfo.vue";
import PlanMenu from "./PlanMenu.vue";
defineProps({
  plan: {
    type: Object,
    required: true,
  },
  isEditMode: Boolean,
});
</script>
<template>
  <div class="p-4">
    <PlanMenu
      :isEditMode="isEditMode"
      :planNo="plan.planNo"
      @savePlan="$emit('savePlan', $event)"
      @deletePlan="$emit('deletePlan', $event)"
      @updateEditMode="$emit('updateEditMode', $event)"
    ></PlanMenu>
    <PlanInfo
      :plan="plan"
      :isEditMode="isEditMode"
      @updatePlan="$emit('updatePlan', $event)"
      @updateEditMode="$emit('updateEditMode', $event)"
    ></PlanInfo>
  </div>
</template>
<style></style>
