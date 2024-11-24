<template>
  <div class="col-12 mb-2">
    <div class="card h-100">
      <img
        :src="attraction.firstImage1 || attraction.firstImage2"
        class="card-img-top"
        :alt="attraction.title"
        style="height: 200px; object-fit: cover"
      />
      <div class="card-body">
        <h5 class="card-title">{{ attraction.title }}</h5>
      </div>
      <div class="card-footer text-muted">
        <div class="d-flex justify-content-between">
          <div>
            <small>{{ attraction.addr1 }}</small
            ><br />
            <small class="text-info">{{ attraction.contentTypeName }}</small>
          </div>
          <div>
            <button
              class="btn btn-primary h-100"
              @click="$emit('selectDestination', attraction)"
            >
              추가
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  attraction: {
    type: Object,
    required: true,
  },
  isPlan: Boolean,
});
</script>

<style scoped>
.card {
  transition: transform 0.1s ease-in-out;
}
.card:hover {
  transform: scale(1.03);
}

.card-img-top {
  max-height: 200px;
  object-fit: cover;
}
</style>
