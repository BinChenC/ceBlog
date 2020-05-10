<template>
    <div>
        <!--主体-->
        <div id="waypointex" class="m-container-small m-padded-tb-big">
            <div class="ui container">
                <div class="ui top attached segment">
                    <div class="ui horizontal link list">
                        <div class="item">
                            <img src="../assets/images/avatar2.jpg" alt=""
                                 class="ui avatar image">
                            <div class="content"><a href="#" class="header">{{author}}</a></div>
                        </div>

                        <div class="item" title="发布时间">
                            <i class="calendar icon"></i>{{createTime}}
                        </div>
                        <div class="item" title="访问次数">
                            <i class="eye icon"></i>{{views}}
                        </div>
                        <div class="item" title="评论数">
                            <i class="comment icon"></i>{{commentNum}}
                        </div>
                        <div class="item" title="点赞">
                            <i class="thumbs up outline icon"></i>{{likes}}
                        </div>
                    </div>
                </div>
                <div class="ui attached segment">
                    <!--图片-->
                    <img :src="firstPicture" alt="" class="ui fluid image rounded">
                </div>

                <div class="ui attached padded segment">
                    <!--内容-->
                    <div class="ui right aligned basic segment">
                        <div class="ui orange label basic">{{blogType}}</div>
                    </div>

                    <h2 class="ui center aligned header">{{title}}</h2>
                    <br>
                    <div class="m-padded-lr-responsive m-padded-tb-large js-toc-content">
                        <article v-highlight v-html="mdContent" class="typo content"></article>
                        <!--<h1 id="h1">测试tocbot</h1>-->

                    </div>
                    <!--标签-->
                    <div class="m-padded-lr-responsive">
                        <div class="ui basic teal left pointing label" v-for="(item) in tags">{{item.name}}</div>
                    </div>

                    <!--赞赏-->

                    <div class="ui center aligned basic segment">
                        <button v-on:click="payPopup" id="payButton" class="ui basic circular orange button">点赞~
                        </button>
                    </div>

                    <!-- <div class="ui payQR flowing popup transition hidden">
                     <div class="ui orange basic label">
                     <div class="ui images" style="font-size: inherit !important;">
                     <div class="image">
                     <img src="../assets/images/weixin.png" alt="" class="ui image rounded bordered"
                     style="width: 120px;">
                     <div>支付宝</div>
                     </div>
                     <div class="image">
                     <img src="../assets/images/weixin.png" alt="" class="ui image rounded bordered"
                     style="width: 120px;">
                     <div>微信</div>
                     </div>
                     </div>
                     </div>
                     </div>-->

                </div>

                <div class="ui attached positive message">
                    <!--博客信息-->
                    <div class="ui middle aligned grid">
                        <div class="eleven wide column">
                            <ul class="list">
                                <li>作者：{{author}}</li>
                                <li>发表时间：{{createTime}}</li>
                                <li>最后更新时间：{{updateTime}}</li>
                                <li>公众号转载：请在文末添加作者公众号二维码</li>
                            </ul>
                        </div>
                        <div class="five wide column">
                            <img src="../assets/images/weixin.png" alt=""
                                 class="ui rounded right floated bordered image" style="width: 110px;">
                        </div>
                    </div>

                </div>
                <div id="comment-container" class="ui bottom attached segment">
                    <!--留言-->
                    <div class="ui teal segment">
                        <div class="ui threaded comments" style="max-width: 100%;">
                            <h3 class="ui dividing header">评论区</h3><span> ( 一个非常简单的评论区，若有具体需要交流的可以在页脚添加我的 QQ )</span>
                            <!--评论区-->
                            <div class="comment" v-for="(item,index) in comments" v-bind:key="item.id" v-if="!item.parentComment!=''">
                                <!--主评论-->
                                <a class="avatar">
                                    <img :src="item.avatar">
                                </a>
                                <div class="content">
                                    <a class="author">{{item.nickname}}</a>
                                    <div class="metadata">
                                        <span class="date">{{item.createTime}}</span>
                                    </div>
                                    <div class="text">
                                        {{item.content}}
                                    </div>
                                    <div class="actions">
                                        <a href="#commentArea" class="reply" @click="reply(item)">回复</a>
                                    </div>
                                </div>

                                <!--子评论-->
                                <div v-if="textFlag[index]">
                                    <div class="comments" v-for="(child) in item.replyComment" v-bind:key="child.id">
                                        <div class="comment" >
                                            <a class="avatar" style="padding-top: 4px">
                                                <img :src="child.avatar">
                                            </a>
                                            <div class="content">
                                                <a class="author">{{child.nickname}}</a>
                                                <div class="metadata">
                                                    <span class="date">{{child.createTime}}</span>
                                                </div>
                                                <div class="text">
                                                    <div>
                                                    <span style="background-color: #d9d9e2;padding: 4px 4px 4px 0;line-height: 2;font-size: 10px">
                                                    {{child.content.split("zPVwibWlaSB0ZWF")[0]}}
                                                    </span>
                                                    </div>
                                                    <div>
                                                        {{child.content.substring(child.content.split("zPVwibWlaSB0ZWF")[0].length+15)}}
                                                    </div>
                                                </div>
                                                <div class="actions">
                                                    <a class="reply" href="#commentArea"
                                                       @click="reply(child,item.id)">回复</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--查看回复-->
                                <div class="actions" style="padding-left: 8em !important;"
                                     v-if="item.replyComment.length > 0 && !textFlag[index]">
                                    <span style="padding: 10px">
                                        <a href="javascript:void(0)" class="reply" style="font-weight: bold;"
                                           @click="replyExpandFunc(index)">
                                        查看回复( {{item.replyComment.length}} )
                                    </a>
                                    </span>
                                </div>
                                <!--收起回复-->
                                <div class="actions"
                                     v-else-if="item.replyComment.length > 0 && textFlag[index]">
                                    <a href="javascript:void(0)" class="reply"
                                       style="font-weight: bold;padding-left: 2em !important;"
                                       @click="replyStorageFunc(index)">
                                        收起回复 ...
                                    </a>
                                </div>


                            </div>

                        </div>
                    </div>

                    <el-tag id="commentArea" type="success" v-show="replyFlag" closable @close="closeReply"
                            style="max-width: 100%;">{{parentContent}}
                    </el-tag>
                    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm">
                        <div class="ui form">
                            <el-form-item prop="content">
                                <div class="field">
                                    <textarea name="content" v-model="ruleForm.content"
                                              placeholder="请输入评论信息...(必填)"></textarea>
                                </div>
                            </el-form-item>

                            <div class="fields">
                                <div class="field m-mobile-wide m-margin-bottom-small">
                                    <div class="ui left icon input">
                                        <el-form-item prop="nickname">
                                            <i class="user icon"></i>
                                            <input type="text" v-model="ruleForm.nickname" placeholder="别名(必填)"
                                                   name="nickname">
                                        </el-form-item>
                                    </div>
                                </div>
                                <div class="field m-mobile-wide m-margin-bottom-small">
                                    <div class="ui left icon input">
                                        <el-form-item prop="email"
                                                      :rules="[
                                          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                                        ]">
                                            <i class="mail icon"></i>
                                            <input type="text" v-model="ruleForm.email" placeholder="邮箱" name="email">
                                        </el-form-item>
                                    </div>
                                </div>
                                <div class="field m-mobile-wide m-margin-bottom-small">
                                    <el-form-item style="padding-top: 14%">
                                        <el-button class="ui teal button m-mobile-wide" @click="submitForm('ruleForm')">
                                            <i class="edit icon"></i>发布
                                        </el-button>
                                    </el-form-item>
                                </div>
                            </div>
                        </div>
                    </el-form>


                </div>
            </div>
        </div>

        <!--功能按钮-->
        <div id="funBtn" class="m-padded m-fixed m-right-bottom m-button">
            <div class="ui vertical icon buttons">
                <button class="ui teal button"  type="button" id="popTocBtn" v-on:mouseover="popToc">目录</button>
                <a href="javascript:void(0)" @click="scrollComment" class="ui teal button" type="button" id="commentBtn">留言</a>
                <a href="javascript:void(0)" @click="scrollTop" class="ui icon button" type="button"><i class="chevron up icon"></i></a>
            </div>
        </div>
        <!--弹出目录-->
        <div class="ui toc-container flowing popup left center transition hidden"
             style="top: -85.1565px; right: 64px; left: auto; bottom: auto; width: 268px !important;display: block !important;">
            <ol class="js-toc" @click="reductionUrl">

            </ol>
        </div>

    </div>

</template>

<script>
    import marked from 'marked'
    // import Vue from 'vue'
    import {Loading} from 'element-ui';

    export default {
        name: "blog",
        mdContent: '',
        inject: ['reload'],

        mounted() {
            setTimeout(() => {
                tocbot.init({
                    // 构建目录的容器
                    tocSelector: '.js-toc',
                    // 文章容器
                    contentSelector: '.js-toc-content',
                    // 需要解析的标题
                    headingSelector: 'h1, h2, h3',
                    smoothScroll: true,
                    scrollSmoothOffset: -80,
                    headingsOffset: -600,
                    collapseDepth: 2,
                    scrollSmoothDuration: 500,
                    hasInnerContainers: true,
                });
            }, 1000);
        },
        methods: {

            reductionUrl(){
                let top;
                if (document.documentElement && document.documentElement.scrollTop) {
                    top = document.documentElement.scrollTop
                }else if (document.body) {
                    top = document.body.scrollTop;
                } else {
                    top = window.pageYOffset;
                }

                document.body.scrollTop = top;
                document.documentElement.scrollTop = top;
                window.pageYOffset = top;

                let url = window.location.href;
                setTimeout(()=>{
                    window.location.href = url;
                },50)
            },
            scrollTop() {
                this.utils.scrollTop()
            },

            scrollComment(){
                var c = document.getElementById("waypointex").clientHeight - document.getElementById("comment-container").clientHeight;
                window.scroll({ top: c, left: 0, behavior: 'smooth' });
            },

            // 查看回复和收起回复
            replyExpandFunc(index) {
                Vue.set(this.textFlag, index, -1)
            },
            replyStorageFunc(index) {
                Vue.set(this.textFlag, index, 0)
            },


            // 回复评论功能 item：父评论的id
            reply(item, id) {
                let process = item.content;
                if (id != null) {
                    this.parentId = id;
                    process = item.content.substring(item.content.split("zPVwibWlaSB0ZWF")[0].length + 15);
                } else {
                    this.parentId = item.id;

                }
                this.replyFlag = true;
                if (item.content.length < 10) {
                    this.parentContent = '回复 【@' + item.nickname + '】：' + process + '（点击‘x’取消回复）';
                    this.combContent = '回复 【@' + item.nickname + '】：' + process;
                } else {
                    this.parentContent = '回复 【@' + item.nickname + '】：' + process.substring(0, 10) + '……' + '（点击‘x’取消回复）';
                    this.combContent = '回复 【@' + item.nickname + '】：' + process.substring(0, 10) + '……';
                }
            },
            // 取消回复
            closeReply() {
                this.parentId = -1;
                this.replyFlag = false;
                this.parentContent = '';
                this.combContent = '';
            },

            /*
             * 弹出赞赏框，但此处不做此功能，改成点赞。
             *   想要恢复赞赏可以将 click 事件改成 mouseover
             */
            payPopup() {
                // $("#payButton").popup({
                //     popup: $('.payQR.popup'),
                //     on: 'click',
                //     position: 'bottom center'
                // })
                this.$message({
                    offset: 200,
                    message: '请先登录再进行点赞哦~~',
                    type: 'warning',
                    duration: 2000
                });
                setTimeout(() => {
                    this.$notify({
                        title: '_(:з」∠)_',
                        message: '实际上是我偷懒没做啦...',
                        position: 'bottom-right'
                    });
                }, 2000)
            },
            popToc() {
                window.$("#popTocBtn").popup({
                    popup: $('.toc-container'),
                    on: 'click',
                    position: 'left center'
                })
            },
            // 提交
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

                        if (this.parentId < 0) {
                            // 此时是正常发表状态
                            axios.post('/blog/user/blog', this.ruleForm).then(resp => {
                                if (resp.data == 'success') {
                                    setTimeout(() => {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 140,
                                            message: '发表评论成功~',
                                            type: 'success',
                                            duration: 1500
                                        });
                                        this.reload();
                                        this.scrollComment();
                                    }, 1000);

                                } else {
                                    setTimeout(() => {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 140,
                                            message: '服务器可能出现了点异常QAQ',
                                            type: 'error',
                                            duration: 1500
                                        });
                                        this.reload();
                                        this.scrollComment();
                                    }, 1000);

                                }
                            });
                        } else {
                            // 此时是回复别人的评论状态

                            this.ruleForm.cmtParentId = this.parentId;
                            this.ruleForm.content = this.combContent + 'zPVwibWlaSB0ZWF' + this.ruleForm.content;
                            axios.post('/blog/user/blog', this.ruleForm).then(resp => {
                                if (resp.data == 'success') {
                                    setTimeout(() => {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 140,
                                            message: '发表评论成功~',
                                            type: 'success',
                                            duration: 1500
                                        });
                                        this.reload();
                                        this.scrollComment();
                                    }, 1000);

                                } else {
                                    setTimeout(() => {
                                        this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                                            loadingInstance.close();
                                        });
                                        this.$message({
                                            offset: 140,
                                            message: '服务器可能出现了点异常QAQ',
                                            type: 'error',
                                            duration: 1500
                                        });
                                        this.reload();
                                        this.scrollComment();
                                    }, 1000);
                                }
                            });
                        }

                    } else {
                        return false;
                    }
                });
            },
        },
        data() {
            return {
                // 前端关联的页面数据
                author: '',
                avatar: '',
                createTime: '',
                views: 0,
                likes: 0,
                commentNum: 0,
                firstPicture: '',
                title: '',
                blogType: '',

                // 后端传入的博客信息
                tags: [],
                content: '',
                updateTime: '',
                mdContent: '',
                comments: [],
                src: 'https://unsplash.it/100/100?image=1005',

                //评论区，回复属性
                textFlag: [],       //是否展开回复内容

                //comment 回复区属性
                replyFlag: false,       //是否显示回复 Tag
                parentId: -1,           // -1：当前不处于回复状态
                //父评论的内容，最终提交数据格式：‘【@parentNickName】：’+‘parentContent zPVwibWlaSB0ZWF’ + ‘ruleForm.content’
                parentContent: '',
                combContent: '',

                ruleForm: {
                    blogId: 0,
                    cmtParentId: -1,
                    content: '',
                    nickname: '',
                    email: '',
                },
                rules: {
                    content: [
                        {required: true, message: '请输入评论信息...', trigger: 'change'},
                        {min: 1, max: 300, message: '长度在 1 到 300 个字符...'}
                    ],
                    nickname: [
                        {required: true, message: '请输入别名...', trigger: 'change'},
                        {min: 1, max: 5, message: '长度在 1 到 5 个字符...'}
                    ],
                },
            }
        },
        created() {
            // this.hljs.initHighlightingOnLoad();
            // 初始化博客数据
            axios.get('/blog/user/blogs/' + this.$route.query.id).then(resp => {
                this.blogType = resp.data.type.name
                this.ruleForm.blogId = resp.data.id
                this.updateTime = resp.data.updateTime;
                this.author = resp.data.user.nickname;
                this.avatar = resp.data.user.avatar;
                this.createTime = resp.data.createTime;
                this.views = resp.data.views;
                this.likes = resp.data.likes;
                this.commentNum = resp.data.commentNum;
                this.firstPicture = resp.data.firstPicture;
                this.title = resp.data.title;
                this.tags = resp.data.tags;
                this.mdContent = marked(resp.data.content);
                this.comments = resp.data.comments

                let len = this.comments.length;
                for (let i = 0; i < len; i++) {
                    this.textFlag[i] = 0;
                }

            })
        }
    }

</script>

<style scoped>

</style>
<style>
    code.hljs {
        font-size: 1em !important;
        line-height: 1.7em;
    }
</style>