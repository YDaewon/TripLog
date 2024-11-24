<script setup>
import { ref, onMounted } from "vue";
import { getPlans, createPlan } from "@/api/plan";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import PlanCard from "@/components/plan/PlanCard.vue";
import router from "@/router";
import PlanDate from "@/components/plan/PlanDate.vue";

const plans = ref([]);
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const isModalOpen = ref(false);
const selectedRange = ref({
  startDate: null,
  endDate: null,
});
const openModal = () => {
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};

const formatDate = (date) => {
  if (!date) return "";
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const handleDateSelect = (range) => {
  newPlan(range);
};

onMounted(() => {
  getUserPlans();
});

const getUserPlans = () => {
  getPlans(
    { userNo: userInfo.value.userNo },
    ({ data }) => {
      plans.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};

const newPlan = (range) => {
  createPlan(
    {
      title: "제목없음",
      description: "설명없음",
      userNo: userInfo.value.userNo,
      startAt: formatDate(range.startDate),
      endAt: formatDate(range.endDate),
    },
    (planNo) => {
      router.push({
        name: "planDetail",
        params: { planNo: planNo.data, isEditMode: true },
      });
    },
    (err) => {
      console.log(err);
    }
  );
};
</script>

<template>
  <div>
    <h1>내 여행 계획</h1>
    <button class="btn btn-primary" @click="openModal">플랜 만들기</button>
    <div>
      <PlanCard
        v-for="plan in plans"
        :key="plan.planNo"
        :plan="plan"
        @click="
          $router.push({
            name: 'planDetail',
            params: { planNo: plan.planNo, isEditMode: false },
          })
        "
      />
    </div>
    <PlanDate
      :isOpen="isModalOpen"
      @close="closeModal"
      @select="handleDateSelect"
    />
  </div>
</template>

<style></style>
