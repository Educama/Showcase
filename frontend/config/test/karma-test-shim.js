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
            '@angular/animations' : 'node_modules/@angular/animations/bundles/animations.umd.js',
            '@angular/animations/browser': 'node_modules/@angular/animations/bundles/animations-browser.umd.js',
            '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
            '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
            '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
            '@angular/forms': 'node_modules/@angular/forms/bundles/forms.umd.js',
            '@angular/http': 'node_modules/@angular/http/bundles/http.umd.js',
            '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser/animations': 'node_modules/@angular/platform-browser/bundles/platform-browser-animations.umd.js',
            '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
            '@angular/router': 'node_modules/@angular/router/bundles/router.umd.js',

            // angular testing umd bundles
            '@angular/core/testing': 'npm:@angular/core/bundles/core-testing.umd.js',
            '@angular/common/testing': 'npm:@angular/common/bundles/common-testing.umd.js',
            '@angular/compiler/testing': 'npm:@angular/compiler/bundles/compiler-testing.umd.js',
            '@angular/platform-browser/testing': 'npm:@angular/platform-browser/bundles/platform-browser-testing.umd.js',
            '@angular/platform-browser-dynamic/testing': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic-testing.umd.js',

            // reactive extensions for angular
            '@ngrx/core': 'node_modules/@ngrx/core/bundles/core.min.umd.js',
            '@ngrx/effects': 'node_modules/@ngrx/effects/bundles/effects.umd.js',
            '@ngrx/effects/testing': 'node_modules/@ngrx/effects/testing/index.js',
            '@ngrx/store': 'node_modules/@ngrx/store/bundles/store.min.umd.js',

            // other libraries
            'lodash': 'node_modules/lodash',
            'moment': 'node_modules/moment/min/moment-with-locales.min.js',
            'ng2-file-upload': 'node_modules/ng2-file-upload',
            'ng2-translate': 'node_modules/ng2-translate/bundles/ng2-translate.umd.js',
            'primeng': 'node_modules/primeng',
            'rxjs': 'node_modules/rxjs'
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
