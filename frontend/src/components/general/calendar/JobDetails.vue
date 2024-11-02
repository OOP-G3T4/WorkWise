<script setup>
import { mapState } from "vuex";
import GmapInput from "../forms/GmapInput.vue";
import DropdownSearch from "../forms/DropdownSearch.vue";
</script>

<template>
    <!-- Parent Container -->
    <div
        @mouseover="showHoverContent(true)"
        @mouseleave="showHoverContent(false)"
        @click="openMainModal(true)"
        :class="parentContainerClasses"
        :style="parentContainerStyle"
        class="rounded"
    >
        <!-- Job Card -->
        <div @click="openMainModal(true)" class="card" :class="jobCardClasses">
            <!-- Client Name and Warning (optional) -->            
            <div
                class="fs-9 fs-md-7 card-header fw-semibold px-2 px-md-3 py-1 py-md-2 text-truncate"
            >
                <font-awesome-icon
                    v-if="showJobStartedWarning"
                    class="text-danger me-2"
                    icon="fa-solid fa-circle-exclamation"
                />{{ jobDetails.clientDetails.clientName }}
            </div>

            <!-- Job address and Num cleaners -->
            <div class="card-body overflow-auto px-2 px-md-3 py-1 py-md-2">
                <p class="fs-9 fs-md-7 mb-1">
                    {{ jobDetails.jobAddress.address }}
                </p>
                <p class="fs-9 fs-md-7 mb-1">
                    <font-awesome-icon
                        class="me-2"
                        :icon="
                            overOneCleaner
                                ? `fa-solid fa-users`
                                : `fa-solid fa-user`
                        "
                    />{{ Object.keys(jobDetails.cleaners).length }}
                    <span class="d-none d-md-inline-block"
                        >cleaner{{ overOneCleaner ? `s` : `` }}</span
                    >
                </p>
                <p class="fs-9 fs-md-7 mb-0">
                    <font-awesome-icon
                        class="me-2 d-none d-md-inline-block"
                        icon="fa-solid fa-clock"
                    />{{ convertTimeToReadable(jobDetails.startTime) }} -
                    {{ convertTimeToReadable(jobDetails.endTime) }}
                </p>
            </div>

            <!-- Job status -->
            <div
                class="card-footer fs-10 fs-md-8 px-2 px-md-3 py-1 py-md-2 text-truncate"
            >
                <font-awesome-icon
                    class="me-2"
                    icon="fa-solid fa-circle"
                    :style="{ color: statusColorMap[jobDetails.jobStatus] }"
                />{{ jobDetails.jobStatus }}
            </div>
        </div>
    </div>

    <!-- Main Modal -->
    <div class="modal fade" :id="`job-modal-${jobDetails.appointmentId}`" tabindex="-1" :aria-labelledby="`job-modal-label-${jobDetails.appointmentId}`" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header justify-content-between">
                    <h1 class="modal-title fs-5" :id="`job-modal-label-${jobDetails.appointmentId}`">
                        <span class="text-secondary">Appointment ID:</span>
                        {{ jobDetails.appointmentId }}
                    </h1>

                    <div class="d-flex align-items-center">
                        <button v-if="userType == 'admin' && jobDetails.jobStatus != 'COMPLETED'" @click="toggleEditMode()" type="button" class="btn btn-outline-secondary border-0" :class="isEditMode ? 'active' : ''">
                            <font-awesome-icon icon="fa-solid fa-pen-to-square" />
                        </button>

                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                </div>

                <div class="modal-body">
                    <div class="container-fluid">
                        <!-- Job status -->
                        <div class="row">
                            <div class="col-12">
                                <!-- Job Status -->
                                <p>
                                    <font-awesome-icon class="me-2" icon="fa-solid fa-circle"
                                        :style="{
                                            color: statusColorMap[
                                                jobDetails.jobStatus
                                            ],
                                        }"
                                    />{{ jobDetails.jobStatus }}
                                </p>
                            </div>
                        </div>

                        <!-- IF ARRIVAL PROOF NOT UPLOADED ERROR -->
                        <template v-if="showJobStartedWarning">
                            <hr class="border-2 rounded border-secondary mt-0" />

                            <button class="btn btn-sm btn-danger mb-3" data-bs-toggle="collapse" :href="`#error-msg-collapse-${jobDetails.appointmentId}`" role="button" :aria-controls="`error-msg-collapse-${jobDetails.appointmentId}`"><font-awesome-icon class="me-2" icon="fa-solid fa-caret-down" />ACTION REQUIRED</button>
    
                            <div class="row mb-3 collapse" :id="`error-msg-collapse-${jobDetails.appointmentId}`">
                                <template v-if="userType == 'admin'">
                                    <div class="col-12">
                                        <p>
                                            Employee did not upload proof of arrival
                                            within {{ arrivalBufferMinutes }} min.
                                            Would you like to cancel this job?
                                        </p>
                                    </div>
    
                                    <div class="col-6">
                                        <button class="btn btn-sm btn-light w-100" @click="openErrorMsgCollapse(false)">
                                            Dismiss
                                        </button>
                                    </div>
    
                                    <div class="col-6">
                                        <button class="btn btn-sm btn-danger w-100" @click="openDelModal(true)">
                                            Cancel Job
                                        </button>
                                    </div>
                                </template>
    
                                <template v-else-if="userType == 'employee'">
                                    <div class="col-12 mb-3">
                                        <p class="m-0">
                                            Upload proof of arrival within
                                            {{ arrivalBufferMinutes }} min to
                                            continue this job
                                        </p>
                                    </div>

                                    <div class="col-12">
                                        <div class="input-group">
                                            <input type="file" class="form-control" @change="handleArrivalFileUpload" />
                                            <button class="btn btn-primary" type="button" @click="submitArrivalImg()">Upload</button>
                                        </div>
                                    </div>
                                </template>
                            </div>
                        </template>

                        <!-- IF JOB COMPLETED BUT NO PROOF UPLOADED -->
                        <template v-if="showJobCompletedWarning">
                            <hr class="border-2 rounded border-secondary mt-0" />

                            <button class="btn btn-sm btn-danger mb-3" data-bs-toggle="collapse" :href="`#error-msg-collapse-completed-${jobDetails.appointmentId}`" role="button" :aria-controls="`error-msg-collapse-completed-${jobDetails.appointmentId}`"><font-awesome-icon class="me-2" icon="fa-solid fa-caret-down" />ACTION REQUIRED</button>
    
                            <div class="row mb-3 collapse" :id="`error-msg-collapse-completed-${jobDetails.appointmentId}`">
                                <template v-if="userType == 'admin'">
                                    <div class="col-12">
                                        <p>
                                            Employee did not upload proof of completion after end of job.
                                            Would you like to confirm job completion anyways?
                                        </p>
                                    </div>
    
                                    <div class="col-6">
                                        <button class="btn btn-sm btn-light w-100" @click="openErrorMsgCollapseCompleted(false)">
                                            Dismiss
                                        </button>
                                    </div>
    
                                    <div class="col-6">
                                        <button class="btn btn-sm btn-success w-100" @click="confirmJob()">
                                            Confirm Job Completion
                                        </button>
                                    </div>
                                </template>
    
                                <template v-else-if="userType == 'employee'">
                                    <div class="col-12 mb-3">
                                        <p class="m-0">
                                            Upload proof of completion to finish this job
                                        </p>
                                    </div>

                                    <div class="col-12">
                                        <div class="input-group">
                                            <input type="file" class="form-control" @change="handleCompletionFileUpload" />
                                            <button class="btn btn-primary" type="button" @click="submitCompletedImg()">Upload</button>
                                        </div>
                                    </div>
                                </template>
                            </div>
                        </template>

                        <hr class="border-2 rounded border-secondary mt-0" />

                        <!-- Client Name & Img -->
                        <div class="row">
                            <!-- Img -->
                            <div class="col-auto">
                                <img
                                    src="https://placehold.co/200x200?text=Profile+Pic"
                                    alt="Client Image"
                                    class="client-img"
                                />
                            </div>

                            <!-- Name -->
                            <div class="col d-flex align-items-center">
                                <div>
                                    <p class="text-secondary m-0">
                                        Client Name
                                    </p>
                                    <h6 class="m-0">
                                        {{ jobDetails.clientDetails.clientName }}
                                    </h6>
                                </div>
                            </div>
                        </div>

                        <!-- Package & Address -->
                        <div class="row gy-2 mt-3">
                            <!-- Package -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Package</p>
                                <h6 class="m-0">
                                    {{ jobDetails.packageType }}
                                </h6>
                            </div>

                            <!-- Address -->
                            <div v-if="!isEditMode" class="col-auto">
                                <p class="text-secondary m-0">Address</p>
                                <h6 class="m-0">
                                    {{ jobDetails.jobAddress.address }}
                                </h6>
                            </div>

                            <!-- Address [Edit Mode] -->
                            <div v-else class="col-12 mt-3">
                                <DropdownSearch :items="clientProperties" :inputValue="jobEdit.jobAddress.id" fieldName="Address" :uniqueComponentId="jobDetails.appointmentId" @valChange="handleAddressChange" />
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
                            <div
                                v-if="
                                    isEditMode &&
                                    isJobEndBeforeStart(
                                        jobEdit.date,
                                        jobEdit.startTime,
                                        jobEdit.endTime
                                    )
                                "
                                class="col-12"
                            >
                                <p class="text-danger">
                                    <font-awesome-icon
                                        icon="fa-solid fa-circle-exclamation"
                                        class="me-2"
                                    />End time cannot be before start time
                                </p>
                            </div>

                            <!-- ERROR: IF DATETIME IN THE PAST -->
                            <div
                                v-else-if="
                                    isEditMode &&
                                    isJobStartBeforeToday(
                                        jobEdit.date,
                                        jobEdit.startTime
                                    )
                                "
                                class="col-12"
                            >
                                <p class="text-danger">
                                    <font-awesome-icon
                                        icon="fa-solid fa-circle-exclamation"
                                        class="me-2"
                                    />Job period selected cannot be in the past
                                </p>
                            </div>

                            <!-- ERROR: TIMING OUT OF ALLOWED RANGE -->
                            <div
                                v-else-if="
                                    isEditMode &&
                                    isJobTimeOutOfBounds(
                                        jobEdit.date,
                                        jobEdit.startTime,
                                        jobEdit.endTime
                                    )
                                "
                                class="col-12"
                            >
                                <p class="text-danger">
                                    <font-awesome-icon
                                        icon="fa-solid fa-circle-exclamation"
                                        class="me-2"
                                    />Job must be between 8AM and 10PM
                                </p>
                            </div>

                            <!-- Date [Edit Mode] -->
                            <div v-if="isEditMode" class="col-auto">
                                <div class="form-floating">
                                    <input
                                        type="date"
                                        class="form-control"
                                        v-model="jobEdit.date"
                                    />
                                    <label for="floatingInput">Date</label>
                                </div>
                            </div>

                            <!-- Day of week -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Day</p>
                                <h6 v-if="!isEditMode" class="m-0">
                                    {{
                                        new Date(
                                            jobDetails.date
                                        ).toLocaleDateString("en-US", {
                                            weekday: "short",
                                        })
                                    }}
                                </h6>
                                <h6 v-else class="m-0">
                                    {{
                                        new Date(
                                            jobEdit.date
                                        ).toLocaleDateString("en-US", {
                                            weekday: "short",
                                        })
                                    }}
                                </h6>
                            </div>

                            <!-- Time -->
                            <div v-if="!isEditMode" class="col-auto">
                                <p class="text-secondary m-0">Time</p>
                                <h6 class="m-0">
                                    {{
                                        convertTimeToReadable(
                                            jobDetails.startTime
                                        )
                                    }}
                                    -
                                    {{
                                        convertTimeToReadable(
                                            jobDetails.endTime
                                        )
                                    }}
                                </h6>
                            </div>

                            <!-- Time [Edit Mode - Start Time] -->
                            <div v-if="isEditMode" class="col-6">
                                <div class="form-floating">
                                    <input
                                        type="time"
                                        class="form-control"
                                        min="08:00"
                                        max="22:00"
                                        v-model="jobEdit.startTime"
                                    />
                                    <label for="floatingInput"
                                        >Start Time</label
                                    >
                                </div>
                            </div>

                            <!-- Time [Edit Mode - End Time] -->
                            <div v-if="isEditMode" class="col-6">
                                <div class="form-floating">
                                    <input
                                        type="time"
                                        class="form-control"
                                        min="08:00"
                                        max="22:00"
                                        v-model="jobEdit.endTime"
                                    />
                                    <label for="floatingInput">End Time</label>
                                </div>
                            </div>
                        </div>

                        <!-- Assigned Cleaner(s) Names -->
                        <div v-if="!isEditMode" class="row gy-2 mt-3">
                            <div class="col-auto">
                                <p class="text-secondary m-0">
                                    Cleaner{{ overOneCleaner ? `s` : `` }}
                                </p>
                                <h6 class="m-0">
                                    <template
                                        v-for="(
                                            e_cleaner_id, index
                                        ) in jobDetails.cleaners"
                                    >
                                        {{ allEmployees[e_cleaner_id] }}
                                        <span
                                            v-if="
                                                index <
                                                Object.keys(jobDetails.cleaners)
                                                    .length -
                                                    1
                                            "
                                            class="text-secondary"
                                            >,
                                        </span>
                                    </template>
                                </h6>
                            </div>
                        </div>

                        <!-- Assigned Cleaner(s) Names [Edit Mode] -->
                        <div v-else class="row gy-2 mt-3">
                            <div class="col-12">
                                <div class="d-flex align-items-center mb-3">
                                    <p class="text-secondary m-0 me-2">
                                        Cleaner(s)
                                    </p>
                                    <button
                                        :disabled="tooManyEmployeesInJob()"
                                        @click="addNewCleaner()"
                                        class="btn btn-sm btn-secondary rounded-5 py-0 px-2"
                                    >
                                        <font-awesome-icon
                                            class="fa-xs"
                                            icon="fa-solid fa-plus"
                                        />
                                    </button>
                                </div>

                                <div
                                    class="input-group mb-2"
                                    v-for="(
                                        e_cleaner_id, idx
                                    ) in jobEdit.cleaners"
                                    :key="idx"
                                >
                                    <div class="form-floating">
                                        <select
                                            class="form-select"
                                            v-model="jobEdit.cleaners[idx]"
                                        >
                                            <option
                                                v-for="(
                                                    e_employee, e_listed_id
                                                ) in allEmployees"
                                                :value="e_listed_id"
                                                :disabled="
                                                    jobEdit.cleaners.includes(
                                                        e_listed_id
                                                    )
                                                "
                                            >
                                                {{ e_employee }} (ID:
                                                {{ e_listed_id }})
                                            </option>
                                        </select>

                                        <label for="floatingInput"
                                            >Cleaner {{ idx + 1 }}</label
                                        >
                                    </div>

                                    <button
                                        v-if="jobEdit.cleaners.length > 1"
                                        @click="deleteCleaner(idx)"
                                        class="btn btn-secondary"
                                        type="button"
                                    >
                                        <font-awesome-icon
                                            class="mx-2"
                                            icon="fa-solid fa-trash"
                                        />
                                    </button>
                                </div>
                            </div>
                        </div>

                        <hr class="border-2 rounded border-secondary" />

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
                                <h6 class="m-0">
                                    {{ jobDetails.clientDetails.clientContact }}
                                </h6>
                            </div>

                            <!-- Email -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Email</p>
                                <h6 class="m-0">
                                    {{ jobDetails.clientDetails.clientEmail }}
                                </h6>
                            </div>
                        </div>

                        <!-- Address, Gender, Age -->
                        <div v-if="userType == 'admin'" class="row gy-2 mt-3">
                            <!-- Address -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Address</p>
                                <h6 class="m-0">
                                    {{ jobDetails.clientDetails.clientAddress }}
                                </h6>
                            </div>

                            <!-- Gender -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Gender</p>
                                <h6 class="m-0">
                                    {{ jobDetails.clientDetails.clientGender }}
                                </h6>
                            </div>

                            <!-- Age -->
                            <div class="col-auto">
                                <p class="text-secondary m-0">Age</p>
                                <h6 class="m-0">
                                    {{ jobDetails.clientDetails.clientAge }}
                                </h6>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer d-flex justify-content-between" v-if="isEditMode">
                    <button type="button" class="btn btn-outline-danger" @click="openDelModal(true)">
                        <font-awesome-icon icon="fa-solid fa-trash" />
                    </button>

                    <div>
                        <button type="button" class="btn btn-light" @click="revertEdits()">
                            Cancel
                        </button>
    
                        <button type="button" class="btn btn-primary ms-3" @click="saveChanges()">
                            Save changes
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Confirm Delete Modal -->
    <div class="modal fade" :id="`job-del-modal-${jobDetails.appointmentId}`" tabindex="-1" :aria-labelledby="`job-del-modal-label-${jobDetails.appointmentId}`" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5 ms-1" :id="`job-del-modal-label-${jobDetails.appointmentId}`">Confirm Cancellation?</h1>
                    <button type="button" class="btn-close" @click="openMainModal(true)"></button>
                </div>

                <div class="modal-body">
                    <p>Are you sure you want to cancel this job?:</p>
                    <p><span class="fw-bold">Client:</span> {{ jobDetails.clientDetails.clientName }}</p>
                    <p><span class="fw-bold">Address:</span> {{ jobDetails.jobAddress.address }}</p>
                    <p><span class="fw-bold">Date:</span> {{ jobDetails.date }}</p>
                    <p><span class="fw-bold">Time:</span> {{ convertTimeToReadable(jobDetails.startTime) }} - {{ convertTimeToReadable(jobDetails.endTime) }}</p>
                    
                    <br />
                    <p>Job cancellations cannot be undone.</p>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" @click="openMainModal(true)" >Go Back</button>
                    <button type="button" class="btn btn-outline-danger" @click="confirmCancelJob()"><font-awesome-icon icon="fa-solid fa-trash" class="me-2" />Confirm</button>
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
        popoverRight: {
            // Where to show the popover (right or left)
            type: Boolean,
            required: false,
            default: true,
        },
    },
    data() {
        return {
            // STATE VARIABLES ================================
            isHovering: false,
            mainModal: null, // Will be automatically populated with bootstrap.Modal on Mounted
            delModal: null, // Will be automatically populated with bootstrap.Modal on Mounted
            errorMsgCollapse: null, // Will be automatically populated with bootstrap.Collapse on Mounted
            errorMsgCollapseCompleted: null, // Will be automatically populated with bootstrap.Collapse on Mounted
            currentDateTime: new Date(),
            isEditMode: false,

            arrivalImg: null,
            completionImg: null,

            // EDITING VARIABLES ==============================
            jobEdit: null, // Will be automatically populated with jobDetails object on Mounted

            // SETTINGS BELOW ===================================
            // To change color of statuses
            statusColorMap: {
                "IN PROGRESS": "#e3b322", // Not a current field update later (ADAMBFT)
                COMPLETED: "#0f6320",
                PENDING: "#858585",
            },

            // Buffer time allowed after job starts before warning is shown if arrivalProof is not uploaded
            arrivalBufferMinutes: 15,

            earliestAllowedJobTime: "08:00",
            latestAllowedJobTime: "22:00",

            // Pull from API
            allEmployees: {}, // (key = ID, value = Name)
            clientProperties: {}, // (key = ID, value = AddressString)
            allPropertyInfo: {}, // (key = ID, value = {address, postalCode})
        };
    },
    computed: {
        ...mapState(["userType"]), // Access userType from Vuex state

        // Returns True if there are more than 1 cleaners in the job
        overOneCleaner() {
            return Object.keys(this.jobDetails.cleaners).length > 1;
        },

        showJobStartedWarning() {
            // Returns True if (job is more than X minutes after start time) AND (arrivalProofUploaded is False)
            
            // Return False if photo has been uploaded OR job status is COMPLETED
            if (this.jobDetails.arrivalProofUploaded || this.jobDetails.jobStatus == "COMPLETED") {
                return false;
            }

            const jobStartDateTime = new Date(
                `${this.jobDetails.date}T${this.jobDetails.startTime}`
            );

            if (this.currentDateTime < jobStartDateTime) {
                return false;
            } else if (this.userType == "admin") {
                const diff = (this.currentDateTime - jobStartDateTime) / 60000;
                return diff > this.arrivalBufferMinutes;
            } else {
                // If employee, show warning if job has started
                return true;
            }
        },

        showJobCompletedWarning() {
            // If proof already uploaded OR job status is COMPLETED, return false
            if (this.jobDetails.completionProofUpload || this.jobDetails.jobStatus == "COMPLETED") {
                return false;
            }

            // If showJobStartedWarning is true, return false
            if (this.showJobStartedWarning) {
                return false;
            }

            // If job not completed, return false
            const jobEndDateTime = new Date(
                `${this.jobDetails.date}T${this.jobDetails.endTime}`
            );

            if (this.currentDateTime < jobEndDateTime) {
                return false;
            } else {
                return true;
            }
        },

        parentContainerStyle() {
            return {
                backgroundColor: this.isCompressed
                    ? this.statusColorMap[this.jobDetails.jobStatus]
                    : "",
                height: `${this.heightInPx}px`,
            };
        },

        parentContainerClasses() {
            return {
                "compressed-parent-container": this.isCompressed,
                "border border-3 border-danger":
                    this.isCompressed && this.showJobStartedWarning,
            };
        },

        jobCardClasses() {
            return {
                "border border-3 border-danger": this.showJobStartedWarning,
                "compressed-job-card": this.isCompressed,
                showPopoverRight: this.popoverRight && this.isCompressed,
                showPopoverLeft: !this.popoverRight && this.isCompressed,
                "h-100": !this.isCompressed,
                "d-none": this.isCompressed && !this.isHovering,
            };
        },
    },
    methods: {
        showHoverContent(toShow = true) {
            this.isHovering = toShow;
        },

        openMainModal(toShow = true) {
            if (toShow) {
                this.openDelModal(false);

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

            const earliestAllowed = new Date(
                `${dateStr}T${this.earliestAllowedJobTime}`
            );
            const latestAllowed = new Date(
                `${dateStr}T${this.latestAllowedJobTime}`
            );

            return jobStart < earliestAllowed || jobEnd > latestAllowed;
        },

        tooManyEmployeesInJob() {
            // Current logic: If all employees are already in the job, disable the 'Add Cleaner' button
            return (
                Object.keys(this.jobEdit.cleaners).length >=
                Object.keys(this.allEmployees).length
            );
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
        revertEdits() {
            // Reverts all edits made to the jobEdit object
            this.jobEdit = JSON.parse(JSON.stringify(this.jobDetails));
            this.isEditMode = false;
        },
        saveChanges() {
            // Saves all changes made to the jobEdit object

            // Check if any errors are present
            if (
                this.isJobEndBeforeStart(
                    this.jobEdit.date,
                    this.jobEdit.startTime,
                    this.jobEdit.endTime
                ) ||
                this.isJobStartBeforeToday(
                    this.jobEdit.date,
                    this.jobEdit.startTime
                ) ||
                this.isJobTimeOutOfBounds(
                    this.jobEdit.date,
                    this.jobEdit.startTime,
                    this.jobEdit.endTime
                )
            ) {
                return;
            }

            // API call to update job
            let jobId = this.jobDetails.appointmentId;
            let reqBody = {
                "client": {
                    "clientId": this.jobEdit.clientDetails.clientId
                },
                "property": {
                    "propertyId": this.jobEdit.jobAddress.id
                },
                "selectedPackage": {
                    "packageId": this.jobEdit.packageType
                },
                "date": this.jobEdit.date,
                "startTime": this.formatTime(this.jobEdit.startTime),
                "status": this.jobEdit.jobStatus,
                "actualDuration": this.calculateHours(
                    this.jobEdit.startTime,
                    this.jobEdit.endTime
                ),
                "employees": []
            }

            // Add all cleaners to the employees array
            for (let cleanerId of this.jobEdit.cleaners) {
                reqBody.employees.push({
                    "employeeId": cleanerId
                });
            }

            fetch(`http://localhost:8081/api/job/${jobId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(reqBody),
            })
                .then((response) => {
                    if (response.ok) {
                        this.$emit("jobUpdated", jobId);
                        this.isEditMode = false;

                        // Close modal
                        this.openMainModal(false);
                        
                    } else {
                        console.error(
                            "Error updating the item:",
                            response.statusText
                        );
                    }
                })
                .catch((error) => {
                    console.error("Error updating the item:", error);
                });
        },
        addressChange(data) {
            // GMaps input emits the new address, this function updates the jobEdit object
            this.jobEdit.jobAddress.address = data.value;
        },
        openDelModal(toOpen = true) {
            if (toOpen) {
                this.openMainModal(false);

                this.delModal.show();
            } else {
                this.delModal.hide();
            }
        },
        async confirmCancelJob() {
            // API call to delete job
            let jobId = this.jobDetails.appointmentId;

            try {
                const response = await fetch(`http://localhost:8081/api/job/${jobId}`, {
                    method: 'DELETE',
                });
                if (response.ok) {
                    this.$emit('jobUpdated', jobId);
                } else {
                    console.error('Error deleting the item:', response.statusText);
                }
            } catch (error) {
                console.error('Error deleting the item:', error);
            }

            // Close modal
            this.openDelModal(false);
        },
        fetchAllCientProperties() {
            fetch (`http://localhost:8081/api/client/${this.jobDetails.clientDetails.clientId}/properties`)
                .then(response => response.json())
                .then(data => {
                    // Format data to match clientAddresses object
                    let allProperties = {};
                    let allPropertyInfo = {};

                    for (let i = 0; i < data.length; i++) {
                        let e_property = data[i];

                        allProperties[e_property.propertyId] = `${e_property.address}, ${e_property.postalCode}`;
                        allPropertyInfo[e_property.propertyId] = {
                            address: e_property.address,
                            postalCode: e_property.postalCode,
                        };
                    }

                    this.clientProperties = allProperties;
                    this.allPropertyInfo = allPropertyInfo;
                });
        },
        handleAddressChange(newAddressId) {
            // Updates the address in the jobEdit object
            const newAddress = this.allPropertyInfo[newAddressId].address;
            const newPostalCode = this.allPropertyInfo[newAddressId].postalCode;

            this.jobEdit.jobAddress.id = newAddressId;
            this.jobEdit.jobAddress.address = newAddress;
            this.jobEdit.jobAddress.postalCode = newPostalCode;
        },
        calculateHours(startTime, endTime) {
            // Takes in startTime (string in HH:mm format) and endTime (string in HH:mm format) and returns int numHours rounded to closest hour
            // Assume they are in 24-hour format and on the same day
            const [startHour, startMin] = startTime.split(":");
            const [endHour, endMin] = endTime.split(":");

            const startTotal = parseInt(startHour) + (parseInt(startMin) / 60);
            const endTotal = parseInt(endHour) + (parseInt(endMin) / 60);

            return Math.round(endTotal - startTotal);
        },
        formatTime(timeStr) {
            // If time is alr in HH:mm:ss format, return as is
            // If time is in HH:mm format, add ":00" to the end

            if (timeStr.length == 5) {
                return `${timeStr}:00`;
            } else {
                return timeStr;
            }
        },
        openErrorMsgCollapse(toOpen = true) {
            if (toOpen) {
                this.errorMsgCollapse.show();
            } else {
                this.errorMsgCollapse.hide();
            }
        },
        openErrorMsgCollapseCompleted(toOpen = true) {
            if (toOpen) {
                this.errorMsgCollapseCompleted.show();
            } else {
                this.errorMsgCollapseCompleted.hide();
            }
        },
        handleArrivalFileUpload(event) {
            // Placeholder function for handling file uploads
            this.arrivalImg = event.target.files[0];
        },
        handleCompletionFileUpload(event) {
            // Placeholder function for handling file uploads
            this.completionImg = event.target.files[0];
        },
        submitArrivalImg() {
            const arrivalImg = this.arrivalImg;

            // Placeholder function for submitting arrival image (set arrivalProofUploaded to true) (Change later ADAMBFT)
            this.jobDetails.arrivalProofUploaded = true;

            // Close the error message
            this.openErrorMsgCollapse(false);
        },
        submitCompletedImg() {
            const completionImg = this.completionImg;

            // Placeholder function for submitting completion image (set completionProofUploaded to true) (Change later ADAMBFT)
            this.jobDetails.completionProofUploaded = true;

            // Close the error message
            this.openErrorMsgCollapseCompleted(false);
        },
        confirmJob() {
            // Send API call to set job status to COMPLETED without photo proof
            // Placeholder, replace with actual API call (ADAMBFT)
        },
    },
    mounted() {
        // Sets up main modal
        // Note to self: 'Next tick' is necessary to ensure that the modal is loaded in DOM before it is accessed
        this.$nextTick(() => {
            this.mainModal = new bootstrap.Modal(
                document.getElementById(
                    `job-modal-${this.jobDetails.appointmentId}`
                )
            );

            this.delModal = new bootstrap.Modal(
                document.getElementById(
                    `job-del-modal-${this.jobDetails.appointmentId}`
                )
            );

            this.errorMsgCollapse = new bootstrap.Collapse(
                document.getElementById(
                    `error-msg-collapse-${this.jobDetails.appointmentId}`
                )
            );

            this.errorMsgCollapseCompleted = new bootstrap.Collapse(
                document.getElementById(
                    `error-msg-collapse-completed-${this.jobDetails.appointmentId}`
                )
            );
        });

        // Duplicates jobDetails object for editing
        this.jobEdit = JSON.parse(JSON.stringify(this.jobDetails));

        // Auto update time every minute
        setInterval(() => {
            this.currentDateTime = new Date();
        }, 60000);

        // Pull clients properties from API
        this.fetchAllCientProperties();

        // Pull actual employees from API
        fetch("http://localhost:8081/api/employee")
            .then((response) => response.json())
            .then((data) => {
                // Format data to match allEmployees object
                var formattedEmployees = {};

                for (var i = 0; i < data.length; i++) {
                    var employee = data[i];
                    formattedEmployees[employee.employeeId] = employee.name;
                }

                this.allEmployees = formattedEmployees;
            });
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
    z-index: 1000;
    pointer-events: none;
    width: 250px;
}

@media (max-width: 768px) {
    .compressed-job-card {
        width: auto;
    }
}

.showPopoverRight {
    left: calc(100% + 10px);
}

.showPopoverLeft {
    right: calc(100% + 10px);
}

.compressed-parent-container {
    position: relative;
    cursor: pointer;
}
</style>
