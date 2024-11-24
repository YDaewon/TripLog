<script setup>
import DestinationBody from "./DestinationBody.vue";
import DestinationFooter from "./DestinationFooter.vue";
import DestinationHeader from "./DestinationHeader.vue";
defineProps({
  groupDestinations: Object,
  startAt: String,
  isEditMode: Boolean,
});
</script>
<template>
  <div class="mb-6 border rounded-lg overflow-hidden shadow-lg bg-white">
    <DestinationHeader
      :groupDestinations="groupDestinations"
      :startAt="startAt"
    ></DestinationHeader>
    <DestinationBody
      :destinations="groupDestinations.destinations"
      :isEditMode="isEditMode"
    ></DestinationBody>
    <DestinationFooter :isEditMode="isEditMode"></DestinationFooter>
  </div>
</template>
<style></style>
