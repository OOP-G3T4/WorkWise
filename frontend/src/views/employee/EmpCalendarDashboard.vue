<script setup>
import DatePicker from '../../components/employee/calendar/DatePicker.vue';
import DateRangeCalendar from '../../components/employee/calendar/DateRangeCalendar.vue';
</script>

<template>
    <div class="contain-parent p-2 p-sm-4 pb-0 pb-sm-0">
        <div class="contain-top">
            <DatePicker @dateRangeChange="handleDateChange" />
        </div>
    
        <div class="contain-bottom">
            <DateRangeCalendar :isCompressed="isCompressed" :startDate="startDate" :numDays="numDays" />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            startDate: new Date(),
            numDays: 3,
            compressThreshold: 3,
        }
    },
    computed: {
        isCompressed() {
            return this.numDays > this.compressThreshold;
        }
    },
    methods: {
        handleDateChange(data) {
            // Get data
            const startDate = data.startDate;
            const numDays = data.numDays;

            // Set data
            this.startDate = startDate;
            this.numDays = numDays;
        }
    }
}
</script>