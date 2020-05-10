<template>
    <!--导航-->
    <div class="ui inverted attached segment m-padded-tb-mini m-shadow-small">
        <div class="ui container">
            <div class="ui inverted secondary stackable menu">
                <h2 class="ui teal header item" @click="loginAdmin">Blog</h2>
                <a @click="onIndex" :class="[{'m-mobile-hide':isA},$route.path=='/blogs'?'active':'','m-item item']"><i
                        class="home icon"></i>首页</a>
                <a @click="onType" :class="[{'m-mobile-hide':isA},$route.path=='/types'?'active':'','m-item item']"><i
                        class="idea icon"></i>分类</a>
                <a @click="onTag" :class="[{'m-mobile-hide':isA},$route.path=='/tags'?'active':'','m-item item']"><i
                        class="tags icon"></i>标签</a>
                <a @click="onArchive"
                   :class="[{'m-mobile-hide':isA},$route.path=='/archives'?'active':'','m-item item']"><i
                        class="book icon"></i>归档</a>
                <a @click="onAboutMe"
                   :class="[{'m-mobile-hide':isA},$route.path=='/about'?'active':'','m-item item']"><i
                        class="info icon"></i>关于我</a>
                <div v-bind:class="{'m-mobile-hide':isA}" class="m-item item ">
                    <div class="ui icon inverted transparent input m-margin-tb-tiny">
                        <input v-model="searchStr" @keyup.enter="searchListen" type="text" placeholder="Search...">
                        <i class="search link icon" @click="searchListen"></i>
                    </div>
                </div>
            </div>
        </div>

        <a href="#" @click="mobileButton()" class="ui menu toggle black icon button m-right-top m-mobile-show">
            <i class="sidebar icon"></i>
        </a>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data() {
            return {
                isA: true,
                searchStr: '',
            }
        },
        methods: {
            searchListen() {
                if (this.$route.path != '/blogs') {
                    this.$router.push({
                        path: '/blogs',
                        name: 'Home',
                        params: {
                            key: 'search'
                        }
                    });
                }
                setTimeout(() => {
                    this.$router.push({
                        query: {
                            search: this.searchStr
                        }
                    });
                }, 500);

            },
            loginAdmin() {
                if (this.$route.path == '/login') {
                    return;
                }
                this.$router.push({
                    path: '/login',
                    name: 'Login',
                })
            },
            onIndex() {
                if (this.$route.path == '/blogs') {
                    return;
                }
                this.$router.push({
                    path: '/blogs',
                    name: 'Home',
                })
            },
            onTag() {
                if (this.$route.path == '/tags') {
                    return;
                }
                this.$router.push({
                    path: '/tags',
                    name: 'MyTags',
                })
            },
            onType() {
                if (this.$route.path == '/types') {
                    return;
                }
                this.$router.push({
                    path: '/types',
                    name: 'Types',
                })
            },
            onArchive() {
                if (this.$route.path == '/archives') {
                    return;
                }
                this.$router.push({
                    path: '/archives',
                    name: 'MyArchives',
                })
            },
            onAboutMe() {
                if (this.$route.path == '/about') {
                    return;
                }
                this.$router.push({
                    path: '/about',
                    name: 'About',
                })
            },
            mobileButton() {
                if (this.isA == true) {
                    this.isA = false;
                } else {
                    this.isA = true;
                }
            },
        }
    }
</script>

<style scoped>

</style>