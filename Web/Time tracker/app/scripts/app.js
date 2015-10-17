var app = angular.module('myApp', ['ngRoute']);
app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider){
	$routeProvider.
	when('/', {
		templateUrl: 'views/_login.html',
		controller: 'loginController',
		controllerAs:'login'
	}).
	when('/forgetpassword', {
		templateUrl: 'views/_forgotpassword.html',
		controller: 'forgotPasswordController',
	  controllerAs:'forgotPwd'
	}).
	when('/signup' ,{
		templateUrl: 'views/_signup.html',
		controller: 'signUpController',
		controllerAs: 'signUp'
  }).
	otherwise({redirectTo: '/'});
	$locationProvider.html5Mode(true);
}]);