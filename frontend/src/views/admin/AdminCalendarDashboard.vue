<script setup>
import DatePicker from "../../components/admin/calendar/DatePicker.vue";
import DailyCalendar from "../../components/admin/calendar/DailyCalendar.vue";
import WeeklyCalendar from "../../components/admin/calendar/WeeklyCalendar.vue";
import MonthlyCalendar from "../../components/admin/calendar/MonthlyCalendar.vue";
import NewJobModal from "../../components/admin/calendar/NewJobModal.vue";
</script>

<template>
    <div class="contain-parent" v-bind="$attrs">
        <div class="contain-top p-3">
            <DatePicker @zoomChanged="adjustZoom" :dateSelected="dateSelected" :rangeSelected="rangeSelected" @curDateChanged="dateChanged" @filterChanged="handleFilterChange" />
        </div>

        <div class="contain-bottom" v-if="jobDetails">
            <!-- DAILY CALENDAR -->
            <template v-if="rangeSelected == 'Daily'">
                <DailyCalendar :isCompressed="isCompressed" :jobDetails="jobDetails" :dateSelected="dateSelected" @jobUpdated="handlejobUpdated" />
            </template>

            <!-- WEEKLY CALENDAR -->
            <template v-else-if="rangeSelected == 'Weekly'">
                <WeeklyCalendar :jobDetails="jobDetails" :dateSelected="dateSelected" @navToDate="navToDate" />
            </template>

            <!-- MONTHLY CALENDAR -->
            <template v-else-if="rangeSelected == 'Monthly'">
                <MonthlyCalendar :jobDetails="jobDetails" :dateSelected="dateSelected" @navToDate="navToDate" @navToWeek="navToWeek" />
            </template>
        </div>
    </div>

    <!-- New Job Modal -->
    <NewJobModal />
</template>

<script>
export default {
    data() {
        return {
            isCompressed: false,

            allJobsRaw: null, // Raw data from API

            dateSelected: new Date(),
            rangeSelected: "",

            // Filters Applied
            selectedClients: [],
            selectedEmployees: [],
            selectedPackages: [],
            selectedStatuses: [],
        };
    },
    computed: {
        jobDetails() {
            // Jobs sorted by month, then day { "mm-yyyy": { "dd": [job1, job2, ...] } }
            let res = {};

            for (var i = 0; i < this.filteredJobs.length; i++) {
                // Handle each job object
                var job = this.filteredJobs[i];

                // Get employee IDs and end time
                const employeeIds = job.employees.map(employee => String(employee.employeeId));
                const endTime = this.getEndTime(job.startTime, job.actualDuration);

                // Update min and max time axis if needed
                var startHour = parseInt(job.startTime.split(":")[0]);
                var endHour = parseInt(endTime.split(":")[0]) + 1;

                if (startHour < this.timeAxisMin) {
                    this.timeAxisMin = startHour;
                }

                if (endHour > this.timeAxisMax) {
                    this.timeAxisMax = endHour;
                }

                // Get job month in "mm-yyyy" and day in "dd" format
                var jobDate = new Date(job.date);
                var jobMonthStr = (jobDate.getMonth()+1) + "-" + jobDate.getFullYear();
                var jobDay = jobDate.getDate();

                // Format job details
                var formattedJob = {
                    subscriptionId: job.subscription.subscriptionId,
                    appointmentId: job.jobId,
                    packageType: job.subscription.selectedPackage.packageId,
                    jobAddress: {
                        id: job.subscription.property.propertyId,
                        address: job.subscription.property.address,
                        postalCode: job.subscription.property.postalCode,
                    },
                    date: job.date,
                    startTime: job.startTime,
                    endTime: endTime,
                    cleaners: employeeIds,
                    arrivalProofUploaded: job.arrivalProofUploaded,
                    completionProofUploaded: job.completionProofUploaded,
                    jobStatus: job.status,
                    clientDetails: {
                        clientId: job.subscription.client.clientId,
                        clientName: job.subscription.client.name,
                        clientContact: job.subscription.client.phoneNumber,
                        clientEmail: job.subscription.client.email,
                        clientAddress: job.subscription.client.clientAddress,
                        clientGender: job.subscription.client.gender,
                        clientAge: job.subscription.client.clientAge,
                    },
                }

                // Add job to jobDetails object
                if (!(jobMonthStr in res)) {
                    res[jobMonthStr] = {};
                }

                if (!(jobDay in res[jobMonthStr])) {
                    res[jobMonthStr][jobDay] = [];
                }

                res[jobMonthStr][jobDay].push(formattedJob);
            }

            return res;
        },
        filteredJobs() {
            // If no jobs pulled yet
            if (this.allJobsRaw == null) {
                return [];
            }

            // If any filters not applied yet
            if (!this.selectedClients || !this.selectedEmployees || !this.selectedPackages || !this.selectedStatuses) {
                return this.jobDetails;
            }

            let res = [];

            for (var i = 0; i < this.allJobsRaw.length; i++) {
                var job = this.allJobsRaw[i];

                var clientMatch = this.selectedClients.length == 0 || this.selectedClients.includes(String(job.subscription.client.clientId));
                var employeeMatch = this.selectedEmployees.length == 0 || job.employees.some(employee => this.selectedEmployees.includes(String(employee.employeeId)));
                var packageMatch = this.selectedPackages.length == 0 || this.selectedPackages.includes(job.subscription.selectedPackage.packageId);
                var statusMatch = this.selectedStatuses.length == 0 || this.selectedStatuses.includes(job.status);

                if (clientMatch && employeeMatch && packageMatch && statusMatch) {
                    res.push(job);
                }
            }

            return res;
        },
    },
    methods: {
        adjustZoom(data) {
            this.isCompressed = data.zoomedOut;
        },
        dateChanged(data) {
            const date = data.date;
            const range = data.range;

            this.dateSelected = date;
            this.rangeSelected = range;
        },
        getEndTime(startTime, durationHours) {
            // Returns the end time (string format: "hh:mm:ss") based on the start time (string format: "hh:mm:ss") and duration in hours
            var today = new Date();

            var today_date_str = today.toISOString().split('T')[0];
            var start_time_str = today_date_str + "T" + startTime;

            var startObj = new Date(start_time_str);

            var endObj = new Date(startObj.getTime() + durationHours * 60 * 60 * 1000);

            return endObj.toTimeString().split(' ')[0];
        },
        navToDate(date) {
            this.dateSelected = date;
            this.rangeSelected = "Daily";
        },
        navToWeek(date) {
            this.dateSelected = date;
            this.rangeSelected = "Weekly";
        },
        pullAllJobs() {
            fetch(`${this.$apiUrl}/job`)
            .then(response => response.json())
            .then(data => {
                this.allJobsRaw = data;
            })
        },
        handlejobUpdated(jobId) {
            // Refresh jobs
            this.pullAllJobs();
        },
        handleFilterChange(data) {
            this.selectedClients = data.clients;
            this.selectedEmployees = data.employees;
            this.selectedPackages = data.packages;
            this.selectedStatuses = data.statuses;
        },
    },
    mounted() {
        // Pull jobs on mount
        this.pullAllJobs();
    },
};
</script>