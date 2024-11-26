<script setup>
defineProps({
  plan: {
    type: Object,
    reqired: true,
  },
  isSelected: Boolean,
});
</script>
<template>
  <div class="col-12 col-md-6 col-lg-4 m-3">
    <div class="card cursor-pointer shadow-sm transition-shadow hover-shadow-lg">
      <div :class="['plan-card', { 'selected': isSelected }]">
        <div class="card-header">
          <h5 class="card-title">{{ plan.title }}</h5>
          <p class="card-text">{{ plan.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
.plan-card.selected {
  border: 2px solid #007bff;
}
</style>
