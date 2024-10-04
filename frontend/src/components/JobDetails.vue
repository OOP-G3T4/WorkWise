<template>
    <div
        :class="{ 'com-ver-parent': isCompressed }"
        :style="
            isCompressed
                ? { backgroundColor: statusColorMap[jobDetails.jobStatus] }
                : {}
        "
        @mouseover="showHoverContent()"
        @mouseleave="hideHoverContent()"
        @click="openMainModal(true)"
    >
        <!-- Button trigger modal -->
        <button
            type="button"
            class="btn"
            data-bs-toggle="modal"
            :data-bs-target="`#${jobDetails.appointmentId}`"
            :class="{
                'com-ver-popover': isCompressed,
                'border border-3 border-danger':
                    hasJobStarted(jobDetails.startTime, jobDetails.date) &&
                    !jobDetails.arrivalProofUploaded,
            }"
            :style="
                isCompressed && !isHovering
                    ? { display: 'none' }
                    : { display: 'block' }
            "
            @click="openMainModal(true)"
        >
            <div class="job-container">
                <div
                    class="text-danger"
                    v-if="
                        hasJobStarted(jobDetails.startTime, jobDetails.date) &&
                        !jobDetails.arrivalProofUploaded
                    "
                >
                    Action Required: Worker Late
                </div>
                <div>{{ jobDetails.clientDetails.clientName }}</div>
                <div>{{ jobDetails.jobAddress }}</div>
                <div>
                    {{ Object.keys(jobDetails.cleaners).length }} cleaners
                </div>
                <hr class="border-white border-3 rounded" />
                <div class="job-status-container">
                    <div
                        class="job-status-circle"
                        :style="{
                            backgroundColor:
                                statusColorMap[jobDetails.jobStatus],
                        }"
                    ></div>
                    <div>{{ jobDetails.jobStatus }}</div>
                </div>
            </div>
        </button>
    </div>

    <!-- Modal -->
    <div
        class="modal fade"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
        :id="jobDetails.appointmentId"
        tabindex="-1"
    >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">
                        Appointment ID: {{ jobDetails.appointmentId }}
                        <div class="job-status-container">
                            <div
                                class="job-status-circle"
                                :style="{
                                    backgroundColor:
                                        statusColorMap[jobDetails.jobStatus],
                                }"
                            ></div>
                            <div>{{ jobDetails.jobStatus }}</div>
                        </div>
                    </h1>
                    <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                    ></button>
                </div>
                <div class="modal-body">
                    <div>
                        <div class="job-status-container">
                            <div
                                class="job-status-circle"
                                :style="{
                                    backgroundColor:
                                        statusColorMap[jobDetails.jobStatus],
                                }"
                            ></div>
                            <div>
                                <div>Client Name</div>
                                <div>
                                    {{ jobDetails.clientDetails.clientName }}
                                </div>
                            </div>
                        </div>
                    </div>

                    <input type="text" v-model="jobEdit.appointmentId" />

                    {{ jobDetails.appointmentId }}

                    <div>
                        {{ jobDetails.packageType }}

                        {{ jobDetails.jobAddress }}

                        {{ jobDetails.date }}

                        {{ jobDetails.startTime }}

                        {{ jobDetails.endTime }}

                        {{ jobDetails.cleaners.cleaner1.name }}

                        {{ jobDetails.cleaners.cleaner2.name }}

                        {{ jobDetails.arrivalProofUploaded }}

                        {{ jobDetails.clientDetails.clientName }}

                        {{ jobDetails.clientDetails.clientContact }}

                        {{ jobDetails.clientDetails.clientEmail }}

                        {{ jobDetails.clientDetails.clientAddress }}

                        {{ jobDetails.clientDetails.clientGender }}

                        {{ jobDetails.clientDetails.clientAge }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import * as bootstrap from "bootstrap";

export default {
    data() {
        return {
            isCompressed: false,
            isHovering: false,
            mainModal: null,

            jobDetails: {
                appointmentId: Math.ceil(Math.random() * 1000000),
                packageType: "A",
                jobAddress: "52 Bukit Batok West Ave 6",
                date: "2024-10-02", // ISO 8601 standard
                startTime: "18:00:00",
                endTime: "19:00:00",
                cleaners: {
                    cleaner1: {
                        id: "1",
                        name: "John",
                    },
                    cleaner2: {
                        id: "2",
                        name: "Jane",
                    },
                },
                arrivalProofUploaded: false,
                jobStatus: "Not Started",
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

            jobEdit: {
                appointmentId: "1001",
                packageType: "A",
                jobAddress: "52 Bukit Batok West Ave 6",
                date: "2024-10-02", // ISO 8601 standard
                startTime: "18:00:00",
                endTime: "19:00:00",
                cleaners: {
                    cleaner1: {
                        id: "1",
                        name: "John",
                    },
                    cleaner2: {
                        id: "2",
                        name: "Jane",
                    },
                },
                arrivalProofUploaded: false,
                jobStatus: "Not Started",
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

            statusColorMap: {
                "In Progress": "#e3b322",
                Completed: "#22e362",
                "Not Started": "#858585",
            },
        };
    },
    methods: {
        // Returns True if job is within X minutes of start time
        hasJobStarted(startTime, startDate, bufferMinutes = 15) {
            const jobStartDateTime = new Date(`${startDate}T${startTime}`);

            const currentTime = new Date();

            if (currentTime < jobStartDateTime) {
                return false;
            } else {
                const diff = (currentTime - jobStartDateTime) / 60000;
                return diff > bufferMinutes;
            }
        },

        showHoverContent() {
            this.isHovering = true;
        },

        hideHoverContent() {
            this.isHovering = false;
        },

        openMainModal(isOpen) {
            if (isOpen) {
                this.mainModal.show();
            } else {
                this.mainModal.hide();
            }
        },
    },
    mounted() {
        this.mainModal = new bootstrap.Modal(
            document.getElementById(`${this.jobDetails.appointmentId}`)
        );
    },
};
</script>

<style scoped>
.job-container {
    padding: 20px;
    /* width: 200px; */
    background-color: #e8e8e8;
    border-radius: 10px;
}

.job-status-container {
    display: flex;
    flex-direction: row;
}

.job-status-circle {
    width: 25px;
    height: 25px;
    border-radius: 50%;
}

.com-ver-popover {
    position: absolute;
    top: 0;
    left: 105%;
    z-index: 1000;
    pointer-events: none;
}

.com-ver-parent {
    position: relative;
    width: 200px;
    height: 200px;
    cursor: pointer;
    border: 1px solid black;
}
</style>
