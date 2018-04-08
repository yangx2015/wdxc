export default{
		setItem(key,val){
			let value = JSON.stringify(val)
			sessionStorage.setItem(key,value)
		},
		getItem(key){
			let sessData = sessionStorage.getItem("sessionStoragekey")
			return JSON.parse(sessData)
		}
}
