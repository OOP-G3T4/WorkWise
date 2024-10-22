<template>
    <div class="container-fluid p-0 m-0">
        <div class="row justify-content-between align-items-center m-0">
            <!-- Time range buttons -->
            <div class="col-auto p-0 order-1">
                <button v-for="(e_range, idx) in timeRanges" :key="idx" class="btn btn-resp" :class="timeRangeClasses(e_range, idx)" @click="handleTimeRangeClick(e_range)">
                    {{ e_range }}
                </button>
            </div>


            <!-- Date Scroller -->
            <div class="col-12 mt-2 mt-md-0 col-md-auto p-0 d-flex flex-column align-items-center order-3 order-md-2">
                <div class="d-flex align-items-center">
                    <button class="btn btn-resp btn-white rounded-5" @click="shiftDates(false)"><font-awesome-icon icon="fa-solid fa-circle-chevron-left" /></button>
                    <p class="text-secondary m-0 fs-8 fs-sm-6">{{ formattedDate }}</p>
                    <button class="btn btn-resp btn-white rounded-5" @click="shiftDates(true)"><font-awesome-icon icon="fa-solid fa-circle-chevron-right" /></button>
                </div>

                <!-- Today & Zoom Buttons (MD and up) -->
                <div class="mt-2 d-none d-md-block">
                    <button class="btn btn-sm btn-resp btn-light" @click="shiftToday()"><font-awesome-icon icon="fa-solid fa-calendar" /><span class="d-none d-sm-inline ms-2 text-secondary">Skip to Today</span></button>
                    <button class="btn btn-sm btn-resp btn-light ms-3" @click="toggleZoom()"><font-awesome-icon :icon="zoomedOut ? 'fa-solid fa-magnifying-glass-plus' : 'fa-solid fa-magnifying-glass-minus'" /><span class="d-none d-sm-inline ms-2 text-secondary">{{ zoomedOut ? 'Zoom In' : 'Zoom Out' }}</span></button>
                </div>
            </div>


            <!-- Filter & New Job -->
            <div class="col-auto p-0 d-flex order-2 order-md-3">
                <!-- Today & Zoom Buttons (Below MD) -->
                <button class="btn btn-resp btn-light d-md-none me-2" @click="shiftToday()"><font-awesome-icon icon="fa-solid fa-calendar" /></button>
                <button class="btn btn-resp btn-light d-md-none me-2" @click="toggleZoom()"><font-awesome-icon :icon="zoomedOut ? 'fa-solid fa-magnifying-glass-plus' : 'fa-solid fa-magnifying-glass-minus'" /></button>

                <button class="btn btn-resp btn-light"><font-awesome-icon icon="fa-solid fa-filter" /></button>
                <button class="btn btn-resp btn-light ms-2"><font-awesome-icon icon="fa-solid fa-plus" /><span class="ms-2 d-none d-md-inline-block">New Job</span></button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            timeRanges: ['Daily', 'Weekly', 'Monthly'],
            selectedRange: 'Daily',

            currentDate: new Date(),
            zoomedOut: false,
        };
    },
    watch: {
        currentDate() {
            this.$emit('curDateChanged', {
                date: this.currentDate,
                range: this.selectedRange,
            });
        }
    },
    computed: {
        formattedDate() {
            const options = {
                weekday: "short",
                day: "numeric",
                month: "short",
                year: "numeric",
            };

            const date = this.currentDate.toLocaleDateString("en-GB", options);
            const weekday = this.currentDate.toLocaleDateString("en-GB", {
                weekday: "short",
            });

            return date.replace(weekday + ",", "") + ` (${weekday})`;
        },
    },
    methods: {
        shiftDates(isForward) {
            const direction = isForward ? 1 : -1;
            const newDate = new Date(this.currentDate);

            if (this.selectedRange == 'Monthly') {
                // Shift by 1 month
                newDate.setMonth(newDate.getMonth() + direction);
            } else {
                // Shift by numdays
                var numdays = this.selectedRange == 'Daily' ? 1 : 7;

                newDate.setDate(newDate.getDate() + (numdays * direction));
            }

            this.currentDate = newDate;
        },
        shiftToday() {
            this.currentDate = new Date();
        },
        toggleZoom() {
            this.zoomedOut = !this.zoomedOut;

            this.$emit('zoomChanged', { zoomedOut: this.zoomedOut });
        },
        timeRangeClasses(e_range, idx) {
            return {
                'btn-primary': e_range === this.selectedRange,
                'btn-light': e_range !== this.selectedRange,
                'me-2': idx < this.timeRanges.length - 1,
            }
        },
        handleTimeRangeClick(e_range) {
            this.selectedRange = e_range;
        },
    },
    mounted() {
        this.$emit('curDateChanged', {
            date: this.currentDate,
            range: this.selectedRange,
        });
    }
};
</script>
