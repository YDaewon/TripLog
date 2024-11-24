<script setup>
import DestinationCard from "./DestinationCard.vue";
defineProps({
  destinations: Array,
  isEditMode: Boolean,
});
</script>
<template>
  <div class="p-4 d-flex flex-column">
    <!-- for로 여러 개 표시 -->
    <DestinationCard
      v-for="destination in destinations"
      :destination="destination"
      :isEditMode="isEditMode"
    ></DestinationCard>
  </div>
</template>
<style></style>
