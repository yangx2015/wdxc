import Cookies from 'js-cookie';
import Vue from 'vue';

const app = {
    state: {
      lineData:{}
    },
    mutations: {
      lineMess(state,data){
            state.lineData = data
        }
    }
};

export default app;
