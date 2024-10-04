<template>
    <div
        class="d-flex justify-content-between align-items-center mt-4 ms-4 me-4 mb-2"
    >
        <div class="view-container d-flex">
            <btn class="btn border p-2 me-2">Daily</btn>
            <btn class="btn border p-2 me-2">Weekly</btn>
            <btn class="btn border p-2">Monthly</btn>
        </div>

        <div
            class="date-picker-container d-flex flex-column align-items-center"
        >
            <div class="d-flex">
                <btn
                    class="btn border left-button p-2"
                    @click="decrementDate()"
                >
                    <font-awesome-icon icon="fa-solid fa-chevron-left" />
                </btn>
                <div class="p-2">{{ formattedDate }}</div>
                <btn
                    class="btn border right-button p-2"
                    @click="incrementDate()"
                >
                    <font-awesome-icon icon="fa-solid fa-chevron-right" />
                </btn>
            </div>

            <div class="">
                <div
                    class="d-flex justify-content-center align-items-center mt-1"
                >
                    <btn
                        class="btn border d-flex p-2 align-items-center me-2"
                        @click="setToday()"
                    >
                        <font-awesome-icon
                            :icon="['fas', 'calendar-week']"
                            class="me-2"
                        />
                        <div class="">Today</div>
                    </btn>
                    <btn class="btn border d-flex p-2 align-items-center">
                        <font-awesome-icon
                            :icon="['fas', 'maximize']"
                            class="me-2"
                        />
                        <div class="">Zoom Out</div>
                    </btn>
                </div>
            </div>
        </div>

        <div class="d-flex align-items-center">
            <btn class="btn border p-2 me-3"
                ><font-awesome-icon icon="fa-solid fa-filter"
            /></btn>

            <btn class="btn border p-2 d-flex align-items-center">
                <font-awesome-icon :icon="['fas', 'plus']" class="me-2" />
                <div class="">Add Job</div>
            </btn>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            currentDate: new Date(),
        };
    },
    computed: {
        formattedDate() {
            const options = {
                weekday: "short",
                day: "numeric",
                month: "short",
                year: "numeric",
            };

            const date = this.currentDate.toLocaleDateString("en-GB", options);
            const weekday = this.currentDate.toLocaleDateString("en-GB", {
                weekday: "short",
            });

            return date.replace(weekday + ",", "") + ` (${weekday})`;
        },
    },
    methods: {
        incrementDate() {
            this.currentDate.setDate(this.currentDate.getDate() + 1);
            this.currentDate = new Date(this.currentDate);
        },
        decrementDate() {
            this.currentDate.setDate(this.currentDate.getDate() - 1);
            this.currentDate = new Date(this.currentDate);
        },
        setToday() {
            this.currentDate = new Date();
        },
    },
};
</script>
