<template>
    <div class="w-100 h-100 bg-light d-flex justify-content-center align-items-center">
        <div class="bg-white p-4 pb-5 rounded-4 m-3">
            <div class="container-fluid">
                <!-- Login title -->
                <div class="row mb-4">
                    <div class="col-12">
                        <h3 class="m-0">Login</h3>
                    </div>
                </div>

                <!-- Error Msg -->
                <div v-if="errorMsg" class="alert alert-danger text-center p-2">
                    <font-awesome-icon icon="fa-solid fa-circle-exclamation" class="me-2" />{{ errorMsg }}
                </div>

                <!-- User Type -->
                <div class="row mb-3">
                    <div class="col" v-for="user_obj, e_user_type in userTypes">
                        <button class="btn w-100" :class="e_user_type == selectedUserType ? 'btn-primary' : 'btn-light'" @click="clickUserType(e_user_type)">{{ e_user_type }}</button>
                    </div>
                </div>

                <!-- Login form -->
                <div class="row mb-5">
                    <div class="col-12">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="loginUsername-9DJQyye2" placeholder=" " v-model="userEmail">
                            <label for="loginUsername-9DJQyye2">Email address</label>
                        </div>
                    </div>
                    
                    <div class="col-12">
                        <div class="form-floating">
                            <input type="password" class="form-control" id="loginPw-8Ghw3Kj62Gt" placeholder=" " v-model="userPassword">
                            <label for="loginPw-8Ghw3Kj62Gt">Password</label>
                        </div>
                    </div>
                </div>

                <!-- Login button -->
                <div class="row">
                    <div class="col-12">
                        <button class="btn w-100 btn-primary" @click="handleLoginClick()">Login</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
export default {
    data() {
        return {
            userTypes: {
                "Admin" : {
                    "route": "/admin-calendar",
                    "navbarFormat": "admin",
                },
                "Employee" : {
                    "route": "/emp-calendar",
                    "navbarFormat": "employee",
                },
            },

            selectedUserType: null,
            userEmail: "",
            userPassword: "",
            errorMsg: "",
            userId: "",
        };
    },
    methods: {
        clickUserType(e_user_type) {
            this.selectedUserType = e_user_type;
        },
        async handleLoginClick() {
            if (!this.findLoginErrors()) {
                // Send API call for login [GET - Pass username and id through URL parameters]
                let api_url = `${this.$apiUrl}/${this.userTypes[this.selectedUserType].navbarFormat}/login?email=${this.userEmail}&password=${this.userPassword}`;

                try {
                    const response = await fetch(api_url, {
                        method: "POST",
                    });

                    if (!response.ok) {
                        // Handle errors
                        let errorMsg = await response.text();
                        this.errorMsg = errorMsg;
                        return;
                    }

                    // If no errors, log in user

                    const userId = await response.json();
                    
                    // Get user name
                    const nameResponse = await fetch(`${this.$apiUrl}/${this.userTypes[this.selectedUserType].navbarFormat}/${userId}`);
                    const userNameTemp = await nameResponse.json();
                    const userName = userNameTemp.name;

                    // Save user type and user id to Vuex
                    this.$store.dispatch("setUserLogin", {
                        type: this.userTypes[this.selectedUserType].navbarFormat,
                        id: userId,
                        name: userName,
                    });

                    // Redirect to user's main page
                    this.$router.push(this.userTypes[this.selectedUserType].route);
                } catch (error) {
                    this.errorMsg = error;
                }

                return;
            }
        },
        findLoginErrors() {
            // Check if email AND pw AND usertype are filled
            if (this.selectedUserType == null ||this.userEmail == "" || this.userPassword == "") {
                this.errorMsg = "Please fill in all fields";
                return true;
            }

            // Check login logic here [FILL LATER]

            // If no errors
            this.errorMsg = "";
            return false;
        },
    },
    computed: {
        navbarFormatMap() {
            // Return object in this format:
            // {
            //     navbarFormat1: route1,
            //     navbarFormat2: route2,
            //     ...
            // }

            var navbarFormatMap = {};

            for (const [e_user_type, user_obj] of Object.entries(this.userTypes)) {
                navbarFormatMap[user_obj.navbarFormat] = user_obj.route;
            }

            return navbarFormatMap;
        }
    },
    mounted() {
        // Auto-redirect if already logged in
        const userType = this.$store.state.userType;

        if (Object.keys(this.navbarFormatMap).includes(userType)) {
            this.$router.push(this.navbarFormatMap[userType]);
        }
    },
};
</script>