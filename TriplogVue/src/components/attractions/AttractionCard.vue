<template>
  <div class="col-12 mb-2">
    <div class="card h-100">
      <div
        class="card-img-top-container"
        style="height: 200px; position: relative"
      >
        <template v-if="attraction.firstImage1 || attraction.firstImage2">
          <img
            :src="attraction.firstImage1 || attraction.firstImage2"
            class="card-img-top"
            :alt="attraction.title"
            style="width: 100%; height: 100%; object-fit: cover"
          />
        </template>
        <template v-else>
          <div class="no-image-text">사진없음</div>
        </template>
      </div>
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
              v-if="planStore.isAddMode"
              class="btn btn-primary h-100"
              @click="attToDest(attraction)"
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
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";
const planStore = usePlanStore();
const { selectedDestination, isAddMode } =
  storeToRefs(planStore);

const attToDest = (attraction) => {
  selectedDestination.value = { ...selectedDestination.value, attractionNo: attraction.attractionNo, };
  planStore.addDestination(attraction.attractionNo);

  isAddMode.value = false;
};

defineProps({
  attraction: {
    type: Object,
    required: true,
  },
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

.card-img-top-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f0f0; /* 이미지가 없는 경우의 배경색 */
}

.no-image-text {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  color: #aaa; /* 텍스트 색상 */
  text-align: center;
}
</style>
