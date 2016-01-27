'use strict';

angular.module('jhipsterholApp')
    .factory('HipsterPoi', function ($resource, DateUtils) {
        return $resource('api/hipsterPois/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
