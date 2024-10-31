import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import "../sass/css/main.min.css";
import "bootstrap";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import store from "./store";
import router from "./router";
import VueGoogleMaps from "@fawmi/vue-google-maps";

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
    faCamera,
    faCircleChevronLeft,
    faCircleChevronRight,
    faMagnifyingGlassPlus,
    faMagnifyingGlassMinus,
    faQuestion,
    faCalendarXmark,
    faCaretDown,
    faMagnifyingGlass,
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
    faCamera,
    faCircleChevronLeft,
    faCircleChevronRight,
    faMagnifyingGlassPlus,
    faMagnifyingGlassMinus,
    faQuestion,
    faCalendarXmark,
    faCaretDown,
    faMagnifyingGlass,
);

const app = createApp(App);

app.component("font-awesome-icon", FontAwesomeIcon);

app.use(router)
    .use(store)
    .use(VueGoogleMaps, {
        load: {
            v: "quarterly",
            key: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
            libraries: "places",
        },
    })
    .mount("#app");
