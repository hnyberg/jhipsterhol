(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('RatingDetailController', RatingDetailController);

    RatingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Rating', 'User', 'HipsterPoi'];

    function RatingDetailController($scope, $rootScope, $stateParams, entity, Rating, User, HipsterPoi) {
        var vm = this;
        vm.rating = entity;
        
        var unsubscribe = $rootScope.$on('jhipsterholApp:ratingUpdate', function(event, result) {
            vm.rating = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
