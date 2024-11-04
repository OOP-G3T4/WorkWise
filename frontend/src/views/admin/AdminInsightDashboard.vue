<script setup>
import NumberFlow from "@number-flow/vue";
import { Line } from "vue-chartjs";
import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    LineElement,
    PointElement,
    CategoryScale,
    LinearScale,
    Filler,
} from "chart.js";

ChartJS.register(
    Title,
    Tooltip,
    Legend,
    LineElement,
    PointElement,
    CategoryScale,
    LinearScale,
    Filler
);
</script>

<template>
    <div class="container">
        <div class="row p-3 shadow mt-4 rounded border">
            <div class="fw-bold mb-3">
                {{
                    selectedEmployeeId
                        ? getEmployeeName(selectedEmployeeId) + "'s Profile"
                        : "Overview"
                }}
            </div>
            <div class="d-flex align-items-center justify-content-between mb-3">
                <div class="d-flex align-items-center fw-bold fs-5">
                    <div class="dropdown">
                        <button
                            class="btn fw-bold fs-5 border rounded dropdown-toggle"
                            type="button"
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                        >
                            <font-awesome-icon
                                :icon="[
                                    'fas',
                                    selectedView === 'income'
                                        ? 'money-bill'
                                        : selectedView === 'hours'
                                        ? 'clock'
                                        : 'box-open',
                                ]"
                                class="me-1"
                            />
                            {{
                                selectedView === "income"
                                    ? "Income Per Month"
                                    : selectedView === "hours"
                                    ? "Hours Worked Per Month"
                                    : "Package Completed Per Month"
                            }}
                        </button>
                        <ul class="dropdown-menu">
                            <li>
                                <a
                                    class="dropdown-item"
                                    href="#"
                                    @click.prevent="setView('income')"
                                >
                                    <font-awesome-icon
                                        :icon="['fas', 'money-bill']"
                                        class="me-2"
                                    />
                                    Income Per Month
                                </a>
                            </li>
                            <li>
                                <a
                                    class="dropdown-item"
                                    href="#"
                                    @click.prevent="setView('hours')"
                                >
                                    <font-awesome-icon
                                        :icon="['fas', 'clock']"
                                        class="me-2"
                                    />
                                    Hours Worked Per Month
                                </a>
                            </li>
                            <li>
                                <a
                                    class="dropdown-item"
                                    href="#"
                                    @click.prevent="setView('packages')"
                                >
                                    <font-awesome-icon
                                        :icon="['fas', 'box-open']"
                                        class="me-2"
                                    />
                                    Package Completed Per Month
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="">
                    <button
                        class="btn btn-primary me-2"
                        @click="resetToOverview"
                        :disabled="!selectedEmployeeId"
                    >
                        Overview
                    </button>
                    <button
                        class="btn btn-primary rounded"
                        @click="exportToCSV"
                    >
                        Export
                    </button>
                </div>
            </div>
            <div v-if="chartData.labels.length > 0">
                <Line :data="filteredChartData" :options="chartOptions" />
            </div>
        </div>

        <div class="container mt-4">
            <div class="row">
                <div class="col-lg-8 d-flex flex-column">
                    <div class="row flex-grow-1 mb-3 d-flex">
                        <div
                            class="col-6 p-3 shadow border rounded d-flex flex-column"
                        >
                            <div class="mb-2">Total Income</div>
                            <div class="fw-bold fs-4">
                                $<NumberFlow
                                    :value="currentMonthSummary.totalIncome"
                                    :trend="true"
                                />
                            </div>
                        </div>
                        <div
                            class="col-6 p-3 shadow border rounded d-flex flex-column"
                        >
                            <div class="mb-2">Total Hours</div>
                            <div class="fw-bold fs-4">
                                <NumberFlow
                                    :value="currentMonthSummary.totalHours"
                                    :trend="true"
                                />
                            </div>
                        </div>
                    </div>

                    <div
                        class="flex-grow-1 p-3 shadow border rounded package-container overflow-y-auto mb-3 mb-lg-0"
                    >
                        <div class="mb-2">Package Completed</div>
                        <div class="row row-cols-2 row-cols-lg-3">
                            <div
                                class="col d-flex align-items-center justify-content-between mb-2"
                                v-for="(p, index) in packageId"
                                :key="p"
                            >
                                <div
                                    class="fs-9 p-1 text-white rounded"
                                    :style="{
                                        backgroundColor: getPackageColor(index),
                                    }"
                                >
                                    {{ p }}
                                </div>
                                <NumberFlow
                                    :value="
                                        currentMonthSummary
                                            .packageCompletionCounts[p] || 0
                                    "
                                    :trend="true"
                                    class="fw-bold ms-2"
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <div
                    class="col-lg-4 p-3 shadow border rounded d-flex flex-column overflow-y-auto"
                    style="height: 500px"
                >
                    <div class="mb-2">Employees</div>
                    <div
                        v-for="(emp, index) in employees"
                        :key="index"
                        :class="[
                            'employee-item',
                            {
                                'employee-active':
                                    selectedEmployeeId === emp.employeeId,
                            },
                        ]"
                        @click="selectEmployee(emp.employeeId)"
                    >
                        <div class="d-flex align-items-center">
                            <div
                                class="d-flex justify-content-center align-items-center me-3 text-white employee-initial"
                            >
                                <img
                                    :src="`https://placehold.co/200x200?text=${getInitials(
                                        emp.name
                                    )}`"
                                    alt="Employee Image"
                                    class="emp-img d-none d-md-block"
                                />
                            </div>
                            <div>
                                {{ emp.name }}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    components: { Line },
    data() {
        return {
            selectedView: "income",
            jobDetails: {},
            employeeEarnings: {},
            employees: [],
            packageId: [],
            selectedEmployeeId: null,
            chartData: {
                labels: [],
                incomeData: [],
                hoursData: [],
                packageDatasets: [],
            },
            chartOptions: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    title: { display: false },
                },
            },
        };
    },
    computed: {
        currentMonthSummary() {
            let totalIncome = 0;
            let totalHours = 0;
            const packageCompletionCounts = {};

            // Check if employee has data or if it's the company overview
            const dataToUse =
                this.selectedEmployeeId &&
                this.employeeEarnings[this.selectedEmployeeId]
                    ? [this.employeeEarnings[this.selectedEmployeeId]]
                    : Object.values(this.employeeEarnings);

            // If employee has no data, return zeroed summary
            if (
                this.selectedEmployeeId &&
                !this.employeeEarnings[this.selectedEmployeeId]
            ) {
                return {
                    totalIncome: 0,
                    totalHours: 0,
                    packageCompletionCounts: {},
                };
            }

            dataToUse.forEach((data) => {
                totalIncome += data.totalIncome;
                totalHours += data.totalHours;

                for (const packageId in data.packageCompletionCounts) {
                    if (!packageCompletionCounts[packageId]) {
                        packageCompletionCounts[packageId] = 0;
                    }
                    packageCompletionCounts[packageId] +=
                        data.packageCompletionCounts[packageId];
                }
            });

            return { totalIncome, totalHours, packageCompletionCounts };
        },
        filteredChartData() {
            const dataKey =
                this.selectedView === "income"
                    ? "incomeData"
                    : this.selectedView === "hours"
                    ? "hoursData"
                    : "packageDatasets";
            return {
                labels: this.chartData.labels,
                datasets: this.chartData[dataKey],
            };
        },
    },
    mounted() {
        this.fetchJobData();
        this.fetchPackageId();
        this.fetchEmployees();
    },
    methods: {
        selectEmployee(employeeId) {
            this.selectedEmployeeId = employeeId;
            this.updateChart();
        },
        setView(view) {
            this.selectedView = view;
        },
        resetToOverview() {
            this.selectedEmployeeId = null;
            this.updateChart();
        },
        getEmployeeName(employeeId) {
            const employee = this.employees.find(
                (emp) => emp.employeeId === employeeId
            );
            return employee ? employee.name : "Overview";
        },
        exportToCSV() {
            const headers = [
                "Month",
                "Income",
                "Hours Worked",
                ...Object.keys(
                    this.currentMonthSummary.packageCompletionCounts
                ),
            ];
            const rows = [headers];

            const months = this.chartData.labels;
            const incomeData = this.chartData.incomeData[0]?.data || [];
            const hoursData = this.chartData.hoursData[0]?.data || [];
            const packageData = this.chartData.packageDatasets.map(
                (ds) => ds.data
            );

            for (let i = 0; i < months.length; i++) {
                const row = [
                    months[i],
                    incomeData[i] || 0,
                    hoursData[i] || 0,
                    ...packageData.map((data) => data[i] || 0),
                ];
                rows.push(row);
            }

            const csvContent = rows.map((e) => e.join(",")).join("\n");
            const blob = new Blob([csvContent], {
                type: "text/csv;charset=utf-8;",
            });
            const link = document.createElement("a");
            link.href = URL.createObjectURL(blob);
            link.setAttribute("download", "data_export.csv");
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        },
        fetchEmployees() {
            fetch(`${this.$apiUrl}/employee`)
                .then((response) => response.json())
                .then((data) => {
                    this.employees.push(...data);
                })
                .catch((error) =>
                    console.error("Error fetching employee data:", error)
                );
        },
        fetchPackageId() {
            fetch(`${this.$apiUrl}/package`)
                .then((response) => response.json())
                .then((data) => {
                    for (const packageData of data) {
                        this.packageId.push(packageData.packageId);
                    }
                })

                .catch((error) =>
                    console.error("Error fetching job data:", error)
                );
        },
        fetchJobData() {
            fetch(`${this.$apiUrl}/job`)
                .then((response) => response.json())
                .then((data) => this.processJobData(data))
                .catch((error) =>
                    console.error("Error fetching job data:", error)
                );
        },
        processJobData(data) {
            this.employeeEarnings = {};

            data.forEach((job) => {
                const jobMonth = new Date(job.date).getMonth() + 1;

                job.employees.forEach((employee) => {
                    const employeeId = employee.employeeId;

                    if (!this.employeeEarnings[employeeId]) {
                        this.employeeEarnings[employeeId] = {
                            monthlyData: Array(12)
                                .fill()
                                .map(() => ({
                                    totalIncome: 0,
                                    totalHours: 0,
                                    packagesWorked: {},
                                })),
                            totalIncome: 0,
                            totalHours: 0,
                            packageCompletionCounts: {},
                        };
                    }

                    const monthData =
                        this.employeeEarnings[employeeId].monthlyData[jobMonth];

                    monthData.totalIncome += job.selectedPackage.price;
                    monthData.totalHours += job.actualDuration;

                    this.employeeEarnings[employeeId].totalIncome +=
                        job.selectedPackage.price;
                    this.employeeEarnings[employeeId].totalHours +=
                        job.actualDuration;

                    const packageId = job.selectedPackage.packageId;
                    if (!monthData.packagesWorked[packageId]) {
                        monthData.packagesWorked[packageId] = 0;
                    }
                    monthData.packagesWorked[packageId]++;

                    if (
                        !this.employeeEarnings[employeeId]
                            .packageCompletionCounts[packageId]
                    ) {
                        this.employeeEarnings[
                            employeeId
                        ].packageCompletionCounts[packageId] = 0;
                    }
                    this.employeeEarnings[employeeId].packageCompletionCounts[
                        packageId
                    ]++;
                });
            });

            this.updateChart();
        },
        updateChart() {
            const labels = [];
            const monthlyEarningsTotals = Array(12).fill(0);
            const monthlyHoursTotals = Array(12).fill(0);
            const packageCounts = {};

            const currentMonth = new Date().getMonth();
            for (let i = 0; i < 12; i++) {
                const month = (currentMonth - (11 - i) + 12) % 12;
                labels.push(this.getMonthName(month));
            }

            // Use selected employee data or company overview; default to zero if employee has no data
            const dataToUse =
                this.selectedEmployeeId &&
                this.employeeEarnings[this.selectedEmployeeId]
                    ? [this.employeeEarnings[this.selectedEmployeeId]]
                    : Object.values(this.employeeEarnings);

            if (
                this.selectedEmployeeId &&
                !this.employeeEarnings[this.selectedEmployeeId]
            ) {
                // Ensure chart shows zeroed data if no employee data is found
                this.chartData = {
                    labels,
                    incomeData: [
                        { label: "Income", data: monthlyEarningsTotals },
                    ],
                    hoursData: [
                        { label: "Hours Worked", data: monthlyHoursTotals },
                    ],
                    packageDatasets: [],
                };
                return;
            }

            dataToUse.forEach((employeeData) => {
                employeeData.monthlyData.forEach((monthData, index) => {
                    monthlyEarningsTotals[index] += monthData.totalIncome;
                    monthlyHoursTotals[index] += monthData.totalHours;

                    Object.entries(monthData.packagesWorked).forEach(
                        ([packageId, count]) => {
                            if (!packageCounts[packageId]) {
                                packageCounts[packageId] = Array(12).fill(0);
                            }
                            packageCounts[packageId][index] += count;
                        }
                    );
                });
            });

            this.chartData.labels = labels;
            this.chartData.incomeData = [
                {
                    label: "Income",
                    backgroundColor: "#4caf50",
                    borderColor: "#4caf50",
                    data: monthlyEarningsTotals,
                    fill: true,
                },
            ];
            this.chartData.hoursData = [
                {
                    label: "Hours Worked",
                    backgroundColor: "#2196f3",
                    borderColor: "#2196f3",
                    data: monthlyHoursTotals,
                    fill: true,
                },
            ];
            this.chartData.packageDatasets = Object.entries(packageCounts).map(
                ([packageId, data], index) => ({
                    label: `Package ${packageId}`,
                    backgroundColor: this.getPackageColor(index),
                    borderColor: this.getPackageColor(index),
                    data,
                    fill: true,
                })
            );

            this.$forceUpdate();
        },
        getMonthName(monthIndex) {
            const monthNames = [
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December",
            ];
            return monthNames[monthIndex];
        },
        getPackageColor(index) {
            const colors = [
                "#4caf50",
                "#2196f3",
                "#ff5722",
                "#9c27b0",
                "#ff9800",
                "#00bcd4",
                "#673ab7",
                "#e91e63",
                "#795548",
                "#3f51b5",
                "#8bc34a",
                "#ffc107",
                "#ffeb3b",
                "#00e676",
                "#d32f2f",
                "#651fff",
                "#f57c00",
                "#64ffda",
                "#304ffe",
                "#ff4081",
            ];
            return colors[index % colors.length];
        },
    },
};
</script>

<style scoped>
.package-container {
    height: 200px;
}

.employee-item {
    cursor: pointer;
    margin-bottom: 1rem;
    transition: background-color 0.3s;
    padding: 0.5rem;
    border-radius: 0.25rem;
}

.employee-item:hover {
    background-color: #f0f0f0;
}

.employee-active {
    background-color: #e0e0e0;
    font-weight: bold;
}

.employee-initial {
    width: 40px;
    height: 40px;
    font-size: 16px;
}

.emp-img {
    width: 40px;
    aspect-ratio: 1/1;
    border-radius: 50%;
}
</style>
