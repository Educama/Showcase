module.exports = function (config) {
    config.set({

        basePath: '../../',

        frameworks: ['jasmine'],

        files: [
            // Polyfills.
            'node_modules/es6-shim/es6-shim.js',
            'node_modules/reflect-metadata/Reflect.js',

            // System.js for module loading
            'node_modules/systemjs/dist/system-polyfills.js',
            'node_modules/systemjs/dist/system.src.js',

            // Zone.js dependencies
            'node_modules/zone.js/dist/zone.js',
            'node_modules/zone.js/dist/proxy.js',
            'node_modules/zone.js/dist/sync-test.js',
            'node_modules/zone.js/dist/jasmine-patch.js',
            'node_modules/zone.js/dist/async-test.js',
            'node_modules/zone.js/dist/fake-async-test.js',

            // RxJs.
            {pattern: 'node_modules/rxjs/**/*.js', included: false, watched: false},
            {pattern: 'node_modules/rxjs/**/*.js.map', included: false, watched: false},

            {pattern: 'config/test/karma-test-shim.js', included: true, watched: true},
            // {pattern: 'build/test/matchers.js', included: true, watched: true},

            // paths loaded via module imports
            // Angular itself
            {pattern: 'node_modules/@angular/**/*.js', included: false, watched: true},
            {pattern: 'node_modules/@angular/**/*.js.map', included: false, watched: true},

            // Our build application code
            {pattern: 'build/**/*.js', included: false, watched: true},

            // paths loaded via Angular's component compiler
            // (these paths need to be rewritten, see proxies section)
            {pattern: 'build/**/*.html', included: false, watched: true},
            {pattern: 'build/**/*.css', included: false, watched: true},

            // paths to support debugging with source maps in dev tools
            {pattern: 'src/**/*.ts', included: false, watched: false},
            {pattern: 'build/**/*.js.map', included: false, watched: false},

            {pattern: 'node_modules/ng2-translate/**/*.js', included: false, watched: false},
            {pattern: 'node_modules/moment/**/*.js', included: false, watched: false},
            {pattern: 'node_modules/primeng/**/*.js', included: false, watched: false},
            {pattern: 'node_modules/@ngrx/**/*.js', included: false, watched: false}
        ],

        plugins: [
            'karma-jasmine',
            'karma-coverage',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-html-reporter',
            'karma-junit-reporter',
            'karma-spec-reporter'
        ],

        // proxied base paths
        proxies: {
            // required for component assests fetched by Angular's compiler
            "/build/app/": "/base/build/app/",
            "/node_modules/": "/base/node_modules/"
        },

        // Coverage reporter generates the coverage
        reporters: ['coverage', 'html', 'junit', 'spec'],

        // Source files that you wanna generate coverage for.
        // Do not include tests or libraries (these files will be instrumented by Istanbul)
        preprocessors: {
            'build/app/**/!(*html).js': ['coverage']
        },

        coverageReporter: {
            dir: 'build/reports/coverage',
            includeAllSources: true,
            reporters: [
                {type: 'json', subdir: '.'}
            ]
        },

        htmlReporter: {
            outputDir: 'build/reports/html'
        },

        junitReporter: {
            outputDir: 'build/reports/junit',
            outputFile: undefined, // if included, results will be saved as $outputDir/$browserName/$outputFile
            suite: '', // suite will become the package name attribute in xml testsuite element
            useBrowserName: true, // add browser name to report and classes names
            nameFormatter: undefined, // function (browser, result) to customize the name attribute in xml testcase element
            classNameFormatter: undefined, // function (browser, result) to customize the classname attribute in xml testcase element
            properties: {} // key value pair of properties to add to the <properties> section of the report
        },

        specReporter: {
            maxLogLines: 5,         // limit number of lines logged per test
            suppressErrorSummary: false,  // do not print error summary
            suppressFailed: false,  // do not print information about failed tests
            suppressPassed: false,  // do not print information about passed tests
            suppressSkipped: false,  // do not print information about skipped tests
            showSpecTiming: false // print the time elapsed for each spec
        },

        port: 9876,
        colors: true,
        logLevel: config.LOG_ERROR,
        autoWatch: true,
        browsers: ['FirefoxAutoAllowGUM'],
        customLaunchers: {
            FirefoxAutoAllowGUM: {
                base: 'Firefox',
                    prefs: {
                        'intl.accept_languages' : 'en-US'
                    }
            }
        },
        singleRun: true
    })
}
