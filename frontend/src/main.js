import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import "../sass/css/main.min.css";
import "bootstrap";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import router from "./router";

import {
    faUser,
    faCalendar,
    faUmbrella,
    faGear,
    faRightFromBracket,
    faChevronLeft,
    faChevronRight,
    faFilter,
    faPlus,
    faCalendarWeek,
    faMaximize,
    faUsers,
    faCircle,
    faCircleExclamation,
    faPenToSquare,
    faTrash,
    faClock,
    faCheck,
    faXmark,
    faPhone,
    faEnvelope,
    faComment,
    faBriefcaseMedical,
    faUmbrellaBeach,
    faCircleCheck,
    faCircleXmark,
    faCircleRight,
    faCircleLeft,
    faArrowRightFromBracket,
} from "@fortawesome/free-solid-svg-icons";

library.add(
    faUser,
    faCalendar,
    faUmbrella,
    faGear,
    faRightFromBracket,
    faChevronLeft,
    faChevronRight,
    faFilter,
    faPlus,
    faCalendarWeek,
    faMaximize,
    faUsers,
    faCircle,
    faCircleExclamation,
    faPenToSquare,
    faTrash,
    faClock,
    faCheck,
    faXmark,
    faPhone,
    faEnvelope,
    faComment,
    faBriefcaseMedical,
    faUmbrellaBeach,
    faCircleCheck,
    faCircleXmark,
    faCircleRight,
    faCircleLeft,
    faArrowRightFromBracket,
);

const app = createApp(App);

app.component("font-awesome-icon", FontAwesomeIcon);

app.use(router);

app.mount("#app");
