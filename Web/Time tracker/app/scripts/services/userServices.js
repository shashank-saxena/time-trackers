app.factory("userServices", ['$http', '$q', function($http, $q) {
	var userJsonData = [];
	return {
		authUser: function() {
			userJsonData = ({firstname:"Varsha", lastname:"Tyagi", doj: '1382812200000'});
			return userJsonData;
		},
		
		getProperty: function() {
      		return userJsonData;
   		},

		registerUser: function(userObj) {
			var q = $q.defer();
			$http({ 
				method: 'POST', 
				url: 'http://time-tracker-backend-app.herokuapp.com/Tracker/rest/employee/register',
		      	data: userObj,
		      	crossDomain:true,
		      	withCredentials: true,
		      	headers : {
			    	 'Content-Type': 'application/x-www-form-urlencoded'
			    }
			}).success(function(response){
				console.log(response);
				q.resolve(response);
			}).
			error(function(response){
				q.reject(response);
			});
			return q.promise;
		},
	 	
		forgotPassword: function(email) {
			var q = $q.defer();
			$http({ 
				method: 'POST', 
				url: 'http://time-tracker-backend-app.herokuapp.com/Tracker/rest/employee/register',
		      	data: email,
		      	crossDomain:true,
		      	withCredentials: true,
		      	headers : {
			    	 'Content-Type': 'application/x-www-form-urlencoded'
				    }
				}).success(function(response){
					console.log(response);
					q.resolve(response);
				}).
				error(function(response){
					q.reject(response);
				});
				return q.promise;
	 	}
	};

}]);