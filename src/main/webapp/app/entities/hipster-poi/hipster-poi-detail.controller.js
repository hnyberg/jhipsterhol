(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('HipsterPoiDetailController', HipsterPoiDetailController);

    HipsterPoiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'HipsterPoi', 'Type', 'User', 'Rating'];

    function HipsterPoiDetailController($scope, $rootScope, $stateParams, entity, HipsterPoi, Type, User, Rating) {
        var vm = this;
        vm.hipsterPoi = entity;
        
        var unsubscribe = $rootScope.$on('jhipsterholApp:hipsterPoiUpdate', function(event, result) {
            vm.hipsterPoi = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
