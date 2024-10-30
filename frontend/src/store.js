import { createStore } from 'vuex';

const store = createStore({
    state() {
        return {
            // Get from localStorage or default to ""
            userType: localStorage.getItem('userType') || "",
            userId: localStorage.getItem('userId') || "",
            userName: localStorage.getItem('userName') || "",
        };
    },
    mutations: {
        // User Type
        setUserType(state, type) {
            state.userType = type;
            localStorage.setItem('userType', type); // Save
        },
        clearUserType(state) {
            state.userType = "";
            localStorage.removeItem('userType'); // Clear
        },

        // User ID
        setUserId(state, id) {
            state.userId = id;
            localStorage.setItem('userId', id); // Save
        },
        clearUserId(state) {
            state.userId = "";
            localStorage.removeItem('userId'); // Clear
        },

        // User Name
        setUserName(state, name) {
            state.userName = name;
            localStorage.setItem('userName', name); // Save
        },
        clearUserName(state) {
            state.userName = "";
            localStorage.removeItem('userName'); // Clear
        },
    },
    actions: {
        setUserLogin({ commit }, { type, id, name }) {
            commit('setUserType', type);
            commit('setUserId', id);
            commit('setUserName', name);
        },
        clearUserLogin({ commit }) {
            commit('clearUserType');
            commit('clearUserId');
            commit('clearUserName');
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