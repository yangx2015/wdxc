<template>
      <div class="modal">
        <div class="modaltit">
            单据确认
        </div>
        <div class="Ymess">
          <p class="mesPadd">
              <Icon type="ios-film-outline"></Icon>
              信息工程学院-小名
          </p>
          <p class="mesPadd">
            <Icon type="ios-clock"></Icon>
            2018-11-11 09:00:00
          </p>
          <p class="mesPadd">
            <Icon type="ios-telephone"></Icon>
            <a>123456789000</a>
          </p>
          <p>
            <Icon type="ios-location" color="#15b740"></Icon>
            候车地点
          </p>
          <p><Icon type="arrow-down-c"></Icon></p>
          <p>
            <Icon type="ios-location" color="#ff9b00"></Icon>
            目的地
          </p>
        </div>
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="0">
            <Row :gutter="16">
                <Col span="12">
                    <FormItem label="里程" prop="lc">
                        <Input v-model="formValidate.lc" placeholder="里程/公里数"></Input>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="单价" prop="dj">
                        <Input type="number" v-model="formValidate.dj"
                               placeholder="行程单价"></Input>
                    </FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="12">
                  <FormItem label="过桥费">
                    <Input type="number" v-model="formValidate.gqf"
                           placeholder="过桥费"></Input>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem label="路停费">
                    <Input type="number" v-model="formValidate.ltf"
                           placeholder="路停费"></Input>
                  </FormItem>
                </Col>
            </Row>
        </Form>
        <div class="but">
          <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
          <Button type="ghost" @click="handleReset()" style="margin-left: 8px">取消</Button>
        </div>
      </div>
</template>
<style lang="less" scoped>
  .modal{
    background: #fff;
    padding: 18px;
    position: fixed;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    .modaltit{
      height: 40px;
      line-height: 40px;
      text-align: center;
      background: rgba(0,0,0,0.1);
      font-size: 18px;
      color: #919191;
      font-weight: 600;
    }
    .Ymess{
      padding: 5px;
      p{
        font-size: 18px;
      }
      .mesPadd{
        padding: 6px 0;
      }
    }
    .but{
      text-align: center;
    }
  }
</style>
<script>
    export default {
        name: "modal",
        data(){
          return {
            formValidate: {
              lc: '',
              dj: '',
              gqf: '',
              ltf: ''
            },
            ruleValidate: {
              lc: [
                { required: true, message: '请填写公里数', trigger: 'blur' }
              ],
              dj: [
                { required: true, message: '请输入行程单价', trigger: 'blur' }
              ]
            }
          }
        },
        methods: {
          handleSubmit (name) {
            this.$refs[name].validate((valid) => {
              if (valid) {
              } else {
              }
            })
          },
          handleReset () {
              // this.$parent.compName= ''
            this.$emit('close')
          }
        }
    }
</script>

<style scoped>

</style>
