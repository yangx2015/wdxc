<template>
  <Form ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="handleSubmit">
    <FormItem prop="username">
      <Input v-model="form.username" placeholder="请输入用户名">
      <span slot="prepend">
          <Icon :size="16" type="ios-person"></Icon>
        </span>
      </Input>
    </FormItem>
    <FormItem prop="password">
      <Input type="password" v-model="form.password" placeholder="请输入密码">
      <span slot="prepend">
          <Icon :size="14" type="md-lock"></Icon>
        </span>
      </Input>
    </FormItem>
    <Row>
      <Col span="14">
        <Row>
          <Col span="24">
            <FormItem prop="captcha">
              <Input v-model="form.captcha" placeholder="请输入验证码">
              <span slot="prepend">
              <Icon :size="14" type="md-key"></Icon>
            </span>
              </Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <Row>
              <Col span="12" style="padding: 0 16px">
                <Button @click="handleSubmit" type="primary" long>登录</Button>
              </Col>
              <Col span="12" style="padding: 0 16px">
                <Button @click="newMess" type="primary" long>重置</Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
      <Col span="10">
        <img :src="YzUrl" width="100%" alt="验证码" style="margin-left: 8px;cursor: pointer"  @click="getUrl">
      </Col>
    </Row>
  </Form>
</template>
<script>
  import Cookies from 'js-cookie';
  import {mapMutations} from 'vuex'

  export default {
    name: 'LoginForm',
    props: {
      userNameRules: {
        type: Array,
        default: () => {
          return [
            {required: true, message: '账号不能为空', trigger: 'blur'}
          ]
        }
      },
      passwordRules: {
        type: Array,
        default: () => {
          return [
            {required: true, message: '密码不能为空', trigger: 'blur'}
          ]
        }
      },
      captchaRules:{
        type: Array,
        default: () => {
          return [
            {required: true, message: '请填写验证码', trigger: 'blur'}
          ]
        }
      }
    },
    data() {
      return {
        YzUrl: '',
        form: {
          username: '',
          password: '',
          captcha:'',
          codeID:''
        }
      }
    },
    computed: {
      rules() {
        return {
          username: this.userNameRules,
          password: this.passwordRules,
          captcha:this.captchaRules
        }
      }
    },
    created() {
      if(localStorage.getItem('user')){
        this.form.username = localStorage.getItem('user')
      }

      this.messClear()
      this.getUrl()
    },
    methods: {
      ...mapMutations([
        'setPermissionMenuList'
      ]),
      newMess(){
        this.form = {
          username: '',
            password: '',
            captcha:'',
            codeID:''
        }
      },
      messClear(){
        localStorage.removeItem("menuList");
        sessionStorage.clear()
        Cookies.remove('accessToken')
        Cookies.remove('usermess')
      },
      getUrl() {
        let codeId =this.AF.getRandom(8)
        this.YzUrl = this.apis.url + this.apis.LOGIN.YZ + codeId
        this.form.codeID = codeId
      },
      handleSubmit() {
        var v = this
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            v.$http.post(this.apis.LOGIN.QUERY, this.form).then((res) => {
              if (res.code === 200) {
                localStorage.setItem('user',this.form.username)
                Cookies.set('usermess', this.form.username);
                Cookies.set('accessToken', res.result.accessToken);

                sessionStorage.setItem("userInfo", JSON.stringify(res.result.userInfo));
                localStorage.setItem('menuList', JSON.stringify(res.result.menuTree))
                this.setMenuList()
                v.initDict(res.result.dictList);

                this.$router.push('home')
              } else {
                this.$Message.error(res.message);
              }
              this.getUrl()
            })
          }
        })
      },
      initDict(dictList) {
        for (let r of dictList){
          if ('ZDCLK1017' === r.lmdm){
              this.initSchoolPY(r);
              break;
          }
        }
        this.session.setItem('dictMap', dictList)
      },
      initSchoolPY(dict){
        for (let r of dict.zdxmList){
          r.by1 = this.util.parsePY(r.zdmc);
        }
      },
      setMenuList() {
        this.setPermissionMenuList(JSON.parse(localStorage.getItem('menuList')))
      }
    }
  }
</script>
