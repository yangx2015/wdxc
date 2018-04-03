
let utils = {
    del(v,id){
        this.$http.post(configApi.XL.QUERY,{params:this.findMess}).then((res) =>{
            this.SpinShow = false;
            if(res.code===200){
                this.data9 = res.page.list;
                this.pageTotal = res.page.total;
                console.log(this.data9);
            }
        })
    }
}