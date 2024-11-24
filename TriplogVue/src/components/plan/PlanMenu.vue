<script setup>
import { watch } from "vue";

const props = defineProps({
  isEditMode: Boolean,
  planNo: Number,
});
const emit = defineEmits();
const savePlan = (event) => {
  if (props.isEditMode) {
    emit("savePlan", event);
  }
  emit("updateEditMode", !props.isEditMode);
};

const deletePlan = (event) => {
  emit("deletePlan", event);
};
</script>
<template>
  <div class="d-flex justify-content-between mb-3">
    <button
      class="btn"
      @click="$router.push({ name: 'plan', params: { planNo: planNo } })"
    >
      ◀ 돌아가기
    </button>
    <div class="d-flex justify-content-end mb-2">
      <button class="btn" @click="savePlan">
        {{ props.isEditMode ? "저장" : "수정" }}
      </button>
      <button
        class="btn btn-outline"
        @click="deletePlan"
        v-show="props.isEditMode"
      >
        삭제
      </button>
    </div>
  </div>
</template>
<style></style>
