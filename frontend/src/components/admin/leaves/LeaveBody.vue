<script setup>
import LeaveCard from '../../general/leaves/LeaveCard.vue';
</script>

<template>
    <div v-for="e_leave in leaveDetailsArr" class="mb-3">
        <LeaveCard v-if="selectedLeaveStatusArr.includes(e_leave.status)" :leaveDetails="e_leave" :employeeDetails="empDetailsArr[e_leave.empId]" @selectedChanged="handleSelectChange" />
    </div>
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
    },
    data() {
        return {
            leaveDetailsArr: [
                // Medical Certicificates (MCs) ===================================================================================================
                // {
                //     // MC - Pending (No MC Proof + Deadline passed)
                //     id: 1,
                //     leaveType: 'MC',
                //     empId: 1,
                //     applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                //     startDate: '2024-10-15',
                //     endDate: '2024-10-16',
                //     status: 'Pending',
                //     comments: 'Leg break cos football 1',
                //     mcProofUploaded: false,
                //     mcProofImg: '',
                // },
                // {
                //     // MC - Pending (No MC Proof + Deadline NOT passed)
                //     id: 2,
                //     leaveType: 'MC',
                //     empId: 2,
                //     applicationDateTime: "2024-10-17T10:00:00", // When the leave was applied
                //     startDate: '2024-10-15',
                //     endDate: '2024-10-16',
                //     status: 'Pending',
                //     comments: 'Leg break cos football 2',
                //     mcProofUploaded: false,
                //     mcProofImg: '',
                // },
                // {
                //     // MC - Pending (MC Proof Uploaded)
                //     id: 3,
                //     leaveType: 'MC',
                //     empId: 3,
                //     applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                //     startDate: '2024-10-15',
                //     endDate: '2024-10-16',
                //     status: 'Pending',
                //     comments: 'Leg break cos football',
                //     mcProofUploaded: true,
                //     mcProofImg: 'https://s3.amazonaws.com/creare-websites-wpms-legacy/wp-content/uploads/sites/32/2016/03/01200959/canstockphoto22402523-arcos-creator.com_-1024x1024.jpg',
                // },
                // {
                //     // MC - Approved
                //     id: 4,
                //     leaveType: 'MC',
                //     empId: 4,
                //     applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                //     startDate: '2024-10-15',
                //     endDate: '2024-10-15',
                //     status: 'Approved',
                //     comments: 'Arm break cos blur',
                //     mcProofUploaded: true,
                //     mcProofImg: 'https://s3.amazonaws.com/creare-websites-wpms-legacy/wp-content/uploads/sites/32/2016/03/01200959/canstockphoto22402523-arcos-creator.com_-1024x1024.jpg',
                // },
                // {
                //     // MC - Rejected
                //     id: 5,
                //     leaveType: 'MC',
                //     empId: 5,
                //     applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                //     startDate: '2024-10-15',
                //     endDate: '2024-10-19',
                //     status: 'Rejected',
                //     comments: 'Headache cos head aching',
                //     mcProofUploaded: true,
                //     mcProofImg: 'https://s3.amazonaws.com/creare-websites-wpms-legacy/wp-content/uploads/sites/32/2016/03/01200959/canstockphoto22402523-arcos-creator.com_-1024x1024.jpg',
                // },

                // // Annual Leaves (ALs) ===================================================================================================
                // {
                //     // AL - Pending
                //     id: 6,
                //     leaveType: 'AL',
                //     empId: 1,
                //     applicationDateTime: "2024-10-13T10:00:00", // When the leave was applied
                //     startDate: '2024-11-01',
                //     endDate: '2024-11-05',
                //     status: 'Pending',
                //     comments: 'Vacation in Maldives swim swim',
                //     mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                //     mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                // },
                // {
                //     // AL - Approved
                //     id: 7,
                //     leaveType: 'AL',
                //     empId: 2,
                //     applicationDateTime: "2024-10-10T20:31:00", // When the leave was applied
                //     startDate: '2024-12-10',
                //     endDate: '2024-12-12',
                //     status: 'Approved',
                //     comments: 'Go to space with Elon Musk',
                //     mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                //     mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                // },
                // {
                //     // AL - Rejected
                //     id: 8,
                //     leaveType: 'AL',
                //     empId: 3,
                //     applicationDateTime: "2024-10-10T23:05:00", // When the leave was applied
                //     startDate: '2024-12-10',
                //     endDate: '2024-12-12',
                //     status: 'Rejected',
                //     comments: 'Go to space with Elon Musk',
                //     mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                //     mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                // }
            ],

            empDetailsArr: {},
            selectedLeaveIds: [],
        };
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
            fetch('http://localhost:8081/api/employee-leave')
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

                    this.leaveDetailsArr = leaveDetailsArr;
                });
        }
    },
    mounted() {
        // Pull employee data from backend
        fetch('http://localhost:8081/api/employee')
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
