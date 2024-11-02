<script setup>
import LeaveCard from '../../general/leaves/LeaveCard.vue';
import { mapState } from "vuex";
import * as bootstrap from 'bootstrap';
</script>

<template>
    <!-- Past Leaves -->
    <div v-if="showPast" v-for="e_leave in leavesBeforeToday" class="mb-3">
        <LeaveCard v-if="showCardLogic(e_leave)" :leaveDetails="e_leave" @mc-uploaded="mcUploadTrigger" />
    </div>

    <!-- Today Line -->
    <div v-if="showPast && showUpcoming" class="d-flex justify-content-center align-items-center mb-4">
        <hr class="w-100 my-0 border border-dark rounded" />
        <p class="mx-3 my-0 text-secondary">Today</p>
        <hr class="w-100 my-0 border border-dark rounded" />
    </div>

    <!-- Upcoming Leaves -->
    <div v-if="showUpcoming" v-for="e_leave in leavesAfterToday" class="mb-3">
        <LeaveCard v-if="showCardLogic(e_leave)" :leaveDetails="e_leave" @mc-uploaded="mcUploadTrigger" />
    </div>

    <!-- Add New Application Modal -->
    <div class="modal fade" id="empAddLeaveModal" tabindex="-1" aria-labelledby="empAddLeaveModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="empAddLeaveModalLabel">New Application</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body">
                    <!-- Error Msg -->
                    <div v-if="errorMsg" class="alert alert-danger text-center p-2">
                        <font-awesome-icon icon="fa-solid fa-circle-exclamation" class="me-2" />{{ errorMsg }}
                    </div>

                    <!-- [1] Application Type (MC or AL) -->
                    <div class="form-floating">
                        <select class="form-select" id="appTypeEmpLeaves" aria-label="Floating label select example" v-model="appType">
                            <option value="MC">Medical Certificate</option>
                            <option value="AL">Annual Leave</option>
                        </select>

                        <label for="appTypeEmpLeaves">Application Type</label>
                    </div>

                    <!-- [2] Start Date -->
                    <div class="form-floating mt-3">
                        <input type="date" class="form-control" id="startDateEmpLeaves" v-model="startDate" :max="endDate" onfocus="this.showPicker()" />
                        <label for="startDateEmpLeaves">Start Date</label>
                    </div>

                    <!-- [3] End Date -->
                    <div class="form-floating mt-3">
                        <input type="date" class="form-control" id="endDateEmpLeaves" v-model="endDate" :min="startDate" onfocus="this.showPicker()" />
                        <label for="endDateEmpLeaves">End Date</label>
                    </div>

                    <!-- [4] Comments -->
                    <div class="form-floating mt-3">
                        <textarea class="form-control" placeholder="Comments" id="commentsEmpLeaves" style="height: 100px;" v-model="comment"></textarea>
                        <label for="commentsEmpLeaves">Comments</label>
                    </div>

                    <!-- [5] Upload Photo (If MC) -->
                    <template v-if="appType == 'MC'">
                        <hr class="mt-3" />
                        <h6 class="text-secondary ms-1"><font-awesome-icon icon="fa-solid fa-camera" class="me-2" />Upload Photo Proof</h6>
                        <input class="form-control mt-3" type="file" @change="handleFileUpload"/>
                    </template>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" @click="handleSubmit()">Submit</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        leaveDetailsArr: {
            type: Array,
            required: true,
        },
        showPast: {
            type: Boolean,
            required: false,
            default: false,
        },
        showUpcoming: {
            type: Boolean,
            required: false,
            default: true,
        },
        showRejected: {
            type: Boolean,
            required: false,
            default: false,
        },
    },
    data() {
        return {
            // Leaves sorting
            leavesBeforeToday: [],
            leavesAfterToday: [],

            // New Leave Application Modal
            appType: "MC",
            startDate: "",
            endDate: "",
            comment: "",
            imgUploaded: null,
            errorMsg: "",

            // Apply Leave Modal
            leaveModal: null,
        }
    },
    watch: {
        leaveDetailsArr: {
            handler(newVal) {
                this.updateBeforeAfterTodayArrs();
            },
            deep: true, //Watch for changes in within array, rather than just the pointer
        },
    },
    computed: {
        ...mapState(["userId"]),
    },
    methods: {
        clearNewLeaveModal() {
            this.appType = "MC";
            this.startDate = "";
            this.endDate = "";
            this.comment = "";
            this.imgUploaded = null;
            this.errorMsg = "";
        },
        sortByDate(arrToSort) {
            arrToSort.sort((a, b) => {
                // Convert startDate and endDate to Date objects
                const startA = new Date(a.startDate);
                const startB = new Date(b.startDate);

                const endA = new Date(a.endDate);
                const endB = new Date(b.endDate);
                
                // Sort by startDate (latest first)
                if (startA < startB) return -1; // b comes first
                if (startA > startB) return 1; // a comes first

                // If startDate is the same, sort by endDate (oldest first)
                if (endA > endB) return 1; // b comes first
                if (endA < endB) return -1; // a comes first

                return 0; // they are equal
            });

            return
        },
        showCardLogic(leaveObj) {
            if (this.showRejected) {
                return true;
            }

            return leaveObj.status !== "REJECTED";
        },
        handleFileUpload(e) {
            this.imgUploaded = e.target.files[0];
        },
        async handleSubmit() {
            // Check for errors
            if (this.isError()) {
                return;
            }

            // SUBMIT FORM ========================================

            // [1] Prepare postData
            const empLeave = {
                "employee": {
                    "employeeId": this.userId,
                },
                "leaveType": this.appType, 
                "applicationDateTime": new Date().toISOString(),
                "startDate": this.startDate,
                "endDate": this.endDate, 
                "status": "PENDING",
                "comments": this.comment,
            }
  
            // [2] Send POST request
            const postData = new FormData();
            postData.append('employeeLeave', JSON.stringify(empLeave));

            if (this.imgUploaded && this.appType == "MC") {
                postData.append('file', this.imgUploaded);
            }

            try {
                const response = await fetch('http://localhost:8081/api/employee-leave/apply', {
                    method: 'POST',
                    body: postData,
                });

                if (response.ok) {
                    const data = await response.json();

                    // Add new leave to leaveDetailsArr
                    let newLeave = {
                        id: data.employeeLeaveId,
                        leaveType: data.leaveType,
                        empId: data.employee.employeeId,
                        applicationDateTime: data.applicationDateTime,
                        startDate: data.startDate,
                        endDate: data.endDate,
                        status: data.status,
                        comments: data.comments,
                        mcProofUploaded: data.mcProofUploaded,
                        mcProofImg: data.mcProofImg,
                    };

                    this.leaveDetailsArr.push(newLeave);

                    this.clearNewLeaveModal();
                    this.showLeaveModal(false);
                } else {
                    console.error('Error:', response.statusText);
                }
            } catch (error) {
                console.error('Error:', error);
            }
        },
        isError() {
            // Check if all required fields are filled

            // [1] Application Type
            if (!this.appType) {
                this.errorMsg = "Please select an application type.";
                return true;
            }

            // [2] Start Date
            if (!this.startDate) {
                this.errorMsg = "Please select a start date.";
                return true;
            }

            // [3] End Date
            if (!this.endDate) {
                this.errorMsg = "Please select an end date.";
                return true;
            }

            // [4] Check if end date is after (or equals) start date
            if (new Date(this.endDate) < new Date(this.startDate)) {
                this.errorMsg = "End date cannot be before Start date.";
                return true;
            }

            this.errorMsg = "";
            return false;
        },
        updateBeforeAfterTodayArrs() {
            // Split leaveDetailsArr into leavesBeforeToday and leavesAfterToday
            const today = new Date();

            // Clear arrays
            this.leavesBeforeToday = [];
            this.leavesAfterToday = [];

            this.leaveDetailsArr.forEach(leave => {
                const end = new Date(leave.endDate);
                end.setHours(23, 59, 59, 999);

                if (end < today) {
                    this.leavesBeforeToday.push(leave);
                } else {
                    this.leavesAfterToday.push(leave);
                }
            });

            // Sort leavesBeforeToday and leavesAfterToday
            this.sortByDate(this.leavesBeforeToday);
            this.sortByDate(this.leavesAfterToday);
        },
        showLeaveModal(toOpen) {
            if (toOpen) {
                this.leaveModal.show();
            } else {
                this.leaveModal.hide();
            }
        },
        mcUploadTrigger(data) {
            // Triggers when any MC is uploaded
            this.$emit('mc-uploaded', data);
        },
    },
    mounted() {
        this.updateBeforeAfterTodayArrs();

        // Apply Leave Modal
        this.leaveModal = new bootstrap.Modal(document.getElementById('empAddLeaveModal'));
    },
};
</script>
