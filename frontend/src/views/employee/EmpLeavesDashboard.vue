<script setup>
import LeaveControls from '../../components/employee/leaves/LeaveControls.vue';
import LeaveBody from '../../components/employee/leaves/LeaveBody.vue';
import { mapState } from 'vuex';
</script>

<template>
    <div class="d-flex flex-column px-3 px-md-4">
        <div class="contain-top">
            <LeaveControls @filterChange="handleFilterChange" />
        </div>
        
        <div class="contain-bottom">
            <LeaveBody :leaveDetailsArr="leaveDetailsArr"
                :showPast="selectedFilters.includes('Past')"
                :showUpcoming="selectedFilters.includes('Upcoming')"
                :showRejected="selectedFilters.includes('Rejected')"
                @mc-uploaded="handleMcUploaded"
            />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            selectedFilters: [],

            leaveDetailsArr: [],
        };
    },
    methods: {
        handleFilterChange(selectedFilters) {
            this.selectedFilters = selectedFilters;
        },
        pullLeavesFromApi() {
            fetch(`${this.$apiUrl}/employee-leave/employee/${this.userId}`)
            .then(response => response.json())
            .then(data => {
                // this.leaveDetailsArr = data;

                let leaveDetailsArr = [];

                for (let i = 0; i < data.length; i++) {
                    let e_leave = data[i];

                    let newLeave = {
                        id: e_leave.employeeLeaveId,
                        leaveType: e_leave.leaveType,
                        empId: e_leave.employee.employeeId,
                        applicationDateTime: e_leave.applicationDateTime, // When the leave was applied
                        startDate: e_leave.startDate,
                        endDate: e_leave.endDate,
                        status: e_leave.status,
                        comments: e_leave.comments,
                        mcProofUploaded: e_leave.mcProofUploaded,
                        mcProofImg: e_leave.mcProofImg,
                    };

                    leaveDetailsArr.push(newLeave);
                }

                this.leaveDetailsArr = leaveDetailsArr;
            })
            .catch(error => {
                console.error('Error:', error);
            });
        },
        handleMcUploaded(data) {
            this.pullLeavesFromApi();
        }
    },
    computed: {
        ...mapState(["userId"]),  // Access userId from Vuex state
    },
    mounted() {
        // Pull leave details from backend
        this.pullLeavesFromApi();
    }
};
</script>