import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import request from "@/utils/request";
import tool from "./utils/tool";
import store from "./store/index";

Vue.config.productionTip = false

Vue.use(ElementUI, {size: 'normal'});

Vue.prototype.request = request
// main.js全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)

Vue.use(tool);

new Vue({
  router,store,
  render: h => h(App)
}).$mount('#app')
