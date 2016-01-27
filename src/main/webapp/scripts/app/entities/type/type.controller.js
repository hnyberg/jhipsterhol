'use strict';

angular.module('jhipsterholApp')
    .controller('TypeController', function ($scope, $state, Type) {

        $scope.types = [];
        $scope.loadAll = function() {
            Type.query(function(result) {
               $scope.types = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.type = {
                title: null,
                id: null
            };
        };
    });
