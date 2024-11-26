<script setup>
const props = defineProps({
  groupDestinations: Object,
  startAt: String,
});
</script>
<template>
  <div class="bg-light p-4 pb-1">
    <h4 class="font-weight-semibold">
      {{
        (new Date(groupDestinations.date) - new Date(startAt)) /
          (1000 * 60 * 60 * 24) +
        1
      }}일차
    </h4>
    <p class="text-secondary">{{ groupDestinations.date }}</p>
  </div>
</template>
<style></style>
