<script setup>
import DestinationPanel from "./destination/DestinationPanel.vue";
import { computed, onMounted, watch } from "vue";
const props = defineProps({
  plan: Object,
  destinations: Array,
  isEditMode: Boolean,
});

// 날짜별 destination 그룹화
const groupedDestinations = computed(() => {
  return props.destinations.reduce((groups, destination) => {
    // 기존 그룹에서 같은 날짜를 찾음
    const existingGroup = groups.find(
      (group) => group.date === destination.visitDate
    );

    if (existingGroup) {
      // 기존 그룹이 있으면 destinations 배열에 추가
      existingGroup.destinations.push(destination);
      // destinationOrder로 정렬
      existingGroup.destinations.sort(
        (a, b) => a.destinationOrder - b.destinationOrder
      );
    } else {
      // 새로운 그룹 생성
      groups.push({
        date: destination.visitDate,
        destinations: [destination],
      });
    }

    // 날짜순으로 그룹 정렬
    return groups.sort((a, b) => new Date(a.date) - new Date(b.date));
  }, []);
});
</script>
<template>
  <div class="flex-fill overflow-auto p-4">
    <DestinationPanel
      v-for="groupDestinations in groupedDestinations"
      :groupDestinations="groupDestinations"
      :plan="plan"
      :isEditMode="isEditMode"
      @createDestination="$emit('createDestination', $event)"
    ></DestinationPanel>
  </div>
</template>
<style></style>
