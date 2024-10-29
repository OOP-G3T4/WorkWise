<template>
    <!-- NOTE: HAVE NOT FIGURED THIS OUT YET, TO DO LATER (ADAMBFT) -->
    <select class="form-select" v-model="value">
        <option v-for="(e_value, e_key) in items" :value="e_key">
            {{ e_value }}
        </option>
    </select>
</template>

<script>
export default {
    props: {
        items: {
            type: Object,
            required: false, // Change to true later
            default: {
                1: 'Employee 1',
                2: 'Employee 2',
                3: 'Employee 3',
                4: 'Employee 4',
                5: 'Employee 5',
            }
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
            console.log("VALUE: ", this.value);
            this.$emit('input', this.value);
        }
    },
}
</script>