<template>
  <div class="col-12 m-0 p-0">
    <div class="card">
      <img
        :src="
          attractionStore.selectedAttraction.firstImage1 ||
          attractionStore.selectedAttraction.firstImage2
        "
        class="card-img-top"
        :alt="attractionStore.selectedAttraction.title"
        style="height: 200px; object-fit: cover"
      />
      <div class="card-body">
        <h5 class="card-title">
          {{ attractionStore.selectedAttraction.title }}
          <small class="text-info d-inline ml-2" style="font-size: 0.85rem">{{
            attractionStore.selectedAttraction.contentTypeName
          }}</small>
        </h5>
        <small>{{ attractionStore.selectedAttraction.addr1 }}</small>
        <hr />
        <small class="card-text"
          >설명{{ attractionStore.selectedAttraction.overview }}</small
        >
      </div>
      <div class="card-footer text-muted"></div>
    </div>
  </div>
</template>

<script setup>
import { useAttractionStore } from "@/stores/attraction";
const attractionStore = useAttractionStore();
</script>

<style scoped></style>
