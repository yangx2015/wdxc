
import session from '@/libs/session.js'
let dictUtil = {
    getValByCode(v,zdlmCode,zdxmCode){
//      let zdlm = v.$store.state.app.dictMap.get(zdlmCode);
		let dic	= session.getItem('dictMap')
		let zdlm = new Map(dic).get(zdlmCode)
        if (!zdlm)return '';
        for (let r of zdlm){
            if (r.key === zdxmCode){
                return r.val;
            }
        }
        return '';
    },
    getByCode(v,code){
//      let val = v.$store.state.app.dictMap.get(code);
        let dic	= session.getItem('dictMap')
        let val = new Map(dic).get(code)
        if (val){
            return val;
        }else{
            log('字典加载失败，重新登陆',code);
            v.$router.push({ name: 'login' });
        }
    },
}
export default dictUtil;
