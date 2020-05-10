const webpack = require('webpack')
const cdn = {
    css: [
        "//unpkg.com/element-ui/lib/theme-chalk/index.css",
        "//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css",
        "//cdn.bootcss.com/semantic-ui/2.2.4/semantic.min.css"
    ],
    js: [
        "//cdn.bootcss.com/vue/2.6.11/vue.min.js",
        "//unpkg.com/vue-router@3.1.5/dist/vue-router.min.js",
        "//cdn.staticfile.org/vuex/3.1.2/vuex.min.js",
        "//cdn.bootcss.com/axios/0.19.2/axios.min.js",
        "//code.jquery.com/jquery-3.4.1.min.js",
        "//cdnjs.cloudflare.com/ajax/libs/tocbot/4.5.0/tocbot.min.js",
        "//unpkg.com/element-ui/lib/index.js",
        "//cdn.bootcss.com/semantic-ui/1.12.2/semantic.min.js",
        "//cdn.bootcss.com/moment.js/2.24.0/moment.min.js",
        "//cdn.bootcss.com/moment.js/2.20.1/locale/zh-cn.js"
    ]
}
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = ['js', 'css']
const isProduction = process.env.NODE_ENV === 'production'

module.exports = {

    publicPath: process.env.NODE_ENV === 'production'
        ? './'
        : '/',
    outputDir: "dist", // where to put static assets (js/css/img/font/...) // 是否在保存时使用‘eslint-loader’进行检查 // 有效值: true | false | 'error' // 当设置为‘error’时，检查出的错误会触发编译失败

    productionSourceMap: false, // 调整内部的webpack配置. // see https://github.com/vuejs/vue-cli/blob/dev/docs/webpack.md

    devServer: {
        disableHostCheck: false,
        open: false,
        host: "0.0.0.0",
        compress: true,
        port: 8088,
        https: false,
        hotOnly: false,
        proxy: /*'http://localhost:8080'*/{
            "/blog": {
                target: "http://127.0.0.1:8080",
                changeOrigin: true,
                pathRewrite: {
                    "^/blog": ""
                }
            },
        }
        // before: app => {}
    }, // 第三方插件配置
    // 在exports中添加，这里很关键，不配置不行
    transpileDependencies: ['element-ui'],


    css: {
        // 将组件内部的css提取到一个单独的css文件（只用在生产环境）
        // 也可以是传递给 extract-text-webpack-plugin 的选项对象
        extract: true, // 允许生成 CSS source maps?
        sourceMap: false, // pass custom options to pre-processor loaders. e.g. to pass options to // sass-loader, use { sass: { ... } }
        loaderOptions: {} // Enable CSS modules for all css / pre-processor files. // This option does not affect *.vue files.
    },

    configureWebpack:
    /*config =>*/ {
        plugins: [
            // Ignore all locale files of moment.js
            new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
            // 配置compression-webpack-plugin压缩
            new CompressionWebpackPlugin({
                algorithm: 'gzip',
                test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
                threshold: 10240,
                minRatio: 0.8
            })
        ],
        externals: {
            'jquery': '$',
            "vue": "Vue",
            "vue-router": "VueRouter",
            "vuex": "Vuex",
            "axios": "axios",
            "element-ui": "ELEMENT",
            "moment": "moment"
        }
    },
    pages: {
        index: {
            entry: 'src/main.js',
            template: 'public/index.html',
            filename: 'index.html',
            title: 'Cebb - ain’t break down',
            cdn: cdn
        }
    }
}