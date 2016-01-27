'use strict';

angular.module('jhipsterholApp').controller('HipsterPoiDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HipsterPoi', 'User', 'Rating', 'Type',
        function($scope, $stateParams, $uibModalInstance, entity, HipsterPoi, User, Rating, Type) {

        $scope.hipsterPoi = entity;
        $scope.users = User.query();
        $scope.ratings = Rating.query();
        $scope.types = Type.query();
        $scope.load = function(id) {
            HipsterPoi.get({id : id}, function(result) {
                $scope.hipsterPoi = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterholApp:hipsterPoiUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.hipsterPoi.id != null) {
                HipsterPoi.update($scope.hipsterPoi, onSaveSuccess, onSaveError);
            } else {
                HipsterPoi.save($scope.hipsterPoi, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
