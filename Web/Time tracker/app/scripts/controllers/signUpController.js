app.controller('signUpController', ['$scope', function($scope){
	$('#dob').datepick();//dateofbirth datepicker
	$('#doj').datepick();//dateofjoining datepicker		
	$scope.checkPassword = function () {
		$scope.signup.confirmpassword.$error.dontMatch = $scope.signup.password !== $scope.signup.confirmpassword;
	};
}]);