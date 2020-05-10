<template>
    <div>
        <!--管理导航-->
        <div class="ui inverted attached segment m-padded-tb-mini m-shadow-small">
            <div class="ui container">
                <div class="ui inverted secondary stackable menu">
                    <h2 class="ui teal header item">后台管理</h2>
                    <a @click="onBlogs" :class="[{'m-mobile-hide':isA},$route.path=='/a_blogs'?'active':'','m-item item']"><i
                            class="home icon"></i>首页</a>
                    <a @click="onTypes" :class="[{'m-mobile-hide':isA},$route.path=='/a_types'?'active':'','m-item item']"><i class="idea icon"></i>分类</a>
                    <a @click="onTags" :class="[{'m-mobile-hide':isA},$route.path=='/a_tags'?'active':'','m-item item']"><i class="tags icon"></i>标签</a>

                    <div v-on:mouseover="loadingUser" v-bind:class="{'m-mobile-hide':isA}" class="right m-item menu">
                        <div class="ui dropdown item">
                            <div class="text">
                                <img src="../../assets/images/avatar2.jpg" alt="" class="ui avatar image">
                                {{nickname}}
                            </div>
                            <i class="dropdown icon"></i>
                            <div class="menu">
                                <a href="#" class="item" @click="logout">注销</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <a href="#" @click="mobileButton()" class="ui menu toggle black icon button m-right-top m-mobile-show">
                <i class="sidebar icon"></i>
            </a>
        </div>
    </div>


</template>

<script>
    export default {
        name: "HeadAdmin",
        inject: ["reload"],
        // this.reload();
        methods: {
            onBlogs(){
                if (this.$route.path == '/a_blogs') {
                    return;
                }
                this.$router.push({
                    path: '/a_blogs',
                    name:'AManage',
                })
            },
            onTags(){
                if (this.$route.path == '/a_tags') {
                    return;
                }
                this.$router.push({
                    path: '/a_tags',
                    name:'ATags',
                })
            },
            onTypes(){
                if (this.$route.path == '/a_types') {
                    return;
                }
                this.$router.push({
                    path: '/a_types',
                    name:'ATypes',
                })
            },

            logout(){
                axios.get('/blog/admin/logout',{params:{userId: localStorage.getItem('id')}}).then(resp=>{
                    localStorage.clear();
                    if (resp.data == 'success') {
                        this.$router.push({
                            path: '/login',
                            name:'Login',
                            params: {
                                status: 'logout'
                            },
                        });
                    } else {
                        alert("未知错误，拉闸啦 QAQ")
                        this.$router.push({
                            path: '/blogs',
                        });
                    }
                })
            },
            mobileButton() {
                if (this.isA == true) {
                    this.isA = false;
                } else {
                    this.isA = true;
                }
            },
            loadingUser(){
                $('.ui.dropdown').dropdown()
            },
        },
        data() {
            return {
                avatar:'',
                isA: true,
                nickname:'',
            }
        },
        created(){
            axios.get('/blog/admin/currentUser', {
                params:{
                    'id': localStorage.getItem('id'),
                    'token': localStorage.getItem('token')
                }
            }).then(resp => {
                this.nickname = resp.data.nickname
                this.avatar = resp.data.avatar
            })

        }
    }
</script>

<style scoped>

</style>