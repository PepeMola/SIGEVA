define(['knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery'], function(ko, app, moduleUtils, accUtils, $) {


		class ModUserViewModel {
			constructor() {
				var self = this;

				self.paciente = ko.observable();
				self.paciente = app.paciente;
				self.nombre = ko.observable(self.paciente.nombre);
				self.email = ko.observable(self.paciente.email);
				self.dni = ko.observable(self.paciente.dni);
				self.tipoUsuario = ko.observable("");
				self.centroAsignado = ko.observable("");
				self.password = ko.observable("");
				self.dosisAdministradas = ko.observable(self.paciente.dosisAdministradas);
				self.localidad = ko.observable(self.paciente.localidad);
				self.provincia = ko.observable(self.paciente.provincia);
				self.puedeEditarCentro = ko.observable((self.dosisAdministradas() == "0") || (self.dosisAdministradas() == undefined));
				self.nombreUsuario = ko.observable("");
				self.mensaje = ko.observable(2);
				self.mostrarSolicitarCita = ko.observable(1);

				self.message = ko.observable();
				self.error = ko.observable();

				// Header Config
				self.headerConfig = ko.observable({
					'view': [],
					'viewModel': null
				});
				moduleUtils.createView({
					'viewPath': 'views/header.html'
				}).then(function(view) {
					self.headerConfig({
						'view': view,
						'viewModel': app.getHeaderModel()
					})
				})
			}

			enable() {
				document.getElementById("menuCentros").disabled = false;
			}

			disable() {
				document.getElementById("menuCentros").disabled = true;
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

			modificarUsuario() {
				var self = this;
				var key = document.getElementsByName("tipoUsuario");
				for (var i = 0; i < key.length; i++) {
					if (key[i].checked)
						var key2 = key[i].value;
				}
				var key3 = document.getElementById("menuCentros");
				let info = {
					nombre: this.nombre(),
					email: this.email(),
					dni: this.dni(),
					password: this.password(),
					tipoUsuario: key2,
					centroAsignado: key3.options[key3.selectedIndex].value,
					dosisAdministradas: this.dosisAdministradas(),
					localidad: this.localidad(),
					provincia: this.provincia(),

				};
				let data = {
					data: JSON.stringify(info),
					url: "Usuario/modificarUsuarios",
					type: "post",
					contentType: 'application/json',
					success: function(response) {
						
						$.confirm({
							title: 'Usuario modificado',
							content: 'Se le enviarán los nuevos datos al correo',
							type: 'green',
							typeAnimated: true,
							buttons: {
								Cerrar: function () {
									app.router.go({ path: "gestionUsuarios" });
								}
							}
						});
					},
					error: function(response) {
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
			
			cogerTipoUsuario() {
				let self = this;
				let data = {
					url: "Usuario/cogerTipoUsuario/" + this.paciente.email,
					type: "get",
					contentType: 'application/json',
					success: function(response) {		
						console.log(response);
						if(response=="Sanitario" || response=="Administrador"){
							document.getElementById("dosis").style.display='none';
							document.getElementById("localidad").style.display='none';
							document.getElementById("provincia").style.display='none';
							document.getElementById("Dosis").style.display='none';
							document.getElementById("Localidad").style.display='none';
							document.getElementById("Provincia").style.display='none';
						}
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
		
		paginaInicio() {
				app.router.go({ path: "homeAdmin" });
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


			connected() {
				accUtils.announce('Inicio page loaded.');
				document.title = "Inicio";
				this.getCentros();
				this.getUserConnect();
				this.cogerTipoUsuario();
			};

			disconnected() {
				// Implement if needed
			};

			transitionCompleted() {
				// Implement if needed
			};
		}

		return ModUserViewModel;
	});
