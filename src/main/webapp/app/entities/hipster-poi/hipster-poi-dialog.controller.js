(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('HipsterPoiDialogController', HipsterPoiDialogController);

    HipsterPoiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HipsterPoi', 'Type', 'User', 'Rating'];

    function HipsterPoiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HipsterPoi, Type, User, Rating) {
        var vm = this;
        vm.hipsterPoi = entity;
        vm.types = Type.query();
        vm.users = User.query();
        vm.ratings = Rating.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterholApp:hipsterPoiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.hipsterPoi.id !== null) {
                HipsterPoi.update(vm.hipsterPoi, onSaveSuccess, onSaveError);
            } else {
                HipsterPoi.save(vm.hipsterPoi, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
