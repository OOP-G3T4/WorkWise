import { createRouter, createWebHistory } from "vue-router";
import CalendarDashboard from "../CalendarDashboard.vue";
import LeavesDashboard from "../LeavesDashboard.vue";
import Login from "../Login.vue";

const routes = [
    {
        path: "/",
        name: "Login",
        component: Login,
    },
    {
        path: "/admin-calendar",
        name: "CalendarDashboard",
        component: CalendarDashboard,
    },
    {
        path: "/leaves",
        name: "LeavesDashboard",
        component: LeavesDashboard,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
