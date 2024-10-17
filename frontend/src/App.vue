<script setup>
import Navbar from "./components/Navbar.vue";
</script>

<template>
    <div class="main-container flex-column flex-md-row">
        <Navbar class="navbar" />
        <router-view class="content-container" />
    </div>
</template>


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