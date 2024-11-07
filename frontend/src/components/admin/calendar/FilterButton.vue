<script setup>
import DropdownCheckbox from '../../general/forms/DropdownCheckbox.vue';
</script>

<template>
    <button class="btn btn-light btn-resp" type="button" data-bs-toggle="offcanvas" data-bs-target="#filterMenueOffcanvas" aria-controls="filterMenueOffcanvas">
        <font-awesome-icon icon="fa-solid fa-filter" />
    </button>

    <div class="offcanvas offcanvas-end" tabindex="-1" id="filterMenueOffcanvas" aria-labelledby="filterMenueOffcanvasLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="filterMenueOffcanvasLabel">Filters</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>

        <div class="offcanvas-body">
            <DropdownCheckbox :items="allClients" fieldName="Clients" uniqueComponentId="filterAdminClient_h47fh3" :showId="true" :showNumSelected="true" @valChange="handleClients" />
            <DropdownCheckbox :items="allEmployees" fieldName="Employees" uniqueComponentId="filterAdminEmployee_h47fh3" :showId="true" :showNumSelected="true" @valChange="handleEmployees" />
            <DropdownCheckbox :items="allPackages" fieldName="Packages" uniqueComponentId="filterAdminPackage_h47fh3" :showId="false" :showNumSelected="true" @valChange="handlePackages" />
            <DropdownCheckbox :items="allStatuses" fieldName="Statuses" uniqueComponentId="filterAdminStatus_h47fh3" :showId="false" :showNumSelected="true" @valChange="handleStatuses" />
        </div>
    </div>
</template>


<script>
export default {
    emits: ['filterChanged'],
    data() {
        return {
            // For displaying in dropdown
            allClients: {},
            allEmployees: {},
            allPackages: {},
            allStatuses: {
                'PENDING': 'PENDING',
                'SCHEDULED': 'SCHEDULED',
                'CANCELLED': 'CANCELLED',
                'IN_PROGRESS': 'IN_PROGRESS',
                'ACTION_REQUIRED': 'ACTION_REQUIRED',
                'COMPLETED': 'COMPLETED',
            },

            // For filtering
            selectedClients: [],
            selectedEmployees: [],
            selectedPackages: [],
            selectedStatuses: [],
        };
    },
    methods: {
        fetchAllClients() {
            fetch(`${this.$apiUrl}/client`)
                .then(response => response.json())
                .then(data => {
                    let allClients = {};

                    for (let i = 0; i < data.length; i++) {
                        let e_client = data[i];

                        allClients[e_client.clientId] = e_client.name;
                    }

                    this.allClients = allClients;
                })
                .catch(error => {
                    console.error(error);
                });
        },
        fetchAllEmployees() {
            fetch(`${this.$apiUrl}/employee`)
                .then(response => response.json())
                .then(data => {
                    let allEmployees = {};

                    for (let i = 0; i < data.length; i++) {
                        let e_employee = data[i];
                        allEmployees[e_employee.employeeId] = e_employee.name;
                    }

                    this.allEmployees = allEmployees;
                })
                .catch(error => {
                    console.error(error);
                });
        },
        fetchAllPackages() {
            fetch(`${this.$apiUrl}/package`)
                .then(response => response.json())
                .then(data => {
                    let allPackages = {};

                    for (let i = 0; i < data.length; i++) {
                        let e_package = data[i];
                        allPackages[e_package.packageId] = e_package.packageId;
                    }

                    this.allPackages = allPackages;
                })
                .catch(error => {
                    console.error(error);
                });
        },
        handleClients(selectedClients) {
            this.selectedClients = selectedClients;
            this.emitFilter();
        },
        handleEmployees(selectedEmployees) {
            this.selectedEmployees = selectedEmployees;
            this.emitFilter();
        },
        handlePackages(selectedPackages) {
            this.selectedPackages = selectedPackages;
            this.emitFilter();
        },
        handleStatuses(selectedStatuses) {
            this.selectedStatuses = selectedStatuses;
            this.emitFilter();
        },
        emitFilter() {
            this.$emit('filterChanged', {
                clients: this.selectedClients,
                employees: this.selectedEmployees,
                packages: this.selectedPackages,
                statuses: this.selectedStatuses,
            });
        },
    },
    mounted() {
        this.fetchAllClients();
        this.fetchAllEmployees();
        this.fetchAllPackages();
    },
};
</script>