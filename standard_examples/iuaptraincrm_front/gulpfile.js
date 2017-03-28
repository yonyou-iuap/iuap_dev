var gulp = require('gulp');
var less = require('gulp-less');
var clean = require('gulp-clean');
var babel = require('gulp-babel');
var copy = require('gulp-copy');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var util = require('gulp-util');
var minifycss = require('gulp-minify-css');
var uglify = require('gulp-uglify');
var koa = require('koa');

var app = koa();
var MockServer = require('./server/mockserver');

var proxyServerConfig = {
    // 是否启用后端代理
    //true -> 使用服务器数据，false -> 使用本地模拟数据
    useProxy : true,
    proxyIgnore:[
        "/",
        "/index_old.html",
        "/index_old.js",
        "/health.html",
        "/index.html",
        "/index.css",
        "/index.js",
        "/login.html",
        "/login.css",
        "/vendor/*",
        "/pages/*",
        "/config/*",
        "/static/*",
        "/styles/*",
        "/util/*",
        "/dist/*",
        "/require.config.js",
        "/json/*",
        "/common/*"
    ],
    host : 'http://localhost:8090',
    username : '',
    password : '',
    context : '',
    serverport : 8090
};

/**
 * 公共错误处理函数
 * 使用示例：
 *  .pipe(uglify())
 .on('error', errHandle)
 */
function errHandle(err) {
    console.log(err);
    util.log(err.fileName + '文件编译出错，出错行数为' + err.lineNumber + '，具体错误信息为：' + err.message);
    this.end();
};

// 编译 src 下所有的 html 文件到 dist 目录
gulp.task('html', function(){
    gulp.src(['src/**/**/*.html','src/*.html'])
        .pipe(rename(function(path){
            path.dirname += '';
        }))
        .pipe(gulp.dest("./dist"));
})

// 编译 src 下所有的 xml 文件到 dist 目录
gulp.task('xml', function(){
    gulp.src('src/widget/**/*.xml')
        .pipe(rename(function(path){
            path.dirname += '';
        }))
        .pipe(gulp.dest("./dist/widget"));
})
// copy 业务目录下的图片到相应的目录
gulp.task('copy:img', function(){
    gulp.src(['src/**/**/**/*.png', 'src/**/**/**/*.jpg'])
        .pipe(rename(function(path){
            path.dirname += '';
        }))
        .pipe(gulp.dest("./dist"));
});

// 完整 copy vendor 目录下的资源到 dist
gulp.task('copy:vendor', function(){
    gulp.src('./vendor/**')
        .pipe(copy('./dist'));
});

// 完整 copy config 目录下的资源到 dist
gulp.task('copy:config', function(){
    gulp.src('./config/**')
        .pipe(copy('./dist'));
});

// 完整 copy json 目录下的资源到 dist
gulp.task('copy:json', function(){
    gulp.src('src/json/**')
        .pipe(gulp.dest('dist/json'));
});

// copy static目录下的资源到 dist
gulp.task('copy:static', function(){
    gulp.src('src/static/**')
        .pipe(gulp.dest('dist/static'))
});

// 匹配所有 css 进行 less 编译
gulp.task('less', function(){
    gulp.src(['src/**/*.css','src/*.css'])
        .pipe(less())
        .pipe(gulp.dest('dist'));
});

//匹配 css 通过 flexible 编译成rem单位
// gulp.task('flexible', function() {
//     var processors = [px2rem({remUnit: 10})];
//     return gulp.src('src/pages/corehr/organiazation/index.css')
//         .pipe(postcss(processors))
//         .pipe(gulp.dest('dist'));
// });

gulp.task('less:dist', function(){
    gulp.src(['src/**/*.css', '!src/styles/*.css'])
        .pipe(less())
        .pipe(minifycss())
        .pipe(gulp.dest('dist'));
});

gulp.task('common', function(){
    gulp.src(['src/styles/*.css', '!src/styles/common.css'])
        .pipe(less())
        .pipe(concat('common.css'))
        .pipe(minifycss())
        .pipe(gulp.dest('dist/styles'));
});

// 编译 JS 代码，支持 ES6 语法编译
gulp.task('es2015',function(){
    gulp.src(['src/**/*.js'])
        .pipe(babel())
        .on('error', errHandle)
        .pipe(gulp.dest('dist'));
});

gulp.task('es2015:dist',function(){
    gulp.src(['src/**/*.js'])
        .pipe(babel())
        .pipe(uglify())
        .on('error', errHandle)
        .pipe(gulp.dest('dist'));
});

// 监听文件改动，执行相应任务
gulp.task('watch', function(){
    gulp.watch('src/**/**/*.css',  ['less']);
    gulp.watch('src/**/*.js',  ['es2015']);
    gulp.watch('src/**/**/*.html', ['html']);
	gulp.watch('src/static/**', ['copy']);
});

// 清空 dist 目录下的资源
gulp.task('clean',function() {
    gulp.src('dist/*', {read: false})
        .pipe(clean({force: true}));
});

// start mock server
gulp.task('mock-server', function(){
    proxyServerConfig.app = app;
    var mockServer = new MockServer(proxyServerConfig);
    mockServer.start(proxyServerConfig);
});

gulp.task('copy', ['copy:static', 'copy:img', 'copy:vendor', 'copy:config','copy:json']);
gulp.task('before', ['copy', 'html','xml', 'less','es2015','common']);
gulp.task('prod', ['copy', 'html','xml', 'less:dist', 'es2015','common']);
gulp.task('default', ['before', 'mock-server', 'watch']);
