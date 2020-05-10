<template>
    <!--主体-->
    <div class="m-container-small m-padded-tb-big">
        <div class="ui container">
            <!--header-->
            <div class="ui top attached padded segment">
                <div class="ui middle aligned grid two column">
                    <div class="column">
                        <h3 class="ui header teal">归档</h3>
                    </div>
                    <div class="right aligned column">
                        共 <h2 class="ui orange header m-inline-block m-text-thin"> {{countBlog}} </h2> 篇博客
                    </div>
                </div>
            </div>

            <div class="ui top attached segment teal">
                <!--todo: 根据 query id 分页分类查询 最后更新的前五条博客-->

                <div class="ui column grid stackable" style="margin-top: 2em">
                    <div class="center aligned five wide column">
                        <h3>
                            <i class="unordered list icon"></i>记录
                        </h3>
                        <div class="m-margin-top-large">
                            <div style="line-height: 2em" class="m-a-hover" v-for="(time,index) in blogtimes">
                                <a href="javascript:void(0)" @click="findBlogsByTime(time)"
                                   style="font-size: 15px;border-bottom: 1px solid #999;color: #616161">
                                    {{time}}
                                </a>
                                <span style="margin-left: 2px;color: #b0b0b0;font-size: 13px">
                               ( {{blogs[time].length}} )
                            </span>
                            </div>

                        </div>

                    </div>
                    <div class="eleven wide column">
                        <h2 class="ui center aligned header">{{currentTime}}</h2>
                        <div class="block">
                            <el-timeline>
                                <el-timeline-item
                                        icon="el-icon-date"
                                        type="primary"
                                        color="#00B5AD"
                                        size="large"
                                        :timestamp="timestamp" style="">
                                    {{content}}
                                </el-timeline-item>
                                <el-timeline-item color="#00B5AD" :timestamp="blog.createTime" placement="top"
                                                  v-for="(blog,blogIndex) in currentBlogs">
                                    <div class="ui fluid vertical menu">
                                        <!--todo: goBlog(blog.id)-->
                                        <a href="javascript:void(0)" class="item" @click="goBlog(blog.id)">
                                        <span>
                                            <i class="mini teal edit icon"></i>&nbsp;{{blog.title}}
                                            <div class="ui teal basic left pointing label m-padded-mini"
                                                 v-for="(tag) in blog.tags">
                                                {{tag.name}}
                                            </div>
                                        </span>
                                            <div class="ui orange basic left pointing label m-padded-tiny">{{blog.type.name}}</div>
                                        </a>
                                    </div>
                                </el-timeline-item>

                            </el-timeline>

                        </div>
                        <!--footer-->
                        <div class="ui fluid  menu m-padded-tb-tiny">
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
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        name: "MyArchives",
        methods: {
            goBlog(blogId){
                this.$router.push({
                    name: 'Blog',
                    query:{id: blogId},
                });
                this.utils.scrollTop();
            },
            //根据游客选择的日期查询博客
            findBlogsByTime(time){
                axios.get('/blog/user/archives/'+time+'/0/3').then(resp => {
                    this.currentBlogs = resp.data.content;
                    this.pageSize = resp.data.size;
                    this.total = resp.data.totalElements;

                    this.currentTime = resp.data.content[0].createTime.replace(/-/g, "/");
                    this.currentTime = new Date(this.currentTime).format('yyyy-MM');
                });
            },
            page(currentPage) {
                axios.get('/blog/user/archives/'+this.currentTime+'/' + (currentPage - 1) + '/3').then(resp => {
                    this.pageSize = resp.data.size;
                    this.total = resp.data.totalElements;
                    this.currentBlogs = resp.data.content;
                });

                this.utils.scrollTop();
            },
        },
        data() {
            return {
                src: 'https://unsplash.it/100/100?image=1005',
                countBlog: 0,
                blogs: [],
                blogtimes: [],

                currentBlogs: [],
                currentTime: '',

                content: '学习虽好，可不要上头哦~  _(:з」∠)_',
                timestamp: '',

                // 分页
                pageSize: 3,
                total: 15,
            }
        },
        created() {
            let initTime;
            axios.get('/blog/user/archives/count').then(resp => {
                this.countBlog = resp.data;
                this.timestamp = '截止至：' + new Date().format('yyyy-MM-dd') + '。已经氵了 ' + resp.data + ' 篇博客啦~';
            });
            axios.get('/blog/user/archives').then(resp => {
                this.blogs = resp.data;
                Object.keys(this.blogs).forEach(key => {
                    this.blogtimes.push(key);
                })
                this.blogtimes.sort((a,b)=> {
                    return a < b ? 1: -1;
                });
                for (const time of this.blogtimes) {
                    initTime = time;
                    break;
                }
                axios.get('/blog/user/archives/'+initTime+'/0/3').then(current => {
                    this.currentBlogs = current.data.content;
                    this.pageSize = current.data.size;
                    this.total = current.data.totalElements;

                    this.currentTime = current.data.content[0].createTime.replace(/-/g, "/");
                    this.currentTime = new Date(this.currentTime).format('yyyy-MM');
                });
            });
        }
    }
</script>

<style scoped>
    i.mini.icon, i.mini.icons{
        font-size: 0.8em !important;
    }
</style>
<style>
    .el-timeline-item__node--large{
        left:-10px;
        width:30px;
        height:30px;
    }
</style>