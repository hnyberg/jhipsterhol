'use strict';

angular.module('jhipsterholApp')
    .controller('MainController', function ($scope, Principal, HipsterPoi) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        
        $scope.map = { center: { latitude: 59.330009, longitude: 18.055628 }, zoom: 14 };

        $scope.markers = [];

        HipsterPoi.query(function(pois) {
                angular.forEach(pois, function(poi){
                    this.push(poi);
                }, $scope.markers);
            }
        );
    });
