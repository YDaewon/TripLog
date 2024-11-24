<template>
  <Bar v-if="isDone" :data="chartData" :options="chartOptions" />
</template>

<script lang="ts" setup>
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js'
import { Bar } from 'vue-chartjs'
import { ref, onMounted } from 'vue'
import { getSidosCount } from '@/api/attraction';

// Chart.js 모듈 등록
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

// 반응형 데이터와 옵션 선언
const chartData = ref({
  labels: ['January', 'February', 'March'], // X축 레이블
  datasets: [
    {
      label: 'sido', // 데이터셋 레이블
      data: [40, 20, 12], // 데이터 값
      backgroundColor: ['#FF6384', // 빨강
        '#36A2EB', // 파랑
        '#FFCD56', // 노랑
        '#4BC0C0', // 청록
        '#9966FF', // 보라
        '#FF9F40', // 주황
        '#E7E9ED', // 회색
        '#5A9BD4', // 하늘색
        '#F45B5B', // 분홍
        '#8085E9', // 연보라
        '#8D4653', // 자주
        '#91E8E1', // 민트
        '#2B908F'  // 녹청색
      ] // 각 데이터의 색상
    }
  ]
})

const chartOptions = ref({
  responsive: true, // 반응형 차트
  plugins: {
    legend: {
      display: true,
      position: 'top' // 범례 위치
    },
    title: {
      display: true,
      text: '전국 시도별 인기 순위', // 차트 제목
      font: {
        size: 24, // 제목 글꼴 크기 (픽셀 단위)
        weight: 'bold', // 글꼴 굵기
        family: 'Arial', // 글꼴 패밀리
      },
    }
  }
})

const isDone = ref(false)

// API 데이터 가져오기
onMounted(() => {
  getSidosCount(
    (response) => {
      if (response.status === 200) {
        const sidoCount = response.data
        console.log(`${sidoCount.length}, ${JSON.stringify(sidoCount)}`)

        // labels와 data에 데이터 설정
        chartData.value.labels = sidoCount.map((item: { sido_name: string }) => item.sido_name)
        chartData.value.datasets[0].data = sidoCount.map((item: { count: number }) => item.count)

        console.log(chartData.value.labels[0])
        isDone.value = true;
      }
    },
    (error) => {
      console.error(error)
    }
  )
})
</script>
