(function(global) {

    // map tells the System loader where to look for things
    var map = {
        'app': 'build',

        // angular bundles
        '@angular/core': 'node_modules/@angular/core/bundles/core.umd.js',
        '@angular/common': 'node_modules/@angular/common/bundles/common.umd.js',
        '@angular/compiler': 'node_modules/@angular/compiler/bundles/compiler.umd.js',
        '@angular/platform-browser': 'node_modules/@angular/platform-browser/bundles/platform-browser.umd.js',
        '@angular/platform-browser-dynamic': 'node_modules/@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
        '@angular/http': 'node_modules/@angular/http/bundles/http.umd.js',
        '@angular/router': 'node_modules/@angular/router/bundles/router.umd.js',
        '@angular/forms': 'node_modules/@angular/forms/bundles/forms.umd.js',

        'lodash': 'node_modules/lodash/lodash.js',
        'rxjs': 'node_modules/rxjs',
        'primeng': 'node_modules/primeng',
        'ng2-translate': 'node_modules/ng2-translate/bundles',
        'moment': 'node_modules/moment/min/moment-with-locales.min.js',
        '@ngrx': 'node_modules/@ngrx'
    };

    // packages tells the System loader how to load when no filename and/or no extension
    var packages = {
        'app': {
            main: './app/main.js',
            defaultExtension: 'js'
        },
        'rxjs': {
            defaultExtension: 'js'
        },
        'angular2-in-memory-web-api': {
            defaultExtension: 'js'
        },
        'primeng': {
            defaultExtension: 'js'
        },
        'ng2-translate': {
            defaultExtension: 'js'
        },
        '@ngrx/core': {
            main: 'index.js',
            format: 'cjs'
        },
        '@ngrx/store': {
            main: 'index.js',
            format: 'cjs'
        },
        '@ngrx/store-devtools': {
            main: 'bundles/store-devtools.umd.js',
            format: 'cjs'
        }
    };

    var config = {
        map: map,
        packages: packages
    };

    System.config(config);

})(this);
