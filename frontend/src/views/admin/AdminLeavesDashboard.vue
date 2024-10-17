<script setup>
import LeaveControls from '../../components/admin/leaves/LeaveControls.vue';
import LeaveBody from '../../components/admin/leaves/LeaveBody.vue';
</script>

<template>
    <div class="d-flex flex-column px-4">
        <LeaveControls id="leave-dashboard-controls" class="sticky-top" @statusChange="handleStatusChange"/>
        <LeaveBody id="leave-dashboard-body" :selectedLeaveStatusArr="selectedLeaveStatuses" />
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

<style scoped>
#leave-dashboard-controls {
    flex: 0 1 1px;
}

#leave-dashboard-body {
    flex: 1 1 1px;
    overflow-y: auto;
}
</style>