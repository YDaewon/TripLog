<script setup>
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import AttractionPanel from "@/components/attractions/AttractionPanel.vue";
</script>

<template>
  <div class="map-container">
    <AttractionPanel/>
    <VKakaoMap class="map"/>
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
