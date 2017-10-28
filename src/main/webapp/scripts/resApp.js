var app = angular.module("resApp", ["ngRoute",'angularUtils.directives.dirPagination','ngMaterial', 'ngMdIcons', 'ngCookies']);

app.directive('header', function() {
  return {
    templateUrl: 'templates/header.html'
  };
});

app.directive('navigator', function() {
	  return {
	    templateUrl: 'templates/navigator.html'
	  };
	});

app.directive('footer', function() {
  return {
    templateUrl: 'templates/footer.html'
  };
});

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "templates/login.html"
    })
    .when("/item", {
        templateUrl : "templates/item.html"
    })
    .when("/material", {
        templateUrl : "templates/material.html"
    })
    .when("/materialOrder", {
        templateUrl : "templates/material_order.html"
    })
    .when("/calendar", {
        templateUrl : "templates/calendar.html"
    })
    .when("/sales", {
        templateUrl : "templates/sales.html"
    })
    .when("/balanceSummary", {
        templateUrl : "templates/balance_summary.html"
    })
    .when("/dailyClose", {
        templateUrl : "templates/daily_close.html"
    })
    .when("/paymentInfo", {
        templateUrl : "templates/payment_info.html"
    });
});

app.controller('AppCtrl', function ($scope, $timeout, $mdSidenav, $rootScope, $location, $http, $cookieStore,AuthenticationService) {

	$rootScope.globals = $cookieStore.get('globals');
	$scope.header = "Restaurant";
	
	$scope.menu = [
	    {
	      link : '/item',
	      title: 'Menu',
	      icon: 'dashboard'
	    },
	    {
	      link : '/material',
	      title: 'Materials',
	      icon: 'group'
	    },
	    {
		  link : '/materialOrder',
		  title: 'Material Orders',
		  icon: 'message'
		}
	  ];
	  $scope.admin = [
	    {
	      link : '/sales',
	      title: 'Credits',
	      icon: 'delete'
	    },
	    {
		      link : '/paymentInfo',
		      title: 'Debits',
		      icon: 'message'
		},
		{
		      link : '/balanceSummary',
		      title: 'Balance Summary',
		      icon: 'message'
		},
		{
		      link : '/dailyClose',
		      title: 'Dialy Close',
		      icon: 'message'
		},
	    {
	      link : '/calendar',
	      title: 'Calendar',
	      icon: 'settings'
	    }
	  ];
	  
	$rootScope.isObjectEmpty = function(card){
	   return Object.keys(card).length === 0;
	}
	
	$scope.logout = function(){
		AuthenticationService.ClearCredentials();
		$http.get('Res?action=logout').success(function(response){
			if(response.status == "success") {
				
				$rootScope.user = {};
	            $location.path('/');
	        } else {
	            $scope.error = response.result;
	        }
		});
	}
	
    $scope.toggleLeft = buildToggler('left');
    $scope.toggleRight = buildToggler('right');

    function buildToggler(componentId) {
      return function() {
        $mdSidenav(componentId).toggle();
      };
    }
  });

app.config(function($mdThemingProvider) {
	  var customBlueMap = 		$mdThemingProvider.extendPalette('light-blue', {
	    'contrastDefaultColor': 'light',
	    'contrastDarkColors': ['50'],
	    '50': 'ffffff'
	  });
	  $mdThemingProvider.definePalette('customBlue', customBlueMap);
	  $mdThemingProvider.theme('default')
	    .primaryPalette('customBlue', {
	      'default': '500',
	      'hue-1': '50'
	    })
	    .accentPalette('pink');
	  $mdThemingProvider.theme('input', 'default')
	        .primaryPalette('grey')
	});



app.directive('appFilereader', function(
	    $q
) {
  /*
  made by elmerbulthuis@gmail.com WTFPL licensed
  */
  var slice = Array.prototype.slice;

  return {
    restrict: 'A',
    require: '?ngModel',
    link: function(scope, element, attrs, ngModel) {
      if (!ngModel) return;

      ngModel.$render = function() {}

      element.bind('change', function(e) {
        var element = e.target;
        if(!element.value) return;

        element.disabled = true;
        $q.all(slice.call(element.files, 0).map(readFile))
          .then(function(values) {
            if (element.multiple) ngModel.$setViewValue(values);
            else ngModel.$setViewValue(values.length ? values[0] : null);
            element.value = null;
            element.disabled = false;
          });

        function readFile(file) {
          var deferred = $q.defer();

          var reader = new FileReader()
          reader.onload = function(e) {
            deferred.resolve(e.target.result);
          }
          reader.onerror = function(e) {
            deferred.reject(e);
          }
          reader.readAsDataURL(file);

          return deferred.promise;
        }

      }); //change

    } //link

  }; //return

}) //appFilereader
;