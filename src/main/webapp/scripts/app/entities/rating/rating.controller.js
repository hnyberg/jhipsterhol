'use strict';

angular.module('jhipsterholApp')
    .controller('RatingController', function ($scope, $state, Rating, ParseLinks) {

        $scope.ratings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Rating.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.ratings = result;
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
            $scope.rating = {
                value: null,
                id: null
            };
        };
    });
