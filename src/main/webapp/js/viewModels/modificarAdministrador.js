define(['knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery'], function(ko, app, moduleUtils, accUtils, $) {


		class ModUserViewModel {
			constructor() {
				var self = this;

				self.administrador = ko.observable();
				self.administrador = app.administrador;
				self.nombre = ko.observable(self.administrador.nombre);
				self.email = ko.observable(self.administrador.email);
				self.dni = ko.observable(self.administrador.dni);
				self.password = ko.observable("");


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


			modificarAdministrador() {
				var self = this;
				
				var key3 = document.getElementById("menuCentros");
				let info = {
					nombre: this.nombre(),
					email: this.email(),
					dni: this.dni(),
					password: this.password(),
					
				};
				let data = {
					data: JSON.stringify(info),
					url: "administrador/modificarAdministrador/"+app.administrador.dni,
					type: "post",
					contentType: 'application/json',
					success: function(response) {
						
						$.confirm({
							title: 'Confirmado',
							content: 'Administrador modificado',
							type: 'green',
							typeAnimated: true,
							buttons: {
								Cerrar: function () {
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


			connected() {
				accUtils.announce('Inicio page loaded.');
				document.title = "Inicio";
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
