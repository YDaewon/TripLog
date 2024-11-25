<template>
  <div class="search-container">
    <SearchPanel class="search-header" />
    <AttractionList class="search-results" />
    <AttractionDetail />
  </div>
</template>

<script setup>
import SearchPanel from "./SearchPanel.vue";
import AttractionList from "./AttractionList.vue";
import AttractionDetail from "./AttractionDetail.vue";
import { onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useAttractionStore } from "@/stores/attraction";
const attractionStore = useAttractionStore();
const {attractions, selectedAttraction} = storeToRefs(attractionStore);
onMounted(()=>{
  attractions.value = [];
  selectedAttraction.value = {};
})
</script>

<style>
.search-header {
  margin-bottom: 10px;
}

.search-results {
  margin-top: 10px;
  padding: 10px;
  height: calc(100vh - 150px); /* 화면 크기에서 검색창 높이 제외 */
  overflow-y: auto;
}
</style>
