(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('TypeController', TypeController);

    TypeController.$inject = ['$scope', '$state', 'Type'];

    function TypeController ($scope, $state, Type) {
        var vm = this;
        vm.types = [];
        vm.loadAll = function() {
            Type.query(function(result) {
                vm.types = result;
            });
        };

        vm.loadAll();
        
    }
})();
