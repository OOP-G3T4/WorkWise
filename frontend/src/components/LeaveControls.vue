<template>
    <div class="container-fluid">
        <div class="row justify-content-between">
            <div class="col-auto p-0 d-flex">
                <button class="btn btn-secondary me-2">Pending</button>
                <button class="btn btn-secondary me-2">Approved</button>
                <button class="btn btn-secondary">Rejected</button>
            </div>

            <div class="col-auto p-0 d-flex">
                <button class="btn btn-secondary me-2"><font-awesome-icon class="me-2" icon="fa-solid fa-xmark" />Reject Selected</button>
                <button class="btn btn-secondary me-2"><font-awesome-icon class="me-2" icon="fa-solid fa-check" />Approve Selected</button>
                <button class="btn btn-secondary"><font-awesome-icon icon="fa-solid fa-filter" /></button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
        };
    },
    computed: {
    },
    methods: {
    },
};
</script>