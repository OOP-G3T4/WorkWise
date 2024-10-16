<script setup>
import LeaveCard from './LeaveCard.vue';
</script>

<template>
    <template v-for="e_leave in leaveDetailsArr">
        <LeaveCard v-if="selectedLeaveStatusArr.includes(e_leave.status)" :leaveDetails="e_leave" :employeeDetails="empDetailsArr[e_leave.empId]" class="mb-4 mt-1" />
    </template>
</template>

<script>
export default {
    props: {
        selectedLeaveStatusArr: {
            type: Array,
            required: false,
            default: () => ["Pending", "Approved", "Rejected"],
        },
    },
    data() {
        return {
            leaveDetailsArr: [
                // Medical Certicificates (MCs) ===================================================================================================
                {
                    // MC - Pending (No MC Proof + Deadline passed)
                    id: 1,
                    leaveType: 'MC',
                    empId: 1,
                    applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                    startDate: '2024-10-15',
                    endDate: '2024-10-16',
                    status: 'Pending',
                    comments: 'Leg break cos football',
                    mcProofUploaded: false,
                    mcProofImg: '',
                },
                {
                    // MC - Pending (No MC Proof + Deadline NOT passed)
                    id: 2,
                    leaveType: 'MC',
                    empId: 2,
                    applicationDateTime: "2024-10-15T10:00:00", // When the leave was applied
                    startDate: '2024-10-15',
                    endDate: '2024-10-16',
                    status: 'Pending',
                    comments: 'Leg break cos football',
                    mcProofUploaded: false,
                    mcProofImg: '',
                },
                {
                    // MC - Pending (MC Proof Uploaded)
                    id: 3,
                    leaveType: 'MC',
                    empId: 3,
                    applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                    startDate: '2024-10-15',
                    endDate: '2024-10-16',
                    status: 'Pending',
                    comments: 'Leg break cos football',
                    mcProofUploaded: true,
                    mcProofImg: 'https://s3.amazonaws.com/creare-websites-wpms-legacy/wp-content/uploads/sites/32/2016/03/01200959/canstockphoto22402523-arcos-creator.com_-1024x1024.jpg',
                },
                {
                    // MC - Approved
                    id: 4,
                    leaveType: 'MC',
                    empId: 4,
                    applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                    startDate: '2024-10-15',
                    endDate: '2024-10-15',
                    status: 'Approved',
                    comments: 'Arm break cos blur',
                    mcProofUploaded: true,
                    mcProofImg: 'https://s3.amazonaws.com/creare-websites-wpms-legacy/wp-content/uploads/sites/32/2016/03/01200959/canstockphoto22402523-arcos-creator.com_-1024x1024.jpg',
                },
                {
                    // MC - Rejected
                    id: 5,
                    leaveType: 'MC',
                    empId: 5,
                    applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                    startDate: '2024-10-15',
                    endDate: '2024-10-19',
                    status: 'Rejected',
                    comments: 'Headache cos head aching',
                    mcProofUploaded: true,
                    mcProofImg: 'https://s3.amazonaws.com/creare-websites-wpms-legacy/wp-content/uploads/sites/32/2016/03/01200959/canstockphoto22402523-arcos-creator.com_-1024x1024.jpg',
                },

                // Annual Leaves (ALs) ===================================================================================================
                {
                    // AL - Pending
                    id: 6,
                    leaveType: 'AL',
                    empId: 1,
                    applicationDateTime: "2024-10-13T10:00:00", // When the leave was applied
                    startDate: '2024-11-01',
                    endDate: '2024-11-05',
                    status: 'Pending',
                    comments: 'Vacation in Maldives swim swim',
                    mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                    mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                },
                {
                    // AL - Approved
                    id: 7,
                    leaveType: 'AL',
                    empId: 2,
                    applicationDateTime: "2024-10-10T20:31:00", // When the leave was applied
                    startDate: '2024-12-10',
                    endDate: '2024-12-12',
                    status: 'Approved',
                    comments: 'Go to space with Elon Musk',
                    mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                    mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                },
                {
                    // AL - Rejected
                    id: 8,
                    leaveType: 'AL',
                    empId: 3,
                    applicationDateTime: "2024-10-10T23:05:00", // When the leave was applied
                    startDate: '2024-12-10',
                    endDate: '2024-12-12',
                    status: 'Rejected',
                    comments: 'Go to space with Elon Musk',
                    mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                    mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                }
            ],

            empDetailsArr: {
                "1" : {
                    "id": "1",
                    "name": "Marky McFly",
                    "home_address": "789 Tampines Avenue, East",
                    "phone_number": "91234567",
                    "email": "marky.mcfly@example.com",
                    "short_bio": "Time traveler, occasional cleaner.",
                    "al_balance": "5",
                    "mc_balance": "8"
                },
                "2" : {
                    "id": "2",
                    "name": "Betty Broomstick",
                    "home_address": "321 Yishun Ring Road, North",
                    "phone_number": "92234567",
                    "email": "betty.broomstick@example.com",
                    "short_bio": "Sweeps like a storm!",
                    "al_balance": "5",
                    "mc_balance": "8"
                },
                "3" : {
                    "id": "3",
                    "name": "Charlie Mopster",
                    "home_address": "654 Orchard Road, Central",
                    "phone_number": "93234567",
                    "email": "charlie.mopster@example.com",
                    "short_bio": "Champion floor mopper.",
                    "al_balance": "5",
                    "mc_balance": "8"
                },
                "4" : {
                    "id": "4",
                    "name": "Lenny Laundry",
                    "home_address": "888 Sembawang Road, North",
                    "phone_number": "94234567",
                    "email": "lenny.laundry@example.com",
                    "short_bio": "Folding expert.",
                    "al_balance": "5",
                    "mc_balance": "8"
                },
                "5" : {
                    "id": "5",
                    "name": "Harriet Hoover",
                    "home_address": "567 Clementi Avenue, West",
                    "phone_number": "95234567",
                    "email": "harriet.hoover@example.com",
                    "short_bio": "Suction master!",
                    "al_balance": "5",
                    "mc_balance": "8"
                }
            }
        };
    },
};
</script>
