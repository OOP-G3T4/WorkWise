<script setup>
import { mapState } from 'vuex';
import * as bootstrap from 'bootstrap';
</script>

<template>
    <!-- Leave Card -->
    <div class="accordion mb-3" :id="`leave-id-${leaveDetails.id}`">
        <div class="accordion-item" :class="accordianClasses">
            <!-- Top Section (Always in view) -->
            <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="`#flush-collapse-leave-id-${leaveDetails.id}`" aria-expanded="false" :aria-controls="`#flush-collapse-leave-id-${leaveDetails.id}`" :id="`accordian-btn-${leaveDetails.id}`">                    
                    <div class="container-fluid p-0">
                        <!-- Error Message -->
                        <div v-if="displayMcError()" class="row">
                            <div class="col-auto">
                                <p class="text-danger m-0 mb-3 fs-7"><font-awesome-icon class="me-2" icon="fa-solid fa-circle-exclamation" />Did not upload MC photo on time</p>
                            </div>
                        </div>

                        <!-- Upload Photo Reminder -->
                        <div v-if="displayMcReminder()" class="row">
                            <div class="col-auto">
                                <p class="text-primary m-0 mb-3 fs-7"><font-awesome-icon class="me-2" icon="fa-solid fa-camera" />{{ displayMcReminder() }}</p>
                            </div>
                        </div>

                        <!-- Main TOP Content -->
                        <div class="row align-items-center gy-3">
                            <!-- Checkbox -->
                             <div class="col-auto">
                                <input v-if="showCheckBox()" type="checkbox" class="form-check-input" :id="`checkbox-leave-card-${leaveDetails.id}`" v-model="isSelected" />
                                <font-awesome-icon v-else :class="leaveStatusClassMap[leaveDetails.status]" :icon="leaveStatusIconMap[leaveDetails.status]" />
                             </div>

                            <!-- Leave Type Icon -->
                            <div class="col-auto">
                                <h5 class="m-0 fs-6 fs-md-5"><font-awesome-icon class="me-2" :class="leaveTypeClass(leaveDetails.leaveType)" :icon="leaveTypeIconMap[leaveDetails.leaveType]" />{{ leaveDetails.leaveType }}</h5>
                            </div>

                            <!-- Employee Name -->
                            <div v-if="userType=='admin'" class="col-auto d-flex align-items-center">
                                <img src="https://placehold.co/200x200?text=Profile+Pic" alt="Employee Image" class="emp-img me-2 d-none d-md-block" />
                                <h6 class="m-0">{{ employeeDetails.name }}</h6>
                            </div>

                            <!-- Date(s) -->
                            <div class="col-12 col-sm-auto">
                                <p class="m-0 fs-7 fs-md-6"><font-awesome-icon class="me-2 d-none d-md-inline-block" icon="fa-solid fa-calendar" />{{ convertDatesToHumanReadble(leaveDetails.startDate, leaveDetails.endDate) }}</p>
                            </div>
                        </div>
                    </div>
                </button>
            </h2>

            <!-- Bottom Section (When expanded) -->
            <div :id="`flush-collapse-leave-id-${leaveDetails.id}`" class="accordion-collapse collapse" :data-bs-parent="`#leave-id-${leaveDetails.id}`">
                <div class="accordion-body container-fluid">
                    <div class="row g-3">
                        <!-- Phone Num -->
                        <div v-if="userType == 'admin'" class="col-auto">
                            <p class="m-0 fs-7 fs-md-6"><font-awesome-icon class="me-2" icon="fa-solid fa-phone" />{{ convertPhoneToHumanReadable(employeeDetails.phone_number) }}</p>
                        </div>

                        <!-- Email -->
                        <div v-if="userType == 'admin'" class="col-auto">
                            <p class="m-0 fs-7 fs-md-6"><font-awesome-icon class="me-2" icon="fa-solid fa-envelope" />{{ employeeDetails.email }}</p>
                        </div>

                        <!-- Comments -->
                        <div class="col-12">
                            <p class="m-0 fs-7 fs-md-6"><font-awesome-icon class="me-2" icon="fa-solid fa-comment" />{{ leaveDetails.comments }}</p>
                        </div>

                        <!-- Photo Upload Button -->
                        <div v-if="displayMcReminder()">
                            <button class="btn btn-primary btn-sm" @click="showPicModal(true)"><font-awesome-icon class="me-2" icon="fa-solid fa-camera" />Upload Photo</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Upload Photo Modal -->
    <div class="modal fade" :id="`upload-photo-modal-${leaveDetails.id}`" tabindex="-1" :aria-labelledby="`upload-photo-modal-${leaveDetails.id}-label`" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" :id="`upload-photo-modal-${leaveDetails.id}-label`">Upload MC Photo Proof</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body">
                    <input class="form-control" type="file" @change="handleFileUpload"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" @click="handleSave()">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        leaveDetails: {
            type: Object,
            required: true,
        },
        employeeDetails: {
            type: Object,
            required: false,
        },
    },
    computed: {
        ...mapState(["userType"]),  // Access userType from Vuex state
        
        accordianClasses() {
            return {
                "border border-3 border-danger": this.displayMcError(),
                "border border-3 border-primary": this.displayMcReminder(),
            };
        },
    },
    data() {
        return {
            leaveTypeIconMap: {
                "MC": "fa-solid fa-briefcase-medical",
                "AL": "fa-solid fa-umbrella-beach",
            },

            leaveStatusIconMap: {
                "Approved": "fa-solid fa-circle-check",
                "Rejected": "fa-solid fa-circle-xmark",
                "Pending": "fa-solid fa-clock",
            },

            leaveStatusClassMap: {
                "Approved": "text-success",
                "Rejected": "text-danger",
                "Pending": "text-secondary",
            },

            isSelected: false,

            leaveStatusCheckbox: ['Pending'], // The leave status(es) that will have a checkbox

            uploadPhotoModal: null,

            imageUploaded: null,
        };
    },
    methods: {
        convertPhoneToHumanReadable(phoneStr) {
            if (phoneStr.length != 8) {
                return phoneStr;
            }

            return phoneStr.substring(0,4) + "-" + phoneStr.substring(4,8);
        },
        convertDatesToHumanReadble(startDate, endDate) {
            // If only 1 day, return just that day
            startDate = new Date(startDate);
            endDate = new Date(endDate);

            var prettyStartDate = this.prettifyDate(startDate);
            var prettyEndDate = this.prettifyDate(endDate);

            if (startDate.toDateString() == endDate.toDateString()) {
                return prettyStartDate;
            }

            return prettyStartDate + " - " + prettyEndDate;
        },
        prettifyDate(dateObj) {
            const options = { 
                day: 'numeric',       // For the day: 5
                month: 'short',       // For the short month: Oct
                year: '2-digit',      // For the full year: 2024
            };

            // Format the date with the locale string
            const formattedDate = dateObj.toLocaleString('en-US', options);
            const reformattedDate = formattedDate.replace(", ", " '"); // Remove comma, add apostrophe before year

            // Manually add the parentheses around the weekday
            return `${reformattedDate} (${dateObj.toLocaleString('en-US', { weekday: 'short' })})`;
        },
        leaveTypeClass(leaveType) {
            return {
                // Can reformat later, not sure what color yet

                // "text-secondary": leaveType == "MC",
                "text-secondary": leaveType == "AL" || leaveType == "MC",
            };
        },
        displayMcError() {
            // Skip if not Admin || not MC || not Pending || MC proof alr uploaded
            if (this.userType !== "admin" || this.leaveDetails.leaveType != "MC" || this.leaveDetails.status != "Pending" || this.leaveDetails.mcProofUploaded) {
                return false;
            }

            var submitDeadline = new Date(this.leaveDetails.applicationDateTime);
            submitDeadline.setHours(23, 59, 59, 999);

            var now = new Date();
            
            // Return true if photo not uploaded by end of day of application
            return now > submitDeadline;
        },
        displayMcReminder() {
            // Skip if not Employee || not MC || not Pending || MC proof alr uploaded
            if (this.userType !== "employee" || this.leaveDetails.leaveType != "MC" || this.leaveDetails.status != "Pending" || this.leaveDetails.mcProofUploaded) {
                return false;
            }

            var submitDeadline = new Date(this.leaveDetails.applicationDateTime);
            submitDeadline.setHours(23, 59, 59, 999);

            var now = new Date();

            // If deadline not passed, set reminder
            if (now < submitDeadline) {
                return "Upload photo to 2359 today";
            } else {
                return "Photo upload deadline passed!";
            }
        },
        showCheckBox() {
            return this.userType == 'admin' && this.leaveStatusCheckbox.includes(this.leaveDetails.status);
        },
        showPicModal(show) {
            if (show) {
                this.uploadPhotoModal.show();
            } else {
                this.uploadPhotoModal.hide();
            }
        },
        handleFileUpload(e) {
            this.imageUploaded = e.target.files[0];
        },
        handleSave() {
            // Save the image to the server (adambft)
            console.log(this.imageUploaded);
            this.showPicModal(false);
        },
    },
    mounted() {
        if (this.showCheckBox()) {
            // Prevent default when checkbox clicked (ie. dont expand/ collapse accordion)

            const checkboxButton = document.getElementById(`checkbox-leave-card-${this.leaveDetails.id}`);
            const accordionButton = document.getElementById(`accordian-btn-${this.leaveDetails.id}`);

            checkboxButton.addEventListener('click', (e) => {
                e.stopPropagation();
            });

            checkboxButton.addEventListener('mouseenter', (e) => {
                accordionButton.setAttribute('data-bs-toggle', '');
            });

            checkboxButton.addEventListener('mouseleave', (e) => {
                accordionButton.setAttribute('data-bs-toggle', 'collapse');
            });
        }

        // Initialize modal if needed
        if (this.displayMcReminder()) {
            this.uploadPhotoModal = new bootstrap.Modal(document.getElementById(`upload-photo-modal-${this.leaveDetails.id}`));
        }
    },
};
</script>

<style scoped>
.emp-img {
    height: 35px;
    aspect-ratio: 1/1;
    border-radius: 50%;
}
</style>