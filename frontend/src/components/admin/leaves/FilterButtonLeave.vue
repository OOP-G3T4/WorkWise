<script setup>
import DropdownCheckbox from '../../general/forms/DropdownCheckbox.vue';
</script>

<template>
    <button class="btn btn-light btn-resp" type="button" data-bs-toggle="offcanvas" data-bs-target="#filterLeaveMenuOffcanvas" aria-controls="filterLeaveMenuOffcanvas">
        <font-awesome-icon icon="fa-solid fa-filter" />
    </button>

    <div class="offcanvas offcanvas-end" tabindex="-1" id="filterLeaveMenuOffcanvas" aria-labelledby="filterLeaveMenuOffcanvasLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="filterLeaveMenuOffcanvasLabel">Filters</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>

        <div class="offcanvas-body">
            <DropdownCheckbox :items="allEmployees" fieldName="Employees" uniqueComponentId="filterLeavesAdminEmployee_d38uj8" :showId="true" :showNumSelected="true" @valChange="handleEmployees" />
            <DropdownCheckbox :items="allLeavetypes" fieldName="Leavetypes" uniqueComponentId="filterLeavesAdminLeavetype_d38uj8" :showId="false" :showNumSelected="true" @valChange="handleLeavetypes" />
        </div>
    </div>
</template>


<script>
export default {
    emits: ['filterChanged'],
    data() {
        return {
            // For displaying in dropdown
            allEmployees: {},
            allLeavetypes: {
                'MC' : 'Medical Leave',
                'AL' : 'Annual Leave',
            },

            // For filtering
            selectedEmployees: [],
            selectedLeavetypes: [],
        };
    },
    methods: {
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
        handleEmployees(selectedEmployees) {
            this.selectedEmployees = selectedEmployees;
            this.emitFilter();
        },
        handleLeavetypes(selectedLeavetypes) {
            this.selectedLeavetypes = selectedLeavetypes;
            this.emitFilter();
        },
        emitFilter() {
            this.$emit('filterChanged', {
                employees: this.selectedEmployees,
                leavetypes: this.selectedLeavetypes,
            });
        },
    },
    mounted() {
        this.fetchAllEmployees();
    },
};
</script>