<script setup>
import { onBeforeUnmount } from 'vue';
import JobDetails from '../../general/calendar/JobDetails.vue';
</script>

<template>
    <div id="main-container-daily-cal">
        <div class="left-timestamp-container" @mouseover="enableScroll" @mouseleave="disableScroll">
            <!-- Empty white div for padding (using div instead of padding-top to allow sticky-top to work) -->
            <div v-if="!hideTopBar" class="sticky-top bg-white" :style="{height: topPaddingPx+'px'}"></div>
            
            <div
                v-for="i in timeAxisMax - timeAxisMin"
                class="text-end border-end border-2 px-2 px-md-3 bg-white fs-10 fs-md-8"
                :key="i"
                :style="{ height: heightPerIntervalAxis + 'px' }"
            >
                {{ convertTimeToReadable(timeAxisMin + i - 1) }}
            </div>
        </div>
    
        <div class="right-calendar-container justify-content-center">
            <!-- Now Line (Horizontal Line that shows you Current Time) -->
            <div v-if="isToday" class="now-line" :style="nowLineStyle"></div>

            <!-- Background Grid -->
            <div id="bgGridDailyCal" class="position-absolute top-0 start-0 w-100" :style="bgGridStyles">
                <div
                    v-for="i in (timeAxisMax - timeAxisMin)"
                    :key="i"
                    :style="{ height: heightPerIntervalAxis + 'px' }"
                ></div>
            </div>

            <!-- No jobs message -->
            <div v-if="jobDetailsArr.length == 0" class="position-absolute top-0 start-0 w-100 d-flex justify-content-center align-items-center" :style="bgGridStyles">
                <h1 class="text-secondary"><font-awesome-icon icon="fa-solid fa-question" class="me-3" beat />No Jobs</h1>
            </div>

            <!-- Each Client Column(s) -->
            <template v-for="eData, idx in objectEntries(jobDetailsArrSorted)">
                <div class="d-flex flex-column" :class="idx != 0 ?  'border-start border-3 border-light' : ''" :style="clientColStyles(Object.keys(eData[1]).length)">
                    <!-- Client Details (Fills all cols per client) -->
                    <div v-if="!hideTopBar" class="sticky-top bg-white d-flex justify-content-center align-items-center overflow-hidden" :style="{flex: `0 1 ${topPaddingPx}px`, height: `${topPaddingPx}px`}">
                        <!-- Img -->
                        <div class="col-auto me-2">
                            <img src="https://placehold.co/200x200?text=Profile+Pic" alt="Client Image" class="client-img" />
                        </div>
    
                        <!-- Name -->
                        <div class="col-auto d-flex align-items-center">
                            <div>
                                <h6 class="m-0 text-tiny">{{ clientData[eData[0]].clientName }}</h6>
                            </div>
                        </div>
                    </div>

                    <!-- Job Blocks Container (Contains all job columns below client details) -->
                    <div class="job-block h-100 row m-0 p-0">
                        <!-- Each Job Column -->
                        <div class="h-100 col" v-for="eArrJobs, idx2 in objectEntries(eData[1])">
                            <div class="position-relative">
                                <!-- Place Each Job Block -->
                                <template v-for="jobDetails in eArrJobs[1]">
                                    <div class="e-job-child" :style="eJobChildStyle(jobDetails.startTime)">
                                        <JobDetails :popoverRight="colIsLeftHalf(idx,idx2)" :heightInPx="calculateHeightPx(jobDetails.startTime, jobDetails.endTime)" :jobDetails="jobDetails" :isCompressed="isCompressed" />
                                    </div>
                                </template>
                            </div>
                        </div>
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
            // Axis settings [In 24 hour format (only whole hours)]
            defaultTimeAxisMin: 8,
            defaultTimeAxisMax: 22,
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

            // Job Data (Sorted - By Client)
            jobDetailsArrSorted: null,
            clientData: {},
        }
    },
    computed: {
        heightPerIntervalAxis() {
            return (this.yHeightPx - this.topPaddingPx) / (this.timeAxisMax - this.timeAxisMin);
        },
        getNowLineHeight() {
            // Returns height at bottom/ top of axis if out of range
            const now = new Date();
            const timeAxisMinPadded = this.timeAxisMin.toString().padStart(2, '0');
            const earliestTimeAllowed = new Date(`${now.toISOString().split('T')[0]}T${timeAxisMinPadded}:00:00`);
            const timeDiffMin = Math.abs(now - earliestTimeAllowed) / 60000;
            const nowLineHeight = (timeDiffMin / 60) * this.heightPerIntervalAxis;

            var buffer = this.hideTopBar ? 0 : this.topPaddingPx;
            var antiBuffer = this.hideTopBar ? this.topPaddingPx : 0;

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
                height: this.hideTopBar ? `${this.yHeightPx - this.topPaddingPx}px` : `${this.yHeightPx}px`,
                paddingTop: this.hideTopBar ? '0' : this.topPaddingPx + 'px',
            }
        },
        nowLineStyle() {
            return {
                top: this.getNowLineHeight + 'px',
                width: this.nowLineWidth + 'px',
                height: this.nowLineThickness + 'px',
            }
        },
        jobDetailsArr() {
            // Return the jobDetailsArr for the selected date
            var return_data = this.getMonthAndDay(this.dateSelected);
            var monthStr = return_data[0];
            var day = return_data[1];

            if (!this.jobDetails) {
                return [];
            }

            if (!(monthStr in this.jobDetails)) {
                return [];
            }

            if (!(day in this.jobDetails[monthStr])) {
                return [];
            }

            return this.jobDetails[monthStr][day];
        },
        isToday() {
            // Returns true if the selected date is today
            var today = new Date();
            return today.toISOString().split('T')[0] == this.dateSelected.toISOString().split('T')[0];
        },
        hideTopBar() {
            return this.isCompressed || this.jobDetailsArr.length == 0;
        },
        numCols() {
            var numCols = 0;

            for (var clientId in this.jobDetailsArrSorted) {
                numCols += Object.keys(this.jobDetailsArrSorted[clientId]).length;
            }

            return numCols;
        },
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
        colIsLeftHalf(idx1, idx2) {
            var jobEntries = this.objectEntries(this.jobDetailsArrSorted);
            var colIdx = 0;

            // Loop trough up to idx1 to count columns up to previous client
            for (var i = 0; i < idx1; i++) {
                colIdx += Object.keys(jobEntries[i][1]).length;
            }

            colIdx += idx2 + 1;
            var halfNumClients = Math.floor(this.numCols / 2);
            return colIdx <= halfNumClients;
        },
        objectEntries(obj) {
            if (!obj) return [];

            return Object.entries(obj);
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
            // Sort jobDetailsArr into jobDetailsArrSorted by client ID (key: clientID, value: <jobDetails>)
            this.jobDetailsArrSorted = this.jobDetailsArr.reduce((acc, jobDetails) => {
                // Update min and max time axis if needed
                var startHour = parseInt(jobDetails.startTime.split(":")[0]);
                var endHour = parseInt(jobDetails.endTime.split(":")[0]) + 1;

                if (startHour < this.timeAxisMin) {
                    this.timeAxisMin = startHour;
                }

                if (endHour > this.timeAxisMax) {
                    this.timeAxisMax = endHour;
                }

                // Get vars
                const clientId = jobDetails.clientDetails.clientId;
                const jobAddressId = jobDetails.jobAddress.id;

                // Add client ID to clientData
                if (!this.clientData[clientId]) {
                    this.clientData[clientId] = jobDetails.clientDetails;
                }

                // Create client ID key if not in acc
                if (!acc[clientId]) {
                    acc[clientId] = {};
                }

                // Create sub property ID key if not in acc
                if (!acc[clientId][jobAddressId]) {
                    acc[clientId][jobAddressId] = [];
                }

                // Push
                acc[clientId][jobAddressId].push(jobDetails);

                return acc;
            }, {});
        },
        getMonthAndDay(dateObj) {
            // Returns the month and day in string format
            var jobMonthStr = (dateObj.getMonth()+1) + "-" + dateObj.getFullYear();
            var jobDay = dateObj.getDate();

            return [jobMonthStr, jobDay];
        },
        clientColStyles(numColsInp) {
            var padding = this.hideTopBar ? this.topPaddingPx : 0;
            var properWidthPerCol = this.isCompressed ? this.clientColWidthCompressed : this.clientColWidth;
            var flexGrow = this.isCompressed ? 0 : numColsInp;

            return {
                height: (this.yHeightPx - padding) +'px',
                width: `${properWidthPerCol * numColsInp}px`,
                flex: `${flexGrow} 1 ${properWidthPerCol * numColsInp}px`,
            }
        },
    },
    watch: {
        hideTopBar(newVal) {
            // Update min height allowed if compressed
            if (newVal) {
                // If compressed, set the yHeight to fill the gap made by the top padding
                if (this.yHeightPx < (this.minYHeightAllowed + this.topPaddingPx)) {
                    this.yHeightPx = this.minYHeightAllowed + this.topPaddingPx;
                }
            }
        },
        yHeightPx(newVal) {
            var buffer = this.hideTopBar ? this.topPaddingPx : 0;
            var min_height = this.minYHeightAllowed + buffer;

            // Limit the height of the container
            if (newVal < min_height) {
                this.yHeightPx = min_height;
            }
        },
        jobDetailsArr: {
            handler(newVal) {
                this.timeAxisMin = this.defaultTimeAxisMin;
                this.timeAxisMax = this.defaultTimeAxisMax;

                if (newVal.length == 0) {
                    this.jobDetailsArrSorted = null;
                    return;
                }

                this.updateJobDetailsArrSorted();
            },
            deep: true, //Watch for changes in within array, rather than just the pointer
        },
    },
    mounted() {
        this.updateJobDetailsArrSorted();

        // Initialize ResizeObserver to track the height changes for #main-container-daily-cal
        const observer = new ResizeObserver(this.updateContainerHeight);
        const mainContainer = document.querySelector('#main-container-daily-cal');
        
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
    }
}
</script>


<style scoped>
#main-container-daily-cal {
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

.client-img {
    width: 35px;
    aspect-ratio: 1/1;
    border-radius: 50%;
}

.text-tiny {
    font-size: 0.75em;
}

.job-block {
    flex: 1 1 1px;
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

.now-line::before {
  content: ''; /* Creates an empty content for the circle */
  position: absolute;
  left: -8px; /* Aligns the circle to the left side */
  top: 50%; /* Centers vertically */
  transform: translateY(-50%); /* Adjusts vertical position */
  
  width: 16px; /* Size of the circle */
  height: 16px; /* Size of the circle */
  background-color: #3498db; /* Color of the circle */
  border-radius: 50%; /* Makes the element circular */
}

#bgGridDailyCal > :nth-child(odd) {
    background-color: #dfdfdf84;
}
</style>