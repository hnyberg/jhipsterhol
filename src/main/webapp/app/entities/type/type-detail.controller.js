(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('TypeDetailController', TypeDetailController);

    TypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Type', 'HipsterPoi'];

    function TypeDetailController($scope, $rootScope, $stateParams, entity, Type, HipsterPoi) {
        var vm = this;
        vm.type = entity;
        
        var unsubscribe = $rootScope.$on('jhipsterholApp:typeUpdate', function(event, result) {
            vm.type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
