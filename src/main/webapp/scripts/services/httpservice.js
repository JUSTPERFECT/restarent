app.factory('HttpService',
    [ '$http', '$cookieStore', '$rootScope', '$timeout',
    function ($http, $cookieStore, $rootScope, $timeout) {
    	 var service = {};
    	 var async=false;
    	 service.httpPost= function (url,model, callback,async) {
    		 if(async){
    			 async=true;
    		 }
    		 $timeout(function(){
             	$http({
             	    method : 'POST',
             	    url : url,
             	    data : model,
             	   async:async
             	   
             	}).then(function(data) {
             		data.success=true;
             		callback(data);
             	    //console.log(data);
             	},function(data){
             		console.log(data);
             	});
                /* var response = { success: username === 'test' && password === 'test' };
                 if(!response.success) {
                     response.message = 'Username or password is incorrect';
                 }*/
                 
             }, 1000);
    	 }
    	 
     	 service.httpGet= function (url,model, callback) {
    		 $timeout(function(){
             	$http({
             	    method : 'GET',
             	    url : url,
             	    data : model
             	   
             	}).then(function(data) {
             		data.success=true;
             		callback(data);
             	    //console.log(data);
             	},function(data){
             		console.log(data);
             	});
                 
             }, 1000);
    	 }
    	 return service;
    }]);