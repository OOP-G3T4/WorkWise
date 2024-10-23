<script setup>
import DatePicker from "../../components/admin/calendar/DatePicker.vue";
import DailyCalendar from "../../components/admin/calendar/DailyCalendar.vue";
import WeeklyCalendar from "../../components/admin/calendar/WeeklyCalendar.vue";
import MonthlyCalendar from "../../components/admin/calendar/MonthlyCalendar.vue";
</script>

<template>
    <div class="contain-parent">
        <div class="contain-top p-3">
            <DatePicker @zoomChanged="adjustZoom" @curDateChanged="dateChanged" />
        </div>

        <div class="contain-bottom">
            <!-- DAILY CALENDAR -->
            <template v-if="rangeSelected == 'Daily'">
                <DailyCalendar :isCompressed="isCompressed" :jobDetails="jobDetails" :dateSelected="dateSelected" />
            </template>

            <!-- WEEKLY CALENDAR -->
            <template v-else-if="rangeSelected == 'Weekly'">
                <WeeklyCalendar />
            </template>

            <!-- MONTHLY CALENDAR -->
            <template v-else-if="rangeSelected == 'Monthly'">
                <MonthlyCalendar />
            </template>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            isCompressed: false,

            // Job Details (Sorted by month, then day)
            jobDetails: null,

            dateSelected: null,
            rangeSelected: "",
        };
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
    },
    mounted() {
        // Fetch job details from API [For now, fetch everything]
        fetch('http://localhost:8081/api/job')
            .then(response => response.json())
            .then(data => {
                // Initialize jobDetails object
                this.jobDetails = {};

                for (var i = 0; i < data.length; i++) {
                    // Handle each job object
                    var job = data[i];

                    // Get employee IDs and end time
                    const employeeIds = job.employees.map(employee => String(employee.employeeId));
                    const endTime = this.getEndTime(job.startTime, job.selectedPackage.hours);

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
                        appointmentId: job.jobId,
                        packageType: job.selectedPackage.packageId,
                        jobAddress: {
                            id: job.property.propertyId,
                            address: job.property.address,
                            postalCode: job.property.postalCode,
                        },
                        date: job.date,
                        startTime: job.startTime,
                        endTime: endTime,
                        cleaners: employeeIds,
                        arrivalProofUploaded: true, // Not included yet in DB, replace later (ADAMBFT)
                        completionProofUpload: true, // Not included yet in DB, replace later (ADAMBFT)
                        jobStatus: job.status,
                        clientDetails: {
                            clientId: job.client.clientId,
                            clientName: job.client.name,
                            clientContact: job.client.phoneNumber,
                            clientEmail: job.client.email,
                            clientAddress: "MAILING ADDRESS PLACEHOLDER", // Not included yet in DB, replace later (ADAMBFT)
                            clientGender: "M", // Not included yet in DB, replace later (ADAMBFT)
                            clientAge: "42", // Not included yet in DB, replace later (ADAMBFT)
                        },
                    }

                    // Add job to jobDetails object
                    if (!(jobMonthStr in this.jobDetails)) {
                        this.jobDetails[jobMonthStr] = {};
                    }

                    if (!(jobDay in this.jobDetails[jobMonthStr])) {
                        this.jobDetails[jobMonthStr][jobDay] = [];
                    }

                    this.jobDetails[jobMonthStr][jobDay].push(formattedJob);
                }
            })
    },
};
</script>