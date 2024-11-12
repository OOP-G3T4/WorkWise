<script setup>
import LeaveControls from '../../components/admin/leaves/LeaveControls.vue';
import LeaveBody from '../../components/admin/leaves/LeaveBody.vue';
</script>

<template>
    <div class="contain-parent px-3 px-md-4">
        <div class="contain-top">
            <LeaveControls @statusChange="handleStatusChange" @rejectAll="approveAll(false)" @approveAll="approveAll(true)" @filterChange="handleFilterChange" />
        </div>
        
        <div class="contain-bottom">
            <LeaveBody :selectedLeaveStatusArr="selectedLeaveStatuses" :toUpdateLeaves="toUpdateLeaves" @selectedChanged="handleSelectedChange" :filtersObj="filterObj" />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            selectedLeaveStatuses: ["Pending", "Approved", "Rejected"],
            selectedLeaveIds: [],
            toUpdateLeaves: false,

            // For filtering
            filterObj : {
                employees: [],
                leavetypes: [],
            },
        };
    },
    methods: {
        handleStatusChange(statuses) {
            this.selectedLeaveStatuses = statuses;
        },
        approveAll(toApprove = true) {
            let fetchPromises = []; // Array to hold all fetch promises

            let command = toApprove ? "approve" : "reject";

            for (let leaveId of this.selectedLeaveIds) {
                let fetchPromise = fetch(`${this.$apiUrl}/employee-leave/${leaveId}/${command}`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                .then((res) => {
                    if (!res.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return res.json();
                })
                .then((data) => {
                    // Approved or rejected successfully
                })
                .catch((err) => {
                    console.error(err);
                });

                fetchPromises.push(fetchPromise); // Add the promise to the array
            }

            // Wait for all promises to complete
            Promise.all(fetchPromises)
                .then(() => {
                    // Refresh the leave data after all fetch calls have completed
                    this.updateLeaves();
                })
                .catch((err) => {
                    console.error("Error occurred during approval: ", err);
                });
        },
        updateLeaves() {
            // Toggle the toUpdateLeaves variable to trigger a refresh of the leave data
            this.toUpdateLeaves = !this.toUpdateLeaves;
        },
        handleSelectedChange(selectedLeaveIds) {
            this.selectedLeaveIds = selectedLeaveIds;
        },
        handleFilterChange(filters) {
            // Handle filter change
            this.filterObj = filters;
        },
    },
};
</script>