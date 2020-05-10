<template>
    <!--<div style="margin-top: 2em">-->

    <!--</div>-->

    <div class="m-container-big m-padded-tb-big">
        <div class="ui container">
            <div class="ui top attached segment teal">
                <div class="m-container m-padded-tb-big segment">
                    <div class="ui container">
                        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" align="center" label-width="70px"
                                 :inline-message="true"
                                 class="demo-ruleForm">
                            <el-container>
                                <el-container>
                                    <el-aside class="add_book_msg">
                                        <el-form-item label="标题" prop="title">
                                            <el-input v-model="ruleForm.title"
                                                      style="padding-left: 20px;padding-right: 15px" placeholder="标题名称"></el-input>
                                        </el-form-item>

                                        <el-form-item label="分类" prop="sort">
                                            <el-select
                                                    allow-create
                                                    filterable
                                                    default-first-option
                                                    clearable
                                                    v-model="ruleForm.sort"
                                                    placeholder="请选择(可自定义)">
                                                <el-option
                                                        v-for="item in sorts"
                                                        :key="item.id"
                                                        :label="item.name"
                                                        :value="item.id">
                                                </el-option>
                                            </el-select>

                                        </el-form-item>

                                        <el-form-item label="标签" prop="tags">
                                            <el-select
                                                    v-model="ruleForm.tags"
                                                    multiple
                                                    allow-create
                                                    filterable
                                                    default-first-option
                                                    placeholder="请选择(可自定义)">
                                                <el-option
                                                        v-for="item in options"
                                                        :key="item.id"
                                                        :label="item.name"
                                                        :value="item.id">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>

                                        <el-form-item label="描述" prop="description">
                                            <el-input
                                                    type="textarea"
                                                    :rows="4"
                                                    style="padding-left: 20px;padding-right: 15px"
                                                    placeholder="请输入文章描述..."
                                                    v-model="ruleForm.description">
                                            </el-input>
                                        </el-form-item>

                                        <el-form-item label="推荐" prop="recommend">
                                            <el-checkbox :checked="recommend" v-model="ruleForm.flag" label="true">是否推荐</el-checkbox>
                                        </el-form-item>

                                        <el-form-item label="首图">
                                            <el-upload
                                                    ref="upload"
                                                    action="fakeaction"
                                                    list-type="picture"
                                                    accept="image/png,image/jpeg"
                                                    :limit="1"
                                                    :auto-upload="false"
                                                    :on-preview="handlePictureCardPreview"
                                                    :on-exceed="handleExceed"
                                                    :on-remove="handleRemove"
                                                    :on-change="handleChange"
                                                    :http-request="uploadSectionFile">
                                                <el-button size="small" type="primary">选择图片</el-button>
                                            </el-upload>
                                            <el-button size="small" type="success" @click="editImagePreview">预览</el-button>
                                            <el-dialog :visible.sync="dialogVisible" >
                                                <img width="100%" :src="dialogImageUrl" alt="" @click="handlePictureCardPreview">
                                            </el-dialog>
                                        </el-form-item>

                                        <el-form-item style="padding-top: 20%">
                                            <el-button type="primary" @click="submitForm('ruleForm')">上传</el-button>
                                            <el-button @click="resetForm('ruleForm')">重置</el-button>
                                        </el-form-item>
                                    </el-aside>
                                    <el-main class="add_book_article" style="padding-top: 3%">
                                        <el-form-item prop="content" label-width="100px">

                                            <!--MarkDown编辑器-->
                                            <mavon-editor id="markDown" v-model="ruleForm.content" previewBackground="#EDF1F2"
                                                          toolbarsBackground="#3C3F41" :autosize="{ minRows: 15}" ref="md"
                                                          :tabSize="4"
                                                          codeStyle="androidstudio"
                                                          @change="change" @imgAdd="imgAdd"  @imgDel="imgDel"
                                                          style="min-height: 600px;max-height: 600px"/>
                                        </el-form-item>
                                    </el-main>
                                </el-container>
                            </el-container>
                        </el-form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script type="text/ecmascript-6">
    import {mavonEditor} from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'
    import { Loading } from 'element-ui';

    export default {
        name: "blogInput",
        components: {
            mavonEditor,    //mavon-editor组件    (markdown)
        },
        data() {
            return {
                dialogImageUrl: '/upload/2020/03/25/db7257b8-6f47-4e9b-a7bb-bd68925d360c.jpg',
                dialogVisible: false,

                fullscreenLoading: false,

                recommend: true,
                content: '', // 输入的markdown
                html: '',    // 转成的html
                addType: false,
                addTag: false,
                ruleForm: {     //表单数据
                    description: '',
                    title: '',
                    sort: [],
                    content: '',
                    tags: [],
                    imageUrl: '',
                    createTime: '',
                    flag: '',
                },
                rules: {
                    title: [
                        {required: true, message: '请输入标题...', trigger: 'blur'},
                        {min: 1, max: 10, message: '长度在 1 到 10 个字符...'}
                    ],
                    sort: [
                        {required: true, message: '请选择或创建文章类型...', trigger: 'blur'}
                    ],
                    tags: [
                        {required: true, message: '请选择或创建文章标签...', trigger: 'blur'}
                    ],
                    content: [
                        {required: true, message: '请填写文章内容...', trigger: 'blur'}
                    ],
                    description: [
                        {required: true, message: '请简要描述一下文章...', trigger: 'blur'}
                    ]
                },
                sorts: [{}],
                options: [],
                newTag: [],
                sortEl: {}
            };
        },
        methods: {

            //提交按钮
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.$route.params.key == 'add') {

                            let options = {
                                lock: true,
                                text: 'Loading',
                                spinner: 'el-icon-loading',
                                background: 'rgba(0, 0, 0, 0.7)'
                            }

                            let loadingInstance = Loading.service(options);

                            //判断是否有新增分类
                            if (typeof(this.ruleForm.sort) == "string") {
                                this.addType = true;
                                this.sortEl = {name: this.ruleForm.sort}
                            } else if (typeof(this.ruleForm.sort) != "number") {
                                this.$message({
                                    offset: 140,
                                    message: '分类数据类型校验异常_(:з」∠)_',
                                    type: 'error',
                                    duration: 1500
                                });
                                return false;
                            }

                            //判断是否有新增标签
                            for (let i = 0, len = this.ruleForm.tags.length; i < len; i++) {
                                if (typeof(this.ruleForm.tags[i]) == 'string') {
                                    this.newTag.push({name: this.ruleForm.tags[i]})
                                }
                            }
                            this.newTag.length > 0 ? this.addTag = true : this.addTag = false;

                            //新增类别 axios
                            if (this.addType) {
                                axios.post('/blog/admin/types', this.sortEl).then(resp => {
                                    if (resp.data == 'success') {
                                        this.$message({
                                            offset: 140,
                                            message: '新增类别成功~',
                                            type: 'success',
                                            duration: 1500
                                        });
                                    } else if (resp.data == 'null') {
                                        this.$message({
                                            offset: 140,
                                            message: '类别不能为空哦~',
                                            type: 'error',
                                            duration: 1500
                                        });
                                        return false;
                                    } else if (resp.data == 'exists') {
                                        this.$message({
                                            offset: 140,
                                            message: '当前类别已存在，换一个试试吧~',
                                            type: 'warning',
                                            duration: 1500
                                        });
                                        return false;
                                    } else {
                                        this.$message({
                                            offset: 140,
                                            message: '新增类别异常...',
                                            type: 'error',
                                            duration: 1500
                                        });
                                        return false;
                                    }
                                });
                            }

                            //新增标签 axios
                            if (this.addTag) {
                                for (let i = 0, len = this.newTag.length; i < len; i++) {
                                    axios.post('/blog/admin/tags', this.newTag[i]).then(resp => {
                                        if (resp.data == 'success') {
                                            this.$message({
                                                offset: 140,
                                                message: '新增标签成功~',
                                                type: 'success',
                                                duration: 1500
                                            });
                                        } else if (resp.data == 'null') {
                                            this.$message({
                                                offset: 140,
                                                message: '标签名不能为空哦~',
                                                type: 'error',
                                                duration: 1500
                                            });
                                            return false;
                                        } else if (resp.data == 'exists') {
                                            this.$message({
                                                offset: 140,
                                                message: '新增标签已存在...',
                                                type: 'error',
                                                duration: 1500
                                            });
                                            return false;
                                        } else {
                                            this.$message({
                                                offset: 140,
                                                message: '新增标签出现异常...',
                                                type: 'error',
                                                duration: 1500
                                            });
                                            return false;
                                        }
                                    });
                                }
                            }

                            this.$refs.upload.submit();

                            //文字信息上传
                            setTimeout(()=>{
                                axios.post('/blog/admin/blogs', this.ruleForm).then(resp => {
                                    if (resp.data == 'success') {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 200,
                                            message: '上传成功~',
                                            type: 'success'
                                        });
                                        setTimeout(() => {
                                            this.$router.push('/a_blogs')
                                        }, 1500)

                                    } else {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 140,
                                            message: '服务器可能出现了点异常QAQ',
                                            type: 'error',
                                            duration: 1500
                                        });
                                    }
                                })
                            }, 2000);

                        } else if (this.$route.params.key == 'edit') {

                            let options = {
                                lock: true,
                                text: 'Loading',
                                spinner: 'el-icon-loading',
                                background: 'rgba(0, 0, 0, 0.7)'
                            }

                            let loadingInstance = Loading.service(options);

                            //判断是否有新增分类
                            if (typeof(this.ruleForm.sort) == "string") {
                                this.addType = true;
                                this.sortEl = {name: this.ruleForm.sort}
                            } else if (typeof(this.ruleForm.sort) != "number") {
                                this.$message({
                                    offset: 140,
                                    message: '分类数据类型校验异常_(:з」∠)_',
                                    type: 'error',
                                    duration: 1500
                                });
                                return false;
                            }

                            //判断是否有新增标签
                            for (let i = 0, len = this.ruleForm.tags.length; i < len; i++) {
                                if (typeof(this.ruleForm.tags[i]) == 'string') {
                                    this.newTag.push({name: this.ruleForm.tags[i]})
                                }
                            }
                            this.newTag.length > 0 ? this.addTag = true : this.addTag = false;

                            //新增类别 axios
                            if (this.addType) {
                                axios.post('/blog/admin/types', this.sortEl).then(resp => {
                                    if (resp.data == 'success') {
                                        this.$message({
                                            offset: 140,
                                            message: '新增类别成功~',
                                            type: 'success',
                                            duration: 1500
                                        });
                                    } else if (resp.data == 'null') {
                                        this.$message({
                                            offset: 140,
                                            message: '类别不能为空哦~',
                                            type: 'error',
                                            duration: 1500
                                        });
                                        return false;
                                    } else if (resp.data == 'exists') {
                                        this.$message({
                                            offset: 140,
                                            message: '当前类别已存在，换一个试试吧~',
                                            type: 'warning',
                                            duration: 1500
                                        });
                                        return false;
                                    } else {
                                        this.$message({
                                            offset: 140,
                                            message: '新增类别异常...',
                                            type: 'error',
                                            duration: 1500
                                        });
                                        return false;
                                    }
                                });
                            }

                            //新增标签 axios
                            if (this.addTag) {
                                for (let i = 0, len = this.newTag.length; i < len; i++) {
                                    axios.post('/blog/admin/tags', this.newTag[i]).then(resp => {
                                        if (resp.data == 'success') {
                                            this.$message({
                                                offset: 140,
                                                message: '新增标签成功~',
                                                type: 'success',
                                                duration: 1500
                                            });
                                        } else if (resp.data == 'null') {
                                            this.$message({
                                                offset: 140,
                                                message: '标签名不能为空哦~',
                                                type: 'error',
                                                duration: 1500
                                            });
                                            return false;
                                        } else if (resp.data == 'exists') {
                                            this.$message({
                                                offset: 140,
                                                message: '新增标签已存在...',
                                                type: 'error',
                                                duration: 1500
                                            });
                                            return false;
                                        } else {
                                            this.$message({
                                                offset: 140,
                                                message: '新增标签出现异常...',
                                                type: 'error',
                                                duration: 1500
                                            });
                                            return false;
                                        }
                                    });
                                }
                            }

                            this.$refs.upload.submit();

                            //防止进行修改操作时未更改首图，导致首图为空
                            // Todo: 观看次数、评论数、点赞数。这三个数据的保留还没实现。
                            if (this.ruleForm.imageUrl == '' || this.ruleForm.imageUrl == null) {
                                this.ruleForm.imageUrl = this.$route.params.firstPicture
                            }
                            //文字信息上传
                            setTimeout(()=>{
                                axios.put('/blog/admin/blogs/'+this.$route.params.id, this.ruleForm).then(resp => {
                                    if (resp.data == 'success') {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 200,
                                            message: '上传成功~',
                                            type: 'success'
                                        });
                                        setTimeout(() => {
                                            this.$router.push('/a_blogs')
                                        }, 1500)

                                    } else {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 140,
                                            message: '服务器可能出现了点异常QAQ',
                                            type: 'error',
                                            duration: 1500
                                        });
                                    }
                                })
                            }, 2000);
                        }

                    } else {
                        return false;
                    }
                });
            },

            //重置按钮
            resetForm(formName) {
                if (this.$route.params.key == 'edit') {
                    this.ruleForm.title = this.$route.params.title;
                    this.ruleForm.sort = this.$route.params.type;
                    this.ruleForm.content = this.$route.params.content;
                    this.ruleForm.description = this.$route.params.description;
                    this.dialogImageUrl = this.$route.params.firstPicture;
                    if (this.$route.params.recommend != 'true') {
                        this.recommend = false;
                    }
                    let arr = []
                    for (let i = 0, len = this.$route.params.tag.length; i < len; i++) {
                        arr.push(this.$route.params.tag[i].id)
                    }
                    this.ruleForm.tags = arr;
                } else {
                    this.$refs[formName].resetFields();
                }
            },

            //markdown实时监听器
            change(value, render) {
                // markdown编辑器实时获取转成html的数据
                this.html = render
            },

            // markdown编辑器将图片上传到服务器，返回地址替换到md中
            imgAdd(pos, file) {
                const form = new FormData();
                // 文件对象
                form.append("file", file);
                // 用自己方法覆盖默认的action
                form.append("clientType", 'xxx');

                axios.post('/blog/admin/import', form, {
                    headers: {"content-type": "multipart/form-data"}
                }).then(resp=>{
                    this.$refs.md.$img2Url(pos,resp.data)
                })
            },
            imgDel(pos) {
                // // 删除文件
                // this.$axios.post(`${this.$settings.Host}/article/image/delete/`,{
                //     image_instance: this.img_file[pos]
                // }).catch(error=>{
                //     this.$message.error("删除图片失败")
                // })
            },

            uploadSectionFile(params) {
                const file = params.file,
                    fileType = file.type,
                    isImage = fileType.indexOf("image") != -1,
                    isLt10M = file.size / 1024 / 1024 < 10;
                // 这里常规检验，看项目需求而定
                if (!isImage) {
                    this.$message({
                        offset: 140,
                        message: '图片格式只能为JPG、PNG！',
                        type: 'error',
                        duration:2000
                    });
                    return;
                }
                if (!isLt10M) {
                    this.$message({
                        offset: 140,
                        message: '上传图片不能超过10M！',
                        type: 'error',
                        duration:2000
                    });
                    return;
                }
                // 根据后台需求数据格式
                const form = new FormData();
                // 文件对象
                form.append("file", file);
                // 用自己方法覆盖默认的action
                form.append("clientType", 'xxx');
                // 项目封装的请求方法


                imageUpload(form)
                    .then(res => {
                        //自行处理各种情况
                        if (res.data != null) {
                            this.ruleForm.imageUrl = res.data
                        } else {
                            this.$message({
                                offset: 140,
                                message: '上传失败 ....',
                                type: 'warning',
                                duration:1500
                            });
                        }
                    })
                    .catch(() => {
                    });
            },

            //超过图片上传个数的钩子
            handleExceed(){
                this.$message({
                    offset: 200,
                    message: '只能上传一张图片',
                    type: 'warning',
                    duration:2000
                });
            },

            //删除图片钩子
            handleRemove(file, fileList) {
                this.pictureCard = 'picture-card';
                this.uploadIcon = 'el-icon-plus';
            },

            //点击图片钩子
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },

            //预览按钮
            editImagePreview(){
                this.dialogVisible = true;
            },

            //文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
            handleChange(file){
                this.dialogImageUrl = file.url
            }
        },
        created() {
            axios.get('/blog/admin/tags').then(resp => {
                this.options = resp.data
            })
            axios.get('/blog/admin/types').then(resp => {
                this.sorts = resp.data
            })
            if (this.$route.params.key == 'edit') {
                this.ruleForm.title = this.$route.params.title;
                this.ruleForm.sort = this.$route.params.type;
                this.ruleForm.content = this.$route.params.content;
                this.dialogImageUrl = this.$route.params.firstPicture;
                this.ruleForm.createTime = this.$route.params.createTime;
                this.ruleForm.description = this.$route.params.description;
                if (this.$route.params.recommend != 'true') {
                    this.recommend = false;
                }
                let arr = []
                for (let i = 0, len = this.$route.params.tag.length; i < len; i++) {
                    arr.push(this.$route.params.tag[i].id)
                }
                this.ruleForm.tags = arr;

            }
        }
    }
    const imageUpload = params => {
        const url = '/blog/admin/import'
        // 根据后台需求的数据格式确定headers
        return axios.post(url, params, {
            headers: {"content-type": "multipart/form-data"}
        })
    }
</script>

<style>
    .add_book_article {

        margin-right: 3%;
    }

    .add_book_msg {

        padding-top: 3%;
    }

    .mavonEditor {
        width: 100%;
        height: 100%;
    }
</style>