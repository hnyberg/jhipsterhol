'use strict';

angular.module('jhipsterholApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/api/account") == 0 )){
	                $rootScope.$emit('jhipsterholApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });