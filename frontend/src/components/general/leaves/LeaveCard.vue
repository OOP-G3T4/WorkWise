<template>
    <div class="accordion" :id="`leave-id-${leaveDetails.id}`">
        <div class="accordion-item" :class="displayMcError() ? 'border border-3 border-danger' : ''">
            <!-- Top Section (Always in view) -->
            <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="`#flush-collapse-leave-id-${leaveDetails.id}`" aria-expanded="false" :aria-controls="`#flush-collapse-leave-id-${leaveDetails.id}`">                    
                    <div class="container-fluid p-0">
                        <!-- Error Message -->
                        <div v-if="displayMcError()" class="row">
                            <div class="col-auto">
                                <p class="text-danger m-0 mb-3 text-sm"><font-awesome-icon class="me-2" icon="fa-solid fa-circle-exclamation" />Did not upload MC photo on time</p>
                            </div>
                        </div>

                        <!-- Main TOP Content -->
                        <div class="row align-items-center">
                            <!-- Checkbox -->
                             <div class="col-auto">
                                <input v-if="leaveStatusCheckbox.includes(leaveDetails.status)" type="checkbox" class="form-check-input checkbox-leave-card" v-model="isSelected" />
                                <font-awesome-icon v-else :class="leaveStatusClassMap[leaveDetails.status]" :icon="leaveStatusIconMap[leaveDetails.status]" />
                             </div>

                            <!-- Leave Type Icon -->
                            <div class="col-auto">
                                <h5 class="m-0"><font-awesome-icon class="me-2" :class="leaveTypeClass(leaveDetails.leaveType)" :icon="leaveTypeIconMap[leaveDetails.leaveType]" />{{ leaveDetails.leaveType }}</h5>
                            </div>

                            <!-- Employee Name -->
                            <div class="col-auto d-flex align-items-center">
                                <img src="https://placehold.co/200x200?text=Profile+Pic" alt="Employee Image" class="emp-img me-2" />
                                <p class="m-0">{{ employeeDetails.name }}</p>
                            </div>

                            <!-- Date(s) -->
                            <div class="col-auto">
                                <p class="m-0"><font-awesome-icon class="me-2" icon="fa-solid fa-calendar" />{{ convertDatesToHumanReadble(leaveDetails.startDate, leaveDetails.endDate) }}</p>
                            </div>
                        </div>
                    </div>
                </button>
            </h2>

            <!-- Bottom Section (When expanded) -->
            <div :id="`flush-collapse-leave-id-${leaveDetails.id}`" class="accordion-collapse collapse" :data-bs-parent="`#leave-id-${leaveDetails.id}`">
                <div class="accordion-body container-fluid">
                    <div class="row g-3">
                        <div class="col-auto">
                            <p class="m-0"><font-awesome-icon class="me-2" icon="fa-solid fa-phone" />{{ convertPhoneToHumanReadable(employeeDetails.phone_number) }}</p>
                        </div>

                        <div class="col-auto">
                            <p class="m-0"><font-awesome-icon class="me-2" icon="fa-solid fa-envelope" />{{ employeeDetails.email }}</p>
                        </div>

                        <div class="col-12">
                            <p class="m-0"><font-awesome-icon class="me-2" icon="fa-solid fa-comment" />{{ leaveDetails.comments }}</p>
                        </div>
                    </div>
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
            required: true,
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
            },

            leaveStatusClassMap: {
                "Approved": "text-success",
                "Rejected": "text-danger",
            },

            isSelected: false,

            leaveStatusCheckbox: ['Pending'] // The leave status(es) that will have a checkbox
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
                year: 'numeric',      // For the full year: 2024
            };

            // Format the date with the locale string
            const formattedDate = dateObj.toLocaleString('en-US', options);

            // Manually add the parentheses around the weekday
            return `${formattedDate} (${dateObj.toLocaleString('en-US', { weekday: 'short' })})`;
        },
        leaveTypeClass(leaveType) {
            return {
                // Can reformat later, not sure what color yet

                // "text-secondary": leaveType == "MC",
                "text-secondary": leaveType == "AL" || leaveType == "MC",
            };
        },
        displayMcError() {
            // Skip if not MC || not Pending || MC proof alr uploaded
            if (this.leaveDetails.leaveType != "MC" || this.leaveDetails.status != "Pending" || this.leaveDetails.mcProofUploaded) {
                return false;
            }

            var applyDatetime = new Date(this.leaveDetails.applicationDateTime);
            var today = new Date();
            
            // Return true if photo not uploaded by end of day of application
            return applyDatetime.toDateString() < today.toDateString();
        }
    },
    mounted() {
        if (this.leaveStatusCheckbox.includes(this.leaveDetails.status)) {
            // Prevent default when checkbox clicked (ie. dont expand/ collapse accordion)
            const checkboxButton = this.$el.querySelector('.checkbox-leave-card');
            const accordionButton = this.$el.querySelector('.accordion-button');

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
    },
};
</script>

<style scoped>
.emp-img {
    height: 35px;
    aspect-ratio: 1/1;
    border-radius: 50%;
}

.text-sm {
    font-size: 0.75em;
}
</style>