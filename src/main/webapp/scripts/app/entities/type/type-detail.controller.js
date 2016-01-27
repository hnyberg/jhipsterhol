'use strict';

angular.module('jhipsterholApp')
    .controller('TypeDetailController', function ($scope, $rootScope, $stateParams, entity, Type, HipsterPoi) {
        $scope.type = entity;
        $scope.load = function (id) {
            Type.get({id: id}, function(result) {
                $scope.type = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterholApp:typeUpdate', function(event, result) {
            $scope.type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
