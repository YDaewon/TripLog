<template>
  <div class="col-12 m-0 p-0">
    <div class="card">
      <img
            :src="selectAttraction.firstImage1 || selectAttraction.firstImage2"
            class="card-img-top"
            :alt="selectAttraction.title"
            style="height: 200px; object-fit: cover;"
        />
      <div class="card-body">
        <h5 class="card-title">
          {{ selectAttraction.title }}
          <small class="text-info d-inline ml-2 " style="font-size: 0.85rem;">{{ selectAttraction.contentTypeName }}</small>
        </h5>
        <small>{{ selectAttraction.addr1 }}</small>
        <hr/>
        <small class="card-text">설명{{ selectAttraction.overview }}</small>
      </div>
      <div class="card-footer text-muted">
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  selectAttraction: {
    type: Object,
    required: true,
  },
});
</script>

<style scoped></style>
