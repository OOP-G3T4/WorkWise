import { createRouter, createWebHistory } from "vue-router";
import AdminCalendarDashboard from "../views/AdminCalendarDashboard.vue";
import AdminLeavesDashboard from "../views/AdminLeavesDashboard.vue";
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
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
