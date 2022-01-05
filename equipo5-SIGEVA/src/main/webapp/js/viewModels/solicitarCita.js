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
			self.nombre = ko.observable("");
			self.apellidos = ko.observable("");
			self.dni = ko.observable("");
			self.tipoUsuario = ko.observable("");
			self.centroAsignado = ko.observable("");
			self.dosisAdministradas = ko.observable("");
			self.localidad = ko.observable("");
			self.provincia = ko.observable("");		


			self.mensaje= ko.observable(2);
			self.mostrarSolicitarCita = ko.observable(1);

			self.message = ko.observable(null);
			self.error = ko.observable(null);

			self.cita = ko.observable("");
			self.fechaPrimeraDosis = ko.observable("");
			self.fechaSegundaDosis = ko.observable("");

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

		solicitarCita(){
			var self = this;
			self.mostrarsolicitarCita(2);
			self.mensaje(1);
			self.mostrarverCitas(1);
			self.mostrarGestionUsuario(1);
			self.mostrarModificacionUsuario(1);
		}

		verCitas(){
			var self = this;
			self.mostrarsolicitarCita(1);
			self.mensaje(1);
			self.mostrarverCitas(2);
			self.mostrarGestionUsuario(1);
			self.mostrarModificacionUsuario(1);
		}

		gestionUsuarios(){
			var self = this;
			self.mostrarsolicitarCita(1);
			self.mensaje(1);
			self.mostrarverCitas(1);
			self.mostrarGestionUsuario(2);
			self.mostrarModificacionUsuario(1);
		}

		modificarUsuarios(){
			var self = this;
			app.paciente = this;
			self.mostrarsolicitarCita(1);
			self.mensaje(1);
			self.mostrarverCitas(1);
			self.mostrarGestionUsuario(1);
			self.mostrarModificacionUsuario(2);
		}

		getUsuarios() {
			let self = this;
			let data = {
					url : "paciente/getTodos",
					type : "get",
					contentType : 'application/json',
					success : function(response) {
						self.usuarios([]);
						for (let i=0; i<response.length; i++) {
							let paciente = {
									nombre : response[i].nombre,
									apellidos: response[i].apellidos,
									dni : response[i].dni,
									tipoUsuario : response[i].tipoUsuario,
									centroAsignado : response[i].centroAsignado,
									dosisAdministradas : response[i].dosisAdministradas,
									localidad : response[i].localidad,
									provincia : response[i].provincia,
									eliminar : function() {
										self.eliminarUsuario(response[i].dni); 
									},
									modificarUsuarios : function() {
										app.paciente = this;
										self.modificarUsuarios();
										self.paciente = ko.observable();
										self.paciente = app.paciente;
										self.nombre2 = ko.observable(self.paciente.nombre);
									},						
							};
							self.usuarios.push(paciente);
						}
					},
					error : function(response) {
						self.error(response.responseJSON.errorMessage);
					}
			};
			$.ajax(data);
		}

		solicitarCita() {
			let self = this;
			var time = new Date().getTime();
			let data = {
					url : "cita/add",
					type : "put",
					contentType : 'application/json',
					success : function(response) {
						
						var date = new Date (response.fechaPrimeraDosis)
						date.toLocaleString();
						var date2 = new Date (response.fechaSegundaDosis)
						date2.toLocaleString();
						
						console.log(date.toLocaleString());
						console.log(date2.toLocaleString());
						
						self.fechaPrimeraDosis(date.toLocaleString());
						self.fechaSegundaDosis(date2.toLocaleString());
						self.message("Cita Creada");	
						self.botonCita(2);
						self.botonFecha(1);
					},
					error : function(response) {
						self.error(response.responseJSON.errorMessage);
					}
			};
			$.ajax(data);
		}

		eliminarUsuario(dni) {
			let self = this;
			console.log("Hey");
			let data = {
					url : "paciente/eliminarUsuario/" + dni,
					type : "delete",
					contentType : 'application/json',
					success : function(response) {
						self.message("Usuario eliminado ");
						self.getUsuarios();
					},
					error : function(response) {
						self.error(response.responseJSON.errorMessage);
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
			let info = {
					nombre : this.nombre(),
					apellidos: this.apellidos(),
					dni : this.dni(),
					tipoUsuario: key2,
					localidad: this.localidad(),
					provincia: this.provincia(),

			};
			let data = {
					data : JSON.stringify(info),
					url : "paciente/add",
					type : "put",
					contentType : 'application/json',
					success : function(response) {
						self.message("Usuario guardado");
						self.getUsuarios();
						self.error(null);
					},
					error : function(response) {
						self.error(response.responseJSON.errorMessage);
					}
			};
			$.ajax(data);
		}	

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Inicio";
			this.getUsuarios();
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
