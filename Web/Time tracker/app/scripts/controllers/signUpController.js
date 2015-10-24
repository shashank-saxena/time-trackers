app.controller('signUpController', ['$scope', 'signupUser', function($scope, signupUser){	
	$scope.errorMessage = null;
	//user signup
	this.userSignUp = function() {
		var userReg = $scope.user;
		var age=$scope.calculateAge(userReg.dateofbirth);
		if(age<18) {
			$scope.errorMessage = "Opps ! Age should be 18 above you can't Register";
			return ;
		}
		resetForm();
		
		var dataPromise = userServices.registerUser($scope.user);
				dataPromise.then(function(response) {
					$scope.user = response;  console.log('service rseult'+response);
				},function(error) {
					//$scope.status = error;  
					$scope.errorMessage = error;
				});
		this.resetForm();
		
	};
	
	$scope.compareDate = function(user) {
		var dob = $scope.user.dateofbirth;
		var doj = $scope.user.dateofjoining;
		resetForm();
		if(new Date(dob)>new Date(doj)) {
			 $scope.errorMessage = " Opps ! Date of Birth should be Less than Date of Joining";
			 return ;
		}		
	};
	
	$scope.calculateAge = function(dateofbirth)
	{
		var dob = new Date(dateofbirth);
		var birth_year = dob.getFullYear();
		var birth_month = dob.getMonth();
	    var birth_day = dob.getDate();
	    
	    today_date = new Date();
	    today_year = today_date.getFullYear();
	    today_month = today_date.getMonth();
	    today_day = today_date.getDate();
	    age = today_year - birth_year;
	
	    if ( today_month < (birth_month - 1))
	    {
	        age--;
	    }
	    if (((birth_month - 1) == today_month) && (today_day < birth_day))
	    {
	        age--;
	    }
	    return age;
	};
		
	$scope.open = function($event) {
    $event.preventDefault();
    $event.stopPropagation();
    $scope.opened = true;
  	};
	
	$scope.reset = function() { 
        $scope.user = "";
    };
    $scope.reset();
	
	resetForm = function(){
	    $scope.errorMessage = null;
	};	

}]);


