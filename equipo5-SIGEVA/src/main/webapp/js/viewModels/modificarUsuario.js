define(['knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery'], function(ko, app, moduleUtils, accUtils, $) {


		class ModUserViewModel {
			constructor() {
				var self = this;

				self.paciente = ko.observable();
				self.paciente = app.paciente;
				self.nombre = ko.observable(self.paciente.nombre);
				self.apellidos = ko.observable(self.paciente.apellidos);
				self.dni = ko.observable(self.paciente.dni);
				self.tipoUsuario = ko.observable("");
				self.centroAsignado = ko.observable("");
				self.dosisAdministradas = ko.observable(self.paciente.dosisAdministradas);
				self.localidad = ko.observable(self.paciente.localidad);
				self.provincia = ko.observable(self.paciente.provincia);


				self.mensaje = ko.observable(2);
				self.mostrarSolicitarCita = ko.observable(1);

				self.message = ko.observable(null);
				self.error = ko.observable(null);

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
					url: "paciente/getCentros",
					type: "get",
					contentType: 'application/json',
					success: function(response) {
						self.centroAsignado(response);
					},
					error: function(response) {
						self.error(response.responseJSON.errorMessage);
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
					apellidos: this.apellidos(),
					dni: this.dni(),
					tipoUsuario: key2,
					centroAsignado: key3.options[key3.selectedIndex].value,
					dosisAdministradas: this.dosisAdministradas(),
					localidad: this.localidad(),
					provincia: this.provincia(),

				};
				let data = {
					data: JSON.stringify(info),
					url: "paciente/modificarUsuarios",
					type: "post",
					contentType: 'application/json',
					success: function(response) {
						self.error("");
						self.message("Usuario modificado");
					},
					error: function(response) {
						self.message("");
						self.error(response.responseJSON.errorMessage);
					}
				};
				$.ajax(data);
			}


			connected() {
				accUtils.announce('Inicio page loaded.');
				document.title = "Inicio";
				this.getCentros();
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
