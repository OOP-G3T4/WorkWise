<template>
    <div class="container-fluid bg-white py-4">
        <div class="row justify-content-between">
            <div class="col-auto p-0 d-flex">
                <button v-for="e_status in possibleFilters" class="btn btn-resp me-2" :class="statusBtnClass(e_status)" @click="toggleStatus(e_status)" data-bs-toggle="button">{{ e_status }}</button>
            </div>

            <div class="col-auto p-0 d-flex">
                <button class="btn btn-resp btn-light me-2"><font-awesome-icon icon="fa-solid fa-plus" /><span class="ms-2 d-none d-sm-inline">New</span></button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            possibleFilters: ["Upcoming", "Past", "Rejected"], // Status buttons auto-generated from this array
            selectedFilters: ["Upcoming"],
        };
    },
    watch: {
        selectedFilters: {
            handler() {
                this.$emit("filterChange", this.selectedFilters);
            },
            deep: true, //Watch for changes in within array, rather than just the pointer
        },
    },
    methods: {
        toggleStatus(status) {
            const index = this.selectedFilters.indexOf(status);
            const isAdding = (index <= -1);

            if (this.selectedFilters.length === 2 && !isAdding && this.selectedFilters.includes("Rejected") && status !== "Rejected") {
                // If 2 statuses are already selected and user tries to deselect Rejected, do nothing
                return;
            } else if (!isAdding && this.selectedFilters.length === 1) {
                // If only 1 status is selected and user tries to deselect it, do nothing
                return;
            }

            if (!isAdding) {
                // Remove status if already selected
                this.selectedFilters.splice(index, 1);
            } else {
                // Add status if not already selected
                this.selectedFilters.push(status);
            }
        },
        statusBtnClass(status) {
            var statusSelected = this.selectedFilters.includes(status);

            return {
                "btn-light": !statusSelected,
                "btn-primary": statusSelected,
                "active": statusSelected,
            };
        },
    },
    mounted() {
        // Send initial status array to parent
        this.$emit("filterChange", this.selectedFilters);
    },
};
</script>