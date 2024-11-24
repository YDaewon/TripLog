<script setup>
import { computed, ref, watchEffect } from "vue";
import PlanDate from "./PlanDate.vue";

const props = defineProps({
  plan: {
    type: Object,
    required: true,
  },
  isEditMode: Boolean,
});

const emit = defineEmits();

const editablePlan = ref({
  title: props.plan.title,
  description: props.plan.description,
  startAt: props.plan.startAt,
  endAt: props.plan.endAt,
});

// Watch for changes in props to update editablePlan
watchEffect(() => {
  editablePlan.value = { ...props.plan };
});

// Function to save changes and emit the updated plan
const saveChanges = () => {
  emit("updatePlan", editablePlan.value);
};

const nights = computed(() => {
  return (
    (new Date(props.plan.endAt) - new Date(props.plan.startAt)) /
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
  props.plan.startAt = formatDate(range.startDate);
  props.plan.endAt = formatDate(range.endDate);
  emit("updatePlan", props.plan);
};

// ===============test=====================
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
          v-if="showTitleEdit && isEditMode"
          v-model="editablePlan.title"
          class="form-control"
          @blur="showTitleEdit = false"
          @focusout="saveChanges"
          ref="titleInput"
          @keyup.enter="$event.target.blur()"
        />
        <span v-else>{{ plan.title }}</span>
      </h1>
      <!-- 설명 -->
      <p
        @click="isEditMode && (showDescEdit = true)"
        :class="{ 'edit-mode': isEditMode }"
      >
        <textarea
          v-if="showDescEdit && isEditMode"
          v-model="editablePlan.description"
          class="form-control"
          @blur="showDescEdit = false"
          @focusout="saveChanges"
          ref="descInput"
        ></textarea>
        <span v-else>{{ plan.description }}</span>
      </p>
      <!-- 기간 -->
      <p
        class="text-secondary"
        v-on="isEditMode ? { click: openModal } : {}"
        :class="{ 'edit-mode': isEditMode }"
      >
        {{ plan.startAt }} ~ {{ plan.endAt }} ({{ nights }}박
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
