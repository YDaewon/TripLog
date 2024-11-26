<script setup>
import { watch } from "vue";
import DestinationBody from "./DestinationBody.vue";
import DestinationFooter from "./DestinationFooter.vue";
import DestinationHeader from "./DestinationHeader.vue";
const props = defineProps({
  groupDestinations: Object,
  plan: Object,
  isEditMode: Boolean,
});
// watch(
//   () => props.groupDestinations,
//   (newValue) => {
//     if (Object.keys(newValue).length > 0) {
//       console.log("loaded:", newValue);
//     }
//   },
//   { immediate: true, deep: true }
// );
</script>
<template>
  <div class="mb-6 border rounded-lg overflow-hidden shadow-lg bg-white">
    <DestinationHeader
      :groupDestinations="groupDestinations"
      :startAt="plan.startAt"
    ></DestinationHeader>
    <DestinationBody
      :destinations="groupDestinations.destinations"
      :isEditMode="isEditMode"
    ></DestinationBody>
    <DestinationFooter
      :isEditMode="isEditMode"
      :plan="plan"
      :groupDestinations="groupDestinations"
      @createDestination="$emit('createDestination', $event)"
    ></DestinationFooter>
  </div>
</template>
<style></style>
