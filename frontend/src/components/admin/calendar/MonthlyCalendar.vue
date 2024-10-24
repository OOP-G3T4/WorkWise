<template>
    <div class="container-fluid px-4 pb-2 d-flex flex-column w-100 h-100">
        <!-- Display days of week (Mon, Tue, ..., Sun) -->
        <div class="row">
            <div v-for="eDay in allDaysInWk" class="date-block">
                <p class="text-center">{{ eDay }}</p>
            </div>
        </div>

        <!-- Display date rows and columns -->
        <div v-for="eRow in datesMaster" class="row flex-grow-1">
            <div v-for="eDate in eRow" class="date-block p-2">
                <div class="rounded-4 h-100 w-100 py-2 px-3 container-fluid d-flex flex-column" :class="eDate == null ? 'bg-light' : 'cursor-pointer'" :style="dateBlockStyles(getNumJobs(eDate))" @click="navigateToDate(eDate)">
                    <template v-if="eDate != null">
                        <!-- Show day of month -->
                        <div class="row px-1">
                            <div class="col-auto d-flex justify-content-center align-items-center px-2 rounded-5" :class="isToday(eDate) ? 'bg-primary-dark text-white' : 'bg-white text-dark'">
                                <p class="d-inline-block m-0">{{ eDate.getDate() }}</p>
                            </div>
                        </div>

                        <!-- Show num jobs -->
                        <div class="row flex-grow-1 align-items-center">
                            <div class="col-12">
                                <h4 class="text-center">{{ getNumJobs(eDate) }}</h4>
                            </div>
                        </div>
                    </template>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        jobDetails: {
            type: Object,
            required: true,
        },
        dateSelected: {
            type: Date,
            required: true,
        },
    },
    data() {
        return {
            allDaysInWk: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            today: new Date(),
            minJobs: 0,
            maxJobs: 0,

            // Color settings
            zeroJobsLightness: 96,
            minJobsLightness: 90,
            maxJobsLightness: 44,
            baseColorH: 200,
            baseColorS: 62,
            textWhiteThreshold: 50,
        }
    },
    methods: {
        getNumJobs(dateObj) {
            // Return -1 if dateObj is null
            if (dateObj == null) {
                return -1;
            }

            // Get the number of jobs on the given date
            const monthKey = (dateObj.getMonth()+1) + "-" + dateObj.getFullYear();
            const dayKey = dateObj.getDate();

            if (!(monthKey in this.jobDetails)) {
                return 0;
            }

            if (!(dayKey in this.jobDetails[monthKey])) {
                return 0;
            }
            
            return this.jobDetails[monthKey][dayKey].length;
        },
        isToday(dateObj) {
            // Check if the given date is today
            return dateObj.getDate() == this.today.getDate() && dateObj.getMonth() == this.today.getMonth() && dateObj.getFullYear() == this.today.getFullYear();
        },
        updateMaxJobs() {
            // Update the max number of jobs
            var max = 0;

            // Break if dateSelected is null
            if (this.dateSelected == null) {
                return;
            }

            const monthKey = (this.dateSelected.getMonth()+1) + "-" + this.dateSelected.getFullYear();

            for (var dayKey in this.jobDetails[monthKey]) {
                const numJobs = this.jobDetails[monthKey][dayKey].length;
                max = Math.max(max, numJobs);
            }

            this.maxJobs = max;
            return;
        },
        dateBlockStyles(numJobs) {
            // Use HSL color to represent the number of jobs
            if (numJobs == -1) {
                return {};
            }

            if (numJobs == 0) {
                return {
                    backgroundColor: `hsl(${this.baseColorH}, ${this.baseColorS}%, ${this.zeroJobsLightness}%)`,
                };
            }

            // percentageJobs is the percentage of jobs between minJobs and maxJobs
            const diffJobs = this.maxJobs - this.minJobs;
            const percentageJobs = (numJobs - this.minJobs) / diffJobs;
            const lightnessToSubtract = percentageJobs * (this.minJobsLightness - this.maxJobsLightness);
            const lightness = this.minJobsLightness - lightnessToSubtract;
            const textColor = lightness > this.textWhiteThreshold ? 'black' : 'white';

            return {
                backgroundColor: `hsl(${this.baseColorH}, ${this.baseColorS}%, ${lightness}%)`,
                color: textColor,
            };
        },
        navigateToDate(dateObj) {
            // Emit the dateSelected event
            if (dateObj == null) {
                return;
            }

            this.$emit('navToDate', dateObj);
        },
    },
    computed: {
        datesMaster() {
            // Fill with Date() objects for each day in the month. Starts from the Sunday of the first week of the month.
            // If the first day of the month is not a Sunday, fill the previous month's dates with nulls.
            // If the last day of the month is not a Saturday, fill the next month's dates with nulls.

            // Get the first day of the month
            const firstDay = new Date(this.dateSelected.getFullYear(), this.dateSelected.getMonth(), 1);
            const lastDay = new Date(this.dateSelected.getFullYear(), this.dateSelected.getMonth() + 1, 0);

            // Get the day of the week of the first day of the month (Sun=0, Mon=1, ..., Sat=6)
            const firstDayOfWeek = firstDay.getDay();

            // Get the number of days in the month
            const numDays = lastDay.getDate();

            var res = [];

            // Create first row (with NULLs for days before the first day of the month)
            res.push([...Array(firstDayOfWeek).fill(null)]);

            // Initialize the date to the first day of the month
            var curDate = new Date(firstDay);

            // Fill in the rest of the dates
            for (var i = 0; i < numDays; i++) {
                // Add the current date to the current row
                res[res.length - 1].push(new Date(curDate));
                
                // If the current date is a Saturday, add a new row
                if (curDate.getDay() == 6 && i != numDays - 1) {
                    res.push([]);
                }

                // Increment the date
                curDate.setDate(curDate.getDate() + 1);
            }

            // Fill in the last row with NULLs for days after the last day of the month
            res[res.length - 1].push(...Array(6 - lastDay.getDay()).fill(null));

            return res;
        },
    },
    watch: {
        dateSelected() {
            this.updateMaxJobs();
        },
    },
    mounted() {
        this.updateMaxJobs();
    }
}
</script>


<style>
.date-block {
    flex: 1 1 1px;
    overflow: auto;
}
</style>