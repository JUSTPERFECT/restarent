app.controller("DailyCloseController", ["$scope","$http", "$filter",function ($scope, $http, $filter) {
	
	$scope.paymentTypes = [];
	$http.get('Res?action=getAllPaymentTypes').success(function(data) {
		$scope.paymentTypes = data.result;
	});
	
	$scope.date= new Date();
	
	var df = new Date();
    var y = df.getFullYear();
    var m = df.getMonth()+1;
    var d = df.getDate();
    
	var date = y+"-"+m+"-"+d;
	
	$scope.$watch('date', function(newVal, oldVal) {
	      if (!newVal) {
	        return false;
	      }
	      date = $filter('date')(new Date(newVal), "yyyy-MM-dd");
	});	
	
	$http.get('Res?action=getBalanceInfo&date='+date).success(function(data) {
		$scope.balanceInfo=data.result;
	});
	
	$scope.filterWithDate = function(){
		$scope.balanceInfos = [];
		$http.get('Res?action=getBalanceInfo&date='+date).success(function(data) {
			$scope.balanceInfo=data.result;
		});
	}
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   
        $scope.reverse = !$scope.reverse;
    }
	
	$scope.balanceSummary = {};
	
	
	
	$scope.balanceSummary.date = date;
	
	$scope.calculateDiscurbance = function(){		
		$scope.totalEnteredAmount = 0;
		for(var i=0;i<$scope.paymentTypes.length;i++){
			$scope.totalEnteredAmount += parseInt($scope.paymentTypes[i].amount);
		}
		$scope.totalEnteredAmount += parseInt($scope.balanceSummary.counterCash);
		console.log("totalEnteredAmount:"+$scope.totalEnteredAmount);
		console.log("Credit Amount:"+$scope.balanceInfo.creditAmount);
		$scope.balanceSummary.discurbance = $scope.balanceInfo.creditAmount - $scope.totalEnteredAmount;
	}
	
	$scope.addDialyCloseInfo = function(){
		$scope.balanceSummary.paymentTypes = $scope.paymentTypes;
		$scope.calculateDiscurbance();	
  	  $http({
            url : 'Res?action=addDailyCloseInfo',
            method : "POST",
            data : {data: $scope.balanceSummary},
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function(response) {
            alert(response.data);
            $scope.balanceSummary = {};
        }, function(response) {
        });
  }
	
}]);