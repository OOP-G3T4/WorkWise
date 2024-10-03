import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import { faUser, faCalendar, faUmbrella, faGear, faRightFromBracket } from "@fortawesome/free-solid-svg-icons";

library.add(faUser, faCalendar, faUmbrella, faGear, faRightFromBracket);

const app = createApp(App);

app.component("font-awesome-icon", FontAwesomeIcon);

app.mount("#app");
