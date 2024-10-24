<script setup>
import Navbar from "./components/general/Navbar.vue";
import { mapState } from "vuex";
</script>

<template>
    <div class="main-container flex-column flex-md-row">
        <Navbar v-if="!toHideNavbar()" :userRole="userType" />
        <router-view class="content-container" />
    </div>
</template>


<script>
export default {
    computed: {
        ...mapState(["userType"]),  // Access userType from Vuex state
    },
    methods: {
        toHideNavbar() {
            var routesHideNavbar = ["/"];

            return routesHideNavbar.includes(this.$route.path);
        },
    },
    mounted() {
        // Reroute to login if userRole not set yet on VUEX
        if (!this.userType) {
            this.$router.push("/");
        }
    },
};
</script>


<style scoped>
.main-container {
    height: 100vh;
    width: 100vw;
    display: flex;
    overflow: hidden;
}

.content-container {
    flex: 1;
    overflow-y: auto;
}
</style>