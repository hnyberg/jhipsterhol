(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('HipsterPoiDeleteController',HipsterPoiDeleteController);

    HipsterPoiDeleteController.$inject = ['$uibModalInstance', 'entity', 'HipsterPoi'];

    function HipsterPoiDeleteController($uibModalInstance, entity, HipsterPoi) {
        var vm = this;
        vm.hipsterPoi = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            HipsterPoi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
