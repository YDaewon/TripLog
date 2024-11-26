<template>
  <div class="search-container">
    <SearchPanel class="search-header" />
    <AttractionList class="search-results" />
  </div>
</template>

<script setup>
import SearchPanel from "./SearchPanel.vue";
import AttractionList from "./AttractionList.vue";
</script>

<style>
.search-header {
  margin-bottom: 10px;
}

.search-results {
  margin-top: 10px;
  padding: 10px;
  overflow-y: auto;
}
</style>
