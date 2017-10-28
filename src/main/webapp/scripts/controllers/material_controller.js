app.controller("MaterialController", ["$scope","$http","$mdDialog", function ($scope, $http, $mdDialog) {
	
	$http.get('Res?action=getAllMaterials').success(function(data) {
		$scope.materials = data.result;
	});
	
	$scope.materialOrder = {};
	
	$scope.materialOrder.orderedMaterials = [];
	
	$scope.materialOrder.totalAmount = 0;
	
	$scope.updateTotalCost = function(){
		$scope.materialOrder.totalAmount = 0;
		for(var i=0;i<$scope.materialOrder.orderedMaterials.length;i++){
			$scope.materialOrder.totalAmount=$scope.materialOrder.totalAmount + ($scope.materialOrder.orderedMaterials[i].quantity*$scope.materialOrder.orderedMaterials[i].material.packCost);
		}
	}
	
	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   
        $scope.reverse = !$scope.reverse; 
    }
	
	$scope.addToMaterialOrder = function (material) {
			var orderedMaterials = $scope.materialOrder.orderedMaterials;
			var isOrdered = false;
			for(var i=0;i<orderedMaterials.length;i++){
				if(orderedMaterials[i].material.materialId == material.materialId){
					$scope.materialOrder.orderedMaterials[i].quantity++;
					$scope.materialOrder.totalAmount += parseInt(material.packCost);
					isOrdered = true;
					break;
				}
			}
			if(!isOrdered){
	            var materialp = {};
	            materialp.material = material;
	            materialp.quantity = 1;
	            $scope.materialOrder.totalAmount += parseInt(material.packCost);
	            $scope.materialOrder.orderedMaterials.push(materialp);
			}
    };
    
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
    $scope.materialOrderUp = function(){
  	  
  	  $http({
            url : 'Res?action=placeMaterialOrder',
            method : "POST",
            data : {data: $scope.materialOrder},
            headers: {
                'Content-Type': 'application/json'
            }
            /*data : {
                'name' : $scope.name
            }*/
        }).then(function(response) {
            alert(response.data);
            $scope.materialOrder = {};
      	  $scope.materialOrder.orderedMaterials = [];
      	  $scope.materialOrder.totalAmount = 0;
        }, function(response) {
        });
    }
	
}]);