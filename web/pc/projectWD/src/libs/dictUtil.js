import configApi from '@/axios/config.js'

let dictUtil = {
    getValByCode(v,zdlmCode,zdxmCode){
        let zdlm = v.$store.state.app.dictMap.get(zdlmCode);
        if (!zdlm)return '';
        for (let r of zdlm){
            if (r.key === zdxmCode){
                return r.val;
            }
        }
        return '';
    },
    getByCode(v,code){
        console.log(code);
        console.log(v.$store.state.app.dictMap);
        let val = v.$store.state.app.dictMap.get(code);
        console.log(val);
        if (val){
            return val;
        }
    },
}
export default dictUtil;