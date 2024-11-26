<script setup>
import { computed, ref } from "vue";
import PlanDate from "./PlanDate.vue";
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";
const planStore = usePlanStore();
const { tempPlan } = storeToRefs(planStore);

console.log();
const nights = computed(() => {
  return (
    (new Date(tempPlan.value.endAt) - new Date(tempPlan.value.startAt)) /
    (1000 * 60 * 60 * 24)
  );
});

const isModalOpen = ref(false);

const openModal = () => {
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};

const formatDate = (date) => {
  if (!date) return "";
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const handleDateSelect = (range) => {
  tempPlan.value.startAt = formatDate(range.startDate);
  tempPlan.value.endAt = formatDate(range.endDate);
  planStore.adjustDates();
};

const showTitleEdit = ref(false);
const showDescEdit = ref(false);
const titleInput = ref(null);
const descInput = ref(null);
</script>

<template>
  <div class="">
    <div>
      <!-- 제목 -->
      <h1
        class="h1 font-weight-bold"
        :class="{ 'edit-mode': isEditMode }"
        @click="isEditMode && (showTitleEdit = true)"
      >
        <input
          v-if="showTitleEdit && isEditMode.value"
          v-model="tempPlan.title"
          class="form-control"
          @blur="showTitleEdit = false"
          ref="titleInput"
          @keyup.enter="$event.target.blur()"
        />
        <span v-else>{{ tempPlan.title }}</span>
      </h1>
      <!-- 설명 -->
      <p
        @click="isEditMode.value && (showDescEdit = true)"
        :class="{ 'edit-mode': isEditMode }"
      >
        <textarea
          v-if="showDescEdit && isEditMode.value"
          v-model="tempPlan.description"
          class="form-control"
          @blur="showDescEdit = false"
          ref="descInput"
        ></textarea>
        <span v-else>{{ tempPlan.description }}</span>
      </p>
      <!-- 기간 -->
      <p
        class="text-secondary"
        v-on="planStore.isEditMode ? { click: openModal } : {}"
        :class="{ 'edit-mode': planStore.isEditMode }"
      >
        {{ tempPlan.startAt }} ~ {{ tempPlan.endAt }} ({{ nights }}박
        {{ nights + 1 }}일)
      </p>
      <!-- 달력 모달 -->
      <PlanDate
        :isOpen="isModalOpen"
        @close="closeModal"
        @select="handleDateSelect"
      />
    </div>
  </div>
</template>

<style scoped>
/* Optional: Add some styles for input elements */
input,
textarea {
  width: 100%;
  margin-top: 8px;
}
.edit-mode {
  cursor: pointer;
}
</style>
