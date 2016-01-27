'use strict';

angular.module('jhipsterholApp')
	.controller('HipsterPoiDeleteController', function($scope, $uibModalInstance, entity, HipsterPoi) {

        $scope.hipsterPoi = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HipsterPoi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
