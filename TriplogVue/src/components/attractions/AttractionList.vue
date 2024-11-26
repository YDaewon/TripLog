<template>
  <div>
    <div>
      <AttractionCard
        v-for="attraction in attractions"
        :key="attraction.attraction_no"
        :attraction="attraction"
        @click="onclick(attraction)"
      />
    </div>
  </div>
</template>
<script setup>
import { storeToRefs } from "pinia";
import AttractionCard from "./AttractionCard.vue";
import { useAttractionStore } from "@/stores/attraction";
const attractionStore = useAttractionStore();
const { attractions, selectedAttraction } = storeToRefs(attractionStore);
const onclick = (attraction) => {
  selectedAttraction.value = attraction;
};
</script>
<style></style>
