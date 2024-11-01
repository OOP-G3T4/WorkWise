<script setup>
import LeaveControls from '../../components/employee/leaves/LeaveControls.vue';
import LeaveBody from '../../components/employee/leaves/LeaveBody.vue';
import { mapState } from 'vuex';
</script>

<template>
    <div class="d-flex flex-column px-3 px-md-4">
        <div class="contain-top">
            <LeaveControls @filterChange="handleFilterChange" />
        </div>
        
        <div class="contain-bottom">
            <LeaveBody :leaveDetailsArr="leaveDetailsArr"
                :showPast="selectedFilters.includes('Past')"
                :showUpcoming="selectedFilters.includes('Upcoming')"
                :showRejected="selectedFilters.includes('Rejected')"
            />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            selectedFilters: [],

            leaveDetailsArr: [],
        };
    },
    methods: {
        handleFilterChange(selectedFilters) {
            this.selectedFilters = selectedFilters;
        },
    },
    computed: {
        ...mapState(["userId"]),  // Access userId from Vuex state
    },
    mounted() {
        // Pull leave details from backend
        fetch(`http://localhost:8081/api/employee-leave/employee/${this.userId}`)
            .then(response => response.json())
            .then(data => {
                this.leaveDetailsArr = data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
};
</script>