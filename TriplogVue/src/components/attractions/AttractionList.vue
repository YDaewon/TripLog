<template>
    <div>
        <div>
            <AttractionCard
            v-for="attraction in attractions"
            :key="attraction.attraction_no"
            :attraction="attraction"
            @click="viewAttraction(attraction)"
            />
        </div>

    </div>
</template>
<script setup>
import AttractionCard from './AttractionCard.vue';

defineProps({
  attractions: {
    type: Array,
    required: true,
  },
  selectAttraction:{
    type: Object,
    required: true,
  },
});
const emit = defineEmits();

const viewAttraction = (attraction) => {
  emit("updateSelectAttraction", attraction);
};
</script>
<style></style>