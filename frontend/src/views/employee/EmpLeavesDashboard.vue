<script setup>
import LeaveControls from '../../components/employee/leaves/LeaveControls.vue';
import LeaveBody from '../../components/employee/leaves/LeaveBody.vue';
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
            />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            selectedFilters: [],

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
                    comments: 'Leg break cos football 1',
                    mcProofUploaded: false,
                    mcProofImg: '',
                },
                {
                    // MC - Pending (No MC Proof + Deadline NOT passed)
                    id: 2,
                    leaveType: 'MC',
                    empId: 1,
                    applicationDateTime: "2024-10-18T10:00:00", // When the leave was applied
                    startDate: '2024-10-15',
                    endDate: '2024-10-17',
                    status: 'Pending',
                    comments: 'Leg break cos football 2',
                    mcProofUploaded: false,
                    mcProofImg: '',
                },
                {
                    // MC - Pending (MC Proof Uploaded)
                    id: 3,
                    leaveType: 'MC',
                    empId: 1,
                    applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                    startDate: '2024-10-02',
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
                    empId: 1,
                    applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                    startDate: '2024-10-01',
                    endDate: '2024-11-15',
                    status: 'Approved',
                    comments: 'Arm break cos blur',
                    mcProofUploaded: true,
                    mcProofImg: 'https://s3.amazonaws.com/creare-websites-wpms-legacy/wp-content/uploads/sites/32/2016/03/01200959/canstockphoto22402523-arcos-creator.com_-1024x1024.jpg',
                },
                {
                    // MC - Rejected
                    id: 5,
                    leaveType: 'MC',
                    empId: 1,
                    applicationDateTime: "2024-10-14T10:00:00", // When the leave was applied
                    startDate: '2024-09-13',
                    endDate: '2024-09-14',
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
                    startDate: '2024-09-14',
                    endDate: '2024-09-15',
                    status: 'Pending',
                    comments: 'Vacation in Maldives swim swim',
                    mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                    mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                },
                {
                    // AL - Approved
                    id: 7,
                    leaveType: 'AL',
                    empId: 1,
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
                    empId: 1,
                    applicationDateTime: "2024-10-10T23:05:00", // When the leave was applied
                    startDate: '2024-11-11',
                    endDate: '2024-12-12',
                    status: 'Rejected',
                    comments: 'Go to space with Elon Musk',
                    mcProofUploaded: false, // Note to JS & Seth: Idk if this is needed for ALs
                    mcProofImg: '', // Note to JS & Seth: Idk if this is needed for ALs
                }
            ],
        };
    },
    methods: {
        handleFilterChange(selectedFilters) {
            this.selectedFilters = selectedFilters;
        },
    },
};
</script>