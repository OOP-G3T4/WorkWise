<script setup>
import DatePicker from "./components/DatePicker.vue";
import DailyCalendar from "./components/DailyCalendar.vue";
</script>

<template>
    <div class="main-container-dashboard">
        <DatePicker class="datetime-header" />

        <div class="calendar-container">
            <DailyCalendar />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            jobDetailsArr: [
                {
                    appointmentId: "213",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "67 Choa Chu Kang Loop",
                    date: "2024-10-05", // ISO 8601 standard
                    startTime: "12:50:00",
                    endTime: "19:00:00",
                    cleaners: [1,2],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "1",
                        clientName: "John Doe",
                        clientContact: "91234567",
                        clientEmail: "johndoe@gmail.com",
                        clientAddress: "101 Clementi Road",
                        clientGender: "Male",
                        clientAge: "49",
                    },
                },
                {
                    appointmentId: "214",
                    packageType: "W_3RM_HDB",
                    jobAddress: "32 Bukit Batok West Ave 6",
                    date: "2024-11-02", // ISO 8601 standard
                    startTime: "12:30:00",
                    endTime: "15:00:00",
                    cleaners: [3],
                    arrivalProofUploaded: false,
                    jobStatus: "Completed",
                    clientDetails: {
                        clientId: "2",
                        clientName: "Harry Sim",
                        clientContact: "9298377",
                        clientEmail: "harrysim@gmail.com",
                        clientAddress: "16 Jalan Riang",
                        clientGender: "Male",
                        clientAge: "32",
                    },
                },
                {
                    appointmentId: "215",
                    packageType: "W_3RM_HDB",
                    jobAddress: "12 Serangoon North Ave 5",
                    date: "2024-10-10", // ISO 8601 standard
                    startTime: "14:00:00",
                    endTime: "18:00:00",
                    cleaners: [4],
                    arrivalProofUploaded: false,
                    jobStatus: "Not Started",
                    clientDetails: {
                        clientId: "3",
                        clientName: "Jane Low",
                        clientContact: "91234567",
                        clientEmail: "janelow@yahoo.com",
                        clientAddress: "34 Jurong East Ave 1",
                        clientGender: "Female",
                        clientAge: "29",
                    },
                },
                {
                    appointmentId: "216",
                    packageType: "W_4RM_HDB",
                    jobAddress: "19 Clementi Road",
                    date: "2024-10-15", // ISO 8601 standard
                    startTime: "12:00:00",
                    endTime: "16:00:00",
                    cleaners: [5],
                    arrivalProofUploaded: false,
                    jobStatus: "Not Started",
                    clientDetails: {
                        clientId: "4",
                        clientName: "Mary Tan",
                        clientContact: "91234567",
                        clientEmail: "marytan@hotmail.com",
                        clientAddress: "45 Bukit Timah Road",
                        clientGender: "Female",
                        clientAge: "39",
                    },
                },
                {
                    appointmentId: "217",
                    packageType: "W_3RM_CONDO",
                    jobAddress: "64 Tampanies Road",
                    date: "2024-10-20", // ISO 8601 standard
                    startTime: "10:00:00",
                    endTime: "12:00:00",
                    cleaners: ["6", "7", "8"],
                    arrivalProofUploaded: false,
                    jobStatus: "In Progress",
                    clientDetails: {
                        clientId: "5",
                        clientName: "Tommy Tan",
                        clientContact: "91234567",
                        clientEmail: "tomtan@gmail.com",
                        clientAddress: "12 Orchard Road",
                        clientGender: "Male",
                        clientAge: "45",
                    },
                },
            ],

            heightsArr: [200, 250, 300, 400, 500],

            isCompressed: true,
        };
    },
};
</script>


<style scoped>
.main-container-dashboard {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
}

.datetime-header {
    flex: 0 1 1px;
}

.calendar-container {
    flex: 1 1 1px;
    overflow-y: auto;
}

</style>