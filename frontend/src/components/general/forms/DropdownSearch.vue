<script setup>
import * as bootstrap from 'bootstrap'
</script>

<template>
    <div class="dropdown">
        <!-- Show Input in Input Text Field -->
        <div class="input-group mb-3" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="outside" :disabled="isDisabled">
            <button class="btn btn-light" :disabled="isDisabled">
                <font-awesome-icon icon="fa-solid fa-caret-down" />
            </button>

            <div class="form-floating">
                <input type="text" class="form-control" :id="`show-selected-${fieldName}-${uniqueComponentId}`" placeholder="Username" :value="items[selectedId]" readonly :disabled="isDisabled">
                <label :for="`show-selected-${fieldName}-${uniqueComponentId}`">{{ fieldName }}</label>
            </div>
        </div>

        <!-- Dropdown Menu -->
        <form class="dropdown-menu p-3 w-100" :id="`dropdown-search-${fieldName}-${uniqueComponentId}`">
            <!-- Search Field -->
            <div class="input-group input-group-sm mb-3">
                <span class="input-group-text">
                    <font-awesome-icon icon="fa-solid fa-magnifying-glass" />
                </span>

                <input type="text" class="form-control" v-model="searchField" placeholder="search">
            </div>

            <!-- List of Items -->
            <div class="list-group">
                <template v-for="(value, id) in filteredItems">
                    <button type="button" class="list-group-item list-group-item-action" :class="listBtnClass(id)" @click="selectThis(id)">
                        {{ `${showId ? `[${id}]` : ``} ${value}` }}
                    </button>
                </template>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    props: {
        items: {
            type: Object,
            required: true,
        },
        inputValue: {
            type: String,
            required: false,
            default: "",
        },
        fieldName: {
            type: String,
            required: true,
        },
        uniqueComponentId: {
            type: String,
            required: true,
        },
        showId: {
            type: Boolean,
            required: false,
            default: false,
        },
        isDisabled: {
            type: Boolean,
            required: false,
            default: false,
        },
    },
    data() {
        return {
            selectedId: this.inputValue,
            filteredItems: this.items,
            searchField: "",
            dropdown: null,
        }
    },
    watch: {
        selectedId() {
            // Listens for changes in this.value
            this.emitValue();
        },
        searchField() {
            // Filters the filteredItems based on the searchField
            this.filteredItems = Object.fromEntries(
                Object.entries(this.items).filter(([key, value]) => {
                    return value.toLowerCase().includes(this.searchField.toLowerCase()) || key.toLowerCase().includes(this.searchField.toLowerCase());
                })
            );
        },
        items() {
            // Updates the filteredItems when items changes
            this.filteredItems = this.items;

            // Clear selectedId if it is not in the items
            if (!(this.selectedId in this.items)) {
                this.selectedId = "";
            }

            // Clear searchField
            this.searchField = "";
        },
    },
    methods: {
        emitValue() {
            // Sends the value to the parent component
            this.$emit('valChange', this.selectedId);
        },
        selectThis(key) {
            // Selects the item and closes the dropdown
            this.selectedId = key;

            // Close the dropdown
            this.dropdown.hide();
        },
        listBtnClass(id) {
            // Returns the class for the list button
            return {
                'active': id == this.selectedId,
            }
        },
    },
    mounted() {
        // Initializes the dropdown
        let dropdownElement = document.getElementById(`dropdown-search-${this.fieldName}-${this.uniqueComponentId}`);
        this.dropdown = new bootstrap.Dropdown(dropdownElement);
    },
}
</script>


<style scoped>
.list-group {
    max-height: 200px;
    overflow-y: auto;
}
</style>