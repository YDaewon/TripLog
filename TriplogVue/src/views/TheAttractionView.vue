<script setup>
import AttractionPanel from "@/components/attractions/AttractionPanel.vue";
import KakaoMap from "@/components/common/KakaoMap.vue";
import { useAttractionStore } from "@/stores/attraction";
import { storeToRefs } from "pinia";
import { onMounted } from "vue";
const attractionStore = useAttractionStore();
const { selectedAttraction, attractions } = storeToRefs(attractionStore);
onMounted(async () => {
  await attractionStore.initData();
});
</script>

<template>
  <div class="map-container">
    <AttractionPanel />
    <KakaoMap
      class="map"
      :attractions="attractions"
      :selectedAttraction="selectedAttraction"
    ></KakaoMap>
    <!-- <VKakaoMap class="map" /> -->
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
