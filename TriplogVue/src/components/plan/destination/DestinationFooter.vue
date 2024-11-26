<script setup>
import { createDestination } from "@/api/destination";
import AttractionPanel from "@/components/attractions/AttractionPanel.vue";
import { ref, watch } from "vue";

const props = defineProps({
  groupDestinations: Object,
  plan: Object,
  isEditMode: Boolean,
});

console.log(props.plan);

const emit = defineEmits();
const isAddMode = ref(false);
const onClickShowAttractionPanel = () => {
  isAddMode.value = true;
};

const attractions = ref([]);
const selectAttraction = ref({});
const onUpdateAttractions = (newValue) => {
  attractions.value = newValue;
  console.log("updatedAttractions: ", attractions);
};

const onSelectDestination = (attraction) => {
  isAddMode.value = false;
  console.log("selected attraction :", attraction.attractionNo);
  newDestination(attraction);
};
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
const newDestination = (attraction) => {
  const temp = {
    attractionNo: attraction.attractionNo,
    planNo: props.plan.planNo,
    visitDate: props.groupDestinations.date,
    destinationOrder: getNextDestinationOrder(props.groupDestinations),
  };
  console.log("newDest(temp): ", temp);
  emit("createDestination", temp);
};
</script>
<template>
  <div
    class="bg-light p-4 d-flex justify-content-center align-items-center space-x-4"
    v-if="isEditMode"
  >
    <button
      class="btn btn-outline-secondary w-40"
      @click="onClickShowAttractionPanel"
    >
      목적지 추가
    </button>
    <button class="btn btn-outline-secondary w-40" @click="addMemo">
      메모 추가
    </button>
    <AttractionPanel
      v-if="isAddMode"
      :attractions="attractions"
      :selectAttraction="selectAttraction"
      :isPlan="isPlan"
      @updateAttractions="onUpdateAttractions"
      @selectDestination="onSelectDestination"
    />
  </div>
</template>
<style></style>
