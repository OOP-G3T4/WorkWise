<template class="smaller-text">
    <!-- Parent Container -->
    <div @mouseover="showHoverContent(true)" @mouseleave="showHoverContent(false)" @click="openMainModal(true)"  :class="parentContainerClasses" :style="parentContainerStyle" class="rounded">
        <!-- Job Card -->
        <div @click="openMainModal()" class="card" :class="jobCardClasses" >
            <!-- Client Name and Warning (optional) -->
            <div class="p-small card-header fw-semibold">
                <font-awesome-icon v-if="showJobStartedWarning" class="text-danger me-2" icon="fa-solid fa-circle-exclamation" />{{ jobDetails.clientDetails.clientName }}
            </div>
    
            <!-- Job address and Num cleaners -->
            <div class="card-body overflow-auto py-2">
                <p class="p-small mb-1">{{ jobDetails.jobAddress }}</p>
                <p class="p-small mb-0"><font-awesome-icon class="me-2" :icon="overOneCleaner ? `fa-solid fa-users` : `fa-solid fa-user`" />{{ Object.keys(jobDetails.cleaners).length }} cleaner{{ overOneCleaner ? `s` : `` }}</p>
            </div>
    
            <!-- Job status -->
            <div class="card-footer p-small">
                <font-awesome-icon class="me-2" icon="fa-solid fa-circle" :style="{ color: statusColorMap[jobDetails.jobStatus] }" />{{ jobDetails.jobStatus }}
            </div>
        </div>
    </div>

    <!-- Main Modal -->
    <div class="modal fade" :id="`job-modal-${jobDetails.appointmentId}`" tabindex="-1" :aria-labelledby="`job-modal-label-${jobDetails.appointmentId}`" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header justify-content-between">
                    <h1 class="modal-title fs-5" :id="`job-modal-label-${jobDetails.appointmentId}`"><span class="text-secondary">Appointment ID:</span> {{ jobDetails.appointmentId }}</h1>
                    
                    <div class="d-flex align-items-center">
                        <button v-if="jobDetails.jobStatus != 'Completed'" @click="toggleEditMode()" type="button" class="btn btn-outline-secondary border-0" :class="isEditMode ? 'active' : ''"><font-awesome-icon icon="fa-solid fa-pen-to-square" /></button>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                </div>
                
                <div class="modal-body">
                    <div class="container-fluid">
                        <!-- Job status -->
                        <div class="row">
                            <div class="col-12">
                                <!-- Job Status -->
                                <p><font-awesome-icon class="me-2" icon="fa-solid fa-circle" :style="{ color: statusColorMap[jobDetails.jobStatus] }" />{{ jobDetails.jobStatus }}</p>
                            </div>
                        </div>

                        <!-- IF PROOF NOT UPLOADED ERROR -->
                        <hr v-if="showJobStartedWarning" class="border-2 rounded border-secondary mt-0">

                        <div v-if="showJobStartedWarning" class="row mb-3">
                            <div class="col-12">
                                <h6 class="text-danger fw-bold">ACTION REQUIRED</h6>
                                <p>Employee did not upload proof of arrival within {{ arrivalBufferMinutes }} min. Should this job continue?</p>
                            </div>

                            <div class="col-6">
                                <button class="btn btn-sm btn-secondary w-100">Continue</button>
                            </div>
                            
                            <div class="col-6">
                                <button class="btn btn-sm btn-danger w-100">Cancel Job</button>
                            </div>
                        </div>

                        <hr class="border-2 rounded border-secondary mt-0">

                        <!-- Client Name & Img (placeholder for now) -->
                        <div class="row">
                            <!-- Img -->
                            <div class="col-auto">
                                <img src="https://placehold.co/200x200?text=Profile+Pic" alt="Client Image" class="client-img" />
                            </div>

                            <!-- Name -->
                            <div class="col d-flex align-items-center">
                                <div>
                                    <p class="text-secondary m-0">Client Name</p>
                                    <h6 class="m-0">{{ jobDetails.clientDetails.clientName }}</h6>
                                </div>
                            </div>
                        </div>

                        <!-- Package & Address -->
                        <div class="row gy-2 mt-3">
                            <!-- Package -->
                            <div v-if="!isEditMode" class="col-auto">
                                <p class="text-secondary m-0">Package</p>
                                <h6 class="m-0">{{ jobDetails.packageType }}</h6>
                            </div>

                            <!-- Package [Edit Mode] -->
                            <div v-else class="col-auto">
                                <div class="form-floating">
                                    <select class="form-select" v-model="jobEdit.packageType">
                                        <option v-for="e_package in allPackages" :value="e_package">{{ e_package }}</option>
                                    </select>

                                    <label for="floatingInput">Package</label>
                                </div>
                            </div>

                            <!-- Address -->
                            <div v-if="!isEditMode" class="col-auto">
                                <p class="text-secondary m-0">Address</p>
                                <h6 class="m-0">{{ jobDetails.jobAddress }}</h6>
                            </div>

                            <!-- Address [Edit Mode] -->
                            <div v-else class="col-auto">
                                <div class="form-floating">
                                    <input type="text" class="form-control" v-model="jobEdit.jobAddress" />
                                    <label for="floatingInput">Address</label>
                                </div>
                            </div>
                        </div>

                        <!-- Date, Day of week, Time -->
                        <div class="row gy-2 mt-3">
                            <!-- Date -->
                            <div v-if="!isEditMode" class="col-auto">
                                <p class="text-secondary m-0">Date</p>
                                <h6 class="m-0">{{ jobDetails.date }}</h6>
                            </div>

                            <!-- ERROR: IF END TIME IS BEFORE START TIME -->
                            <div v-if="isEditMode && isJobEndBeforeStart(jobEdit.date, jobEdit.startTime, jobEdit.endTime)" class="col-12">
                                <p class="text-danger"><font-awesome-icon icon="fa-solid fa-circle-exclamation" class="me-2" />End time cannot be before start time</p>
                            </div>

                            <!-- ERROR: IF DATETIME IN THE PAST -->
                            <div v-else-if="isEditMode && isJobStartBeforeToday(jobEdit.date, jobEdit.startTime)" class="col-12">
                                <p class="text-danger"><font-awesome-icon icon="fa-solid fa-circle-exclamation" class="me-2" />Job period selected cannot be in the past</p>
                            </div>
                            
                            <!-- ERROR: TIMING OUT OF ALLOWED RANGE -->
                            <div v-else-if="isEditMode && isJobTimeOutOfBounds(jobEdit.date, jobEdit.startTime, jobEdit.endTime)" class="col-12">
                                <p class="text-danger"><font-awesome-icon icon="fa-solid fa-circle-exclamation" class="me-2" />Job must be between 8AM and 10PM</p>
                            </div>

                            <!-- Date [Edit Mode] -->
                            <div v-if="isEditMode" class="col-auto">
                                <div class="form-floating">
                                    <input type="date" class="form-control" v-model="jobEdit.date" />
                                    <label for="floatingInput">Date</label>
                                </div>
                            </div>

                            <!-- Day of week -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Day</p>
                                <h6 v-if="!isEditMode" class="m-0">{{ new Date(jobDetails.date).toLocaleDateString("en-US", { weekday: "short" }) }}</h6>
                                <h6 v-else class="m-0">{{ new Date(jobEdit.date).toLocaleDateString("en-US", { weekday: "short" }) }}</h6>
                            </div>

                            <!-- Time -->
                            <div v-if="!isEditMode" class="col-auto">
                                <p class="text-secondary m-0">Time</p>
                                <h6 class="m-0">{{ convertTimeToReadable(jobDetails.startTime) }} - {{ convertTimeToReadable(jobDetails.endTime) }}</h6>
                            </div>

                            <!-- Time [Edit Mode - Start Time] -->
                            <div v-if="isEditMode" class="col-6">
                                <div class="form-floating">
                                    <input type="time" class="form-control" min="08:00" max="22:00" v-model="jobEdit.startTime" />
                                    <label for="floatingInput">Start Time</label>
                                </div>
                            </div>

                            <!-- Time [Edit Mode - End Time] -->
                            <div v-if="isEditMode" class="col-6">
                                <div class="form-floating">
                                    <input type="time" class="form-control" min="08:00" max="22:00" v-model="jobEdit.endTime" />
                                    <label for="floatingInput">End Time</label>
                                </div>
                            </div>
                        </div>

                        <!-- Assigned Cleaner(s) Names -->
                        <div v-if="!isEditMode" class="row gy-2 mt-3">
                            <div class="col-auto">
                                <p class="text-secondary m-0">Cleaner{{ overOneCleaner ?  `s` : `` }}</p>
                                <h6 class="m-0">
                                    <template v-for="e_cleaner_id, index in jobDetails.cleaners">
                                        {{ allEmployees[e_cleaner_id] }}
                                        <span v-if="index < Object.keys(jobDetails.cleaners).length - 1" class="text-secondary">, </span>
                                    </template>
                                </h6>
                            </div>
                        </div>

                        <!-- Assigned Cleaner(s) Names [Edit Mode] -->
                        <div v-else class="row gy-2 mt-3">
                            <div class="col-12">
                                <div class="d-flex align-items-center mb-3">
                                    <p class="text-secondary m-0 me-2">Cleaner(s)</p>
                                    <button :disabled="tooManyEmployeesInJob()" @click="addNewCleaner()" class="btn btn-sm btn-secondary rounded-5 py-0 px-2"><font-awesome-icon class="fa-xs" icon="fa-solid fa-plus" /></button>
                                </div>

                                <div class="input-group mb-2" v-for="(e_cleaner_id, idx) in jobEdit.cleaners" :key="idx">
                                    <div class="form-floating">
                                        <select class="form-select" v-model="jobEdit.cleaners[idx]">
                                            <option v-for="(e_employee, e_listed_id) in allEmployees" :value="e_listed_id" :disabled="jobEdit.cleaners.includes(e_listed_id)">
                                                {{ e_employee }}
                                            </option>
                                        </select>
    
                                        <label for="floatingInput">Cleaner {{ idx+1 }}</label>
                                    </div>

                                    <button v-if="jobEdit.cleaners.length > 1" @click="deleteCleaner(idx)" class="btn btn-secondary" type="button"><font-awesome-icon class="mx-2" icon="fa-solid fa-trash" /></button>
                                </div>
                            </div>
                        </div>

                        <hr class="border-2 rounded border-secondary">

                        <!-- SECTION 2: Client Details -->
                        <div class="row">
                            <div class="col-12">
                                <h4>Client Details</h4>
                            </div>
                        </div>

                        <!-- Phone and Email -->
                        <div class="row gy-2">
                            <!-- Phone -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Phone</p>
                                <h6 class="m-0">{{ jobDetails.clientDetails.clientContact }}</h6>
                            </div>

                            <!-- Email -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Email</p>
                                <h6 class="m-0">{{ jobDetails.clientDetails.clientEmail }}</h6>
                            </div>
                        </div>

                        <!-- Address, Gender, Age -->
                        <div class="row gy-2 mt-3">
                            <!-- Address -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Address</p>
                                <h6 class="m-0">{{ jobDetails.clientDetails.clientAddress }}</h6>
                            </div>

                            <!-- Gender -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Gender</p>
                                <h6 class="m-0">{{ jobDetails.clientDetails.clientGender }}</h6>
                            </div>

                            <!-- Age -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Age</p>
                                <h6 class="m-0">{{ jobDetails.clientDetails.clientAge }}</h6>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import * as bootstrap from "bootstrap";

export default {
    props: {
        heightInPx: {
            type: Number,
            required: true,
        },
        jobDetails: {
            type: Object,
            required: true,
        },
        isCompressed: {
            type: Boolean,
            required: false,
            default: false,
        },
    },
    data() {
        return {
            // STATE VARIABLES ================================
            isHovering: false,
            mainModal: null, // Will be automatically populated with bootstrap.Modal on Mounted
            currentDateTime: new Date(),
            isEditMode: false,


            // EDITING VARIABLES ==============================
            jobEdit: null, // Will be automatically populated with jobDetails object on Mounted


            // SETTINGS BELOW ===================================
            // To change color of statuses
            statusColorMap: {
                "In Progress": "#e3b322",
                "Completed": "#0f6320",
                "Not Started": "#858585",
            },

            // Buffer time allowed after job starts before warning is shown if arrivalProof is not uploaded
            arrivalBufferMinutes: 15,

            earliestAllowedJobTime: "08:00",
            latestAllowedJobTime: "22:00",

            // TO BE FETCHED FROM API LATER =====================
            allPackages: ["W_3RM_CONDO", "W_3RM_HDB", "W_4RM_HDB"],
            
            // Employees (key = ID, value = Name)
            allEmployees: {
                1: "John",
                2: "Harry",
                3: "Sally",
                4: "Jane",
                5: "Tom",
                6: "Zach",
                7: "Cory",
                8: "Lily",
            },
        };
    },
    computed: {
        // Returns True if job is within X minutes of start time
        overOneCleaner() {
            return Object.keys(this.jobDetails.cleaners).length > 1;
        },

        // Returns True if (job is more than X minutes after start time) AND (arrivalProofUploaded is False)
        showJobStartedWarning() {
            // Return False if photo has been uploaded
            if (this.jobDetails.arrivalProofUploaded) {
                return false;
            }

            const jobStartDateTime = new Date(`${this.jobDetails.date}T${this.jobDetails.startTime}`);

            if (this.currentDateTime < jobStartDateTime) {
                return false;
            } else {
                const diff = (this.currentDateTime - jobStartDateTime) / 60000;
                return diff > this.arrivalBufferMinutes;
            }
        },

        parentContainerStyle() {
            return {
                backgroundColor: this.isCompressed ? this.statusColorMap[this.jobDetails.jobStatus] : "",
                height: `${this.heightInPx}px`,
            };
        },

        parentContainerClasses() {
            return {
                'compressed-parent-container': this.isCompressed,
                'border border-3 border-danger': this.isCompressed && this.showJobStartedWarning,
            }
        },

        jobCardClasses() {
            return {
                'border border-3 border-danger': this.showJobStartedWarning,
                'compressed-job-card': this.isCompressed,
                'h-100': !this.isCompressed,
                'd-none': this.isCompressed && !this.isHovering,
            }
        },
    },
    methods: {
        showHoverContent(toShow = true) {
            this.isHovering = toShow;
        },

        openMainModal(toShow = true) {
            if (toShow) {
                this.mainModal.show();
            } else {
                this.mainModal.hide();
            }
        },

        toggleEditMode() {
            this.isEditMode = !this.isEditMode;
        },

        convertTimeToReadable(timeIn) {
            // Returns a human readable time string from ISO 8601 standard (HH:mm:ss) in 12-hour format
            const [hours, minutes] = timeIn.split(":");
            const suffix = hours >= 12 ? "PM" : "AM";
            const hours12 = hours % 12 || 12;

            return `${hours12}:${minutes}${suffix}`;
        },

        isJobStartBeforeToday(dateStr, startTimeStr) {
            const dateInput = new Date(`${dateStr}T${startTimeStr}`);

            return this.currentDateTime > dateInput;
        },

        isJobEndBeforeStart(dateStr, startTimeStr, endTimeStr) {
            const dateInput = new Date(`${dateStr}T${startTimeStr}`);
            const dateEnd = new Date(`${dateStr}T${endTimeStr}`);

            return dateEnd <= dateInput;
        },

        isJobTimeOutOfBounds(dateStr, startTimeStr, endTimeStr) {
            const jobStart = new Date(`${dateStr}T${startTimeStr}`);
            const jobEnd = new Date(`${dateStr}T${endTimeStr}`);

            const earliestAllowed = new Date(`${dateStr}T${this.earliestAllowedJobTime}`);
            const latestAllowed = new Date(`${dateStr}T${this.latestAllowedJobTime}`);

            return jobStart < earliestAllowed || jobEnd > latestAllowed;
        },

        tooManyEmployeesInJob() {
            // Current logic: If all employees are already in the job, disable the 'Add Cleaner' button
            return Object.keys(this.jobEdit.cleaners).length >= Object.keys(this.allEmployees).length;
        },

        addNewCleaner() {
            // Adds a new cleaner to the job (Picks the first available cleaner)
            const allCleaners = Object.keys(this.allEmployees);
            const currentCleaners = this.jobEdit.cleaners;

            // Iterate through all employees and add the first one that is not in the job yet
            for (let cleaner of allCleaners) {
                if (!currentCleaners.includes(cleaner)) {
                    this.jobEdit.cleaners.push(cleaner);
                    break;
                }
            }
        },

        deleteCleaner(idx) {
            // Deletes a cleaner from the job
            this.jobEdit.cleaners.splice(idx, 1);
        },
    },
    mounted() {
        // Sets up main modal
        // Note to self: 'Next tick' is necessary to ensure that the modal is loaded in DOM before it is accessed
        this.$nextTick(() => {
            this.mainModal = new bootstrap.Modal(
                document.getElementById(`job-modal-${this.jobDetails.appointmentId}`)
            );
        });

        // Duplicates jobDetails object for editing
        this.jobEdit = JSON.parse(JSON.stringify(this.jobDetails));

        // Auto update time every minute
        setInterval(() => {
            this.currentDateTime = new Date();
        }, 60000);
    },
};
</script>

<style scoped>
.client-img {
    width: 75px;
    aspect-ratio: 1/1;
    border-radius: 50%;
}

.compressed-job-card {
    position: absolute;
    top: 0;
    left: calc(100% + 10px);
    z-index: 1000;
    pointer-events: none;
    width: 250px;
}

.compressed-parent-container {
    position: relative;
    cursor: pointer;
}

.p-small {
    font-size: 0.8rem;
}
</style>
