<script setup>
import { ref, computed, onMounted } from "vue";
import { getPlans, createPlan } from "@/api/plan";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useArticleStore } from "@/stores/article";
import { usePlanStore } from "@/stores/plan";
import PlanCard from "@/components/plan/PlanCard.vue";
import router from "@/router";
import PlanDate from "@/components/plan/PlanDate.vue";
const planStore = usePlanStore();
const { plans, isEditMode } = storeToRefs(planStore);

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const articleStore = useArticleStore();
const { articleInfo } = storeToRefs(articleStore);

const props = defineProps({
  type: String,
  planNo: Number,
});

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
  newPlan(range);
};

onMounted(() => {
  getUserPlans();
});

// planNo와 일치하는 데이터 필터링
const filteredPlans = computed(() => {
  return plans.value.filter(plan => plan.planNo === props.planNo);
});


const getUserPlans = () => {
  getPlans(
    props.type !== 'viewarticle' ? userInfo.value.userNo : articleInfo.value.userNo,
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
        params: { planNo: planNo.data, isArticle: false },
      });
    },
    (err) => {
      console.log(err);
    }
  );
};


const onClickPlanCard = (plan) => {
  isEditMode.value = false;
  router.push({
    name: "planDetail",
    params: { planNo: plan.planNo, isArticle:false },
  });
};
</script>

<template>
  <h3>내 플랜 목록</h3>
  <div>
    <button class="btn btn-primary" @click="openModal">플랜 만들기</button>
    <div>
      <PlanCard
        v-for="plan in plans"
        :key="plan.planNo"
        :plan="plan"
        :is-selected="plan.planNo === selectedPlanNo"
        @click="onClickPlanCard(plan)"
      />
    </div>
  </div>
  <PlanDate
    :isOpen="isModalOpen"
    @close="closeModal"
    @select="handleDateSelect"
  />
</template>

<style></style>
