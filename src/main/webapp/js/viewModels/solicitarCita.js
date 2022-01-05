define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class InicioViewModel {
		constructor() {
			var self = this;

			self.mostrarsolicitarCita = ko.observable(1);
			self.mostrarverCitas = ko.observable(1);
			self.mostrarMensaje = ko.observable(2);
			self.mostrarGestionUsuario = ko.observable(1);
			self.mostrarModificacionUsuario= ko.observable(1);
			self.botonCita = ko.observable(1);
			self.botonFecha = ko.observable(2);
			self.usuarios = ko.observableArray([]);
			self.nombreUsuario = ko.observable("");
			self.apellidos = ko.observable("");
			self.dni = ko.observable("");
			self.tipoUsuario = ko.observable("");
			self.centroAsignado = ko.observable("");
			self.dosisAdministradas = ko.observable("");
			self.localidad = ko.observable("");
			self.provincia = ko.observable("");	
			
				


			self.dniUsuario = ko.observable("");
			self.mensaje= ko.observable(2);
			self.mostrarSolicitarCita = ko.observable(1);

			self.message = ko.observable();
			self.error = ko.observable();

			self.cita = ko.observable("");
			self.fechaPrimeraDosis = ko.observable("");
			self.fechaSegundaDosis = ko.observable("");
			self.centrosSanitarios = ko.observableArray([]);

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

		
		
		//Gestionar este metodo para que se selecciona el centro de la bbdd
		solicitarCita() {
			let self = this;
			
			var time = new Date().getTime();
			var key3 = document.getElementById("menuCentros");
			
			let info = {
					centroAsignado: key3.options[key3.selectedIndex].value,
					dni : document.getElementById("dniUsuario").value,
			};
			
			let data = {
					
					data: JSON.stringify(info),
					url : "cita/add",
					type : "put",
					contentType : 'application/json',
					success : function(response) {

						var date = new Date (response.fechaPrimeraDosis)
						date.toLocaleString();
						var date2 = new Date (response.fechaSegundaDosis)
						date2.toLocaleString();

						self.fechaPrimeraDosis(date.toLocaleString());
						self.fechaSegundaDosis(date2.toLocaleString());
						self.centroAsignado(key3.options[key3.selectedIndex].value);
						self.error(undefined);
						self.message("Cita Creada");	
						self.botonCita(2);
						self.botonFecha(1);
					},
					error : function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						self.message(undefined);
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
						self.centrosSanitarios(response);
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						self.error(undefined);
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
						self.error("");
						self.nombreUsuario(response[0]);
						self.tipoUsuario(response[1]);
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						self.error(undefined);
					}
				};
				$.ajax(data);
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
					url: "login/comprobarRolPaciente",
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

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Inicio";
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

	return InicioViewModel;
});
