<script setup>
import * as bootstrap from 'bootstrap'
</script>

<template>
    <template v-if="checkUserRoleValid()">
        <!-- Collapsed Side Bar (Always visible on desktop) -->
        <div class="bg-light flex-column justify-content-between align-items-center px-3 py-4 d-none d-md-flex">
            <!-- Logo (Skip for now) -->
    
            <!-- Navigation -->
            <div class="d-flex flex-column align-items-center">
                <template v-for="e_nav_obj in navbarMap[userRole].main">
                    <button class="btn w-100 mb-3" :class="isActiveRoute(e_nav_obj.route) ? 'btn-white-selected' : 'btn-light'" @click="redirectTo(e_nav_obj.route)">
                        <font-awesome-icon class="fa-lg" :icon="e_nav_obj.icon" />
                    </button>
                </template>
            </div>
    
            <!-- Expand Button -->
            <button class="btn btn-light btn-lg rounded-5" type="button" data-bs-toggle="offcanvas" data-bs-target="#mainSideNavBar" aria-controls="mainSideNavBar">
                <font-awesome-icon class="fa-lg" icon="fa-solid fa-circle-right" />
            </button>
        </div>
    
        <!-- Expandable Navbar -->
        <div class="offcanvas offcanvas-start bg-light" data-bs-scroll="true" tabindex="-1" id="mainSideNavBar" aria-labelledby="mainSideNavBarLabel">
            <!-- Header (Top) -->
            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="mainSideNavBarLabel">WorkWise</h5>
                <button type="button" class="btn btn-light ms-auto rounded-5" data-bs-dismiss="offcanvas" aria-label="Close">
                    <font-awesome-icon class="fa-lg" icon="fa-solid fa-circle-left" />
                </button>
            </div>
    
            <!-- Body (Bottom) -->
            <div class="offcanvas-body d-flex flex-column">
                <!-- User Card -->
                <div class="bg-dark text-white rounded d-flex p-3 align-items-center">
                    <img src="https://placehold.co/200x200?text=Profile+Pic" alt="User Image" class="user-img me-3" />
                    <div>
                        <h6 class="m-0">{{ userName }}</h6>
                        <p class="m-0">{{ userRole }}</p>
                    </div>
                </div>
    
                <!-- Divider -->
                <hr class="my-3 border-3 rounded" />
    
                <!-- Main Navigation -->
                <p class="m-0 text-secondary">MAIN MENU</p>
    
                <div class="d-flex flex-column align-items-center mt-3">
                    <template v-for="e_nav_obj in navbarMap[userRole].main">
                        <button class="btn w-100 mb-3 p-3 text-start" :class="isActiveRoute(e_nav_obj.route) ? 'btn-white-selected' : 'btn-light'" @click="redirectTo(e_nav_obj.route)">
                            <h6 class="m-0 fw-normal">
                                <font-awesome-icon class="fa-lg me-2" :icon="e_nav_obj.icon" />{{ e_nav_obj.label }}
                            </h6>
                        </button>
                    </template>
                </div>
    
                <!-- Divider -->
                <hr class="my-3 border-3 rounded" />
    
                <!-- Hidden Navigation -->
                <p class="m-0 text-secondary">OTHERS</p>
    
                <div class="d-flex flex-column align-items-center mt-3">
                    <template v-for="e_nav_obj in navbarMap[userRole].hidden">
                        <button class="btn w-100 mb-3 p-3 text-start" :class="isActiveRoute(e_nav_obj.route) ? 'btn-white-selected' : 'btn-light'" @click="redirectTo(e_nav_obj.route)">
                            <h6 class="m-0 fw-normal">
                                <font-awesome-icon class="fa-lg me-2" :icon="e_nav_obj.icon" />{{ e_nav_obj.label }}
                            </h6>
                        </button>
                    </template>
                </div>
            </div>
        </div>
    
        
        <!-- Top Navbar (For MD and below) -->
        <nav class="navbar navbar-expand-md bg-light d-md-none">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">WorkWise</a>
    
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#topNavbarMain" aria-controls="topNavbarMain" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
    
                <div class="collapse navbar-collapse p-3" id="topNavbarMain">
                    <!-- User Card -->
                    <div class="bg-dark text-white rounded d-flex p-3 align-items-center">
                        <img src="https://placehold.co/200x200?text=Profile+Pic" alt="User Image" class="user-img me-3" />
                        <div>
                            <h6 class="m-0">{{ userName }}</h6>
                            <p class="m-0">{{ userRole }}</p>
                        </div>
                    </div>
    
                    <!-- Divider -->
                    <hr class="my-3 border-3 rounded" />
    
                    <!-- Main Navigation -->
                    <p class="text-secondary">MAIN MENU</p>
    
                    <div class="container-fluid p-0">
                        <div class="row m-0">
                            <template v-for="e_nav_obj in navbarMap[userRole].main">
                                <div class="col-auto p-0 me-3">
                                    <button class="btn w-100 p-3 text-start" :class="isActiveRoute(e_nav_obj.route) ? 'btn-white-selected' : 'btn-light'" @click="redirectTo(e_nav_obj.route)">
                                        <h6 class="m-0 fw-normal">
                                            <font-awesome-icon class="fa-lg me-2" :icon="e_nav_obj.icon" />{{ e_nav_obj.label }}
                                        </h6>
                                    </button>
                                </div>
                            </template>
                        </div>
                    </div>
    
                    <!-- Divider -->
                    <hr class="my-3 border-3 rounded" />
    
                    <!-- Hidden Navigation -->
                    <p class="text-secondary">OTHERS</p>
    
                    <div class="container-fluid p-0">
                        <div class="row m-0">
                            <template v-for="e_nav_obj in navbarMap[userRole].hidden">
                                <div class="col-auto p-0 me-3">
                                    <button class="btn w-100 p-3 text-start" :class="isActiveRoute(e_nav_obj.route) ? 'btn-white-selected' : 'btn-light'" @click="redirectTo(e_nav_obj.route)">
                                        <h6 class="m-0 fw-normal">
                                            <font-awesome-icon class="fa-lg me-2" :icon="e_nav_obj.icon" />{{ e_nav_obj.label }}
                                        </h6>
                                    </button>
                                </div>
                            </template>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </template>
</template>

<script>
export default {
    props: {
        userRole: {
            type: String,
            required: true,
        }
    },
    data() {
        return {
            userName: "John Doe",

            // Offcanvas elem
            sideBarElem: null,
            topNavBarElem: null,

            // Defines the navigation bar items for each user role
            navbarMap: {
                "admin" : {
                    // To be displayed on the collapsed side bar
                    "main" : [
                        {
                            "label" : "Calendar",
                            "icon" : "fa-solid fa-calendar",
                            "route" : "/admin-calendar"
                        },
                        {
                            "label" : "Leaves",
                            "icon" : "fa-solid fa-umbrella-beach",
                            "route" : "/admin-leaves"
                        },
                    ],

                    // To be displayed on the collapsed AND expanded side bar
                    "hidden" : [
                        {
                            "label" : "Settings",
                            "icon" : "fa-solid fa-gear",
                            "route" : "/settings" // CHANGE WHEN IMPLEMENTED
                        },
                        {
                            "label" : "Log Out",
                            "icon" : "fa-solid fa-arrow-right-from-bracket",
                            "route" : "/" // CHANGE WHEN IMPLEMENTED
                        }
                    ]
                },

                "employee" : {
                    // To be displayed on the collapsed side bar
                    "main" : [
                        {
                            "label" : "Leaves",
                            "icon" : "fa-solid fa-umbrella-beach",
                            "route" : "/emp-leaves"
                        }
                    ],

                    // To be displayed on the collapsed AND expanded side bar
                    "hidden" : [
                        {
                            "label" : "Settings",
                            "icon" : "fa-solid fa-gear",
                            "route" : "/settings" // CHANGE WHEN IMPLEMENTED
                        },
                        {
                            "label" : "Log Out",
                            "icon" : "fa-solid fa-arrow-right-from-bracket",
                            "route" : "/" // CHANGE WHEN IMPLEMENTED
                        }
                    ]
                }
            }
        };
    },
    methods: {
        isActiveRoute(route) {
            return this.$route.path === route;
        },
        redirectTo(route) {
            this.sideBarElem.hide();
            this.topNavBarElem.hide();

            // If logging out, clear userRole from Vuex
            if (route === "/") {
                this.$store.commit("clearUserType");
            }

            this.$router.push(route);
        },
        checkUserRoleValid() {
            if (!Object.keys(this.navbarMap).includes(this.userRole)) {
                return false;
            }

            return true;
        }
    },
    mounted() {
        // Initialize sideNavBar elem AND topNavBar elem
        this.sideBarElem = new bootstrap.Offcanvas(document.getElementById("mainSideNavBar"));
        this.topNavBarElem = new bootstrap.Collapse(document.getElementById("topNavbarMain"), {
            toggle: false
        });
    },
};
</script>

<style scoped>
.user-img {
    width: 50px;
    aspect-ratio: 1/1;
    border-radius: 50%;
}
</style>
