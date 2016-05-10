(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('HipsterPoiController', HipsterPoiController);

    HipsterPoiController.$inject = ['$scope', '$state', 'HipsterPoi'];

    function HipsterPoiController ($scope, $state, HipsterPoi) {
        var vm = this;
        vm.hipsterPois = [];
        vm.loadAll = function() {
            HipsterPoi.query(function(result) {
                vm.hipsterPois = result;
            });
        };

        vm.loadAll();
        
    }
})();
