'use strict';

angular.module('jhipsterholApp').controller('TypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Type', 'HipsterPoi',
        function($scope, $stateParams, $uibModalInstance, entity, Type, HipsterPoi) {

        $scope.type = entity;
        $scope.hipsterpois = HipsterPoi.query();
        $scope.load = function(id) {
            Type.get({id : id}, function(result) {
                $scope.type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterholApp:typeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.type.id != null) {
                Type.update($scope.type, onSaveSuccess, onSaveError);
            } else {
                Type.save($scope.type, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
