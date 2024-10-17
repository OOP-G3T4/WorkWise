<template>
    <div class="container-fluid bg-white py-4">
        <div class="row justify-content-between gy-3">
            <div class="col-auto p-0 d-flex">
                <button v-for="e_status in possibleStatuses" class="btn me-2 fs-7 fs-sm-6 px-2 px-md-3" :class="statusBtnClass(e_status)" @click="toggleStatus(e_status)" data-bs-toggle="button">{{ e_status }}</button>
            </div>

            <div class="col-auto p-0 d-flex">
                <button class="btn btn-light fs-7 fs-md-6 px-2 px-md-3 me-2"><font-awesome-icon class="me-2" icon="fa-solid fa-xmark" />Reject Selected</button>
                <button class="btn btn-light fs-7 fs-md-6 px-2 px-md-3 me-2"><font-awesome-icon class="me-2" icon="fa-solid fa-check" />Approve Selected</button>
                <button class="btn btn-light fs-7 fs-md-6 px-2 px-md-3"><font-awesome-icon icon="fa-solid fa-filter" /></button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            possibleStatuses: ["Pending", "Approved", "Rejected"], // Status buttons auto-generated from this array
            selectedStatuses: ["Pending"],
        };
    },
    watch: {
        selectedStatuses: {
            handler() {
                this.$emit("statusChange", this.selectedStatuses);
            },
            deep: true, //Watch for changes in within array, rather than just the pointer
        },
    },
    methods: {
        toggleStatus(status) {
            const index = this.selectedStatuses.indexOf(status);

            if (index > -1) {
                // Remove status if already selected
                this.selectedStatuses.splice(index, 1);
            } else {
                // Add status if not already selected
                this.selectedStatuses.push(status);
            }
        },
        statusBtnClass(status) {
            var statusSelected = this.selectedStatuses.includes(status);

            return {
                "btn-light": !statusSelected,
                "btn-primary": statusSelected,
                "active": statusSelected,
            };
        },
    },
    mounted() {
        // Send initial status array to parent
        this.$emit("statusChange", this.selectedStatuses);
    },
};
</script>