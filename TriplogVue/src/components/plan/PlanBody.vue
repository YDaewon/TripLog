<script setup>
import { storeToRefs } from "pinia";
import DestinationPanel from "./destination/DestinationPanel.vue";
import { usePlanStore } from "@/stores/plan";
const planStore = usePlanStore();
const { groupedDestinations, selectedDate } = storeToRefs(planStore);
</script>
<template>
  <div class="flex-fill overflow-auto p-4">
    <DestinationPanel
      class="mb-4"
      v-for="group in groupedDestinations"
      :key="group.date"
      :group="group"
    ></DestinationPanel>
  </div>
</template>

<style></style>
