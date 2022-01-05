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
		
		getUsuarios() {
			let self = this;
			let data = {
				url : "Usuario/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
				console.log(response);
					self.usuarios([]);
					 for (let i=0; i<response.length; i++) {
						let paciente = {
							nombre : response[i].nombre,
							email : response[i].email,
							password : response[i].password,
							dni : response[i].dni,
							tipoUsuario : response[i].tipoUsuario,						
							centroAsignado : response[i].centroAsignado,
							dosisAdministradas : response[i].dosisAdministradas,
							localidad : response[i].localidad,
							provincia : response[i].provincia,
							eliminar : function() {
								self.eliminarUsuario(response[i].email); 
							},
							modificarUsuarios : function() {
								app.paciente = this;
								app.router.go({ path: "modificarUsuario" });
							},						
						};
						self.usuarios.push(paciente);
					}
				},
				error : function(response) {
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

		eliminarUsuario(email) {
			let self = this;
			console.log("Hey");
			let data = {
				url : "Usuario/eliminarUsuario/" + email,
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					$.confirm({
						title: 'Confirmado',
						content: 'Usuario eliminado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							}
						}
					});
					self.getUsuarios();
				},
				error : function(response) {
					$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
					
					
				}
			};
			$.confirm({
				title: 'Eliminar',
				content: '¿Seguro que desea eliminar?',
				buttons: {
					Confirmar: function () {
						$.ajax(data);
					},
					Cancelar: function () {
					},
				}
			});
				
		}
		
		modificarUsuario(){
			app.router.go({ path: "modificarUsuario" });
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
						self.error(response.responseJSON.errorMessage);
						
					}
				};
				$.ajax(data);
			}	

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Gestion Usuarios";
			this.getUsuarios();
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
