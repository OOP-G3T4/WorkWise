<script setup>
import LeaveCard from '../../general/leaves/LeaveCard.vue';
</script>

<template>
    <template v-if="leaveDetailsArr !== null && empDetailsArr !== null">
        <div v-for="e_leave in leaveDetailsArr">
            <LeaveCard v-if="selectedLeaveStatusArr.includes(e_leave.status)" class="mb-3" :leaveDetails="e_leave" :employeeDetails="empDetailsArr[e_leave.empId]" @selectedChanged="handleSelectChange" />
        </div>
    </template>
</template>

<script>
export default {
    emits: ['selectedChanged'],
    props: {
        selectedLeaveStatusArr: {
            type: Array,
            required: false,
            default: () => ["Pending", "Approved", "Rejected"],
        },
        toUpdateLeaves: {
            type: Boolean,
            required: false,
            default: false,
        },
        filtersObj: {
            type: Object,
            required: false,
            default: () => ({
                employees: [],
                leavetypes: [],
            }),
        },
    },
    data() {
        return {
            rawLeaves: null,

            empDetailsArr: null,
            selectedLeaveIds: [],
        };
    },
    computed: {
        leaveDetailsArr() {
            if (this.rawLeaves === null || this.empDetailsArr === null) {
                return null;
            }

            let filteredLeaves = this.rawLeaves.filter(e_leave => {
                let empId = e_leave.empId;
                let leaveType = e_leave.leaveType;

                let empFilter = this.filtersObj.employees.length === 0 || this.filtersObj.employees.includes(String(empId));
                let leaveTypeFilter = this.filtersObj.leavetypes.length === 0 || this.filtersObj.leavetypes.includes(leaveType);

                return empFilter && leaveTypeFilter;
            });

            return filteredLeaves;
        }
    },
    watch: {
        selectedLeaveIds: {
            handler() {
                this.$emit('selectedChanged', this.selectedLeaveIds);
            },
            deep: true,
        },
        toUpdateLeaves() {
            this.pullLeavesFromBackend();
        },
        filtersObj: {
            handler() {
                
            },
            deep: true,
        },
    },
    methods: {
        handleSelectChange(data) {
            let leaveId = data.id;
            let isChecked = data.isChecked;

            if (isChecked) {
                this.selectedLeaveIds.push(leaveId);
            } else {
                let index = this.selectedLeaveIds.indexOf(leaveId);
                this.selectedLeaveIds.splice(index, 1);
            }
        },
        pullLeavesFromBackend() {
            // Pull leaves data from backend
            fetch(`${this.$apiUrl}/employee-leave`)
                .then(response => response.json())
                .then(data => {
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
                        }

                        leaveDetailsArr.push(newLeave);
                    }

                    this.rawLeaves = leaveDetailsArr;
                });
        }
    },
    mounted() {
        // Pull employee data from backend
        fetch(`${this.$apiUrl}/employee`)
            .then(response => response.json())
            .then(data => {
                let empDetailsArr = {};

                for (let i = 0; i < data.length; i++) {
                    let e_emp = data[i];

                    let newEmp = {
                        id: e_emp.employeeId,
                        name: e_emp.name,
                        home_address: e_emp.homeAddress,
                        phone_number: e_emp.phoneNumber,
                        email: e_emp.email,
                        short_bio: e_emp.shortBio,
                        al_balance: e_emp.alBalance,
                        mc_balance: e_emp.mcBalance
                    }

                    empDetailsArr[e_emp.employeeId] = newEmp;
                }

                this.empDetailsArr = empDetailsArr;
            });

        this.pullLeavesFromBackend();
    }
};
</script>
