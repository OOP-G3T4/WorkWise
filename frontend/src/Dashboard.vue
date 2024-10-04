<script setup>
import JobDetails from "./components/JobDetails.vue";
import Navbar from "./components/Navbar.vue";
import DatePicker from "./components/DatePicker.vue";
</script>

<template>
    <div class="d-flex">
        <Navbar />
        <div class="calendar-container d-flex flex-column">
            <DatePicker />
            <div class="d-flex">
                <JobDetails v-for="(item, value) in testarr" :key="value" />
            </div>
        </div>
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