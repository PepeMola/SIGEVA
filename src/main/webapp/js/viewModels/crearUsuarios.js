define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class GestionUserViewModel {
		constructor() {
			var self = this;
			
			self.usuarios = ko.observableArray([]);
			self.nombre = ko.observable("");
			self.email = ko.observable("");
			self.dni = ko.observable("");
			self.tipoUsuario = ko.observable("");
			self.password = ko.observable("");
			self.password2 = ko.observable("");
			self.centroAsignado = ko.observable("");
			self.dosisAdministradas = ko.observable("");
			self.localidad = ko.observable("");
			self.provincia = ko.observable("");		
			

			self.nombreUsuario = ko.observable("");
			
			

			
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
		
		
		enable() {
				document.getElementById("menuCentros").disabled = false;
			}

			disable() {
				document.getElementById("menuCentros").disabled = true;
			}
		
		gestionUsuarios() {
				app.router.go({ path: "gestionUsuarios" });
			}

			crearUsuarios() {
				app.router.go({ path: "crearUsuarios" });
			}

			gestionCentros() {
				app.router.go({ path: "gestionCentros" });
			}

			crearCentros() {
				app.router.go({ path: "crearCentros" });
			}
			
			paginaInicio() {
				app.router.go({ path: "homeAdmin" });
			}
			
			gestionCitas() {
				app.router.go({ path: "verCitas" });
			}
			
			crearCita() {
				app.router.go({ path: "solicitarCita" });
			}
			
			comprobarRol() {	
				let self = this;
				let data = {
					url: "login/comprobarRolAdmin",
					type: "get",
					contentType: 'application/json',
					success: function(response) {
						if(response=="denegado"){
							app.router.go( { path : "login"} );
						}
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						
					}
				};
				$.ajax(data);
		}
		
		
		getCentros() {
				let self = this;
				let data = {
					url: "Usuario/getCentros",
					type: "get",
					contentType: 'application/json',
					success: function(response) {
						self.centroAsignado(response);
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						
					}
				};
				$.ajax(data);
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
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						
					}
			};
			$.ajax(data);
		}

		add() {
			var self = this;
			var key = document.getElementsByName("tipoUsuario");
				for (var i = 0; i < key.length; i++) {
					if (key[i].checked)
						var key2= key[i].value;
				}
			var key3 = document.getElementById("menuCentros");
			let info = {
				nombre : this.nombre(),
				email : this.email(),
				dni : this.dni(),
				tipoUsuario: key2,
				password : this.password(),
				password2 : this.password2(),
				centroAsignado: key3.options[key3.selectedIndex].value,
				dosisAdministradas: this.dosisAdministradas(),
				localidad: this.localidad(),
				provincia: this.provincia(),
				
			};
			let data = {
				data : JSON.stringify(info),
				url : "Usuario/add",
				type : "put",
				contentType : 'application/json',
				success : function(response) {
					
					$.confirm({
						title: 'Confirmado',
						content: 'Usuario guardado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							}
						}
					});
					
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
						
						self.nombreUsuario(response[0]);
						self.tipoUsuario(response[1]);
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						
					}
				};
				$.ajax(data);
			}	

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Crear Usuario";
			this.comprobarRol();
			this.getCentros();
			this.getUserConnect();
		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return GestionUserViewModel;
});
