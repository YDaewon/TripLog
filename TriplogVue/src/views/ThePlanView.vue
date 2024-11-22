<script setup>
import { ref, onMounted } from "vue";
import { getPlans } from "@/api/plan";

const selectPlan = ref({});
const plans = ref([]);

onMounted(() => {
  getSidoList();
  getContentTypeList();
});

const getUserPlans = () => {
  getPlans({});
};
</script>

<template>
  <div>
    <h1>내 여행 계획</h1>
  </div>
</template>

<style></style>
