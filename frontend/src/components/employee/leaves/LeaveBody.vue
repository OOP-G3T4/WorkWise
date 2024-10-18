<script setup>
import LeaveCard from '../../general/leaves/LeaveCard.vue';
</script>

<template>
    <!-- Past Leaves -->
    <div v-if="showPast" v-for="e_leave in leavesBeforeToday" class="mb-3">
        <LeaveCard v-if="showCardLogic(e_leave)" :leaveDetails="e_leave" />
    </div>

    <!-- Today Line -->
    <div v-if="showPast && showUpcoming" class="d-flex justify-content-center align-items-center mb-4">
        <hr class="w-100 my-0 border border-dark rounded" />
        <p class="mx-3 my-0 text-secondary">Today</p>
        <hr class="w-100 my-0 border border-dark rounded" />
    </div>

    <!-- Upcoming Leaves -->
    <div v-if="showUpcoming" v-for="e_leave in leavesAfterToday" class="mb-3">
        <LeaveCard v-if="showCardLogic(e_leave)" :leaveDetails="e_leave" />
    </div>
</template>

<script>
export default {
    props: {
        leaveDetailsArr: {
            type: Array,
            required: true,
        },
        showPast: {
            type: Boolean,
            required: false,
            default: false,
        },
        showUpcoming: {
            type: Boolean,
            required: false,
            default: true,
        },
        showRejected: {
            type: Boolean,
            required: false,
            default: false,
        },
    },
    data() {
        return {
            leavesBeforeToday: [],
            leavesAfterToday: [],
        }
    },
    methods: {
        sortByDate(arrToSort) {
            arrToSort.sort((a, b) => {
                // Convert startDate and endDate to Date objects
                const startA = new Date(a.startDate);
                const startB = new Date(b.startDate);

                const endA = new Date(a.endDate);
                const endB = new Date(b.endDate);
                
                // Sort by startDate (latest first)
                if (startA < startB) return -1; // b comes first
                if (startA > startB) return 1; // a comes first

                // If startDate is the same, sort by endDate (oldest first)
                if (endA > endB) return 1; // b comes first
                if (endA < endB) return -1; // a comes first

                return 0; // they are equal
            });

            return
        },
        showCardLogic(leaveObj) {
            if (this.showRejected) {
                return true;
            }

            return leaveObj.status !== "Rejected";
        },
    },
    mounted() {
        // Split leaveDetailsArr into leavesBeforeToday and leavesAfterToday
        const today = new Date();

        this.leaveDetailsArr.forEach(leave => {
            const end = new Date(leave.endDate);
            end.setHours(23, 59, 59, 999);

            if (end < today) {
                this.leavesBeforeToday.push(leave);
            } else {
                this.leavesAfterToday.push(leave);
            }
        });

        // Sort leavesBeforeToday and leavesAfterToday
        this.sortByDate(this.leavesBeforeToday);
        this.sortByDate(this.leavesAfterToday);
    },
};
</script>
