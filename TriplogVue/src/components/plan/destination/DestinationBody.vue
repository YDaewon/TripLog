<script setup>
import { storeToRefs } from "pinia";
import DestinationCard from "./DestinationCard.vue";
import { usePlanStore } from "@/stores/plan";

defineProps({
  destinations: Array,
});

</script>
<template>
  <div class="d-flex flex-column">
    <!-- for로 여러 개 표시 -->
    <DestinationCard
      v-for="destination in destinations"
      :destination="destination"
    ></DestinationCard>
  </div>
</template>
<style></style>
