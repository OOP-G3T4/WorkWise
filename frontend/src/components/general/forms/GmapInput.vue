<template>
    <input v-model="value" />
</template>

<script>
export default {
    props: {
        inputValue: {
            type: String,
            required: false,
            default: ''
        }
    },
    data() {
        return {
            value: this.inputValue,
        }
    },
    watch: {
        value() {
            // Listens for changes in this.value
            this.emitValue();
        }
    },
    methods: {
        emitValue() {
            // Sends the value to the parent component
            this.$emit('input', this.value);
        }
    },
}
</script>