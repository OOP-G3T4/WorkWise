<script setup>
import DatePicker from "../../components/admin/calendar/DatePicker.vue";
import DailyCalendar from "../../components/admin/calendar/DailyCalendar.vue";
</script>

<template>
    <div class="main-container-dashboard">
        <DatePicker @zoomChanged="adjustZoom" class="datetime-header" />

        <div class="calendar-container">
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


<style scoped>
.main-container-dashboard {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
}

.datetime-header {
    flex: 0 1 1px;
}

.calendar-container {
    flex: 1 1 1px;
    overflow-y: auto;
}

</style>