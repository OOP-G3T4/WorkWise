<template>
    <!-- Modal -->
    <div class="modal fade" id="adminAddNewJobModal" tabindex="-1" aria-labelledby="adminAddNewJobModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- HEADER -->
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="adminAddNewJobModalLabel">Create New Job</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <!-- BODY -->
                <div class="modal-body">
                    Add new job form goes here [PLACEHOLDER]
                </div>

                <!-- FOOTER -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Create job</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            clientId: "",
            packageId: "",
            jobDay: "",
            startTime: "",
            propertyId: "",
        };
    },
    methods: {
        saveJob() {
            // Save job details to database
        },
    },
};
</script>