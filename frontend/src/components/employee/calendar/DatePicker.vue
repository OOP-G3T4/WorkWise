<template>
    <div class="container-fluid p-0 m-0">
        <div class="row justify-content-between align-items-center m-0">
            <!-- Time range buttons -->
            <div class="col-auto p-0">
                <button v-for="(e_range, idx) in Object.keys(timeRanges)" class="btn btn-sm py-0 px-2 rounded-3" :class="timeRangeClasses(e_range, idx)" @click="handleTimeRangeClick(e_range)"><span class="fs-9 fs-md-7">{{ e_range }}</span></button>
            </div>

            <!-- Date Scroller -->
            <div class="col-auto p-0 d-flex align-items-center">
                <button class="btn btn-sm btn-white rounded-5" @click="shiftDates(false)"><font-awesome-icon icon="fa-solid fa-circle-chevron-left" /></button>
                <p class="text-secondary m-0 fs-8 fs-sm-6">{{ curMonth }}</p>
                <button class="btn btn-sm btn-white rounded-5" @click="shiftDates(true)"><font-awesome-icon icon="fa-solid fa-circle-chevron-right" /></button>
            </div>

            <!-- Go to today button -->
            <div class="col-auto p-0 d-flex">
                <btn class="btn btn-sm btn-light" @click="shiftToday()"><span class="fs-9 fs-md-7"><font-awesome-icon icon="fa-solid fa-calendar" /><span class="d-none d-sm-inline ms-2 text-secondary">Skip to Today</span></span></btn>
            </div>

        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // Time range buttons
            timeRanges: {
                '3 Day': 3,
                'Weekly': 7,
            },
            selectedTimeRange: '3 Day',

            // Date variables
            startDate: new Date(),
        }
    },
    watch: {
        startDate() {
            this.emitDateRangeChange();
        },
        selectedTimeRange() {
            this.emitDateRangeChange();
        }
    },
    computed: {
        curMonth() {
            return this.startDate.toLocaleString('default', { month: 'short' }) + " " + this.startDate.getFullYear();
        }
    },
    methods: {
        timeRangeClasses(e_range, idx) {
            return {
                'btn-primary': e_range === this.selectedTimeRange,
                'btn-light': e_range !== this.selectedTimeRange,
                'me-2': idx < Object.keys(this.timeRanges).length - 1,
            }
        },
        handleTimeRangeClick(e_range) {
            this.selectedTimeRange = e_range;
        },
        shiftDates(forward) {
            const num_days = this.timeRanges[this.selectedTimeRange];
            const move = forward ? num_days : -num_days;

            var tempDate = new Date(this.startDate);
            tempDate.setDate(tempDate.getDate() + move);

            this.startDate = tempDate;
        },
        shiftToday() {
            this.startDate = new Date();
        },
        emitDateRangeChange() {
            this.$emit('dateRangeChange', {
                startDate: this.startDate,
                numDays: this.timeRanges[this.selectedTimeRange],
            });
        }
    },
    mounted() {
        this.emitDateRangeChange();
    }
}
</script>