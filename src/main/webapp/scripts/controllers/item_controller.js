/*var members = [
        { itemId: '1', itemName: 'Sweet Lassi', subcategory : 'LASSI', category: 'Normal'},
        { itemId: '2', itemName: 'Salt Lassi', subcategory: 'LASSI' , category: 'Normal'},
        { itemId: '3', itemName: 'Paan Lassi', subcategory: 'LASSI' , category: 'Normal'}
];*/

app.service('itemService', function() {
	  var totalAmount = 0;
	  var paymentMethods = [];
	  
	  var paymentTypes = [];
	  
	  var addPaymentTypes = function(newObj) {
		  paymentTypes = newObj;
	  };

	  var getPaymentTypes = function(){
	      return paymentTypes;
	  };

	  var addPaymentMethods = function(newObj) {
		  paymentMethods = newObj;
	  };

	  var getPaymentMethods = function(){
	      return paymentMethods;
	  };
	  
	  var setTotalAmount = function(amount){
		  totatlAmount = amount;
	  };
	  
	  var getTotalAmount = function(){
		  return totalAmount;
	  };

	  return {
		  addPaymentMethods: addPaymentMethods,
		  getPaymentMethods: getPaymentMethods,
		  addPaymentTypes: addPaymentTypes,
		  getPaymentTypes: getPaymentTypes,
		  setTotalAmount: setTotalAmount,
		  getTotalAmount: getTotalAmount
	  };

});

app.controller("ItemController", ["$scope","$http","$mdDialog","$rootScope", 'itemService',function ($scope, $http, $mdDialog, $rootScope, itemService) {
    //$scope.Members = members;
	$http.get('Res?action=getAllItems').success(function(data) {
		$scope.items = data.result;
	});
	
	$http.get('Res?action=getAllPaymentTypes').success(function(data) {
		$scope.paymentTypes = data.result;
		itemService.addPaymentTypes($scope.paymentTypes);
	});
	
	/*$http.get('js/order.json').success(function(data) {
		$scope.orders = data;
	});*/
	
	$scope.itemOrder = {};
	
	$scope.itemOrder.orderedItems = [];
	
	$scope.itemOrder.totalAmount = 0;
	
	$scope.itemOrder.paymentTypes = [];
	
	$scope.updateTotalCost = function(quantity,item){
		if(quantity==0){
			var idx = $scope.itemOrder.orderedItems.indexOf(item);
			if (idx !== -1) {
				$scope.itemOrder.orderedItems.splice(idx, 1);
		    }
		}
		
		$scope.itemOrder.totalAmount = 0;
		for(var i=0;i<$scope.itemOrder.orderedItems.length;i++){
			$scope.itemOrder.totalAmount=$scope.itemOrder.totalAmount + ($scope.itemOrder.orderedItems[i].quantity*$scope.itemOrder.orderedItems[i].item.price);
		}
	}
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;
        $scope.reverse = !$scope.reverse;
    }
	
	$scope.addToItemOrder = function (item) {
			var orderedItems = $scope.itemOrder.orderedItems;
			var isOrdered = false;
			for(var i=0;i<orderedItems.length;i++){
				if(orderedItems[i].item.itemId == item.itemId){
					$scope.itemOrder.orderedItems[i].quantity++;
					$scope.itemOrder.totalAmount += parseInt(item.price);
					isOrdered = true;
					break;
				}
			}
			if(!isOrdered){
	            var itemp = {};
	            itemp.item = item;
	            itemp.quantity = 1;
	            $scope.itemOrder.totalAmount += parseInt(item.price);
	            $scope.itemOrder.orderedItems.push(itemp);
			}
    };
    
    $scope.customFullscreen = false;
    
    $scope.openPaymentDialog = function(ev) {
    	itemService.setTotalAmount($scope.itemOrder.totalAmount);
        $mdDialog.show({
          controller: DialogController,
          templateUrl: 'templates/payment_type_dialog.html',
          //scope:$scope,
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose:true,
          fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
        .then(function(answer) {
        	var df = new Date();
            var y = df.getFullYear();
            var m = df.getMonth()+1;
            var d = df.getDate();
            
        	var dateFrom = y+"-"+m+"-"+d;
        	$scope.itemOrder.orderDate = dateFrom;
          $scope.itemOrder.paymentTypes = itemService.getPaymentMethods();
          $scope.itemOrderUp();
        }, function() {
        });
    };
      
    function DialogController($scope, $mdDialog,itemService) {
    	  	$scope.paymentTypes = itemService.getPaymentTypes();
    	  	$scope.totalAmount = itemService.getTotalAmount();
    	    $scope.hide = function() {
    	      $mdDialog.hide();
    	    };

    	    $scope.cancel = function() {
    	      $mdDialog.cancel();
    	    };
    	    
    	    $scope.selected = [];

    	      $scope.toggle = function (item, list) {
    	        var idx = list.indexOf(item);
    	        if (idx > -1) {
    	          list.splice(idx, 1);
    	        }
    	        else {
    	          list.push(item);
    	        }
    	      };


    	    $scope.exists = function (item, list) {
    	          return list.indexOf(item) > -1;
    	    };

    	    $scope.answer = function() {
    	      itemService.addPaymentMethods($scope.selected);
    	      $mdDialog.hide();
    	    };
    }
      
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
    $scope.itemOrderUp = function(){    	  
    	  $http({
              url : 'Res?action=placeItemOrder',
              method : "POST",
              data : {data: $scope.itemOrder},
              headers: {
                  'Content-Type': 'application/json'
              }
              /*data : {
                  'name' : $scope.name
              }*/
          }).then(function(response) {
              alert(response.data);
              $scope.itemOrder = {};
        	  $scope.itemOrder.orderedItems = [];
        	  $scope.itemOrder.paymentTypes = [];
        	  $scope.itemOrder.totalAmount = 0;
          }, function(response) {
          });
    }

}]);