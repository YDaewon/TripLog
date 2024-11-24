<!-- CalendarModal.vue -->
<template>
  <div v-if="isOpen" class="modal-backdrop" @click="closeModal">
    <div class="modal-container" @click.stop>
      <div class="modal-header">
        <h3>{{ title }}</h3>
      </div>

      <div class="modal-body">
        <div class="calendar">
          <!-- 월 이동 네비게이션 -->
          <div class="calendar-nav">
            <button @click="prevMonth">&lt;</button>
            <span>{{ currentMonthName }}</span>
            <button @click="nextMonth">&gt;</button>
          </div>

          <!-- 요일 헤더 -->
          <div class="calendar-header">
            <div
              v-for="day in ['일', '월', '화', '수', '목', '금', '토']"
              :key="day"
            >
              {{ day }}
            </div>
          </div>

          <!-- 날짜 그리드 -->
          <div class="calendar-grid">
            <div
              v-for="date in calendarDays"
              :key="date.fullDate"
              class="calendar-day"
              :class="{
                'different-month': !date.isCurrentMonth,
                selected: isDateSelected(date),
                'in-range': isInRange(date),
                'start-date': isStartDate(date),
                'end-date': isEndDate(date),
              }"
              @click="handleDateClick(date)"
            >
              {{ date.day }}
            </div>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn-cancel" @click="closeModal">취소</button>
        <button
          class="btn-confirm"
          @click="confirmSelection"
          :disabled="!startDate || !endDate"
        >
          확인
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true,
  },
  title: {
    type: String,
    default: "날짜 선택",
  },
});

const emit = defineEmits(["close", "select"]);

const currentDate = ref(new Date());
const startDate = ref(null);
const endDate = ref(null);

const currentMonthName = computed(() => {
  return `${currentDate.value.getFullYear()}년 ${
    currentDate.value.getMonth() + 1
  }월`;
});

const calendarDays = computed(() => {
  const year = currentDate.value.getFullYear();
  const month = currentDate.value.getMonth();
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);

  const days = [];

  // 이전 달의 날짜들
  const prevMonthDays = firstDay.getDay();
  const prevMonth = new Date(year, month, 0);
  for (let i = prevMonthDays - 1; i >= 0; i--) {
    days.push({
      day: prevMonth.getDate() - i,
      fullDate: new Date(year, month - 1, prevMonth.getDate() - i),
      isCurrentMonth: false,
    });
  }

  // 현재 달의 날짜들
  for (let i = 1; i <= lastDay.getDate(); i++) {
    days.push({
      day: i,
      fullDate: new Date(year, month, i),
      isCurrentMonth: true,
    });
  }

  // 다음 달의 날짜들
  const remainingDays = 42 - days.length;
  for (let i = 1; i <= remainingDays; i++) {
    days.push({
      day: i,
      fullDate: new Date(year, month + 1, i),
      isCurrentMonth: false,
    });
  }

  return days;
});

const handleDateClick = (date) => {
  if (!startDate.value || (startDate.value && endDate.value)) {
    startDate.value = date.fullDate;
    endDate.value = null;
  } else {
    if (date.fullDate < startDate.value) {
      endDate.value = startDate.value;
      startDate.value = date.fullDate;
    } else {
      endDate.value = date.fullDate;
    }
  }
};

const isDateSelected = (date) => {
  return (
    (startDate.value &&
      date.fullDate.getTime() === startDate.value.getTime()) ||
    (endDate.value && date.fullDate.getTime() === endDate.value.getTime())
  );
};

const isInRange = (date) => {
  if (!startDate.value || !endDate.value) return false;
  return date.fullDate > startDate.value && date.fullDate < endDate.value;
};

const isStartDate = (date) => {
  return (
    startDate.value && date.fullDate.getTime() === startDate.value.getTime()
  );
};

const isEndDate = (date) => {
  return endDate.value && date.fullDate.getTime() === endDate.value.getTime();
};

const prevMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() - 1
  );
};

const nextMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() + 1
  );
};

const confirmSelection = () => {
  if (startDate.value && endDate.value) {
    emit("select", {
      startDate: startDate.value,
      endDate: endDate.value,
    });
  }
  closeModal();
};

const closeModal = () => {
  emit("close");
};
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}

.modal-container {
  background-color: white;
  border-radius: 8px;
  width: 90%;
  max-width: 400px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.calendar {
  width: 100%;
}

.calendar-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.calendar-nav button {
  background: none;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  font-size: 1.2rem;
}

.calendar-header {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  text-align: center;
  font-weight: bold;
  margin-bottom: 10px;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 2px;
}

.calendar-day {
  aspect-ratio: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border-radius: 50%;
  transition: all 0.2s;
}

.calendar-day:hover {
  background-color: #f0f0f0;
}

.different-month {
  color: #ccc;
}

.selected {
  background-color: #6200ee !important;
  color: white !important;
}

.in-range {
  background-color: #e8ddff;
}

.start-date {
  background-color: #6200ee !important;
  color: white !important;
}

.end-date {
  background-color: #6200ee !important;
  color: white !important;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.btn-cancel,
.btn-confirm {
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-cancel {
  border: 1px solid #ddd;
  background-color: white;
}

.btn-confirm {
  background-color: #6200ee;
  color: white;
  border: none;
}

.btn-confirm:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>
