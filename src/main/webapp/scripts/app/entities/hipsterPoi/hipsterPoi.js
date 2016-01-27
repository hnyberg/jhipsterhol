'use strict';

angular.module('jhipsterholApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hipsterPoi', {
                parent: 'entity',
                url: '/hipsterPois',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipsterholApp.hipsterPoi.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hipsterPoi/hipsterPois.html',
                        controller: 'HipsterPoiController'
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
            .state('hipsterPoi.detail', {
                parent: 'entity',
                url: '/hipsterPoi/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jhipsterholApp.hipsterPoi.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hipsterPoi/hipsterPoi-detail.html',
                        controller: 'HipsterPoiDetailController'
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
            .state('hipsterPoi.new', {
                parent: 'hipsterPoi',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hipsterPoi/hipsterPoi-dialog.html',
                        controller: 'HipsterPoiDialogController',
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
                    }).result.then(function(result) {
                        $state.go('hipsterPoi', null, { reload: true });
                    }, function() {
                        $state.go('hipsterPoi');
                    })
                }]
            })
            .state('hipsterPoi.edit', {
                parent: 'hipsterPoi',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hipsterPoi/hipsterPoi-dialog.html',
                        controller: 'HipsterPoiDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HipsterPoi', function(HipsterPoi) {
                                return HipsterPoi.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hipsterPoi', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hipsterPoi.delete', {
                parent: 'hipsterPoi',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hipsterPoi/hipsterPoi-delete-dialog.html',
                        controller: 'HipsterPoiDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HipsterPoi', function(HipsterPoi) {
                                return HipsterPoi.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hipsterPoi', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
