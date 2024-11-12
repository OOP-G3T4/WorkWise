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
                <input type="text" class="form-control" :id="`show-selected-${fieldName}-${uniqueComponentId}`" placeholder="Username" :value="showItemsSelected" readonly :disabled="isDisabled">
                <label :for="`show-selected-${fieldName}-${uniqueComponentId}`">{{ fieldName }}{{ showNumSelected ? ` [${selectedIds.length > 0 ? selectedIds.length : 'all'}]` : '' }}</label>
            </div>

            <button class="btn btn-light" @click="clearSelected(true)">
                <font-awesome-icon icon="fa-solid fa-xmark" />
            </button>
        </div>

        <!-- Dropdown Menu -->
        <form class="dropdown-menu p-3 w-100" :id="`dropdown-search-${fieldName}-${uniqueComponentId}`">
            <!-- Top Bar -->
            <div class="btn-toolbar row m-0 mb-3">
                <!-- Search Field -->
                <div class="input-group input-group-sm p-0 col">
                    <span class="input-group-text">
                        <font-awesome-icon icon="fa-solid fa-magnifying-glass" />
                    </span>
    
                    <input type="text" class="form-control" v-model="searchField" placeholder="search">
                </div>

                <!-- Select All Button -->
                <button type="button" class="btn btn-sm btn-light col-auto ms-2" @click="selectAllFiltered()">
                    <font-awesome-icon icon="fa-solid fa-check" />
                </button>

                <!-- Clear Button -->
                <button type="button" class="btn btn-sm btn-light col-auto ms-2" @click="clearSelected(false)">
                    <font-awesome-icon icon="fa-solid fa-xmark" />
                </button>
            </div>

            <!-- List of Items -->
            <div class="list-group">
                <!-- Load selected first -->
                <template v-for="(value, id) in filteredItems">
                    <button v-if="selectedIds.includes(id)" type="button" class="list-group-item list-group-item-action" :class="listBtnClass(id)" @click="selectThis(id)">
                        {{ `${showId ? `[${id}]` : ``} ${value}` }}
                    </button>
                </template>

                <!-- Load unselected next -->
                <template v-for="(value, id) in filteredItems">
                    <button v-if="!selectedIds.includes(id)" type="button" class="list-group-item list-group-item-action" :class="listBtnClass(id)" @click="selectThis(id)">
                        {{ `${showId ? `[${id}]` : ``} ${value}` }}
                    </button>
                </template>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    emits: ['valChange'],
    props: {
        items: {
            type: Object,
            required: true,
        },
        inputValues: {
            type: [Array],
            required: false,
            default: () => [],
        },
        fieldName: {
            type: String,
            required: true,
        },
        uniqueComponentId: {
            type: [String, Number],
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
        showNumSelected: {
            type: Boolean,
            required: false,
            default: false,
        },
    },
    data() {
        return {
            selectedIds: this.inputValues,
            filteredItems: this.items,
            searchField: "",
            dropdown: null,
        }
    },
    watch: {
        selectedIds: {
            handler() {
                // Listens for changes in this.value
                this.emitValue();
            },
            deep: true,
            immediate: true,
        },
        searchField() {
            // Filters the filteredItems based on the searchField
            this.filteredItems = Object.fromEntries(
                Object.entries(this.items).filter(([key, value]) => {
                    return value.toLowerCase().includes(this.searchField.toLowerCase()) || key.toLowerCase().includes(this.searchField.toLowerCase());
                })
            );
        },
        items: {
            handler() {
                // Go through each item in selectedIds and remove any that are not in items
                this.filteredItems = this.items;
                this.selectedIds = this.selectedIds.filter(id => id in this.items);
            },
            deep: true,
            immediate: true,
        },
        inputValues: {
            handler() {
                // Updates the selectedId when inputValue changes
                this.selectedIds = this.inputValues;
            },
            deep: true,
        },
    },
    computed: {
        showItemsSelected() {
            // Returns the selected items in a string format
            return this.selectedIds.map(id => `${this.showId ? `[${id}]` : ``} ${this.items[id]}`).join(', ');
        },
    },
    methods: {
        emitValue() {
            // Sends the value to the parent component
            this.$emit('valChange', this.selectedIds);
        },
        selectThis(key) {
            // Adds or removes the key from the selectedIds depending on if it is already in the list
            if (this.selectedIds.includes(key)) {
                this.selectedIds = this.selectedIds.filter(id => id != key);
            } else {
                this.selectedIds.push(key);
            }
        },
        selectAllFiltered() {
            // Adds all the filtered items to the selectedIds
            this.selectedIds = Object.keys(this.filteredItems);
        },
        listBtnClass(id) {
            // Returns the class for the list button
            return {
                'active': this.selectedIds.includes(id),
            }
        },
        clearSelected(closeDropdown) {
            // Clears the selectedIds
            this.selectedIds = [];

            // Close the dropdown
            if (closeDropdown) {
                this.dropdown.hide();
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