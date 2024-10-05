<script setup>
import { onBeforeUnmount } from 'vue';
import JobDetails from './JobDetails.vue';
</script>

<template>
    <div id="main-container-daily-cal">
        <div class="left-timestamp-container px-2 border-end" :style="{paddingTop: topPaddingPx+'px'}">
            <div
                v-for="i in timeAxisMax - timeAxisMin"
                class="text-end"
                :key="i"
                :style="{ height: heightPerIntervalAxis + 'px' }"
            >
                {{ convertTimeToReadable(timeAxisMin + i - 1) }}
            </div>
        </div>
    
        <div class="right-calendar-container">
            <template v-for="eachJDArr, eachClientId in jobDetailsArrSorted">

                <div class="container-fluid d-flex flex-column" :style="{height: yHeightPx+'px'}">
                    <!-- Client Details (TOP) -->
                    <div class="row justify-content-center align-items-center pt-2" :style="{flex: `0 1 ${topPaddingPx}px`}">
                        <!-- Img -->
                        <div class="col-auto">
                            <img src="https://placehold.co/200x200?text=Profile+Pic" alt="Client Image" class="client-img" />
                        </div>

                        <!-- Name -->
                        <div class="col-auto d-flex align-items-center">
                            <div>
                                <h6 class="m-0 text-tiny">{{ eachJDArr[0].clientDetails.clientName }}</h6>
                            </div>
                        </div>
                    </div>

                    <!-- Job Details (BOTTOM) -->
                    <div class="jobs-parent">
                        <!-- Place Each Job Block -->
                        <template v-for="jobDetails in eachJDArr">
                            <div class="e-job-child" :style="eJobChildStyle(jobDetails.startTime)">
                                <JobDetails :heightInPx="calculateHeightPx(jobDetails.startTime, jobDetails.endTime)" :jobDetails="jobDetails" :isCompressed="isCompressed" />
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
    data() {
        return {
            // Axis settings [In 24 hour format (only whole hours)]
            timeAxisMin: 8,
            timeAxisMax: 22,
            yHeightPx: 1000,
            topPaddingPx: 80,

            // Job Data (Unsorted)
            jobDetailsArr: [
                {
                    appointmentId: "213",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "67 Choa Chu Kang Loop",
                    date: "2024-10-05", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "17:00:00",
                    cleaners: [1,2],
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
                    appointmentId: "214",
                    packageType: "W_3RM_HDB",
                    jobAddress: "32 Bukit Batok West Ave 6",
                    date: "2024-10-05", // ISO 8601 standard
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
                    date: "2024-10-05", // ISO 8601 standard
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
                    date: "2024-10-05", // ISO 8601 standard
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
                    date: "2024-10-05", // ISO 8601 standard
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
            ],

            // Job Data (Sorted - By Client)
            jobDetailsArrSorted: null,

            // SETTINGS ========================================
            isCompressed: false,
        }
    },
    computed: {
        heightPerIntervalAxis() {
            return (this.yHeightPx - this.topPaddingPx) / (this.timeAxisMax - this.timeAxisMin);
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
                var newHeight = entry.contentRect.height;

                // this.yHeightPx = entry.contentRect.height;
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

        // Initialize ResizeObserver to track the height changes
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
}

.left-timestamp-container {
    flex: 0 1 1px;
    font-size: 0.75em;
}

.right-calendar-container {
    flex: 1 1 1px;
    display: flex;
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

</style>