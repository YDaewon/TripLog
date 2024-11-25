<script setup>
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";
const planStore = usePlanStore();
const {isEditMode,selectedDestination, tempDestinations, tempCreateDestinations} = storeToRefs(planStore);
const props = defineProps({
  destination: Object,
});
const onClickDestCard=()=>{
  console.log(props.destination);
  selectedDestination.value = props.destination;
}
const deleteDest = ()=>{
  planStore.subTempDestination(props.destination);
}

</script>
<template>
  <div class="border" @click="onClickDestCard">
    <div class="d-flex justify-content-between">
      <div class="p-4">
        <h3 class="fw-semibold h5 mb-2">
          {{ destination.attractionTitle }}
        </h3>
        <p class="text-muted">{{ destination.contentTypeName }}</p>
      </div>
      <div
        class="d-flex flex-column align-items-center"
        v-if="isEditMode"
      >
        <button class="btn" @click="moveUp">△</button>
        <button class="btn btn-outline-danger" @click="deleteDest">삭제</button>
        <button class="btn" @click="moveDown">▽</button>
      </div>
    </div>
  </div>
</template>
<style></style>
