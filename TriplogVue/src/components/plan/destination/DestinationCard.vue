<script setup>
const props = defineProps({
  destination: Object,
  isEditMode: Boolean,
});
const moveUp = () => {};
</script>
<template>
  <div
    class="border rounded p-4 shadow-sm hover:shadow-md transition-shadow duration-200"
  >
    <div class="d-flex justify-content-between">
      <div>
        <h3 class="fw-semibold h5 mb-2">
          {{ destination.attractionTitle }}
        </h3>
        <p class="text-muted">{{ destination.contentTypeName }}</p>
      </div>
      <div class="d-flex flex-column align-items-end" v-if="props.isEditMode">
        <button class="btn" @click="moveUp">△</button>
        <button class="btn" @click="moveDown">▽</button>
      </div>
    </div>
  </div>
</template>
<style></style>
