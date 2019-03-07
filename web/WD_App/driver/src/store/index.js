import Vue from 'vue';
import Vuex from 'vuex';
import app from './modules/app';
import user from './modules/user';
import createLogger from 'vuex/dist/logger';
import { Header } from 'mint-ui';

Vue.component(Header.name, Header);
Vue.use(Vuex);
const debug = process.env.NODE_ENV !== 'production'
const store = new Vuex.Store({
    state: {
        //
    },
    mutations: {
        //
    },
    actions: {

    },
    modules: {
        app,
        user
    },
    plugins: debug ? [createLogger()] : []
});

export default store;
