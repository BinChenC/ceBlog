<template>
    <!--主体-->
        <div class="m-container m-padded-tb-big">
        <div class="ui container">
            <div class="ui grid stackable">
                <!--左边博客列表-->
                <div class="eleven wide column">
                    <!--header-->
                    <div class="ui top attached segment">
                        <div class="ui middle aligned grid two column">
                            <div class="column">
                                <h3 class="ui header teal">博客</h3>
                            </div>
                            <div class="right aligned column">
                                共 <h2 class="ui orange header m-inline-block m-text-thin">
                                {{allInfo.totalElements}} </h2> 篇
                            </div>
                        </div>
                    </div>
                    <!--content-->
                    <div class="ui segment attached">

                        <!--todo：中间博客内容-->
                        <div v-for="(item,index) in allInfo.content"
                             class="ui  padded vertical segment m-padded-tb-large">
                            <div class="ui middle aligned mobile reversed stackable grid">
                                <div class="eleven wide column">
                                    <!--todo: 跳转博客详情-->
                                    <h3 class="ui header"><a href="javascript:void(0);" @click="goBlog(item)" class="m-black">{{item.title}}</a></h3>
                                    <p class="m-text"><a href="javascript:void(0);" @click="goBlog(item)" class="m-black">{{item.description}}......</a></p>
                                    <div class="ui stackable grid">
                                        <div class="thirteen wide column">
                                            <div class="ui mini horizontal link list">
                                                <div class="item">
                                                    <img src="../assets/images/avatar2.jpg" alt=""
                                                         @click="goBlog(item)"
                                                         class="ui avatar image">
                                                    <div class="content"><a href="javascript:void(0);" @click="goAboutMe" class="header">{{item.user.nickname}}</a>
                                                    </div>
                                                </div>

                                                <div class="item" title="发布时间">
                                                    <i class="calendar icon"></i>{{item.createTime | dateformat('YYYY年MM月DD日')}}
                                                </div>
                                                <div class="item" title="访问次数">
                                                    <i class="eye icon"></i>{{item.views}}
                                                </div>
                                                <div class="item" title="评论数">
                                                    <i class="comment icon"></i>{{item.commentNum}}
                                                </div>
                                                <div class="item" title="点赞">
                                                    <i class="thumbs up outline icon"></i>{{item.likes}}
                                                </div>
                                            </div>
                                        </div>
                                        <!--todo：点击分类名，跳转到分类页-->
                                        <div class="right aligned three wide column">
                                            <a href="javascript:void(0)" class="teal basic ui label m-padded-tiny m-text-thin"
                                               @click="goType(item.type.id)">
                                                {{item.type.name}}
                                            </a>
                                        </div>
                                    </div>
                                </div>

                                <div class="five wide column">
                                    <a href="javascript:void(0)" @click="goBlog(item)">
                                        <img :src="item.firstPicture" alt=""
                                             class="ui rounded image">
                                    </a>
                                </div>

                            </div>
                        </div>

                    </div>

                    <!--footer-->
                    <div class="ui bottom attached segment">
                        <!--todo: 分页按钮-->
                        <div class="block">
                            <el-pagination
                                    @current-change="page"
                                    :page-size="pageSize"
                                    layout="prev, pager, next, jumper"
                                    :total="total">
                            </el-pagination>
                        </div>
                    </div>
                </div>

                <!--右边的top-->
                <div class="four wide column">

                    <!--分类-->
                    <div class="ui segments">
                        <div class="ui secondary segment">
                            <div class="ui two column grid">
                                <div class="column">
                                    <i class="idea icon"></i>分类
                                </div>
                                <div class="right aligned column">
                                    <a href="javascript:void(0)" @click="goType(2)">more <i class="angle double right icon"></i></a>
                                </div>
                            </div>
                        </div>

                        <div class="ui teal segment">
                            <div class="ui fluid vertical menu">

                                <!--todo: 右边的 分类栏 -->
                                <a href="javascript:void(0)"
                                   class="item" v-for="(item,index) in types"
                                    @click="goType(item.id)">
                                    {{item.name}}
                                    <div class="ui teal basic left pointing label">{{item.blogs.length}}</div>
                                </a>

                            </div>
                        </div>

                    </div>

                    <!--标签-->
                    <div class="ui segments m-margin-top-large">
                        <div class="ui secondary segment">
                            <div class="ui two column grid">
                                <div class="column">
                                    <i class="idea icon"></i>标签
                                </div>
                                <div class="right aligned column">
                                    <a href="javascript:void(0)" @click="goTag(9)">more <i class="angle double right icon"></i></a>
                                </div>
                            </div>
                        </div>

                        <div class="ui teal segment">

                            <!--todo: 右边的 标签栏 -->
                            <a href="javascript:void(0)" class="ui teal basic left pointing label m-margin-tb-tiny"
                               v-for="(item,index) in tags" @click="goTag(item.id)">
                                {{item.name}}
                                <div class="detail">{{item.blogs.length}}</div>
                            </a>
                        </div>

                    </div>

                    <!--最新推荐-->
                    <div class="ui segments m-margin-top-large">
                        <div class="ui secondary segment">
                            <i class="bookmark icon"></i>最新推荐
                        </div>

                        <!--todo：右边的 最新推荐栏-->
                        <div class="ui segment" v-for="(item,index) in recommendBlog">
                            <a href="javascript:void(0)" @click="goBlog(item)" class="m-black m-text-thin">{{item.title}}</a>
                        </div>

                    </div>

                    <!--二维码-->
                    <h4 class="ui horizontal divider header m-margin-top-large">与我交流~</h4>
                    <div class="ui card centered" style="width: 11em">
                        <img src="../assets/images/weixin.png" alt="" class="ui rounded image">
                    </div>

                </div>
            </div>
        </div>
    </div>

</template>
<script type="text/ecmascript-6">

    export default {
        name: "Home",
        data() {
            return {
                allInfo: [],
                tags: [],
                types: [],
                recommendBlog: [],
                pageSize: 5,
                total: 100,
                searchStr: '',
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
        watch: {
            $route: 'watchrouter'//路由变化时，执行的方法
        },
        methods: {
            goTag(tagId) {
                this.$router.push({
                    name: 'MyTags',
                    query: {id: tagId}
                });
            },
            goType(typeId) {
                this.$router.push({
                    name: 'Types',
                    query: {id: typeId}
                });
            },
            goAboutMe(){
                this.$router.push("/about")
            },
            //todo: 点击博客简介跳转至博客详情页面
            goBlog(blog){
                this.$router.push({
                    name: 'Blog',
                    query:{id: blog.id},
                });
                this.utils.scrollTop();
            },
            goType(typeId){
                this.$router.push({
                    name: 'Types',
                    query:{id: typeId},
                });
                this.utils.scrollTop();
            },
            page(currentPage) {
                if (this.searchStr == '') {
                    axios.get('/blog/user/blogs/' + (currentPage - 1) + '/5').then(resp => {
                        this.allInfo = resp.data;
                        this.pageSize = resp.data.size;
                        this.total = resp.data.totalElements;
                    });
                } else {
                    axios.get('/blog/user/blogs/' + (currentPage - 1) + '/5/'+this.searchStr).then(resp => {
                        this.pageSize = resp.data.size;
                        this.total = resp.data.totalElements;
                        this.allInfo = resp.data;
                        if (this.allInfo.content.length === 0) {
                            this.$message({
                                offset: 200,
                                message: '没有查到您想要的博客...',
                                type: 'warning',
                                duration: 2000
                            });
                        }
                    });
                }
                this.utils.scrollTop();
            },
            watchrouter() {//如果路由有变化，执行的对应的动作
                this.searchStr = this.$route.query.search;
                this.searchBlog(this.searchStr);
            },
            searchBlog(search) {
                axios.get('/blog/user/blogs/0/5/' + search).then(resp => {
                    this.allInfo = resp.data;
                    this.pageSize = resp.data.size;
                    this.total = resp.data.totalElements;
                    if (this.allInfo.content.length === 0) {
                        this.$message({
                            offset: 200,
                            message: '没有查到您想要的博客...',
                            type: 'warning',
                            duration: 2000
                        });
                    }
                })
            }
        },
        created() {
            if (this.$route.params.key != 'search') {
                axios.get('/blog/user/blogs/0/5').then(resp => {
                    this.allInfo = resp.data;
                    this.pageSize = resp.data.size
                    this.total = resp.data.totalElements
                });
            }
            axios.get('/blog/user/types').then(resp => {
                this.types = resp.data
            })
            axios.get('/blog/user/tags').then(resp => {
                this.tags = resp.data
            })
            axios.get('/blog/user/recommendBlog').then(resp => {
                this.recommendBlog = resp.data;
            })

        }
    }
</script>
<style scoped>

</style>