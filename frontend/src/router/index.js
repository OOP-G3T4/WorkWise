import { createRouter, createWebHistory } from "vue-router";
import AdminCalendarDashboard from "../views/admin/AdminCalendarDashboard.vue";
import AdminLeavesDashboard from "../views/admin/AdminLeavesDashboard.vue";
import EmpLeavesDashboard from "../views/employee/EmpLeavesDashboard.vue";
import EmpCalendarDashboard from "../views/employee/EmpCalendarDashboard.vue";
import Login from "../views/Login.vue";
import store from "../store";
import AdminInsightDashboard from "../views/admin/AdminInsightDashboard.vue";

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
        meta: {
            requiresAuth: true,
            allowedUserTypes: ["admin"],
        },
    },
    {
        path: "/admin-leaves",
        name: "LeavesDashboard",
        component: AdminLeavesDashboard,
        meta: {
            requiresAuth: true,
            allowedUserTypes: ["admin"],
        },
    },
    {
        path: "/emp-leaves",
        name: "EmpLeavesDashboard",
        component: EmpLeavesDashboard,
        meta: {
            requiresAuth: true,
            allowedUserTypes: ["employee"],
        },
    },
    {
        path: "/emp-calendar",
        name: "EmpCalendarDashboard",
        component: EmpCalendarDashboard,
        meta: {
            requiresAuth: true,
            allowedUserTypes: ["employee"],
        },
    },
    {
        path: "/insight-dashboard",
        name: "InsightDashboard",
        component: AdminInsightDashboard,
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Global navigation guard
router.beforeEach((to, from, next) => {
    const userType = store.getters.getUserType;
    const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
    const allowedUserTypes = to.meta.allowedUserTypes;

    if (requiresAuth && !userType) {
        // If the route requires authentication and no userType is set, redirect to login
        next('/');
    } else if (requiresAuth && !allowedUserTypes.includes(userType)) {
        // If user type is not allowed for this route, prevent access
        next('/');
    } else {
        // Proceed if user is authorized or the page doesn't require auth
        next();
    }
});

export default router;
