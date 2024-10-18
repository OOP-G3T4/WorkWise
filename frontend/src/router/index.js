import { createRouter, createWebHistory } from "vue-router";
import AdminCalendarDashboard from "../views/admin/AdminCalendarDashboard.vue";
import AdminLeavesDashboard from "../views/admin/AdminLeavesDashboard.vue";
import EmpLeavesDashboard from "../views/employee/EmpLeavesDashboard.vue";
import EmpCalendarDashboard from "../views/employee/EmpCalendarDashboard.vue";
import Login from "../views/Login.vue";

const routes = [
    {
        path: "/",
        name: "Login",
        component: Login,
    },
    {
        path: "/admin-calendar",
        name: "CalendarDashboard",
        component: AdminCalendarDashboard,
    },
    {
        path: "/admin-leaves",
        name: "LeavesDashboard",
        component: AdminLeavesDashboard,
    },
    {
        path: "/emp-leaves",
        name: "EmpLeavesDashboard",
        component: EmpLeavesDashboard,
    },
    {
        path: "/emp-calendar",
        name: "EmpCalendarDashboard",
        component: EmpCalendarDashboard,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
