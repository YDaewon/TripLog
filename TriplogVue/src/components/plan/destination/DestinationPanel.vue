<script setup>
import { storeToRefs } from "pinia";
import DestinationCard from "./DestinationCard.vue";
import { usePlanStore } from "@/stores/plan";
import { onMounted, ref } from "vue";
import AttractionPanel from "@/components/attractions/AttractionPanel.vue";
const planStore = usePlanStore();
const { selectedDestination, isAddMode, isEditMode, selectedDate, plan } =
  storeToRefs(planStore);
const props = defineProps({
  group: Object,
});
const toggleDailyMarker = () => {
  selectedDate.value =
    selectedDate.value === props.group.date ? "" : props.group.date;
  console.log(selectedDate.value);
};
onMounted(() => {
  isAddMode.value = false;
});

const onAddDestination = () => {
  isAddMode.value = true;
  selectedDestination.value = { visitDate: props.group.date, planNo: plan.value.planNo};
};
</script>
<template>
  <div class="border rounded-lg overflow-hidden shadow-lg bg-white">
    <div
      class="bg-light p-3 d-flex align-items-center"
      @click="toggleDailyMarker"
    >
      <h4 class="font-weight-bold">
        {{
          (new Date(props.group.date) - new Date(planStore.tempPlan.startAt)) /
            (1000 * 60 * 60 * 24) +
          1
        }}일차
      </h4>
      <span class="text-secondary ms-2">{{ props.group.date }}</span>
      <button
        class="btn btn-outline-secondary w-40 ms-auto"
        @click="onAddDestination"
        v-show="isEditMode"
      >
        목적지 추가
      </button>
    </div>
    <div class="d-flex flex-column">
      <!-- for로 여러 개 표시 -->
      <DestinationCard
        v-for="destination in group.destinations"
        :destination="destination"
      ></DestinationCard>
    </div>
    <div
      class="bg-light p-4 d-flex justify-content-center align-items-center space-x-4"
      v-if="isEditMode"
    >
      <AttractionPanel v-if="isAddMode" />
    </div>
  </div>
</template>
<style></style>
