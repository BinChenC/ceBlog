<template>
    <!--主体-->
    <div class="m-container-small m-padded-tb-big">
        <div class="ui container">
            <!--header-->
            <div class="ui top attached segment">
                <div class="ui middle aligned grid two column">
                    <div class="column">
                        <h3 class="ui header teal">标签</h3>
                    </div>
                    <div class="right aligned column">
                        共 <h2 class="ui orange header m-inline-block m-text-thin"> {{tags.length}} </h2> 个
                    </div>
                </div>
            </div>
            <!--标签模块-->
            <div class="ui attached segment m-padded-tb-large">
                <a href="javascript:void(0)"
                   @click="tagBlogs(item.id)"
                   :class="[$route.query.id==item.id?'teal':'','ui basic left pointing large label m-margin-tb-tiny']"
                   v-for="(item,tagNum) in tags">
                    {{item.name}}
                    <div class="detail">{{item.blogs.length}}</div>
                </a>

            </div>

            <!--文章模块-->
            <div class="ui top attached segment teal">
                <div v-for="(blog,blogNum) in blogs.content"
                     class="ui m-mobile-lr-clear padded vertical segment m-padded-tb-large">
                    <div class="ui middle aligned mobile reversed stackable grid">
                        <div class="eleven wide column">
                            <h3 class="ui header">
                                <a href="javascript:void(0)" @click="goBlog(blog.id)" class="m-black">
                                    {{blog.title}}
                                </a>
                            </h3>
                            <p class="m-text">
                                <a class="m-black" href="javascript:void(0)" @click="goBlog(blog.id)">
                                    {{blog.description}}......
                                </a>
                            </p>
                            <div class="ui stackable grid">
                                <div class="row">
                                    <div class="thirteen wide column">
                                        <div class="ui mini horizontal link list m-margin-top-large">
                                            <div class="item">
                                                <img src="../assets/images/avatar2.jpg" alt=""
                                                     class="ui avatar image">
                                                <div class="content">
                                                    <a href="javascript:void(0)" class="header" @click="goAboutMe">
                                                        {{blog.user.nickname}}
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="item" title="发布时间">
                                                <i class="calendar icon"></i>{{blog.createTime |
                                                dateformat('YYYY年MM月DD日')}}
                                            </div>
                                            <div class="item" title="访问次数">
                                                <i class="eye icon"></i>{{blog.views}}
                                            </div>
                                            <div class="item" title="评论数">
                                                <i class="comment icon"></i>{{blog.commentNum}}
                                            </div>
                                            <div class="item" title="点赞">
                                                <i class="thumbs up outline icon"></i>{{blog.likes}}
                                            </div>
                                        </div>
                                    </div>
                                    <div class="right aligned three wide column m-margin-top-large">
                                        <a href="javascript:void(0)" @click="goType(blog.type.id)"
                                           class="teal basic ui label m-padded-tiny m-text-thin">
                                            {{blog.type.name}}
                                        </a>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="column">
                                        <a href="javascript:void(0)" v-for="(tag,elNum) in blog.tags"
                                           @click="goTag(tag.id)"
                                           class="ui basic teal right pointing label m-padded-tiny m-text-thin">
                                            {{tag.name}}
                                        </a>
                                    </div>

                                </div>

                            </div>


                        </div>

                        <div class="five wide column">
                            <a href="javascript:void(0)">
                                <img :src="blog.firstPicture" alt="" @click="goBlog(blog.id)" class="ui rounded image">
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
        name: "tags",
        // FIXME: methods{} ----------< 方法块 >----------
        methods: {
            goTag(tagId) {
                this.$router.push({query: tagId});
                this.tagBlogs(tagId);
            },
            goType(typeId) {
                this.$router.push({
                    name: 'Types',
                    query: {id: typeId}
                });
            },
            goAboutMe() {
                this.$router.push("/about");
            },
            //todo: 点击博客简介跳转至博客详情页面
            goBlog(blogId) {
                this.$router.push({
                    name: 'Blog',
                    query: {id: blogId},
                });
                this.utils.scrollTop();
            },
            //点击分类名，切换底下的博客
            tagBlogs(itemId) {
                if (this.$route.query.id == itemId) return;
                this.$router.push({
                    query: {id: itemId}
                });
                axios.get('/blog/user/tagDetails/' + this.$route.query.id + '/0/5').then(resp => {
                    this.pageSize = resp.data.size;
                    this.total = resp.data.totalElements;
                    this.blogs = resp.data;
                    if (this.blogs.content.length === 0) {
                        this.$message({
                            offset: 100,
                            message: '当前标签暂无博客...',
                            type: 'warning',
                            duration: 2000
                        });
                    }
                });
            },
            //分页
            page(currentPage) {
                axios.get('/blog/user/tagDetails/' + this.$route.query.id + '/' + (currentPage - 1) + '/5').then(resp => {
                    this.pageSize = resp.data.size;
                    this.total = resp.data.totalElements;
                    this.blogs = resp.data;
                });

                this.utils.scrollTop();
            },
        },
        // FIXME: data{} ----------< 数据块 >----------
        data() {
            return {
                src: 'https://unsplash.it/100/100?image=1005',

                tags: [],

                blogs: [],

                // 分页
                pageSize: 5,
                total: 100,
            }
        },
        created() {
            if (this.$route.query.id == null || this.$route.query.id == '') {
                this.$router.push({
                    query: {id: 2}
                });
            }
            axios.get('/blog/user/tagAll').then(resp => {
                this.tags = resp.data;
            })

            axios.get('/blog/user/tagDetails/' + this.$route.query.id + '/0/5').then(resp => {
                this.pageSize = resp.data.size;
                this.total = resp.data.totalElements;
                this.blogs = resp.data;
                if (this.blogs.content.length === 0) {
                    this.$message({
                        offset: 100,
                        message: '当前标签暂无博客...',
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