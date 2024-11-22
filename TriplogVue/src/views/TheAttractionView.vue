<script setup>
import { ref } from "vue";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import AttractionPanel from "@/components/attractions/AttractionPanel.vue";

const attractions = ref([]);
const selectAttraction = ref({});

const onUpdateAttractions = (newValue) =>{
  attractions.value = newValue;
}
const onUpdateSelectAttractions = (newValue) =>{
  selectAttraction.value = newValue;
}

</script>

<template>
  <div class="map-container">
    <AttractionPanel
    :attractions="attractions"
    :selectAttraction="selectAttraction"
    @updateAttractions="onUpdateAttractions"
    @updateSelectAttraction="onUpdateSelectAttractions"
    />

    <VKakaoMap
      class="map"
      :attractions="attractions"
      :selectAttraction="selectAttraction"
    />
  </div>
</template>

<style scoped>
.map-container {
  position: relative;
  width: 100%;
  height: 100vh;
}
.map {
  height: 100vh;
}
</style>
