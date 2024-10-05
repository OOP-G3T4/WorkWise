<template>
    <div
        class="d-flex flex-column flex-shrink-0 p-3 navbar-container border"
    >
        <div class="mb-4">WorkWise</div>

        <div
            class="name-role-container rounded-2 bg-secondary p-2 text-light"
        >
            <div class="d-flex align-items-center">
                <font-awesome-icon
                    icon="fa-bold fa-user"
                    class="fa-2x me-2"
                />
                <div class="name-role-details">
                    <div class="name">{{ adminName }}</div>
                    <div class="role">{{ role }}</div>
                </div>
            </div>
        </div>

        <hr />

        <div class="main-menu-container">
            <div class="main-menu-title mb-2">Main Menu</div>
            <router-link to="/" class="btn border d-flex align-items-center mb-2 p-2">
                <div class="icon-container me-2">
                    <font-awesome-icon
                        icon="fa-bold fa-calendar"
                        class="fa-lg"
                    />
                </div>
                <span>Calendar</span>
            </router-link>

            <router-link
                to="/leaves"
                class="btn border d-flex align-items-center p-2"
            >
                <div class="icon-container me-2">
                    <font-awesome-icon
                        icon="fa-bold fa-umbrella"
                        class="fa-lg"
                    />
                </div>
                <span>Leaves</span>
            </router-link>
        </div>

        <div class="mt-auto">
            <btn class="btn border d-flex align-items-center p-2 mb-2">
                <div class="icon-container me-2">
                    <font-awesome-icon icon="fa-solid fa-gear" />
                </div>
                <span>Settings</span>
            </btn>

            <btn class="btn border d-flex align-items-center p-2">
                <div class="icon-container me-2">
                    <font-awesome-icon
                        icon="fa-solid fa-right-from-bracket"
                    />
                </div>
                <span>Log Out</span>
            </btn>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            adminName: "John Doe",
            role: "admin",
        };
    },
};
</script>

<style scope>
.navbar-container {
    width: 200px;
    height: 100vh;
}

.icon-container {
    width: 24px;
    display: flex;
    justify-content: center;
}

.name-role-details {
    font-size: 0.9rem;
}

.role {
    font-size: 0.8rem;
    text-transform: capitalize;
}

.main-menu-title {
    text-transform: uppercase;
}
</style>
