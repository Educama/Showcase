/*global jasmine, __karma__, window*/
Error.stackTraceLimit = Infinity;
jasmine.DEFAULT_TIMEOUT_INTERVAL = 3000;

__karma__.loaded = function () {
};

function isJsFile(path) {
    return path.slice(-3) == '.js';
}

function isSpecFile(path) {
    return path.slice(-8) == '.spec.js';
}

function isBuiltFile(path) {
    var buildPath = '/base/build/';
    return isJsFile(path) && (path.substr(0, buildPath.length) == buildPath);
}

var allSpecFiles = Object.keys(window.__karma__.files)
    .filter(isSpecFile)
    .filter(isBuiltFile);

// Load our SystemJS configuration.
System.config({
    baseURL: '/base'
});

System.config(
    {
        paths: {
            // paths serve as alias
            'npm:': 'node_modules/'
        },
        map: {
            'app': 'build',

            // angular bundles
            '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
            '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
            '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
            '@angular/forms': 'node_modules/@angular/forms/bundles/forms.umd.js',
            '@angular/http': 'node_modules/@angular/http/bundles/http.umd.js',
            '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
            '@angular/router': 'node_modules/@angular/router/bundles/router.umd.js',

            // angular testing umd bundles
            '@angular/core/testing': 'npm:@angular/core/bundles/core-testing.umd.js',
            '@angular/common/testing': 'npm:@angular/common/bundles/common-testing.umd.js',
            '@angular/compiler/testing': 'npm:@angular/compiler/bundles/compiler-testing.umd.js',
            '@angular/platform-browser/testing': 'npm:@angular/platform-browser/bundles/platform-browser-testing.umd.js',
            '@angular/platform-browser-dynamic/testing': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic-testing.umd.js',

            // other libraries
            'rxjs': 'node_modules/rxjs',
            'primeng': 'node_modules/primeng',
            'ng2-translate': 'node_modules/ng2-translate/bundles',
            'moment': 'node_modules/moment/min/moment-with-locales.min.js',
            '@ngrx': 'node_modules/@ngrx'
        },
        packages: {
            'app': {
                defaultExtension: 'js'
            },
            'rxjs': {
                defaultExtension: 'js'
            },
            'primeng': {
                defaultExtension: 'js'
            },
            'ng2-translate': {
                defaultExtension: 'js'
            },
            'angular2-in-memory-web-api': {
                defaultExtension: 'js'
            },
            '@ngrx/core': {
                main: 'index.js',
                format: 'cjs'
            },
            '@ngrx/store': {
                main: 'index.js',
                format: 'cjs'
            }
        }
    });

Promise.all([
    System.import('@angular/core/testing'),
    System.import('@angular/platform-browser-dynamic/testing')

]).then(function (providers) {
    var testing = providers[0];
    var testingBrowser = providers[1];

    testing.TestBed.initTestEnvironment(testingBrowser.BrowserDynamicTestingModule,
        testingBrowser.platformBrowserDynamicTesting());

}).then(function () {
    // Finally, load all spec files.
    // This will run the tests directly.
    return Promise.all(
        allSpecFiles.map(function (moduleName) {
            return System.import(moduleName);
        }));

}).then(__karma__.start, __karma__.error);
