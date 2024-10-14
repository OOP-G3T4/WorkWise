<script setup>
import LeaveControls from './components/LeaveControls.vue';
import LeaveBody from './components/LeaveBody.vue';
</script>

<template>
    <div class="d-flex flex-column p-4">
        <LeaveControls id="leave-dashboard-controls" />
        <LeaveBody id="leave-dashboard-body" />
    </div>
</template>

<style scoped>
#leave-dashboard-controls {
    flex: 0 1 1px;
}

#leave-dashboard-body {
    flex: 1 1 1px;
    overflow-y: auto;
}
</style>