(function() {
    'use strict';

    angular
        .module('jhipsterholApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('hipster-poi', {
            parent: 'entity',
            url: '/hipster-poi',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterholApp.hipsterPoi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hipster-poi/hipster-pois.html',
                    controller: 'HipsterPoiController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hipsterPoi');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('hipster-poi-detail', {
            parent: 'entity',
            url: '/hipster-poi/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterholApp.hipsterPoi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hipster-poi/hipster-poi-detail.html',
                    controller: 'HipsterPoiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hipsterPoi');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'HipsterPoi', function($stateParams, HipsterPoi) {
                    return HipsterPoi.get({id : $stateParams.id});
                }]
            }
        })
        .state('hipster-poi.new', {
            parent: 'hipster-poi',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hipster-poi/hipster-poi-dialog.html',
                    controller: 'HipsterPoiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                adress: null,
                                latitude: null,
                                longitude: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('hipster-poi', null, { reload: true });
                }, function() {
                    $state.go('hipster-poi');
                });
            }]
        })
        .state('hipster-poi.edit', {
            parent: 'hipster-poi',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hipster-poi/hipster-poi-dialog.html',
                    controller: 'HipsterPoiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HipsterPoi', function(HipsterPoi) {
                            return HipsterPoi.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('hipster-poi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hipster-poi.delete', {
            parent: 'hipster-poi',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hipster-poi/hipster-poi-delete-dialog.html',
                    controller: 'HipsterPoiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HipsterPoi', function(HipsterPoi) {
                            return HipsterPoi.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('hipster-poi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
