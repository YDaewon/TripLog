<template>
  <div class="search-container">
    <SearchPanel
      class="search-header"
      :attractions="attractions"
      :showAttractionDetail="showAttractionDetail"
      @updateShowAttractionDetail="onUpdateShowAttractionDetail"
      @updateAttractions="$emit('updateAttractions', $event)"
    />

    <AttractionList
      class="search-results"
      v-show="!showAttractionDetail"
      :attractions="attractions"
      :selectAttraction="selectAttraction"
      @updateSelectAttraction="$emit('updateSelectAttraction', $event)"
    />

    <AttractionDetail :selectAttraction="selectAttraction" />
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue";
import SearchPanel from "./SearchPanel.vue";
import AttractionList from "./AttractionList.vue";
import AttractionDetail from "./AttractionDetail.vue";

const props = defineProps({
  attractions: {
    type: Array,
    required: true,
  },
  selectAttraction: {
    type: Object,
    required: true,
  },
});
const showAttractionDetail = ref(false);
const emit = defineEmits();

const onUpdateShowAttractionDetail = (newValue) => {
  showAttractionDetail.value = newValue;
};
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
