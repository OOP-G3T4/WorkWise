<template>
    <div class="w-100 h-100 d-flex px-1 p-md-3">
        <!-- Left time column -->
        <div class="time-bar d-flex flex-column px-1 px-md-2">
            <!-- Set padding -->
            <div :style="{height: `${topPaddingPx}px`}"></div>

            <!-- Set time blocks -->
            <div v-for="time in blockStartTimes" :key="time" class="flex-grow-1">
                <p class="m-0 fs-10 fs-md-8 text-end">{{ convertTimeToReadable(time) }}</p>
            </div>
        </div>

        <!-- Right bars container -->
        <div class="week-diagram">
            <div v-for="eDate in arrDates" class="h-100 col d-flex flex-column">
                <!-- Date Info -->
                <div class="d-flex flex-column align-items-center" :style="{height: `${topPaddingPx}px`}">
                    <h6 class="m-0 fs-10 fs-md-9 d-inline-block mb-2">{{ eDate.toLocaleDateString("en-GB", {weekday: "short",}) }}</h6>
                    <h6 class="m-0 fs-10 fs-md-7 d-inline-block p-2 rounded-5 cursor-pointer" :class="isToday(eDate) ? 'bg-primary text-white' : 'bg-lighter'" @click="navigateToDate(eDate)">{{ eDate.getDate() }}</h6>
                </div>

                <!-- Job Blocks -->
                <div v-for="eStartTime in blockStartTimes" class="flex-grow-1 d-flex flex-column justify-content-end p-1 p-md-3">
                    <div class="w-100 rounded d-flex justify-content-center align-items-center cursor-pointer" :style="numJobsBarStyle(getJobCount(eDate, eStartTime))" @click="navigateToDate(eDate)">
                        <p class="m-0 fs-7">{{ getJobCount(eDate, eStartTime) }}</p>
                    </div>
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
            blockStartTimes: [8 ,12, 18], // Hours in 24 hour format
            topPaddingPx: 80,
            sampleDate: '1998-12-28', // Random date to manage time comparisons

            // Color settings
            zeroJobsLightness: 96,
            minJobsLightness: 90,
            maxJobsLightness: 44,
            baseColorH: 200,
            baseColorS: 62,
            textWhiteThreshold: 50,
        }
    },
    computed: {
        startDate() {
            // Returns the start date of the week (Sunday)
            var date = new Date(this.dateSelected);
            var day = date.getDay();
            var diff = date.getDate() - day;
            return new Date(date.setDate(diff));
        },
        arrDates() {
            // Returns an array of dates for the week
            var arr = [];
            var tempDate = new Date(this.startDate);

            for (var i = 0; i < 7; i++) {
                arr.push(new Date(tempDate));
                tempDate.setDate(tempDate.getDate() + 1);
            }

            return arr;
        },
        dataFormatted() {
            // Returns job details by: Key 1) Date, Key 2) BlockStartTime
            var res = {};

            const timeMap = {}

            for (var i in this.blockStartTimes) {
                const time = this.blockStartTimes[i];
                
                timeMap[time] = new Date(`${this.sampleDate}T${String(time).padStart(2, "0")}:00:00`);
            }

            for (var eMonthKey in this.jobDetails) {
                for (var eDayKey in this.jobDetails[eMonthKey]) {
                    const jobArr = this.jobDetails[eMonthKey][eDayKey];

                    const month = eMonthKey.split("-")[0];
                    const year = eMonthKey.split("-")[1];
                    const day = eDayKey;

                    const dateStr = `${year}-${month}-${day}`;

                    for (var idx in jobArr) {
                        // Iterate through each job
                        const job = jobArr[idx];
                        const midTime = this.getMidTime(job.startTime, job.endTime);
                        var jobAdded = false;

                        for (var idx2 in this.blockStartTimes) {
                            if (idx2 == 0) {
                                // Skip first block
                                continue;
                            }

                            if (jobAdded) {
                                // Skip if job has alr been added
                                break;
                            }

                            const time = this.blockStartTimes[idx2];
                            const timeObj = timeMap[time];

                            // If midTime is before timeObj, then it belongs to the previous block
                            if (midTime < timeObj) {
                                const timeKey = this.blockStartTimes[idx2 - 1];

                                // Create arr if not alr initialized
                                if (!res[dateStr]) {
                                    res[dateStr] = {};
                                }

                                if (!res[dateStr][timeKey]) {
                                    res[dateStr][timeKey] = [];
                                }

                                // Push job to the block
                                res[dateStr][timeKey].push(job);
                                jobAdded = true;
                            }
                        }

                        // If job is not added, then it belongs to the last block
                        if (!jobAdded) {
                            const timeKey = this.blockStartTimes[this.blockStartTimes.length - 1];

                            // Create arr if not alr initialized
                            if (!res[dateStr]) {
                                res[dateStr] = {};
                            }

                            if (!res[dateStr][timeKey]) {
                                res[dateStr][timeKey] = [];
                            }

                            // Push job to the block
                            res[dateStr][timeKey].push(job);
                        }
                    }
                }
            }

            return res;
        },
        maxNumJobs() {
            // Returns the max number of jobs in a block for specific week
            var max = 0;

            for (var idx in this.arrDates) {
                const eDate = this.arrDates[idx];

                for (var idx2 in this.blockStartTimes) {
                    const eTime = this.blockStartTimes[idx2];
                    
                    const numJobs = this.getJobCount(eDate, eTime);

                    if (numJobs > max) {
                        max = numJobs;
                    }
                }
            }

            return max;
        }
    },
    methods: {
        isToday(dateObj) {
            var today = new Date();
            return dateObj.getDate() == today.getDate() && dateObj.getMonth() == today.getMonth() && dateObj.getFullYear() == today.getFullYear();
        },
        convertTimeToReadable(hrIn) {
            // Returns a human readable time string from whole number representing time in 24 hour format
            const suffix = hrIn >= 12 ? "PM" : "AM";
            const hours12 = hrIn % 12 || 12;

            return `${hours12}${suffix}`;
        },
        getMidTime(start, end) {
            const dateStart = new Date(`${this.sampleDate}T${start}`);
            const dateEnd = new Date(`${this.sampleDate}T${end}`);

            const timeStart = dateStart.getTime();
            const timeEnd = dateEnd.getTime();

            const timeMid = (timeStart + timeEnd) / 2;

            const midDate = new Date(timeMid);

            return midDate;
        },
        getJobCount(date, time) {
            const dateStr = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;

            if (this.dataFormatted[dateStr] && this.dataFormatted[dateStr][time]) {
                return this.dataFormatted[dateStr][time].length;
            }

            return 0;
        },
        numJobsBarStyle(numJobs) {
            // Returns the style for the job count bar
            if (numJobs == 0) {
                return {
                    height: "auto",
                    backgroundColor: `hsl(${this.baseColorH}, ${this.baseColorS}%, ${this.zeroJobsLightness}%)`,
                }
            }

            const heightPerc = numJobs / this.maxNumJobs;
            const lightnessToSubtract = heightPerc * (this.minJobsLightness - this.maxJobsLightness);
            const lightness = this.minJobsLightness - lightnessToSubtract;
            const textColor = lightness > this.textWhiteThreshold ? 'black' : 'white';

            return {
                height: `${heightPerc * 100}%`,
                backgroundColor: `hsl(${this.baseColorH}, ${this.baseColorS}%, ${lightness}%)`,
                color: textColor,
            }
        },
        navigateToDate(dateObj) {
            // Emit the dateSelected event
            if (dateObj == null) {
                return;
            }

            this.$emit('navToDate', dateObj);
        },
    },
}
</script>

<style scoped>
.time-bar {
    flex: 0 1 1px;
}

.week-diagram {
    flex: 1 1 1px;
    overflow: auto;
    display: flex;
}
</style>