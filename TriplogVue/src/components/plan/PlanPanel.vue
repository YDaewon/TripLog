<script setup>
import { watch } from "vue";
import PlanBody from "./PlanBody.vue";
import PlanHeader from "./PlanHeader.vue";
const props = defineProps({
  plan: {
    type: Object,
    required: true,
  },
  destinations: Array,
  isEditMode: Boolean,
});

const emit = defineEmits();
</script>
<template>
  <div
    class="bg-white shadow-lg rounded-lg overflow-hidden h-100 d-flex flex-column"
  >
    <PlanHeader
      :plan="plan"
      :isEditMode="isEditMode"
      @updatePlan="$emit('updatePlan', $event)"
      @savePlan="$emit('savePlan', $event)"
      @deletePlan="$emit('deletePlan', $event)"
      @updateEditMode="$emit('updateEditMode', $event)"
    />
    <PlanBody
      :destinations="destinations"
      :plan="plan"
      :isEditMode="isEditMode"
      @updatePlan="$emit('updatePlan', $event)"
      @createDestination="$emit('createDestination', $event)"
    />
  </div>
</template>
<style></style>
