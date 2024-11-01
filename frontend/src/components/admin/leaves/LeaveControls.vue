<template>
    <div class="container-fluid bg-white py-4">
        <div class="row justify-content-between gy-3">
            <div class="col-auto p-0 d-flex">
                <button v-for="e_status in possibleStatuses" class="btn btn-resp me-2" :class="statusBtnClass(e_status.toShow)" @click="toggleStatus(e_status.toShow)" data-bs-toggle="button">{{ e_status.toShow }}</button>
            </div>

            <div class="col-auto p-0 d-flex">
                <button class="btn btn-resp btn-light me-2"><font-awesome-icon class="me-2" icon="fa-solid fa-xmark" />Reject Selected</button>
                <button class="btn btn-resp btn-light me-2"><font-awesome-icon class="me-2" icon="fa-solid fa-check" />Approve Selected</button>
                <button class="btn btn-resp btn-light"><font-awesome-icon icon="fa-solid fa-filter" /></button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // possibleStatuses: ["Pending", "Approved", "Rejected"], // Status buttons auto-generated from this array
            possibleStatuses: [
                {
                    toShow: "Pending",
                    toEmit: "PENDING"
                },
                {
                    toShow: "Approved",
                    toEmit: "APPROVED"
                },
                {
                    toShow: "Rejected",
                    toEmit: "REJECTED"
                }
            ],
            selectedStatuses: ["Pending"],
        };
    },
    watch: {
        selectedStatuses: {
            handler() {
                this.emitArr();
            },
            deep: true, //Watch for changes in within array, rather than just the pointer
        },
    },
    computed: {
        mapping() {
            // returns object of keys (toShow), values (toEmit)
            let res = {};

            for (let eObj of this.possibleStatuses) {
                let toShow = eObj.toShow;
                let toEmit = eObj.toEmit;

                res[toShow] = toEmit;
            }

            return res;
        }
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
        emitArr() {
            let res = [];

            for (let eStatus of this.selectedStatuses) {
                res.push(this.mapping[eStatus])
            }

            this.$emit("statusChange", res)
        },
    },
    mounted() {
        // Send initial status array to parent
        this.emitArr();
    },
};
</script>