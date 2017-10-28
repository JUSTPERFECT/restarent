app.controller("MaterialOrderController", ["$scope","$http", function ($scope, $http) {
	
	$http.get('Res?action=getAllMaterialOrders').success(function(data) {
		$scope.materialOrders = data.result;
	});
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   
        $scope.reverse = !$scope.reverse;
    }
	
	$scope.materialOrder = {};
	
	$scope.setMaterialOrder = function (materialOrder) {
		$scope.materialOrder = materialOrder;
	}
	
}]);