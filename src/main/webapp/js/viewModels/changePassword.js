define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class InicioViewModel {
		constructor() {
			var self = this;
			
			self.citas = ko.observableArray([]);
			self.usuarios = ko.observableArray([]);
			self.nombre = ko.observable("");
			self.password = ko.observable("");
			self.password2 = ko.observable("");
			self.apellidos = ko.observable("");
			self.email = ko.observable("");
			self.dniPaciente = ko.observable("");
			self.tipoUsuario = ko.observable("");

			self.mensaje= ko.observable(2);
			self.mostrarSolicitarCita = ko.observable(1);
			
			
			
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
		

		
		
		
		verCitasSanitario() {
			console.log("holaa")
			app.router.go({ path: "verCitasSanitario" });
		}
			
		paginaInicio() {
			app.router.go({ path: "homeSanitario" });
		}
		
		
		
		logout() {
			let self = this;
			let data = {
				url : "login/logout",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					app.router.go( { path : "login" } );
				},
				error : function(response) {
				}
			};
			$.ajax(data);
		}
		
		cambiarPassword() {
			let self = this;
			let info = {
				password : this.password(),
				password2 : this.password2(),		
			};
			let data = {
				data : JSON.stringify(info),
				url : "login/cambiarPassword",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					app.router.go( { path : "login" } );
				},
				error : function(response) {
				$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
				}
			};
			$.ajax(data);
		}
		
		
		
			getUserConnect() {	
				let self = this;
				let data = {
					url: "login/getUser",
					type: "get",
					contentType: 'application/json',
					success: function(response) {
						self.nombre(response[0]);
						self.tipoUsuario(response[1]);
						self.email(response[2]);
					},
					error: function(response) {
					}
				};
				$.ajax(data);
		}
				
				
		
		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Inicio";
			this.getUserConnect();
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
