app.service('paymentInfoService', function() {
	  var paymentMethod = 0;
	  
	  var paymentCategory;
	  
	  var materialCategories = [];
	  
	  var materialCategory;
	  
	  var paymentAmount = 0;
	  
	  var paymentBill;
	  
	  var paymentTypes = [];
	  
	  var paymentCategories = [];
	  
	  var addPaymentTypes = function(newObj) {
		  paymentTypes = newObj;
	  };

	  var getPaymentTypes = function(){
	      return paymentTypes;
	  };
	  
	  var addPaymentCategories = function(newObj) {
		  paymentCategories = newObj;
	  };

	  var getPaymentCategories = function(){
	      return paymentCategories;
	  };
	  
	  var addMaterialCategories = function(newObj) {
		  materialCategories = newObj;
	  };

	  var getMaterialCategories = function(){
	      return materialCategories;
	  };

	  var addPaymentMethod = function(newObj) {
		  paymentMethod = newObj;
	  };

	  var getPaymentMethod = function(){
	      return paymentMethod;
	  };

	  var addMaterialCategory = function(newObj) {
		  materialCategory = newObj;
	  };

	  var getMaterialCategory = function(){
	      return materialCategory;
	  };
	  
	  var addPaymentAmount = function(newObj) {
		  paymentAmount = newObj;
	  };

	  var getPaymentAmount = function(){
	      return paymentAmount;
	  };
	  
	  var addPaymentBill = function(newObj) {
		  paymentBill = newObj;
	  };

	  var getPaymentBill = function(){
	      return paymentBill;
	  };
	  
	  var getPaymentCategory = function(){
	      return paymentCategory;
	  };
	  
	  var addPaymentCategory = function(newObj) {
		  paymentCategory = newObj;
	  };
	  
	  var addPaymentDate = function(newObj) {
		  paymentDate = newObj;
	  };

	  var getPaymentDate = function(){
	      return paymentDate;
	  };
	  
	  return {
		  addPaymentMethod: addPaymentMethod,
		  getPaymentMethod: getPaymentMethod,
		  addPaymentCategory: addPaymentCategory,
		  getPaymentCategory: getPaymentCategory,
		  addPaymentTypes: addPaymentTypes,
		  getPaymentTypes: getPaymentTypes,
		  addPaymentCategories: addPaymentCategories,
		  getPaymentCategories: getPaymentCategories,
		  addMaterialCategories: addMaterialCategories,
		  getMaterialCategories: getMaterialCategories,
		  addMaterialCategory: addMaterialCategory,
		  getMaterialCategory: getMaterialCategory,
		  addPaymentDate: addPaymentDate,
		  addPaymentBill: addPaymentBill,
		  addPaymentAmount: addPaymentAmount,
		  getPaymentDate: getPaymentDate,
		  getPaymentBill: getPaymentBill,
		  getPaymentAmount: getPaymentAmount
	  };

});

app.controller("PaymentInfoController", ["$scope","$http","$filter", "$mdDialog","$rootScope","paymentInfoService", function ($scope, $http, $filter,$mdDialog,$rootScope,paymentInfoService) {
	
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
	
	$scope.totalDebitAmount = 0;
	$scope.totalPaymentAmount = 0;
	
	$http.get('Res?action=getAllPaymentInfos&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
		$scope.paymentInfos = data.result;
		for(var i=0;i<$scope.paymentInfos.length;i++){
			$scope.totalDebitAmount += ($scope.paymentInfos[i].paymentAmount);
		}
	});
	
	$http.get('Res?action=getDebitAmountBasedOnPaymentType&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
		$scope.paymentTypes = data.result;
		for(var i=0;i<$scope.paymentTypes.length;i++){
			$scope.totalPaymentAmount += ($scope.paymentTypes[i].amount);
		}
	});
	
	$http.get('Res?action=getAllPaymentTypes').success(function(data) {
		paymentInfoService.addPaymentTypes(data.result);
	});
	
	$http.get('Res?action=getAllPaymentCategories').success(function(data) {
		paymentInfoService.addPaymentCategories(data.result);
	});
	
	$http.get('Res?action=getAllMaterialCategories').success(function(data) {
		paymentInfoService.addMaterialCategories(data.result);
	});
	
	$scope.filterWithDate = function(){
		$http.get('Res?action=getAllPaymentInfos&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
			$scope.paymentInfos = data.result;
			$scope.totalDebitAmount = 0;
			for(var i=0;i<$scope.paymentInfos.length;i++){
				$scope.totalDebitAmount += ($scope.paymentInfos[i].paymentAmount);
			}
		});
		
		$http.get('Res?action=getDebitAmountBasedOnPaymentType&date1='+dateFrom+'&date2='+dateTo).success(function(data) {
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
	
	$scope.paymentInfoJson = {};
	$scope.paymentInfoJson.user = $rootScope.globals.currentUser;
	
	$scope.openPaymentInfoDialog = function(ev) {
        $mdDialog.show({
          controller: DialogController,
          templateUrl: 'templates/payment_info_dialog.html',
          //scope:$scope,
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose:true,
          fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
        .then(function(answer) {
          $scope.paymentInfoJson.paymentType = paymentInfoService.getPaymentMethod();
          $scope.paymentInfoJson.paymentCategory = paymentInfoService.getPaymentCategory();
          $scope.paymentInfoJson.materialCategory = paymentInfoService.getMaterialCategory();
          //$scope.paymentInfoJson.user.userId = 1;
          //$scope.paymentInfoJson.user.userName = "Sai kiran";
          $scope.paymentInfoJson.paymentAmount = paymentInfoService.getPaymentAmount();
          $scope.paymentInfoJson.paymentDate = paymentInfoService.getPaymentDate();
          $scope.paymentInfoJson.paymentBill = paymentInfoService.getPaymentBill();
          var df = new Date();
          var y = df.getFullYear();
          var m = df.getMonth()+1;
          var d = df.getDate();
          
      	var dateFrom = y+"-"+m+"-"+d;
      	$scope.paymentInfoJson.paymentDate = dateFrom;
          $scope.addPaymentInfo();
          $scope.paymentInfos.push($scope.paymentInfoJson);
        }, function() {
        });
    };
      
    function DialogController($scope, $mdDialog,paymentInfoService) {
  	  	$scope.paymentTypes = paymentInfoService.getPaymentTypes();
  	  	$scope.paymentCategories = paymentInfoService.getPaymentCategories();
  	    $scope.materialCategories = paymentInfoService.getMaterialCategories();
  	    $scope.hide = function() {
  	      $mdDialog.hide();
  	    };

  	    $scope.cancel = function() {
  	      $mdDialog.cancel();
  	    };

  	    $scope.answer = function() {
  	      paymentInfoService.addPaymentMethod($scope.paymentType);
  	      paymentInfoService.addPaymentCategory($scope.paymentCategory);
  	      paymentInfoService.addMaterialCategory($scope.materialCategory);
  	      paymentInfoService.addPaymentAmount($scope.paymentAmount);
  	      paymentInfoService.addPaymentDate($scope.paymentDate);
  	      paymentInfoService.addPaymentBill($scope.paymentBill);
  	      
  	      $mdDialog.hide();
  	    };
    }
    
    $scope.addPaymentInfo = function(){    	  
  	  $http({
            url : 'Res?action=addPaymentInfo',
            method : "POST",
            data : {data: $scope.paymentInfoJson},
            headers: {
                'Content-Type': 'application/json'
            }
            /*data : {
                'name' : $scope.name
            }*/
        }).then(function(response) {
            alert(response.data);
           /* $scope.itemOrder = {};
      	  $scope.itemOrder.orderedItems = [];
      	  $scope.itemOrder.paymentType = {};
      	  $scope.itemOrder.totalAmount = 0;*/
        }, function(response) {
        });
    }
	
}]);