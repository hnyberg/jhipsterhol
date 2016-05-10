(function() {
    'use strict';
    angular
        .module('jhipsterholApp')
        .factory('HipsterPoi', HipsterPoi);

    HipsterPoi.$inject = ['$resource'];

    function HipsterPoi ($resource) {
        var resourceUrl =  'api/hipster-pois/:id';

        return $resource(resourceUrl, {}, {
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
    }
})();
