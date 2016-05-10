'use strict';

describe('Controller Tests', function() {

    describe('HipsterPoi Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHipsterPoi, MockType, MockUser, MockRating;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHipsterPoi = jasmine.createSpy('MockHipsterPoi');
            MockType = jasmine.createSpy('MockType');
            MockUser = jasmine.createSpy('MockUser');
            MockRating = jasmine.createSpy('MockRating');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HipsterPoi': MockHipsterPoi,
                'Type': MockType,
                'User': MockUser,
                'Rating': MockRating
            };
            createController = function() {
                $injector.get('$controller')("HipsterPoiDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterholApp:hipsterPoiUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
