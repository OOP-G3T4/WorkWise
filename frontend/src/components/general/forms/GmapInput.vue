<template>
    <div class="form-floating">
        <GMapAutocomplete
            placeholder=" " 
            type="text"
            :options="autoCompleteOptions"
            class="form-control"
            @place_changed="setPlace"
            @change="handleChange()"
            @keyup="handleChange()"
            :value="value"
            ref="autocomplete"
        >
        </GMapAutocomplete>
        <label for="floatingInput">{{ fieldName }}</label>
    </div>
</template>

<script>
export default {
    emits: ['valChange'],
    props: {
        inputValue: {
            type: String,
            required: false,
            default: "",
        },
        fieldName: {
            type: String,
            required: true,
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
            // Get address string
            this.value = place.formatted_address
        },
        handleChange() {
            // Get address string
            let address = this.$refs.autocomplete.$refs.input.value;
            this.value = address;
        },
    },
};
</script>
