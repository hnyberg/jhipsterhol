'use strict';

angular.module('jhipsterholApp').controller('RatingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rating', 'User', 'HipsterPoi',
        function($scope, $stateParams, $uibModalInstance, entity, Rating, User, HipsterPoi) {

        $scope.rating = entity;
        $scope.users = User.query();
        $scope.hipsterpois = HipsterPoi.query();
        $scope.load = function(id) {
            Rating.get({id : id}, function(result) {
                $scope.rating = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterholApp:ratingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rating.id != null) {
                Rating.update($scope.rating, onSaveSuccess, onSaveError);
            } else {
                Rating.save($scope.rating, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
