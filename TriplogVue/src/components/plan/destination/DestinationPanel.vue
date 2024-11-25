<script setup>
import { storeToRefs } from "pinia";
import DestinationCard from "./DestinationCard.vue";
import DestinationFooter from "./DestinationFooter.vue";
import { usePlanStore } from "@/stores/plan";
import { onMounted } from "vue";
import AttractionPanel from "@/components/attractions/AttractionPanel.vue";
const planStore = usePlanStore();
const { selectedDestination, isAddMode, isEditMode } = storeToRefs(planStore);
const props = defineProps({
  group: Object,
});
onMounted(() => {
  isAddMode.value = false;
});
function getNextDestinationOrder(groupDestinations) {
  if (!groupDestinations || !groupDestinations.destinations) {
    return 1;
  }
  if (groupDestinations.destinations.length === 0) {
    return 1;
  }
  const maxOrder = Math.max(
    ...groupDestinations.destinations.map((dest) => dest.destinationOrder || 0)
  );
  return maxOrder + 1;
}
const onAddDestination = () => {
  isAddMode.value = true;
  selectedDestination.value = { visitDate: props.group.date };
};
</script>
<template>
  <div class="border rounded-lg overflow-hidden shadow-lg bg-white">
    <div class="bg-light p-4 pb-1 d-flex align-items-center">
      <h4 class="font-weight-semibold">
        {{
          (new Date(props.group.date) - new Date(planStore.tempPlan.startAt)) /
            (1000 * 60 * 60 * 24) +
          1
        }}
        일차
      </h4>
      <span class="text-secondary">{{ props.group.date }}</span>
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
      <button class="btn btn-outline-secondary w-40" @click="onAddDestination">
        목적지 추가
      </button>
      <AttractionPanel v-if="isAddMode" />
    </div>
  </div>
</template>
<style></style>
