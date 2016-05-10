(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .controller('RatingDialogController', RatingDialogController);

    RatingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rating', 'User', 'HipsterPoi'];

    function RatingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rating, User, HipsterPoi) {
        var vm = this;
        vm.rating = entity;
        vm.users = User.query();
        vm.hipsterpois = HipsterPoi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterholApp:ratingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.rating.id !== null) {
                Rating.update(vm.rating, onSaveSuccess, onSaveError);
            } else {
                Rating.save(vm.rating, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
