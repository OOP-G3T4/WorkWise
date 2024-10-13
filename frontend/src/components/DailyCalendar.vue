<script setup>
import { onBeforeUnmount } from 'vue';
import JobDetails from './JobDetails.vue';
</script>

<template>
    <div id="main-container-daily-cal">
        <div class="left-timestamp-container" @wheel="handleScroll" @mouseover="enableScroll" @mouseleave="disableScroll">
            <!-- Empty white div for padding (using div instead of padding-top to allow sticky-top to work) -->
            <div v-if="!isCompressed" class="sticky-top bg-white" :style="{height: topPaddingPx+'px'}"></div>
            
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
            <div class="now-line" :style="{top: getNowLineHeight, width: nowLineWidth+'px'}"></div>

            <!-- Background Grid -->
            <div id="bgGridDailyCal" class="position-absolute top-0 start-0 w-100" :style="bgGridStyles">
                <div
                    v-for="i in (timeAxisMax - timeAxisMin)"
                    :key="i"
                    :style="{ height: heightPerIntervalAxis + 'px' }"
                ></div>
            </div>

            <template v-for="eData, idx in objectEntries(jobDetailsArrSorted)">
                <div class="container-fluid d-flex flex-column" :style="clientColStyles">
                    <!-- Client Details (TOP) -->
                    <div v-if="!isCompressed" class="sticky-top bg-white row justify-content-center align-items-center pt-2" :style="{flex: `0 1 ${topPaddingPx}px`}">
                        <!-- Img -->
                        <div class="col-auto">
                            <img src="https://placehold.co/200x200?text=Profile+Pic" alt="Client Image" class="client-img" />
                        </div>

                        <!-- Name -->
                        <div class="col-auto d-flex align-items-center">
                            <div>
                                <h6 class="m-0 text-tiny">{{ eData[1][0].clientDetails.clientName }}</h6>
                            </div>
                        </div>
                    </div>

                    <!-- Job Details (BOTTOM) -->
                    <div class="jobs-parent">
                        <!-- Place Each Job Block -->
                        <template v-for="jobDetails in eData[1]">
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

            // Client column settings
            clientColWidth: 200,
            clientColWidthCompressed: 50,

            // Job Data (Unsorted)
            jobDetailsArr: [
                {
                    appointmentId: "213",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "67 Choa Chu Kang Loop",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "17:00:00", // Must be calculated based on startTime and Length
                    cleaners: [1,2], // Not included yet in DB
                    arrivalProofUploaded: false,  // Not included yet in DB
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "1",
                        clientName: "John Doe",
                        clientContact: "91234567",
                        clientEmail: "johndoe@gmail.com",
                        clientAddress: "101 Clementi Road", // Not included yet in DB
                        clientGender: "Male", // Not included yet in DB
                        clientAge: "49", // Not included yet in DB
                    },
                },
                {
                    appointmentId: "214",
                    packageType: "W_3RM_HDB",
                    jobAddress: "32 Bukit Batok West Ave 6",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "12:30:00",
                    endTime: "15:00:00",
                    cleaners: [3],
                    arrivalProofUploaded: false,
                    jobStatus: "Completed",
                    clientDetails: {
                        clientId: "2",
                        clientName: "Harry Sim",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "215",
                    packageType: "W_3RM_HDB",
                    jobAddress: "12 Serangoon North Ave 5",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "18:00:00",
                    cleaners: [4],
                    arrivalProofUploaded: false,
                    jobStatus: "Not Started",
                    clientDetails: {
                        clientId: "3",
                        clientName: "Jane Low",
                        clientContact: "91234567",
                        clientEmail: "janelow@yahoo.com",
                        clientAddress: "34 Jurong East Ave 1",
                        clientGender: "Female",
                        clientAge: "29",
                    },
                },
                {
                    appointmentId: "216",
                    packageType: "W_4RM_HDB",
                    jobAddress: "19 Clementi Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "12:00:00",
                    endTime: "18:00:00",
                    cleaners: [5],
                    arrivalProofUploaded: false,
                    jobStatus: "Not Started",
                    clientDetails: {
                        clientId: "4",
                        clientName: "Mary Tan",
                        clientContact: "91234567",
                        clientEmail: "marytan@hotmail.com",
                        clientAddress: "45 Bukit Timah Road",
                        clientGender: "Female",
                        clientAge: "39",
                    },
                },
                {
                    appointmentId: "217",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "08:00:00",
                    endTime: "11:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "1",
                        clientName: "John Doe",
                        clientContact: "91234567",
                        clientEmail: "johndoe@gmail.com",
                        clientAddress: "101 Clementi Road",
                        clientGender: "Male",
                        clientAge: "49",
                    },
                },
                {
                    appointmentId: "218",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "08:30:00",
                    endTime: "11:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "4",
                        clientName: "Mary Tan",
                        clientContact: "91234567",
                        clientEmail: "marytan@hotmail.com",
                        clientAddress: "45 Bukit Timah Road",
                        clientGender: "Female",
                        clientAge: "39",
                    },
                },
                {
                    appointmentId: "219",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "17:00:00",
                    endTime: "21:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "2",
                        clientName: "Harry Sim",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "220",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "15:30:00",
                    endTime: "19:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "5",
                        clientName: "Wally Wales",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "221",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "10:00:00",
                    endTime: "14:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "6",
                        clientName: "Gregogry Goh",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "222",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "18:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "7",
                        clientName: "Tim Tales",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "223",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "18:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "8",
                        clientName: "Fernando Ferrari",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "224",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "18:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "9",
                        clientName: "Poopy Pants",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "225",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-13", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "18:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "10",
                        clientName: "Mammy Poko Pants",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
            ],

            // Job Data (Sorted - By Client)
            jobDetailsArrSorted: null,
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
            const now = new Date();
            const timeAxisMinPadded = this.timeAxisMin.toString().padStart(2, '0');
            const earliestTimeAllowed = new Date(`${now.toISOString().split('T')[0]}T${timeAxisMinPadded}:00:00`);
            const timeDiffMin = Math.abs(now - earliestTimeAllowed) / 60000;
            const nowLineHeight = (timeDiffMin / 60) * this.heightPerIntervalAxis;

            var buffer = this.isCompressed ? 0 : this.topPaddingPx;

            if (0 < nowLineHeight && nowLineHeight < (this.yHeightPx - this.topPaddingPx)) {
                return `${nowLineHeight+buffer}px`;
            } else if (nowLineHeight < 0) {
                return `${buffer}px`;
            } else {
                return `${this.yHeightPx}px`;
            }
        },
        bgGridStyles() {
            return {
                height: this.isCompressed ? `${this.yHeightPx - this.topPaddingPx}px` : `${this.yHeightPx}px`,
                paddingTop: this.isCompressed ? '0' : this.topPaddingPx + 'px',
            }
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
                this.nowLineWidth = entry.contentRect.width;
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
            var halfNumClients = Math.floor(Object.keys(this.jobDetailsArrSorted).length / 2);
            return idx < halfNumClients;
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
    },
    watch: {
        isCompressed(newVal) {
            // Update min height allowed if compressed
            if (newVal) {
                // If compressed, set the yHeight to fill the gap made by the top padding
                if (this.yHeightPx < (this.minYHeightAllowed + this.topPaddingPx)) {
                    this.yHeightPx = this.minYHeightAllowed + this.topPaddingPx;
                }
            }
        },
        yHeightPx(newVal) {
            var buffer = this.isCompressed ? this.topPaddingPx : 0;
            var min_height = this.minYHeightAllowed + buffer;

            // Limit the height of the container
            if (newVal < min_height) {
                this.yHeightPx = min_height;
            }
        }
    },
    mounted() {
        // Sort jobDetailsArr into jobDetailsArrSorted by client ID (key: clientID, value: <jobDetails>)
        this.jobDetailsArrSorted = this.jobDetailsArr.reduce((acc, jobDetails) => {
            if (acc[jobDetails.clientDetails.clientId]) {
                acc[jobDetails.clientDetails.clientId].push(jobDetails);
            } else {
                acc[jobDetails.clientDetails.clientId] = [jobDetails];
            }
            return acc;
        }, {});

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
    height: 0.25rem;
    background-color: #2a86b491;
    z-index: 1;
}

#bgGridDailyCal > :nth-child(odd) {
    background-color: #f4f4f4;
}

</style>