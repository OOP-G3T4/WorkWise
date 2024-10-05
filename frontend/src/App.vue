<script setup>
import Navbar from "./components/Navbar.vue";
</script>

<template>
    <div class="main-container">
        <Navbar class="navbar" />
        <router-view class="content-container" />
    </div>
</template>


<style scoped>
.main-container {
    height: 100vh;
    width: 100vw;
    display: flex;
    flex-direction: row;
    overflow: hidden;
}

.content-container {
    flex: 1;
    overflow-y: auto;
}
</style>