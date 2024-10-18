<script setup>
import DatePicker from "../../components/admin/calendar/DatePicker.vue";
import DailyCalendar from "../../components/admin/calendar/DailyCalendar.vue";
</script>

<template>
    <div class="contain-parent">
        <div class="contain-top">
            <DatePicker @zoomChanged="adjustZoom" />
        </div>

        <div class="contain-bottom">
            <DailyCalendar :isCompressed="isCompressed" />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            isCompressed: false,
        };
    },
    methods: {
        adjustZoom(data) {
            this.isCompressed = data.zoomedOut;
        },
    },
};
</script>