<template>
    <input type="text" class="form-control" v-model="value" />
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
            this.$emit('valChange', {value : this.value});
        }
    },
}
</script>