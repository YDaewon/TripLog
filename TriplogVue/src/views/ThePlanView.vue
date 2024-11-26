<script setup>
import { ref, computed, onMounted } from "vue";
import { getPlans, createPlan } from "@/api/plan";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useArticleStore } from "@/stores/article";
import PlanCard from "@/components/plan/PlanCard.vue";
import router from "@/router";
import PlanDate from "@/components/plan/PlanDate.vue";

const plans = ref([]);
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const articleStore = useArticleStore();
const { articleInfo } = storeToRefs(articleStore);

const props = defineProps({
  type: String,
  planNo: Number,
});

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
        params: { planNo: planNo.data, isEditMode: true },
      });
    },
    (err) => {
      console.log(err);
    }
  );
};

const selectedPlanNo = ref(0);

function handlePlanClick(plan){
  selectedPlanNo.value = plan.planNo;
}

</script>

<template>
  <h3 v-if="type === 'mkarticle' && plans.length > 0">등록할 플랜 선택</h3>
  <h3 v-if="type === 'mkarticle' && plans.length === 0">등록된 플랜이 없어요...</h3>
  <h3 v-if="type === 'viewarticle'">Plan</h3>
  <h3 v-if="type !== 'mkarticle' && type !== 'viewarticle'">내 플랜 목록</h3>
  <div v-if="type !== 'viewarticle'">
    <button v-if="type === 'plan'" class="btn btn-primary" @click="openModal">플랜 만들기</button>
    <div>
      <PlanCard
        v-for="plan in plans"
        :key="plan.planNo"
        :plan="plan"
        :is-selected="plan.planNo === selectedPlanNo"
        @click= "type !== 'mkarticle' && type !== 'viewarticle' ? $router.push({name: 'planDetail', params: { planNo: plan.planNo, isEditMode: false },}) : handlePlanClick(plan)"
      />
    </div>
  </div>
  <div v-else>
    <div>
      <PlanCard
        v-for="plan in filteredPlans"
        :key="plan.planNo"
        :plan="plan"
        :is-selected= true
        @click= "$router.push({name: 'planDetail', params: { planNo: plan.planNo, isEditMode: false },})"
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
