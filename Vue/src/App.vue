<template>
    <div>
        <router-view name="head"></router-view>
        <router-view name="HeadAdmin"></router-view>
        <!--主体-->
        <div id="v-content" v-bind:style="{minHeight: Height+'px'}" v-cloak>
            <router-view v-if="isRouterAlive" :name="$route.path=='/'?'default':$route.name"></router-view>
        </div>
        <router-view name="foot"></router-view>
        <router-view name="FootAdmin"></router-view>
    </div>
</template>
<script>
    export default {
        name: 'app',
        components: {},

        provide() {
            return {
                reload: this.reload
            }
        },
        data() {
            return {
                isA: true,
                Height: 0,
                isRouterAlive: true
            }
        },
        mounted() {
            this.Height = document.documentElement.clientHeight - 300;
            //监听浏览器窗口变化　
            window.onresize = () => {
                this.Height = document.documentElement.clientHeight - 300
            }
        },
        methods: {
            mobileButton() {
                console.log("切换")
                if (this.isA == true) {
                    this.isA = false;
                    console.log(this.isA)
                } else {
                    this.isA = true;
                    console.log(this.isA)
                }
            },

            reload() {
                this.isRouterAlive = false;
                this.$nextTick(function () {
                    this.isRouterAlive = true;
                })
            },

        }
    }
</script>


<style>

    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }

    [v-cloak] {
        display: none;
    }

    .fade-enter {
        opacity: 0;
    }

    .fade-leave {
        opacity: 1;
    }

    .fade-enter-active {
        transition: opacity .5s;
    }

    .fade-leave-active {
        opacity: 0;
        transition: opacity .5s;
    }

</style>
