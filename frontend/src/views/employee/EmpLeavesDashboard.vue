<script setup>
import LeaveControls from '../../components/employee/leaves/LeaveControls.vue';
</script>

<template>
    <div class="d-flex flex-column px-4">
        <LeaveControls id="emp-leave-dashboard-controls" class="sticky-top"/>
        <LeaveBody id="emp-leave-dashboard-body" />
    </div>
</template>

<script>
export default {
    data() {
        return {
            // selectedLeaveStatuses: ["Pending", "Approved", "Rejected"],
        };
    },
    methods: {
    },
};
</script>

<style scoped>
#emp-leave-dashboard-controls {
    flex: 0 1 1px;
}

#emp-leave-dashboard-body {
    flex: 1 1 1px;
    overflow-y: auto;
}
</style>