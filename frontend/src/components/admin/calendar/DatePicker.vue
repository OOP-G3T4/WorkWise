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
                <div class="d-flex justify-content-between align-items-center w-100">
                    <button class="btn btn-resp btn-white rounded-5" @click="shiftDates(false)"><font-awesome-icon icon="fa-solid fa-circle-chevron-left" /></button>
                    <p class="text-secondary m-0 fs-8 fs-sm-6">{{ formattedDate }}</p>
                    <button class="btn btn-resp btn-white rounded-5" @click="shiftDates(true)"><font-awesome-icon icon="fa-solid fa-circle-chevron-right" /></button>
                </div>

                <!-- Today & Zoom Buttons (MD and up) -->
                <div class="mt-2 d-none d-md-block">
                    <button class="btn btn-sm btn-resp btn-primary" @click="shiftToday()" :disabled="isToday"><font-awesome-icon icon="fa-solid fa-calendar" /><span class="d-none d-sm-inline ms-2">Skip to Today</span></button>
                    <button v-if="selectedRange == 'Daily'" class="btn btn-sm btn-resp btn-light ms-3" @click="toggleZoom()"><font-awesome-icon :icon="zoomedOut ? 'fa-solid fa-magnifying-glass-plus' : 'fa-solid fa-magnifying-glass-minus'" /><span class="d-none d-sm-inline ms-2">{{ zoomedOut ? 'Zoom In' : 'Zoom Out' }}</span></button>
                </div>
            </div>


            <!-- Filter & New Job -->
            <div class="col-auto p-0 d-flex order-2 order-md-3">
                <!-- Today & Zoom Buttons (Below MD) -->
                <button class="btn btn-resp btn-primary d-md-none me-2" @click="shiftToday()" :disabled="isToday"><font-awesome-icon icon="fa-solid fa-calendar" /></button>
                <button class="btn btn-resp btn-light d-md-none me-2" @click="toggleZoom()"><font-awesome-icon :icon="zoomedOut ? 'fa-solid fa-magnifying-glass-plus' : 'fa-solid fa-magnifying-glass-minus'" /></button>

                <button class="btn btn-resp btn-light"><font-awesome-icon icon="fa-solid fa-filter" /></button>
                <button class="btn btn-resp btn-light ms-2"><font-awesome-icon icon="fa-solid fa-plus" /><span class="ms-2 d-none d-md-inline-block">New Job</span></button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        dateSelected: {
            type: Date,
            required: false,
        },
        rangeSelected: {
            type: String,
            required: false,
        },
    },
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
        },
        selectedRange() {
            this.$emit('curDateChanged', {
                date: this.currentDate,
                range: this.selectedRange,
            });
        },
        dateSelected() {
            if (this.dateSelected instanceof Date) {
                this.currentDate = this.dateSelected;
            }
        },
        rangeSelected() {
            if (this.timeRanges.includes(this.rangeSelected)) {
                this.selectedRange = this.rangeSelected;
            }
        }
    },
    computed: {
        formattedDate() {
            const options = {
                day: "numeric",
                month: "short",
                year: "numeric",
            };

            const date = this.currentDate.toLocaleDateString("en-GB", options);
            const weekday = this.currentDate.toLocaleDateString("en-GB", {
                weekday: "short",
            });
            const month = this.currentDate.toLocaleDateString("en-GB", {
                month: "short",
            });

            if (this.selectedRange == 'Monthly') {
                return month + " " + this.currentDate.getFullYear();
            }
            
            if (this.selectedRange == 'Daily') {
                return `${date} (${weekday})`;
            }

            // If weekly, start = sunday, end = saturday
            if (this.selectedRange == 'Weekly') {
                options.year = "2-digit";

                const start = new Date(this.currentDate);
                const end = new Date(this.currentDate);

                start.setDate(start.getDate() - start.getDay());
                end.setDate(end.getDate() + (6 - end.getDay()));

                return start.toLocaleDateString("en-GB", options) + " - " + end.toLocaleDateString("en-GB", options);
            }
        },
        isToday() {
            const today = new Date();

            if (this.selectedRange == 'Daily') {
                return this.currentDate.toDateString() === today.toDateString();
            }

            const todayMonth = today.getMonth();

            if (this.selectedRange == 'Monthly') {
                return this.currentDate.getMonth() === todayMonth && this.currentDate.getFullYear() === today.getFullYear();
            }

            if (this.selectedRange == 'Weekly') {
                const start = new Date(this.currentDate);
                const end = new Date(this.currentDate);

                start.setDate(start.getDate() - start.getDay());
                end.setDate(end.getDate() + (6 - end.getDay()));

                return start <= today && today <= end;
            }
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
        if (this.dateSelected instanceof Date) {
            this.currentDate = this.dateSelected;
        }

        if (this.timeRanges.includes(this.rangeSelected)) {
            this.selectedRange = this.rangeSelected;
        }

        this.$emit('curDateChanged', {
            date: this.currentDate,
            range: this.selectedRange,
        });
    }
};
</script>
