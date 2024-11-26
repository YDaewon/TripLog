<script setup>
import DestinationCard from "./DestinationCard.vue";
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
