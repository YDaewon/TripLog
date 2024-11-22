<script setup>
import PlanBody from './PlanBody.vue';
import PlanHeader from './PlanHeader.vue';
defineProps({
    plan: Object
});
const onUpdatePlan = (newValue) =>{
    plan.value = newValue;
};
</script>
<template>
    <div class="bg-white shadow-lg rounded-lg overflow-hidden h-100 d-flex flex-column">
    <PlanHeader
    :plan="plan" @updatePlan="onUpdatePlan"
    />
    <PlanBody />
    </div>
</template>
<style></style>