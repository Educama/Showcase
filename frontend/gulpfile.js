/**
 * Gulp Build Script for Educama Frontend
 */

var gulp = require('gulp');

var autoprefixer = require('autoprefixer');
var Builder = require('systemjs-builder');
var browsers = require('./config/build/autoprefixer');
var bs = require("browser-sync");
var concat = require('gulp-concat');
var cssnano = require('cssnano');
var del = require('del');
var ext = require('gulp-ext-replace');
var gulpif = require('gulp-if');
var historyApiFallback = require('connect-history-api-fallback');
var htmlreplace = require('gulp-html-replace');
var inlineNg2Template = require('gulp-inline-ng2-template');
var uglify = require('gulp-uglify');
var KarmaServer = require('karma').Server;
var postcss = require('gulp-postcss');
var remapIstanbul = require('remap-istanbul/lib/gulpRemapIstanbul');
var rev = require('gulp-rev');
var revReplace = require('gulp-rev-replace');
var sass = require("gulp-sass");
var shell = require('gulp-shell');
var sourcemaps = require('gulp-sourcemaps');
var tslint = require("gulp-tslint");
var typedoc = require("gulp-typedoc");
var typescript = require('gulp-typescript');
var useref = require('gulp-useref');
var util = require('gulp-util');
var zip = require('gulp-zip');

const targetName = 'educama-frontend';
const targetFilename = targetName + '.zip';

const nodeModulesDir = 'node_modules';
const buildDir = 'build';
const distDir = 'dist';
const resourceDir = 'resources';

var tsFiles = ['src/**/*.ts'];
var tsWatchFiles = ['src/**/*.ts', 'src/**/*.html'];
var sassFiles = [resourceDir + '/styles/**/*.scss'];

/**************************************************************
 * Clean Output Directories
 *************************************************************/

// clean the build directory
gulp.task('cleanBuildDir', function () {
    return del(buildDir + '/**/*');
});

// clean the dist directory
gulp.task('cleanDistDir', function () {
    return del(distDir + '/**/*');
});

// delete css files
gulp.task('cleanCSS', function () {
    return del(resourceDir + '/styles/**/*.css');
});

/**************************************************************
 * Compile App (and analyize typescript files with linter)
 *************************************************************/

// function to transpile type script files
function compileTs(files, watchMode) {
    watchMode = watchMode || false;

    var tsProject = typescript.createProject('tsconfig.json');
    var allFiles = [].concat(files);
    var res = gulp.src(allFiles, {
        base: './src',
        outDir: buildDir
    })
        .pipe(tslint({
            formatter: "verbose"
        }))
        .pipe(tslint.report({
            emitError: !watchMode
        }))
        .pipe(inlineNg2Template({useRelativePaths: true}))
        .pipe(sourcemaps.init())
        .pipe(tsProject())
        .on('error', function () {
            if (watchMode) {
                return;
            }
            process.exit(1);
        });
    return res.js
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(buildDir));
}

// transpile typescript files
gulp.task('transpile', ['cleanBuildDir', 'cleanDistDir', 'cleanCSS'], function (done) {
    compileTs(tsFiles)
        .on('finish', done);
});

// function to compile sass files
function compileSass() {
    return gulp.src(resourceDir + '/styles/main.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(postcss([autoprefixer(browsers), cssnano]))
        .pipe(gulp.dest(resourceDir + '/styles/'));
}

// generate css from sass files
gulp.task('sass', ['cleanBuildDir', 'cleanDistDir', 'cleanCSS'], function (done) {
    compileSass()
        .on('finish', done);
});

/**************************************************************
 * Bundle App and Resources
 *************************************************************/

// copy fonts
gulp.task('copy-fonts', ['cleanDistDir'], function (done) {
    gulp.src(resourceDir + '/fonts/*')
        .pipe(gulp.dest(distDir + '/resources/fonts/')).on('finish', done);
});

// copy icons
gulp.task('copy-icons', ['cleanDistDir'], function (done) {
    gulp.src(resourceDir + '/icons/**/*')
        .pipe(gulp.dest(distDir + '/resources/icons/')).on('finish', done);
});

// copy favicon
gulp.task('copy-favicon', ['cleanDistDir'], function (done) {
    gulp.src(resourceDir + '/styles/favicon.png')
        .pipe(gulp.dest(distDir + '/resources/styles/')).on('finish', done);
});

// copy i18n files
gulp.task('copy-i18n', ['cleanDistDir'], function (done) {
    gulp.src(resourceDir + '/i18n/*')
        .pipe(gulp.dest(distDir + '/resources/i18n/')).on('finish', done);
});

// copy resources
gulp.task('copy-resources', ['transpile', 'sass', 'copy-fonts', 'copy-icons', 'copy-favicon', 'copy-i18n'], function (done) {
    // copy favorite icon
    gulp.src('*.ico')
        .pipe(gulp.dest(distDir));

    // copy staticfile (used for cloud foundry deployment)
    gulp.src('Staticfile')
        .pipe(gulp.dest(distDir))
        .on('finish', done);
});

// copy css file
gulp.task('copy-css-file', ['copy-resources'], function (done) {
    gulp.src(resourceDir + '/styles/main.css')
        .pipe(gulp.dest(distDir + '/resources/styles'))
        .on('finish', done);
});

//bundle required (already minified) third party libs
gulp.task('bundle-vendor-files', ['copy-resources'], function (done) {
    // order of Libs (especially polyfills, shims_for_IE) is HIGHLY IMPORTANT!
    const js = [
        nodeModulesDir + '/reflect-metadata/Reflect.js',
        nodeModulesDir + '/core-js/client/shim.min.js',
        nodeModulesDir + '/systemjs/dist/system-polyfills.js',
        nodeModulesDir + '/zone.js/dist/zone.js',
        nodeModulesDir + '/primeui/primeui-ng-all.min.js',
        nodeModulesDir + '/web-animations-js/web-animations-next.min.js',
        resourceDir + '/polyfills/shims_for_IE.js',
        resourceDir + '/polyfills/svg4everybody.js'
    ];

    gulp.src(js)
        .pipe(uglify())
        .pipe(concat('vendor.js'))
        .pipe(gulp.dest(distDir + '/js/'))
        .on('finish', done);
});

// bundle application files
gulp.task('bundle-app-files', ['copy-resources'], function () {
    var builder = new Builder('.', 'systemjs.config.js');
    return builder.buildStatic(buildDir + '/app/main.js', distDir + '/js/app.js');
});

// uglify the static bundle
gulp.task('uglify-bundled-app-files', ['bundle-app-files'], function (done) {
    gulp.src(distDir + '/js/app.js')
        .pipe(uglify())
        .pipe(gulp.dest(distDir + '/js/'))
        .on('finish', done);
});

// replace single files with bundled files in index.html
gulp.task('html-replace', ['copy-css-file', 'bundle-vendor-files', 'uglify-bundled-app-files'], function (done) {
    gulp.src('index.html')
        .pipe(htmlreplace({
            'libs': 'js/vendor.js',
            'app': 'js/app.js',
            'css': 'resources/styles/main.css'
        }))
        .pipe(gulp.dest(distDir))
        .on('finish', done);
});

// revision files (js, css) by adding a hash value to the file name
gulp.task("revision-static-files", ['html-replace'], function (done) {
    gulp.src(["dist/**/*.css", "dist/**/*.js"])
        .pipe(rev())
        .pipe(gulp.dest(distDir))
        .pipe(rev.manifest())
        .pipe(gulp.dest(distDir))
        .on('finish', done);
})

// replace revisioned files (js, css) in index.html
gulp.task("replace-revisioned-files-in-index-html", ["revision-static-files"], function (done) {
    var manifest = gulp.src('./' + distDir + "/rev-manifest.json");

    gulp.src(distDir + "/index.html")
        .pipe(revReplace({manifest: manifest}))
        .pipe(gulp.dest(distDir))
        .on('finish', done);
});

// delete files that have been revisioned
gulp.task("delete-unrevisioned-files", ['replace-revisioned-files-in-index-html'], function () {
    del(distDir + '/rev-manifest.json');
    del(distDir + '/js/vendor.js');
    del(distDir + '/js/app.js');
    return del(distDir + '/resources/styles/main.css');
});

/**************************************************************
 * Create ZIP for deployment
 *************************************************************/

// zip the application (the content of the dist folder)
gulp.task('zipApplication', ['delete-unrevisioned-files'], function (done) {
    gulp.src(distDir + '/**/*')
        .pipe(zip(targetFilename))
        .pipe(gulp.dest(distDir))
        .on('finish', done);
});

/**************************************************************
 * Create Documentation
 *************************************************************/

function generateDocs() {
    return gulp.src(["src/app/**/*.ts"])
        .pipe(typedoc({
            module: "commonjs",
            // "es6:" is a workaround for now to get typedoc running with type definition from @Types
            target: "es6",
            experimentalDecorators: true,
            includeDeclarations: true,
            out: "docs/",
            json: "docs/educama-documenation.json",
            name: "Educama Angular Documentation",
            externalPattern: "src/**/*.spec.ts",
            excludeExternals: true,
            excludeNotExported: true,
            mode: "file"
        }));
}

//generate documentation (TypeDoc)
gulp.task('typedoc', ['zipApplication'], function (done) {
    generateDocs()
        .on('finish', done)
});

/**************************************************************
 * Run Tests
 *************************************************************/

// run tests once and exit
gulp.task('run-karma', function (done) {
    new KarmaServer({
        configFile: __dirname + '/config/test/karma.conf.js',
        singleRun: true
    }, done).start();
});

// generate coverage report for type script files
gulp.task('remap-istanbul', ['run-karma'], function (done) {
    gulp.src(buildDir + '/reports/coverage/coverage-final.json')
        .pipe(remapIstanbul({
            reports: {
                'json': buildDir + '/reports/coverage/coverage.json',
                'html': buildDir + '/reports/coverage/html-report'
            }
        }))
        .on('finish', done);
});

/**************************************************************
 * Watch file changes (ts and sass files)
 *************************************************************/

// watch typescript files and transpile them
gulp.task('watch-ts', function () {
    return gulp.watch(tsWatchFiles, function (file) {
        file.path.endsWith('.html')
            ? filename = file.path.replace('.html', ".ts")
            : filename = file.path;
        util.log('Compiling ' + filename + '...');
        return compileTs(filename, true);
    });
});

// watch sass files and process them
gulp.task('watch-sass', function () {
    return gulp.watch(sassFiles, function (file) {
        util.log('Compiling ' + file.path + '...');
        return compileSass();
    });
});

gulp.task('watch', ['watch-ts', 'watch-sass']), function () {
};

/**************************************************************
 * Serve App locally
 *************************************************************/

var browserSyncConfig = {
    dev: {
        port: 8080,
        server: {
            baseDir: './',
            middleware: [historyApiFallback()]
        },
        files: [
            buildDir + "/index.html",
            buildDir + "/**/*.js",
            resourceDir + "/**/*.css"
        ]
    },
    dist: {
        port: 8080,
        server: {
            baseDir: './dist',
            middleware: [historyApiFallback()]
        }
    }
};

function startBrowsersync(config) {
    bsIns = bs.create();
    bsIns.init(config);
    bsIns.reload();
}

/* Start live server dev mode */
gulp.task('serve-dev', ['watch-ts', 'watch-sass'], function () {
    startBrowsersync(browserSyncConfig.dev);
});

/* Start live server production mode */
gulp.task('serve-dist', function () {
    startBrowsersync(browserSyncConfig.dist);
});

/**************************************************************
 * Task definitions
 *************************************************************/

gulp.task('compile', ['sass', 'transpile']);
gulp.task('build', ['typedoc']);
gulp.task('test', ['remap-istanbul']);