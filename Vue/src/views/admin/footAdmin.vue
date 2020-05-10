<template>
    <!--页脚-->
    <footer class="ui inverted vertical segment">

        <div class="ui center aligned container" style="padding-top: 2em">

            <div class="ui inverted divided stackable grid">
                <div class="three wide column">
                    <div class="ui inverted link list">
                        <div class="item">
                            <img src="../../assets/images/weixin.png" alt="" class="ui rounded image" style="width: 110px">
                        </div>
                    </div>
                </div>
                <div class="three wide column">
                    <h4 class="ui inverted header m-text-thin m-text-spaced">最新博客</h4>
                    <div class="ui inverted link list">
                        <a href="javascript:void(0)" class="item" v-for="(item,index) in allInfo.content" @click="goBlog(item.id)">{{item.title}}</a>
                    </div>
                </div>
                <div class="three wide column">
                    <h4 class="ui inverted header m-text-thin m-text-spaced">联系我</h4>
                    <div class="ui inverted link list">
                        <a href="tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=479641136" class="item">----< 点击添加 >----</a>
                        <a href="tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=479641136" class="item">QQ:479641136</a>
                    </div>
                </div>
                <div class="seven wide column">
                    <h4 class="ui inverted header m-text-thin m-text-spaced">Cebb</h4>
                    <p class="m-text-thin m-text-spaced m-opacity-mini">
                        于浩歌狂热之际中寒，于天上看见深渊，于一切眼中看见无所有，于无所希望中得救。    ——鲁迅《野草·墓碣文》</p>
                </div>
            </div>
            <div class="ui inverted section divider"></div>
            <p class="m-text-thin m-text-spaced m-opacity-tiny">Copyright © 2020-2020 Cebb Designed by CB</p>
        </div>

    </footer>
</template>

<script>
    export default {
        name: "FootAdmin",
        inject: ['reload'],
        data() {
            return {
                allInfo: [],
            }
        },
        methods: {
            goBlog(id){
                if (this.$route.path !== '/blog') {
                    this.$router.push({
                        name: 'Blog',
                        query: {id: id},
                    })
                }else if (this.$route.query.id !== id) {
                    this.$router.push({query:{id: id}})
                    this.reload();
                }

                this.utils.scrollTop();
            },
        },
        created() {
            axios.get('/blog/user/blogs/0/3').then(resp => {
                this.allInfo = resp.data;
            });
        }
    }
</script>

<style scoped>

</style>