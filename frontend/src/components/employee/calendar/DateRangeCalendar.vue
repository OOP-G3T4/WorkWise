<script setup>
import { onBeforeUnmount } from 'vue';
import JobDetails from '../../general/calendar/JobDetails.vue';
</script>

<template>
    <div id="main-container-range-cal">
        <div class="left-timestamp-container" @mouseover="enableScroll" @mouseleave="disableScroll">
            <!-- Empty white div for padding (using div instead of padding-top to allow sticky-top to work) -->
            <div class="sticky-top bg-white" :style="{height: topPaddingPx+'px'}"></div>
            
            <div
                v-for="i in timeAxisMax - timeAxisMin"
                class="text-end border-end border-2 px-3 bg-white"
                :key="i"
                :style="{ height: heightPerIntervalAxis + 'px' }"
            >
                {{ convertTimeToReadable(timeAxisMin + i - 1) }}
            </div>
        </div>
    
        <div class="right-calendar-container">
            <!-- Now Line (Horizontal Line that shows you Current Time) -->
            <div class="now-line" :style="nowLineStyle"></div>

            <!-- Background Grid -->
            <div id="bgGridRangeCal" class="position-absolute top-0 start-0 w-100" :style="bgGridStyles">
                <div
                    v-for="i in (timeAxisMax - timeAxisMin)"
                    :key="i"
                    :style="{ height: heightPerIntervalAxis + 'px' }"
                ></div>
            </div>

            <!-- Each Client Column -->
            <template v-for="(eDateObj, idx) in arrDates" :key="idx">
                <div class="container-fluid d-flex flex-column" :class="canClientColExpand ? 'w-100' : ''" :style="clientColStyles">
                    <!-- Client Details (TOP) -->
                    <div class="sticky-top bg-white row justify-content-center align-items-center pt-2" :style="{flex: `0 1 ${topPaddingPx}px`}">
                        <!-- Date -->
                        <div class="col-auto d-flex flex-column align-items-center">
                            <h6 class="m-0 text-tiny d-inline-block mb-2">{{ eDateObj.dayOfWeek }}</h6>
                            <h6 class="m-0 text-tiny d-inline-block p-2 rounded-5" :class="highlightTodayClass(eDateObj.dateStr)">{{ eDateObj.day }}</h6>
                        </div>
                    </div>

                    <!-- Job Details (BOTTOM) -->
                    <div class="jobs-parent">
                        <!-- Place Each Job Block -->
                        <template v-for="jobDetails in getCopyArrJobs(eDateObj.dateStr)">
                            <div class="e-job-child" :style="eJobChildStyle(jobDetails.startTime)">
                                <JobDetails :popoverRight="colIsLeftHalf(idx)" :heightInPx="calculateHeightPx(jobDetails.startTime, jobDetails.endTime)" :jobDetails="jobDetails" :isCompressed="isCompressed" />
                            </div>
                        </template>
                    </div>
                </div>
            </template>
        </div>
    </div>
</template>


<script>
export default {
    props: {
        isCompressed: {
            type: Boolean,
            required: true,
        },
        startDate: {
            type: Date,
            required: true,
        },
        numDays: {
            type: Number,
            required: true,
        },
    },
    data() {
        return {
            // Axis settings [In 24 hour format (only whole hours)]
            timeAxisMin: 8,
            timeAxisMax: 22,

            // Container height settings
            yHeightPx: 1000,
            topPaddingPx: 80,
            minYHeightAllowed: 500,
            nowLineWidth: 0,
            nowLineThickness: 4,

            // Client column settings
            clientColWidth: 200,
            clientColWidthCompressed: 50,
            canClientColExpand: false,

            // Job Data (Unsorted)
            jobDetailsArr: [],

            // Job Data (Sorted - By Client)
            jobDetailsArrSorted: null,

            // Today
            today: new Date(),
        }
    },
    computed: {
        heightPerIntervalAxis() {
            return (this.yHeightPx - this.topPaddingPx) / (this.timeAxisMax - this.timeAxisMin);
        },
        clientColStyles() {
            return {
                height: (this.yHeightPx - this.topPaddingPx ) +'px',
                width: this.isCompressed ? `${this.clientColWidthCompressed}px` : `${this.clientColWidth}px`
            }
        },
        getNowLineHeight() {
            // Returns height at bottom/ top of axis if out of range
            const now = this.today;
            const timeAxisMinPadded = this.timeAxisMin.toString().padStart(2, '0');
            const earliestTimeAllowed = new Date(`${now.toISOString().split('T')[0]}T${timeAxisMinPadded}:00:00`);
            const timeDiffMin = Math.abs(now - earliestTimeAllowed) / 60000;
            const nowLineHeight = (timeDiffMin / 60) * this.heightPerIntervalAxis;

            var buffer = this.topPaddingPx;
            var antiBuffer = 0;

            if (0 <= nowLineHeight && nowLineHeight < (this.yHeightPx - this.topPaddingPx)) {
                // If within time range
                return nowLineHeight + buffer;
            } else if (nowLineHeight < 0) {
                // If before time range
                return buffer;
            } else {
                // If beyond time range
                return this.yHeightPx - antiBuffer - this.nowLineThickness;
            }
        },
        bgGridStyles() {
            return {
                height: `${this.yHeightPx}px`,
                paddingTop: this.topPaddingPx + 'px',
            }
        },
        nowLineStyle() {
            return {
                top: this.getNowLineHeight + 'px',
                width: this.nowLineWidth + 'px',
                height: this.nowLineThickness + 'px',
            }
        },
        arrDates() {
            // Returns an array of objects with info about each date
            var arrDates = [];
            var date = new Date(this.startDate);

            for (var i = 0; i < this.numDays; i++) {
                var dateStr = date.toISOString().split('T')[0];
                
                var newDateObj = {
                    dateStr: dateStr,
                    day: date.getDate(),
                    month: date.getMonth() + 1,
                    year: date.getFullYear(),
                    dayOfWeek: date.toLocaleString('default', { weekday: 'short' }),
                }

                arrDates.push(newDateObj);

                date.setDate(date.getDate() + 1);
            }

            return arrDates;
        }
    },
    methods: {
        convertTimeToReadable(hrIn) {
            // Returns a human readable time string from whole number representing time in 24 hour format
            const suffix = hrIn >= 12 ? "PM" : "AM";
            const hours12 = hrIn % 12 || 12;

            return `${hours12}${suffix}`;
        },
        updateContainerHeight(entries) {
            for (let entry of entries) {
                this.minYHeightAllowed = entry.contentRect.height;
                this.yHeightPx = Math.max(this.yHeightPx, this.minYHeightAllowed);
            }
        },
        updateContainer2Width(entries) {
            for (let entry of entries) {
                const newWidth = entry.contentRect.width;
                this.nowLineWidth = newWidth;

                this.updateCanClientExpand(newWidth);
            }
        },
        calculateHeightPx(startTime, endTime) {
            let today = new Date().toISOString().split('T')[0];
            
            const startDate = new Date(`${today}T${startTime}`);
            const endDate = new Date(`${today}T${endTime}`);

            const timeDiffMin = Math.abs(endDate - startDate) / 60000;
            const heightPx = (timeDiffMin / 60) * this.heightPerIntervalAxis;

            return heightPx;
        },
        eJobChildStyle(startTime) {
            // Figures out the position of the job block
            const today = new Date().toISOString().split('T')[0];
            const startDate = new Date(`${today}T${startTime}`);

            const timeAxisMinPadded = this.timeAxisMin.toString().padStart(2, '0');
            const earliestTimeAllowed = new Date(`${today}T${timeAxisMinPadded}:00:00`);
            
            // Check time difference in minutes from timeAxisMin
            const timeDiffMin = Math.abs(startDate - earliestTimeAllowed) / 60000;

            return {
                top: `${(timeDiffMin / 60) * this.heightPerIntervalAxis}px`
            }
        },
        colIsLeftHalf(idx) {
            var halfNumClients = Math.floor(this.numDays / 2);
            return idx < halfNumClients;
        },
        handleScroll(event) {
            // Prevent the actual page from scrolling
            event.preventDefault();

            // Check the scroll direction by looking at `deltaY`
            if (event.deltaY > 0) {
                // Custom functionality for scrolling down
                this.yHeightPx -= 10;
            } else {
                // Custom functionality for scrolling up
                this.yHeightPx += 10;
            }
        },
        enableScroll() {
            // Optionally add a wheel event listener only when the mouse is over the element
            const container = this.$el.querySelector('.left-timestamp-container');
            container.addEventListener('wheel', this.handleScroll, { passive: false }); // Make it passive: false to prevent default scroll
        },
        disableScroll() {
            // Optionally remove the event listener when the mouse leaves the element
            const container = this.$el.querySelector('.left-timestamp-container');
            container.removeEventListener('wheel', this.handleScroll);
        },
        getEndTime(startTime, durationHours) {
            // Returns the end time (string format: "hh:mm:ss") based on the start time (string format: "hh:mm:ss") and duration in hours
            var today = new Date();

            var today_date_str = today.toISOString().split('T')[0];
            var start_time_str = today_date_str + "T" + startTime;

            var startObj = new Date(start_time_str);

            var endObj = new Date(startObj.getTime() + durationHours * 60 * 60 * 1000);

            return endObj.toTimeString().split(' ')[0];
        },
        updateJobDetailsArrSorted() {
            // Sort jobDetailsArr into jobDetailsArrSorted by date (ie. 1 key per unique day) (key: date [eg. "2024-12-30"], value: arr<jobDetails>)
            this.jobDetailsArrSorted = this.jobDetailsArr.reduce((acc, jobDetails) => {
                if (acc[jobDetails.date]) {
                    acc[jobDetails.date].push(jobDetails);
                } else {
                    acc[jobDetails.date] = [jobDetails];
                }
                return acc;
            }, {});
        },
        updateCanClientExpand(newWidth) {
            // Break if jobDetailsArrSorted is not initialized yet
            if (this.jobDetailsArrSorted == null) {
                return;
            }

            // Break if compressed
            if (this.isCompressed) {
                return;
            }

            const numCols = this.numDays;
            const newPossibleWidth = newWidth / numCols;

            // Check if client column can expand
            if (newPossibleWidth > this.clientColWidth) {
                this.canClientColExpand = true;
            } else {
                this.canClientColExpand = false;
            }
        },
        pullJobData() {
            // Build random jobs from numDays days back and forwards [FOR TESTING]
            const sDate = this.startDate;
            const numDays = 100;

            const minDate = new Date(sDate);
            minDate.setDate(minDate.getDate() - numDays);
            minDate.setHours(0, 0, 0, 0);

            const maxDate = new Date(sDate);
            maxDate.setDate(maxDate.getDate() + (numDays*2) );
            maxDate.setHours(23, 59, 59, 999);

            var timeRanges = [
                { start: '08', end: '12' },
                { start: '13', end: '17' },
                { start: '18', end: '22' }
            ]

            // Generates random arr of jobs
            const eDate = new Date(minDate);
            var jobArr = [];
            var jobID = 1;

            while (eDate < maxDate) {
                // Increment eDate to next day
                eDate.setDate(eDate.getDate() + 1);

                // Go through each time range
                for (var i = 0; i < timeRanges.length; i++) {
                    // Break with 30% chance
                    if (this.flipCoin(0.3)) {
                        continue;
                    }

                    // Create a new job
                    var newJob = {};

                    // set arrival proof uploaded
                    var arrivalProofUploaded;

                    if (eDate < new Date()) {
                        arrivalProofUploaded = true;
                    } else {
                        arrivalProofUploaded = this.flipCoin(0.5);
                    }
                    
                    // Set job status
                    var jobStatus;

                    if (arrivalProofUploaded || eDate < new Date()) {
                        jobStatus = "COMPLETED";
                    } else {
                        jobStatus = "PENDING";
                    }

                    newJob.appointmentId = jobID;
                    newJob.packageType = "W_3RM_CONDO";
                    newJob.jobAddress = "1234 Random St";
                    newJob.date = eDate.toISOString().split('T')[0];
                    newJob.startTime = timeRanges[i].start + ":00:00";
                    newJob.endTime = timeRanges[i].end + ":00:00";
                    newJob.cleaners = ["1"];
                    newJob.arrivalProofUploaded = arrivalProofUploaded;
                    newJob.jobStatus = jobStatus;
                    newJob.clientDetails = {
                        clientId: "1",
                        clientName: "John Doe",
                        clientContact: "91234567",
                        clientEmail: "johndoe@gmail.com",
                        clientAddress: "101 Clementi Road", // Not included yet in DB
                        clientGender: "Male", // Not included yet in DB
                        clientAge: "49", // Not included yet in DB
                    }
                    
                    // Increments
                    jobID += 1;

                    // Push to jobArr
                    jobArr.push(newJob);
                }
            }

            // Shuffle the array
            jobArr.sort(() => Math.random() - 0.5);

            this.jobDetailsArr = jobArr;
        },
        flipCoin(percentageChance) {
            // [FOR TESTING] Takes in percetageChance in decimal form (0.0 - 1.0) and returns true or false based on chance
            return Math.random() < percentageChance;
        },
        getCopyArrJobs(dateStr) {
            // Returns a copy of the jobDetailsArr for a specific date
            if (!this.jobDetailsArrSorted) {
                return [];
            } else if (!this.jobDetailsArrSorted[dateStr]) {
                return [];
            }

            return [...this.jobDetailsArrSorted[dateStr]];
        },
        highlightTodayClass(inpDateStr) {
            const isToday = inpDateStr === this.today.toISOString().split('T')[0];

            return {
                'bg-primary text-white': isToday,
            }
        },
    },
    watch: {
        isCompressed(newVal) {
            // Update min height allowed if compressed
            if (newVal) {
                // If compressed, set canClientColExpand to false
                this.canClientColExpand = false;
            } else {
                // If expanded, check and update if client column can expand
                this.updateCanClientExpand(this.nowLineWidth);
            }
        },
        yHeightPx(newVal) {
            var buffer = 0;
            var min_height = this.minYHeightAllowed + buffer;

            // Limit the height of the container
            if (newVal < min_height) {
                this.yHeightPx = min_height;
            }
        },
        jobDetailsArr: {
            handler() {
                this.updateJobDetailsArrSorted();
                this.updateCanClientExpand(this.nowLineWidth);
            },
            deep: true, //Watch for changes in within array, rather than just the pointer
        },
    },
    mounted() {
        // Initialize ResizeObserver to track the height changes for #main-container-range-cal
        const observer = new ResizeObserver(this.updateContainerHeight);
        const mainContainer = document.querySelector('#main-container-range-cal');
        
        if (mainContainer) {
            observer.observe(mainContainer);
        }

        // Cleanup the observer when component is destroyed
        onBeforeUnmount(() => {
            if (observer && mainContainer) {
                observer.unobserve(mainContainer);
            }
        });

        // Initialize ResizeObserver to track the width changes for .right-calendar-container
        const observer2 = new ResizeObserver(this.updateContainer2Width);
        const rightContainer = document.querySelector('.right-calendar-container');

        if (rightContainer) {
            observer2.observe(rightContainer);
        } 
        
        // Cleanup the observer when component is destroyed
        onBeforeUnmount(() => {
            if (observer2 && rightContainer) {
                observer2.unobserve(rightContainer);
            }
        });


        // Pull data from "API" [FOR TESTING]
        this.pullJobData();
    }
}
</script>


<style scoped>
#main-container-range-cal {
    height: 100%;
    width: 100%; 
    display: flex;
    flex-direction: row;
    overflow: auto;
    position: relative;
}

.left-timestamp-container {
    flex: 0 1 1px;
    font-size: 0.75em;
    position: sticky;
    left: 0;
    z-index: 2;
}

.right-calendar-container {
    flex: 1 1 1px;
    display: flex;
    position: relative;
}

.text-tiny {
    font-size: 0.75em;
}

.jobs-parent {
    flex: 1 1 1px;
    position: relative;
}

.e-job-child {
    position: absolute;
    width: 100%;
}

.now-line {
    position: absolute;
    width: 100%;
    background-color: #2a86b491;
    z-index: 1;
}

#bgGridRangeCal > :nth-child(odd) {
    background-color: #f4f4f4;
}
</style>