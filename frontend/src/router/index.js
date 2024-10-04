import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "../Dashboard.vue";
import Leaves from "../Leaves.vue";

const routes = [
    {
        path: "/",
        name: "Dashboard",
        component: Dashboard,
    },
    {
        path: "/leaves",
        name: "Leaves",
        component: Leaves,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
