import { createStore } from 'vuex';

const store = createStore({
    state() {
        return {
            // UserType
            userType: localStorage.getItem('userType') || "", // Get from localStorage or default to ""
        };
    },
    mutations: {
        // UserType
        setUserType(state, type) {
            state.userType = type;
            localStorage.setItem('userType', type); // Save to localStorage
        },
        clearUserType(state) {
            state.userType = "";
            localStorage.removeItem('userType'); // Clear localStorage
        },
    },
    actions: {
        // UserType
        updateUserType({ commit }, type) {
            commit('setUserType', type);
        },
    },
});

export default store;