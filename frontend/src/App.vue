<script setup>
import Navbar from "./components/Navbar.vue";
</script>

<template>
    <div class="main-container flex-column flex-md-row">
        <Navbar v-if="!toHideNavbar()" />
        <router-view class="content-container" />
    </div>
</template>


<script>
export default {
    methods: {
        toHideNavbar() {
            var routesHideNavbar = ["/"];

            return routesHideNavbar.includes(this.$route.path);
        },
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