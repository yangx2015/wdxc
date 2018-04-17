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
        let val = v.$store.state.app.dictMap.get(code);
        if (val){
            return val;
        }
    },
}
export default dictUtil;