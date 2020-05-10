<template>

    <div>

        <br>
        <br>
        <br>
        <div class="m-container-small m-padded-tb-massive" style="max-width: 30em !important;">
            <div class="ur container">
                <div class="ui middle aligned grid">
                    <div class="column">
                        <h2 class="ui teal image header" style="margin-left: 5.8em">
                            <div class="content">
                                管理后台登录
                            </div>
                        </h2>

                        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                                 class="ui large form">
                            <div class="ui segment">
                                <div class="field">
                                    <el-form-item prop="username" class="ui left icon input">
                                        <el-input suffix-icon="el-icon-user-solid" placeholder="用户名"
                                                  v-model="ruleForm.username" autocomplete="off"></el-input>
                                    </el-form-item>
                                </div>
                                <div class="field">
                                    <el-form-item prop="password" class="ui left icon input">
                                        <el-input suffix-icon="el-icon-lock" placeholder="密码" type="password"
                                                  v-model="ruleForm.password" autocomplete="off" show-password>
                                        </el-input>
                                    </el-form-item>
                                </div>
                                <el-form-item>
                                    <el-button class="ui teal button"
                                               type="primary"
                                               @click="submitForm('ruleForm')">提交
                                    </el-button>
                                    <el-button class="ui button" @click="resetForm('ruleForm')">重置
                                    </el-button>
                                </el-form-item>
                            </div>


                        </el-form>
                    </div>
                </div>
            </div>
        </div>
        <!--<button @click="getUserSession">get</button>-->
        <!--<button @click="rmSession">rm</button>-->

    </div>


</template>

<script>
    import { Loading } from 'element-ui';
    export default {
        name: "Login",
        data() {
            var validateusername = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入用户名'));
                } else if (value.length < 5) {
                    callback(new Error('用户名长度不足5位'));
                } else {
                    callback()
                }
            };
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    callback()
                }
            };
            return {
                meta: {
                    type: ''
                },
                ruleForm: {
                    username: '',
                    password: '',
                },
                rules: {
                    username: [
                        {validator: validateusername, trigger: 'blur'}
                    ],
                    password: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let options = {
                            lock: true,
                            text: 'Loading',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        }

                        let loadingInstance = Loading.service(options);
                        axios.post('/blog/admin/login', this.ruleForm).then(resp => {
                            if (resp.data.msg === 'success') {
                                localStorage.setItem("token",resp.data.token)
                                localStorage.setItem("id",resp.data.id)

                                this.$message({
                                    offset: 140,
                                    message: '登录成功~',
                                    type: 'success',
                                    duration:1500
                                });
                                this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                    loadingInstance.close();
                                });
                                setTimeout(() => {
                                    this.$router.push('/a_blogs')

                                }, 1500)

                            } else {
                                this.$message({
                                    offset: 140,
                                    message: '用户名密码错误QAQ',
                                    type: 'error',
                                    duration:1500
                                });
                                this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                    loadingInstance.close();
                                });
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
        },
        created() {
            if (this.$route.params.status == 'notLoged') {
                this.$message({
                    offset: 140,
                    message: '请先登录后再进行访问哦~',
                    type: 'warning',
                    duration: 2500
                });
            } else if(this.$route.params.status == 'logout'){
                this.$message({
                    offset: 140,
                    message: '注销成功~',
                    type: 'success',
                    duration: 2000
                });
            }
        }
    }
</script>

<style scoped>

</style>