<script setup>
import JobDetails from "./components/JobDetails.vue";
</script>

<template>
    <div class="d-flex">
        <JobDetails v-for="(item, value) in testarr" :key="value" />
    </div>
</template>

<script>
export default {
    data() {
        return {
            testarr: [1, 2, 3, 4, 5],
        };
    },
};
</script>
