import { createStore } from 'vuex';

const store = createStore({
    state() {
        return {
            // UserType
            userType: localStorage.getItem('userType') || "", // Get from localStorage or default to ""
            userId: localStorage.getItem('userId') || "", // Get from localStorage or default to ""
        };
    },
    mutations: {
        // UserType
        setUserType(state, type) {
            state.userType = type;
            localStorage.setItem('userType', type); // Save
        },
        clearUserType(state) {
            state.userType = "";
            localStorage.removeItem('userType'); // Clear
        },
        setUserId(state, id) {
            state.userId = id;
            localStorage.setItem('userId', id); // Save
        },
        clearUserId(state) {
            state.userId = "";
            localStorage.removeItem('userId'); // Clear
        },
    },
    actions: {
        setUserLogin({ commit }, { type, id }) {
            commit('setUserType', type);
            commit('setUserId', id);
        },
        clearUserLogin({ commit }) {
            commit('clearUserType');
            commit('clearUserId');
        }
    },
    getters: {
        getUserType(state) {
            return state.userType;
        },
        getUserId(state) {
            return state.userId;
        },
    },
});

export default store;