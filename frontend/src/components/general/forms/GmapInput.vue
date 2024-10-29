<template>
    <input type="text" class="form-control" v-model="value" />
    <GMapAutocomplete
        placeholder=" " 
        type="text"
        :options="autoCompleteOptions"
        class="form-control"
        @place_changed="setPlace"
    >
    </GMapAutocomplete>
</template>

<script>
export default {
    props: {
        inputValue: {
            type: String,
            required: false,
            default: "",
        },
    },
    data() {
        return {
            value: this.inputValue,
            autoCompleteOptions: {
                componentRestrictions: {
                    country: ["sg"],
                },
            },
        };
    },
    watch: {
        value() {
            // Listens for changes in this.value
            this.emitValue();
        },
    },
    methods: {
        emitValue() {
            // Sends the value to the parent component
            this.$emit("valChange", { value: this.value });
        },
        setPlace(place) {
            console.log(place)
        },
    },
};
</script>
