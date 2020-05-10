<template>
    <!--主体-->
    <div class="m-container-small m-padded-tb-big">
        <div class="ui container">
            <!--header-->
            <div class="ui top attached segment">
                <div class="ui middle aligned grid two column">
                    <div class="column">
                        <h3 class="ui header teal">分类</h3>
                    </div>
                    <div class="right aligned column">
                        共 <h2 class="ui orange header m-inline-block m-text-thin"> {{type.length}} </h2> 个
                    </div>
                </div>
            </div>

            <div class="ui attached segment m-padded-tb-large">
                <div class="ui labeled button m-margin-tb-tiny" v-for="(item,index) in type" @click="typeBlog(item.id)">
                    <a href="javascript:void(0)"
                       :class="[$route.query.id==item.id?'teal':'','ui basic button']">
                        {{item.name}}
                    </a>
                    <div :class="[$route.query.id==item.id?'teal':'','ui basic left pointing label']">
                        {{item.blogs.length}}
                    </div>
                </div>

            </div>

            <div class="ui top attached segment teal">
                <!--todo: 根据 query id 分页分类查询 最后更新的前五条博客-->
                <div
                        v-for="(item2,index2) in blogs.content"
                        class="ui m-mobile-lr-clear padded vertical segment m-padded-tb-large">
                    <div class="ui middle aligned mobile reversed stackable grid">
                        <div class="eleven wide column">
                            <h3 class="ui header"><a href="javascript:void(0)" @click="goBlog(item2)" class="m-black">{{item2.title}}</a></h3>
                            <p class="m-text">
                                <a href="javascript:void(0);" @click="goBlog(item2)" class="m-black">{{item2.description}}......</a>
                            </p>
                            <div class="ui stackable grid">
                                <div class="thirteen wide column">
                                    <div class="ui mini horizontal link list m-margin-top-large">
                                        <div class="item">
                                            <img src="../assets/images/avatar2.jpg" alt=""
                                                 class="ui avatar image">
                                            <div class="content"><a href="javascript:void(0)" @click="goAboutMe" class="header">{{item2.user.nickname}}</a></div>
                                        </div>

                                        <div class="item" title="发布时间">
                                            <i class="calendar icon"></i>{{item2.createTime | dateformat('YYYY年MM月DD日')}}
                                        </div>
                                        <div class="item" title="访问次数">
                                            <i class="eye icon"></i>{{item2.views}}
                                        </div>
                                        <div class="item" title="评论数">
                                            <i class="comment icon"></i>{{item2.commentNum}}
                                        </div>
                                        <div class="item" title="点赞">
                                            <i class="thumbs up outline icon"></i>{{item2.likes}}
                                        </div>
                                    </div>
                                </div>
                                <div class="right aligned three wide column m-margin-top-large">
                                    <span class="teal basic ui label m-padded-tiny m-text-thin">
                                        {{item2.type.name}}
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="five wide column">
                            <a href="javascript:void(0)">
                                <img :src="item2.firstPicture" alt="" @click="goBlog(item2)"
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
    </div>

</template>

<script>
    export default {
        name: "Types",
        // FIXME: methods{} ----------< 方法块 >----------
        methods:{
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
            //分页
            page(currentPage) {
                axios.get('/blog/user/classifyBlog/'+this.$route.query.id+'/' + (currentPage - 1) + '/5').then(resp => {
                    this.pageSize = resp.data.size;
                    this.total = resp.data.totalElements;
                    this.blogs = resp.data;
                });

                this.utils.scrollTop();
            },
            //点击分类查询博客
            typeBlog(itemId){
                if (this.$route.query.id == itemId) return;
                this.$router.push({
                    query: {id: itemId}
                });
                axios.get('/blog/user/classifyBlog/'+this.$route.query.id+'/0/5').then(resp => {
                    this.pageSize = resp.data.size;
                    this.total = resp.data.totalElements;
                    this.blogs = resp.data;
                    if (this.blogs.content.length === 0) {
                        this.$message({
                            offset: 100,
                            message: '当前分类暂无博客...',
                            type: 'warning',
                            duration: 2000
                        });
                    }
                });
            },

        },
        // FIXME: data{} ----------< 数据块 >----------
        data() {
            return {
                type: {},
                src: 'https://unsplash.it/100/100?image=1005',

                blogs: [],

                // 分页
                pageSize: 5,
                total: 100,
            }
        },
        created(){
            if (this.$route.query.id == null || this.$route.query.id == '') {
                this.$router.push({
                    query: {id: 2}
                });
            }
            axios.get('/blog/user/classify').then(resp => {
                this.type = resp.data;
            })

            axios.get('/blog/user/classifyBlog/'+this.$route.query.id+'/0/5').then(resp => {
                this.pageSize = resp.data.size;
                this.total = resp.data.totalElements;
                this.blogs = resp.data;
                if (this.blogs.content.length === 0) {
                    this.$message({
                        offset: 100,
                        message: '当前分类暂无博客...',
                        type: 'warning',
                        duration: 2000
                    });
                }
            })

        }
    }
</script>

<style scoped>

</style>