<script setup>
import { ref, onMounted } from "vue";
import { createPlan } from "@/api/plan";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import PlanCard from "@/components/plan/PlanCard.vue";
import router from "@/router";
import PlanDate from "@/components/plan/PlanDate.vue";
import { usePlanStore } from "@/stores/plan";
const planStore = usePlanStore();
const {plan,plans,tempPlan, isEditMode} = storeToRefs(planStore)

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const isModalOpen = ref(false);
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
  console.log(range);
  newPlan(range);
};

onMounted(() => {
  console.log("onMounted");
  if (userInfo.value && userInfo.value.userNo) {
    planStore.loadPlans(userInfo.value.userNo);
  }
});

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
      isEditMode.value = true;
      router.push({
        name: "planDetail",
        params: { planNo: planNo.data},
      });
    },
    (err) => {
      console.log(err);
    }
  );
};

const onClickPlanCard = (selPlan) => {
  plan.value = selPlan;
  tempPlan.value = selPlan;
  planStore.loadDestinations(plan.value.planNo);
  isEditMode.value = false;
  router.push({
    name: "planDetail",
    params: { planNo: plan.value.planNo },
  });
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
        @click="onClickPlanCard(plan)"
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
