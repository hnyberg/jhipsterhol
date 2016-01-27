'use strict';

angular.module('jhipsterholApp')
    .controller('HipsterPoiDetailController', function ($scope, $rootScope, $stateParams, entity, HipsterPoi, User, Rating, Type) {
        $scope.hipsterPoi = entity;
        $scope.load = function (id) {
            HipsterPoi.get({id: id}, function(result) {
                $scope.hipsterPoi = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterholApp:hipsterPoiUpdate', function(event, result) {
            $scope.hipsterPoi = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
