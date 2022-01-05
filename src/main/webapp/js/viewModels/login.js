define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class InicioViewModel {
		constructor() {
			var self = this;
			
			self.email = ko.observable("crisAdministrador@sigeva.com");
			self.password = ko.observable("Hola1236=");
			self.dni = ko.observable("");		
			
			self.message = ko.observable();
			self.error = ko.observable();
			
			// Header Config
			self.headerConfig = ko.observable({
				'view' : [],
				'viewModel' : null
			});
			moduleUtils.createView({
				'viewPath' : 'views/header.html'
			}).then(function(view) {
				self.headerConfig({
					'view' : view,
					'viewModel' : app.getHeaderModel()
				})
			})
		}	
		
		login() {
			var self = this;
			var info = {
				email : this.email(),
				password : this.password()
			};
			var data = {
				data : JSON.stringify(info),
				url : "login/login",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					
					
					if(response.tipoUsuario=="Paciente"){
						app.user = response;
						app.navDataMenu.push({ path: 'home', detail : { label : 'Home'} });  
						app.router.go( { path : "homePaciente"} );
						
					}else if(response.tipoUsuario=="Sanitario"){
						app.navDataMenu.push({ path: 'home', detail : { label : 'Home'} });  
						app.router.go( { path : "homeSanitario"} );	
					}else{
						app.navDataMenu.push({ path: 'home', detail : { label : 'Home'} });  
						app.router.go( { path : "homeAdmin"} );	
					}	
					console.log(response);
				},
				error : function(response) {
					$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
				}
			};
			$.ajax(data);
		}

		
		quienesSomos() {
				app.router.go({ path: "quienesSomos" });
			}

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "SIGEVA - Iniciar sesión";
		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return InicioViewModel;
});
