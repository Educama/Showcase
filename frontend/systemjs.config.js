(function(global) {

    // map tells the System loader where to look for things
    var map = {
        'app': 'build',

        // angular bundles
        '@angular/animations' : 'node_modules/@angular/animations/bundles/animations.umd.js',
        '@angular/animations/browser': 'node_modules/@angular/animations/bundles/animations-browser.umd.js',
        '@angular/core': 'node_modules/@angular/core/bundles/core.umd.js',
        '@angular/common': 'node_modules/@angular/common/bundles/common.umd.js',
        '@angular/compiler': 'node_modules/@angular/compiler/bundles/compiler.umd.js',
        '@angular/platform-browser': 'node_modules/@angular/platform-browser/bundles/platform-browser.umd.js',
        '@angular/platform-browser/animations': 'node_modules/@angular/platform-browser/bundles/platform-browser-animations.umd.js',
        '@angular/platform-browser-dynamic': 'node_modules/@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
        '@angular/http': 'node_modules/@angular/http/bundles/http.umd.js',
        '@angular/router': 'node_modules/@angular/router/bundles/router.umd.js',
        '@angular/forms': 'node_modules/@angular/forms/bundles/forms.umd.js',

        // reactive extensions for angular
        '@ngrx/core': 'node_modules/@ngrx/core/bundles/core.min.umd.js',
        '@ngrx/store': 'node_modules/@ngrx/store/bundles/store.min.umd.js',
        '@ngrx/effects': 'node_modules/@ngrx/effects/bundles/effects.umd.js',
        '@ngrx/store-devtools': 'node_modules/@ngrx/store-devtools/bundles/store-devtools.min.umd.js',

        // other libraries
        'lodash': 'node_modules/lodash',
        'moment': 'node_modules/moment/min/moment-with-locales.min.js',
        'ng2-translate': 'node_modules/ng2-translate/bundles/ng2-translate.umd.js',
        'primeng': 'node_modules/primeng',
        'rxjs': 'node_modules/rxjs'

    };

    // packages tells the System loader how to load when no filename and/or no extension
    var packages = {
        'app': {
            main: './app/main.js',
            defaultExtension: 'js'
        },
        'rxjs': {
            main: 'Rx.js',
            defaultExtension: 'js'
        },
        'primeng': {
            defaultExtension: 'js'
        },
        'lodash': {
            main: 'lodash.js',
            format: 'cjs'
        }
    };

    var config = {
        map: map,
        packages: packages
    };

    System.config(config);

})(this);
