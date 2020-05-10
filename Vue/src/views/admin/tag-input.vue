<template>
    <div style="margin-top: 2em">

        <div class="m-container-small" style="padding: 10em;padding-right: 25em">
            <div class="ui container">
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                         class="ui large form">
                    <div class="ui segment">
                        <div class="field">
                            <el-form-item prop="name" class="ui left icon input">
                                <el-input suffix-icon="el-icon-place" placeholder="标签名"
                                          v-model="ruleForm.name" autocomplete="off"></el-input>
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
</template>

<script type="text/ecmascript-6">

    export default {
        name: "tagInput",

        data() {
            return {
                meta: {
                    edit: false
                },
                ruleForm: {
                    name: '',
                },
                rules: {
                    type: [
                        {required: true, message: '请输入类别名称', trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            // 提交
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if ((this.$route.params.key == 'add')) {
                            axios.post('/blog/admin/tags', this.ruleForm).then(resp => {
                                if (resp.data == 'success') {
                                    this.$message({
                                        offset: 140,
                                        message: '新增成功~',
                                        type: 'success',
                                        duration: 1500
                                    });
                                    setTimeout(() => {
                                        this.$router.push('/a_tags')
                                    }, 1500)

                                } else if (resp.data == 'null') {
                                    this.$message({
                                        offset: 140,
                                        message: '不能提交空名字哦。',
                                        type: 'error',
                                        duration: 1500
                                    });
                                } else if (resp.data == 'exists') {
                                    this.$message({
                                        offset: 140,
                                        message: '当前标签已存在，换一个试试吧~',
                                        type: 'warning',
                                        duration: 1500
                                    });
                                } else {
                                    this.$message({
                                        offset: 140,
                                        message: '服务器可能出现了点异常QAQ',
                                        type: 'error',
                                        duration: 1500
                                    });
                                }
                            });
                        } else if ((this.$route.params.key == 'edit')) {
                            axios.post('/blog/admin/tags/' + this.$route.params.id + '/' + this.$route.params.name, this.ruleForm).then(resp => {
                                if (resp.data == 'success') {
                                    this.$message({
                                        offset: 140,
                                        message: '修改成功~',
                                        type: 'success',
                                        duration: 1500
                                    });
                                    setTimeout(() => {
                                        this.$router.push('/a_tags')
                                    }, 1500)

                                } else if (resp.data == 'notExists') {
                                    this.$message({
                                        offset: 140,
                                        message: '需要规范操作哦~',
                                        type: 'warning',
                                        duration: 1500
                                    });
                                } else {
                                    this.$message({
                                        offset: 140,
                                        message: '服务器可能出现了点异常QAQ',
                                        type: 'error',
                                        duration: 1500
                                    });
                                }
                            });
                        }

                    } else {
                        return false;
                    }
                });
            },

            // 重置
            resetForm(formName) {
                if (this.$route.params.key == 'edit') {
                    this.ruleForm.name = this.$route.params.name;
                } else {
                    this.$refs[formName].resetFields();
                }
            },
        },
        created() {
            if (this.$route.params.key == 'edit') {
                this.ruleForm.name = this.$route.params.name;
            }
        }
    }
</script>

<style>

</style>