'use strict';

angular.module('jhipsterholApp')
    .controller('HipsterPoiController', function ($scope, $state, HipsterPoi, ParseLinks) {

        $scope.hipsterPois = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HipsterPoi.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hipsterPois = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hipsterPoi = {
                title: null,
                adress: null,
                latitude: null,
                longitude: null,
                id: null
            };
        };
    });
