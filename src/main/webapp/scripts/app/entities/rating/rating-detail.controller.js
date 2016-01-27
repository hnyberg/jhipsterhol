'use strict';

angular.module('jhipsterholApp')
    .controller('RatingDetailController', function ($scope, $rootScope, $stateParams, entity, Rating, User, HipsterPoi) {
        $scope.rating = entity;
        $scope.load = function (id) {
            Rating.get({id: id}, function(result) {
                $scope.rating = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterholApp:ratingUpdate', function(event, result) {
            $scope.rating = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
