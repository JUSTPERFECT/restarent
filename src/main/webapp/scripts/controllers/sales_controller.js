app.controller("SalesController", ["$scope","$http","$mdDialog", "$filter", function ($scope, $http, $mdDialog, $filter) {

	$scope.dateFrom= new Date();
	$scope.dateTo= new Date();
	
	var df = new Date();
    var y = df.getFullYear();
    var m = df.getMonth()+1;
    var d = df.getDate();
    
	var dateFrom = y+"-"+m+"-"+d;
	var dateTo = y+"-"+m+"-"+d;
	
	$scope.$watch('dateFrom', function(newVal, oldVal) {
	      if (!newVal) {
	        return false;
	      }
	      dateFrom = $filter('date')(new Date(newVal), "yyyy-MM-dd");
	 });
	
	$scope.$watch('dateTo', function(newVal, oldVal) {
	      if (!newVal) {
	        return false;
	      }
	      dateTo = $filter('date')(new Date(newVal), "yyyy-MM-dd");
	 });
	
	$scope.totalItemAmount = 0;
	$scope.totalCategoryAmount = 0;
	$scope.totalOrderAmount = 0;
	$scope.totalPaymentAmount = 0;
	
	$http.get('Res?action=getAllSales&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
		$scope.sales = data.result;
		for(var i=0;i<$scope.sales.items.length;i++){
			$scope.totalItemAmount += ($scope.sales.items[i].quantity*$scope.sales.items[i].item.price);
		}
		for(var i=0;i<$scope.sales.subcategories.length;i++){
			$scope.totalCategoryAmount += ($scope.sales.subcategories[i].totalAmount)
		}
	});
	
	$http.get('Res?action=getCreditAmountBasedOnPaymentType&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
		$scope.paymentTypes = data.result;
		for(var i=0;i<$scope.paymentTypes.length;i++){
			$scope.totalPaymentAmount += ($scope.paymentTypes[i].amount);
		}
	});
	
	$http.get('Res?action=getAllItemOrders&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
		$scope.itemOrders = data.result;
		for(var i=0;i<$scope.itemOrders.length;i++){
			$scope.totalOrderAmount += ($scope.itemOrders[i].totalAmount);
		}
	});
	
	$scope.filterWithDate = function(){
		$scope.itemOrder = {};
		$http.get('Res?action=getAllSales&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
			$scope.sales = data.result;
			$scope.totalItemAmount = 0;
			$scope.totalCategoryAmount = 0;
			for(var i=0;i<$scope.sales.items.length;i++){
				$scope.totalItemAmount += ($scope.sales.items[i].quantity*$scope.sales.items[i].item.price)
			}
			for(var i=0;i<$scope.sales.subcategories.length;i++){
				$scope.totalCategoryAmount += ($scope.sales.subcategories[i].totalAmount)
			}
		});
		
		$http.get('Res?action=getAllItemOrders&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
			$scope.itemOrders = data.result;
			$scope.totalOrderAmount = 0;
			for(var i=0;i<$scope.itemOrders.length;i++){
				$scope.totalOrderAmount += ($scope.itemOrders[i].totalAmount)
			}
		});
		
		$http.get('Res?action=getCreditAmountBasedOnPaymentType&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
			$scope.paymentTypes = data.result;
			$scope.totalPaymentAmount = 0;
			for(var i=0;i<$scope.paymentTypes.length;i++){
				$scope.totalPaymentAmount += ($scope.paymentTypes[i].amount);
			}
		});
	}
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   
        $scope.reverse = !$scope.reverse; 
    }	
	
	$scope.itemOrder = {};
	
	$scope.setItemOrder = function (itemOrder) {
		$scope.itemOrder = itemOrder;
	}
}]);