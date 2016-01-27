'use strict';

angular.module('jhipsterholApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


