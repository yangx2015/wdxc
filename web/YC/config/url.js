import env from './env'
// https://www.easy-mock.com/mock/5add9213ce4d0e69998a6f51/iview-admin/
const DEV_URL = ''
const PRO_URL = 'https://produce.com'

export default env === 'development' ? DEV_URL : PRO_URL
