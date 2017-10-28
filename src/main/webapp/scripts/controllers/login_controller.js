/*app.controller("LoginController", ["$scope","$http","$rootScope",'$location', function ($scope, $http, $rootScope, $location) {
	
	$rootScope.user = {};
	
	$scope.login = function(){
		$http.get('Res?action=login&userName='+$scope.userName+"&password="+$scope.password).success(function(response) {
			if(response.status == "success") {
				$rootScope.user = response.result;
	            $location.path('/item');
	        } else {
	            $scope.error = response.result;
	        }
		});
	}
}]);*/

app.controller('LoginController',
	    ['$scope', '$rootScope', '$location', 'AuthenticationService',
	        function ($scope, $rootScope, $location, AuthenticationService) {
	     
	            $scope.login = function () {
		            AuthenticationService.ClearCredentials();
	            	
	                $scope.dataLoading = true;
	                AuthenticationService.Login($scope.userName, $scope.password, function(response) {
	                	console.log("response:"+JSON.stringify(response));
	                    if(response.status == "success") {
	                    	console.log("hai");
	                        AuthenticationService.SetCredentials($scope.userName, $scope.password,response.result);
	                        $location.path('/item');
	                    } else {
	                        $scope.error = response.message;
	                        $scope.dataLoading = false;
	                    }
	                });
	            };
	        }]);