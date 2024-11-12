<script setup>
import DropdownSearch from "../../general/forms/DropdownSearch.vue"
import * as bootstrap from "bootstrap";
</script>

<template>
    <!-- Modal -->
    <div class="modal fade" id="adminAddNewJobModal" tabindex="-1" aria-labelledby="adminAddNewJobModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- HEADER -->
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="adminAddNewJobModalLabel">Create New Subscription</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <!-- BODY -->
                <div class="modal-body">
                    <!-- Error Message -->
                    <p v-if="errorMsg" class="text-danger">{{ errorMsg }}</p>

                    <!-- [1] Select Client -->
                    <DropdownSearch :items="allClients" fieldName="Client" :inputValue="clientId" :uniqueComponentId="uniqueComponentId" :showId="true" @valChange="clientChange" />

                    <!-- [2] Package Id -->
                    <DropdownSearch :items="formattedPackages" fieldName="Package" :inputValue="packageId" :uniqueComponentId="uniqueComponentId" @valChange="packageChange" />

                    <p v-if="packageId"><span class="fw-bold">Frequency: </span>{{ allPackages[packageId].type }}</p>

                    <!-- [3] Day of the Week -->
                    <div class="form-floating mb-3">
                        <select class="form-select" :id="`day-of-week-${uniqueComponentId}`" aria-label="Day selection" v-model="jobDay">
                            <option v-for="day in daysOfWeek">
                                {{ day }}
                            </option>
                        </select>

                        <label :for="`day-of-week-${uniqueComponentId}`">Day of Week</label>
                    </div>

                    <!-- [4] Start Time -->
                    <div class="form-floating mb-3">
                        <input type="time" class="form-control" :id="`start-time-${uniqueComponentId}`" v-model="startTime" onfocus="this.showPicker()">
                        <label :for="`start-time-${uniqueComponentId}`">Start Time</label>
                    </div>

                    <p v-if="endTimeObj">
                        <span class="fw-bold">Duration: </span> {{ `${dateObjReadable(startTimeObj)} - ${dateObjReadable(endTimeObj)} (${allPackages[packageId].hours} hrs)` }}
                    </p>

                    <!-- [5] Property -->
                    <DropdownSearch :items="allProperties" fieldName="Property" :inputValue="propertyId" :uniqueComponentId="uniqueComponentId" @valChange="propertyChange" :isDisabled="!clientId" />
                </div>

                <!-- FOOTER -->
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-light" @click="resetForm()">Reset</button>

                    <div>
                        <button type="button" class="btn btn-light" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary ms-2" @click="saveJob()" :disabled="!readySubmit">Create</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // Selected values
            clientId: "",
            packageId: "",
            jobDay: "",
            startTime: "",
            propertyId: "",

            // Constraints
            earliestTime: 8,
            latestTime: 22,

            // Identifier
            uniqueComponentId: "new_job_modal_9jd28dc3",

            // Modal
            jobModal: null,

            // Possible Values
            allClients: {},
            allPackages: {},
            allProperties: {},
            daysOfWeek: ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"],
        };
    },
    watch: {
        clientId() {
            this.updateClientsProperties();

            // Reset propertyId
            this.propertyId = "";
        },
    },
    computed: {
        formattedPackages() {
            let arr_pkgs = {};
            let data = this.allPackages;

            for (let pkgId in data) {
                arr_pkgs[pkgId] = pkgId;
            }

            return arr_pkgs;
        },
        earliestTimeObj() {
            let date = new Date();
            date.setHours(this.earliestTime);
            date.setMinutes(0);

            return date;
        },
        latestTimeObj() {
            let date = new Date();
            date.setHours(this.latestTime);
            date.setMinutes(0);

            return date;
        },
        startTimeObj() {
            let date = new Date();
            let startHour = parseInt(this.startTime.split(":")[0]);
            let startMinute = parseInt(this.startTime.split(":")[1]);

            date.setHours(startHour);
            date.setMinutes(startMinute);

            return date;
        },
        endTimeObj() {
            if (!this.startTime || !this.packageId) {
                return null;
            }

            let startTime = this.startTimeObj;
            let numHours = this.allPackages[this.packageId].hours;

            let endTime = new Date(startTime.getTime() + numHours * 60 * 60 * 1000);
            return endTime;
        },
        errorMsg() {
            // Check if start and end times are within constraints
            if (this.startTimeObj < this.earliestTimeObj) {
                return "Start time is too early";
            } else if (this.startTimeObj > this.latestTimeObj) {
                return "Start time is too late";
            } else if (this.packageId && this.endTimeObj > this.latestTimeObj) {
                return "End time is too late";
            } else {
                return "";
            }
        },
        readySubmit() {
            return this.clientId && this.packageId && this.jobDay && this.startTime && this.propertyId && !this.errorMsg;
        },
    },
    methods: {
        saveJob() {
            // Save subscription details to database
            if (this.errorMsg) {
                return;
            }

            // Pad startTime with seconds
            let startTime = `${this.startTime}:00`;

            let data = {
                "client": {
                    "clientId": this.clientId
                },
                "property": {
                    "propertyId": this.propertyId
                },
                "selectedPackage": {
                    "packageId": this.packageId
                },
                "jobDay": this.jobDay,
                "jobStartTime": startTime,
                "subscriptionStatus": "ACTIVE"
            }

            fetch(`${this.$apiUrl}/subscription`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(data),
            })
                .then((response) => response.json())
                .then((data) => {
                    // Close modal
                    this.openModal(false);

                    // Reset form
                    this.resetForm();

                    // Emit event to parent
                    this.$emit("jobUpdated", data);
                });
        },
        clientChange(clientId) {
            this.clientId = clientId;
        },
        packageChange(packageId) {
            this.packageId = packageId;
        },
        dateObjReadable(dateObj) {
            return dateObj.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        },
        propertyChange(propertyId) {
            this.propertyId = propertyId;
        },
        updateClientsProperties() {
            if (this.clientId === "") {
                return;
            }

            fetch(`${this.$apiUrl}/client/${this.clientId}/properties`)
                .then((response) => response.json())
                .then((data) => {
                    let allProperties = {};

                    for (let i = 0; i < data.length; i++) {
                        let e_property = data[i];

                        allProperties[e_property.propertyId] = `${e_property.address}, ${e_property.postalCode}`;
                    }

                    this.allProperties = allProperties;
                });
        },
        resetForm() {
            this.clientId = "";
            this.packageId = "";
            this.jobDay = "";
            this.startTime = "";
            this.propertyId = "";
        },
        openModal(toOpen) {
            if (toOpen) {
                this.jobModal.show();
            } else {
                this.jobModal.hide();
            }
        },
    },
    mounted() {
        // Initialize modal
        this.jobModal = new bootstrap.Modal(document.getElementById("adminAddNewJobModal"));

        // Fetch all clients
        fetch(`${this.$apiUrl}/client`)
            .then((response) => response.json())
            .then((data) => {
                let allClients = {};

                for (let e_client of data) {
                    allClients[e_client.clientId] = e_client.name;
                }

                this.allClients = allClients;
            });
        
        // Fetch all packages
        fetch(`${this.$apiUrl}/package`)
            .then((response) => response.json())
            .then((data) => {
                let allPackages = {};

                for (let i = 0; i < data.length; i++) {
                    let e_package = data[i];

                    allPackages[e_package.packageId] = e_package;
                }

                this.allPackages = allPackages;
            });
    },
};
</script>