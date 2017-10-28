app.controller("BalanceController", ["$scope","$http", "$filter", function ($scope, $http, $filter) {

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
	
	$scope.balanceInfos = [];
	
	$http.get('Res?action=getBalanceInfo&date='+date).success(function(data) {
		$scope.balanceInfos.push(data.result);
	});
	
	$scope.filterWithDate = function(){
		$scope.balanceInfos = [];
		$http.get('Res?action=getBalanceInfo&date='+date).success(function(data) {
			$scope.balanceInfos.push(data.result);
		});
	}
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   
        $scope.reverse = !$scope.reverse; 
    }
	
}]);