import Cookies from 'js-cookie';
import Vue from 'vue';

const app = {
    state: {
        barNum:0
    },
    mutations: {
        barCh(state,data){
            state.barNum = data
        },
    }
};

export default app;
