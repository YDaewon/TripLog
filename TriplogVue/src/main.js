// import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import App from './App.vue';
import { CkeditorPlugin } from '@ckeditor/ckeditor5-vue';
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);

app.use(CkeditorPlugin)
app.use(pinia);
// app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);

router.isReady().then(() => {
  app.mount("#app");
});
