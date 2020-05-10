// import Vue from 'vue'
// import VueRouter from 'vue-router'
// import store from "../store";

const Home = () => import('../views/index.vue');
const Blog = () => import('../views/blog.vue');
const Types = () => import('../views/types.vue');
const MyTags = () => import('../views/tags.vue');
const Head = () => import('../views/header.vue');
const Foot = () => import('../views/footer.vue');
const MyArchives = () => import('../views/archives.vue');
const About = () => import('../views/about.vue');
const AManage = () => import('../views/admin/blogs.vue');
const HeadAdmin = () => import('../views/admin/headAdmin.vue');
const FootAdmin = () => import('../views/admin/footAdmin.vue');
const AddBlog = () => import('../views/admin/blog-input.vue');
const Error404 = () => import('../views/error/404.vue');
const Login = () => import('../views/admin/login.vue');
const ATypes = () => import('../views/admin/types.vue');
const Type_Input = () => import('../views/admin/type-input.vue');
const ATags = () => import('../views/admin/tags.vue');
const Tag_Input = () => import('../views/admin/tag-input.vue');

// Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes:[
        // 浏览模块
        {
            path: '/',
            redirect: '/blogs',
            components: {
                Home:Home,
                head:Head,
                foot:Foot,
                About:About,
                Blog:Blog,
                Types:Types,
                MyTags:MyTags,
                MyArchives:MyArchives,
                Error404:Error404,
                Login:Login
            },
            children: [
                {
                    path:'/blogs',
                    name:'Home',
                    component:Home
                },
                {
                    path:'/login',
                    name:'Login',
                    component:Login
                },
                {
                    path: '/404',
                    name: 'Error404',
                    component: Error404
                },
                {
                    path: '/blog',
                    name: 'Blog',
                    component: Blog
                },
                {
                    path: '/types',
                    name: 'Types',
                    component: Types
                },
                {
                    path: '/about',
                    name: 'About',
                    component: About
                },
                {
                    path: '/tags',
                    name: 'MyTags',
                    component: MyTags
                },
                {
                    path: '/archives',
                    name: 'MyArchives',
                    component: MyArchives
                },
            ]
        },

        // 管理模块
        {
            path:'/admin',
            name:'HeadAdmin',
            redirect:'/a_manage',
            meta:{
                requiresAuth:true
            },
            components:{
                HeadAdmin:HeadAdmin,
                FootAdmin:FootAdmin,
                AManage:AManage,
                blogInput:AddBlog,
                ATypes:ATypes,
                typeInput:Type_Input,
                ATags:ATags,
                tagInput:Tag_Input
            },
            children:[
                {
                    path:'/a_blogs',
                    name:'AManage',
                    component:AManage,
                    meta:{
                        requireAuth:true
                    }
                },
                {
                    path:'/a_type_input',
                    name:'typeInput',
                    component:Type_Input,
                    meta:{
                        requireAuth:true
                    }
                },
                {
                    path:'/a_types',
                    name:'ATypes',
                    component:ATypes,
                    meta:{
                        requireAuth:true
                    }
                },
                {
                    path:'/a_tag_input',
                    name:'tagInput',
                    component:Tag_Input,
                    meta:{
                        requireAuth:true
                    }
                },
                {
                    path:'/a_tags',
                    name:'ATags',
                    component:ATags,
                    meta:{
                        requireAuth:true
                    }
                },
                {
                    path:'/a_blog_input',
                    name:'blogInput',
                    component:AddBlog,
                    meta:{
                        requireAuth:true
                    }
                }
            ]
        },
    ],
})

// 404拦截和登录拦截
router.beforeEach((to, from, next) => {
    if (to.matched.length === 0) { //匹配前往的路由不存在
        from.name ? next({
            name: from.name
        }) : next('/404'); //判断此跳转路由的来源路由是否存在，存在的情况跳转到来源路由，否则跳转到404页面
    } else if (to.meta.requireAuth) { // 判断跳转的路由是否需要登录
        let token = localStorage.getItem("token")
        let userId = localStorage.getItem("id")
        if(token !== "" && token !== null){
            axios.get('/blog/admin/currentUser',{params:{'id': userId,'token': token}}).then(resp => {
                if (resp.data != "" && resp.data != null) { // vuex.state判断token是否存在
                    next(); // 已登录
                } else {
                    next({
                        path: '/login',
                        name: 'Login',
                        params: {
                            status: 'notLoged'
                        }
                        // query: {redirect: to.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
                    });
                }
            })
        }else{
            next({
                path: '/login',
                name: 'Login',
                params:{
                    status: 'notLoged'
                }
            })
        }
    } else {
        next()
    }

});


export default router
