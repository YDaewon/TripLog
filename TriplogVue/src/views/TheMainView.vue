<template>
  <div class="text-center my-4">
    <h1>Trip Log</h1>
    <p>플랜을 쉽고 간단하게 만들어 줍니다.
      플랜을 간편하게 관리하고, 게시판에서 플랜 및 사진을 공유하세요!</p>
  </div>

  <!-- Cards -->
  <div class="row justify-content-center">
    <div class="col-md-3" v-for="(card, index) in cards" :key="index">
      <div
        class="card mb-4"
        data-bs-toggle="modal"
        :data-bs-target="'#' + card.id"
        style="cursor: pointer; transition: transform 0.3s ease-in-out;">
        <img :src="card.img" class="card-img-top" :alt="card.alt" style="width:100%; height:200px">
        <div class="card-body">
          <p class="card-text">{{ card.text }}</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Modals -->
  <div
    class="modal fade"
    v-for="(modal, index) in modals"
    :key="index"
    :id="modal.id"
    tabindex="-1"
    :aria-labelledby="modal.labelId"
    aria-hidden="true">
    <div class="modal-dialog modal-xl custom-modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" :id="modal.labelId">{{ modal.title }}</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <img :src="modal.img" class="img-fluid mb-3" :alt="modal.title">
          <p>{{ modal.body }}</p>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center my-4">
    <div class="chart-container">
      <Chart v-show="shouldRenderChart" />
    </div>
  </div>
</template>

<style scoped>
.chart-container {
  width: 100%;
  height: auto;
  max-width: 1200px;
}

.chart-container > * {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.card:hover {
  transform: scale(1.05);
}
</style>

<script setup>
import Chart from '@/components/layout/mkChart.vue';
import { ref, onMounted, onUnmounted } from 'vue';

const cards = ref([
  { id: 'modal1', img: '/src/assets/make_plan.png', alt: '설명', text: '플랜 작성' },
  { id: 'modal2', img: '/src/assets/attraction_list.png', alt: '설명', text: '여행지 검색' },
  { id: 'modal3', img: '/src/assets/board.png', alt: '설명', text: '게시판 공유' }
]);

const modals = ref([
  { id: 'modal1', labelId: 'modalLabel1', title: '플랜 작성', body: '나만의 플랜을 간편하게 골라 작성하세요!', img: '/src/assets/make_plan.png' },
  { id: 'modal2', labelId: 'modalLabel2', title: '여행지 검색', body: '가고 싶은 여행지를 검색해서 자세한 정보를 확인하세요!', img: '/src/assets/attraction_list.png' },
  { id: 'modal3', labelId: 'modalLabel3', title: '게시판 공유', body: '게시판을 이용해 플랜 후기나 공유를 받을 수 있어요', img: '/src/assets/board.png' }
]);
const shouldRenderChart = ref(true);


function handleResize() {
  shouldRenderChart.value = false; // Chart 컴포넌트 제거
  setTimeout(() => {
    shouldRenderChart.value = true; // Chart 컴포넌트를 다시 렌더링
  }, 0); // 다음 렌더링 사이클에서 다시 렌더링
}

onMounted(() => {
  window.addEventListener("resize", handleResize);
});

// `resize` 이벤트 해제
onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});
</script>
