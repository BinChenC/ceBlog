<template>
    <!--主体-->
    <div class="m-container m-padded-tb-big">
        <div class="ui container">

            <div class="ui stackable grid">
                <div class="eleven wide column">
                    <div class="ui segment">
                        <img src="../assets/images/ikon.jpg" alt=""
                             class="ui rounded image" style="max-height: 800px">
                    </div>
                </div>

                <div class="five wide column">
                    <div class="ui top attached segment">
                        <div class="ui header">关于我</div>
                    </div>

                    <div class="ui attached segment">
                        <p class="m-text">
                            一个生于福建、长于厦门、21年毕业的Java小白，
                            记录学习路上的滴点。
                        </p>
                    </div>

                    <div class="ui attached segment">
                        <p class="m-text">
                            Cebb Blog是由个人开发的博客网站，使用前后端分离方式开发。
                            后端使用 SpringBoot + SpringDataJpa + Redis，
                            前端使用 Vue + ElementUI + SemanticUI，
                            服务器用 Nginx 做反向代理。诞生于2020年4月10日。
                        </p>

                        <!--<div class="ui orange basic label left pointing m-margin-small m-padded-moderate">编程</div>-->
                    </div>

                    <div class="ui attached segment">
                        <div class="ui teal basic label left pointing m-margin-small m-padded-moderate">喜欢收集好看的插画~</div>
                        <div class="ui teal basic label left pointing m-margin-small m-padded-moderate">想玩游戏_(:з」∠)_</div>
                        <div class="ui teal basic label left pointing m-margin-small m-padded-moderate">各种风格的音乐都很喜欢~</div>
                        <div class="ui teal basic label left pointing m-margin-small m-padded-moderate">时间真不耐用QAQ</div>
                        <div class="ui teal basic label left pointing m-margin-small m-padded-moderate">偷懒了..没有为留言单独做一个页面~</div>
                        <div class="ui teal basic label left pointing m-margin-small m-padded-moderate">如果有想交流的可以点击下方QQ图标~</div>
                    </div>

                    <div class="ui bottom attached segment">
                        <a href="tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=479641136" class="ui circular icon button" @click="goQQ"><i class="qq icon"></i></a>
                        <a href="javascript:void(0)" @click="goWeixin" class="ui circular icon button"><i class="weixin icon"></i></a>
                        <a href="javascript:void(0)" @click="goMusic" class="ui circular icon button"><i class="music icon"></i></a>
                    </div>

                </div>

            </div>

        </div>
    </div>

</template>

<script>
    export default {
        name: "about",
        methods: {
            goQQ(){
                this.$notify({
                    title: 'QQ',
                    dangerouslyUseHTMLString: true,
                    message: '<div class="m-text">扣扣号: 479641136</div>',
                    offset: 100,
                    duration: 10000
                });
            },
            goMusic(){
                this.$notify({
                    title: '网易云音乐',
                    dangerouslyUseHTMLString: true,
                    message: '<div class="m-text">网易云ID：-cebb</div>',
                    offset: 100,
                    duration: 10000
                });
            },
            goWeixin(){
                this.$notify({
                    title: '微信信息',
                    dangerouslyUseHTMLString: true,
                    message: '<div class="m-text">微信：ccb9826</div> <div class="m-text">【也可以扫描页脚的二维码 _(:з」∠)_</div>',
                    offset: 100,
                    duration: 10000
                });
            },
        },
        data() {
            return {
            }
        }
    }
</script>

<style scoped>

</style>