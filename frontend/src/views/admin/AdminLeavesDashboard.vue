<script setup>
import LeaveControls from '../../components/admin/leaves/LeaveControls.vue';
import LeaveBody from '../../components/admin/leaves/LeaveBody.vue';
</script>

<template>
    <div class="contain-parent px-3 px-md-4">
        <div class="contain-top">
            <LeaveControls @statusChange="handleStatusChange"/>
        </div>
        
        <div class="contain-bottom">
            <LeaveBody :selectedLeaveStatusArr="selectedLeaveStatuses" />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            selectedLeaveStatuses: ["Pending", "Approved", "Rejected"],
        };
    },
    methods: {
        handleStatusChange(statuses) {
            this.selectedLeaveStatuses = statuses;
        },
    },
};
</script>