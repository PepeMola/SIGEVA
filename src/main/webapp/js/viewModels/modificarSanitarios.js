define(['knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery'], function(ko, app, moduleUtils, accUtils, $) {


		class ModUserViewModel {
			constructor() {
				var self = this;

				self.sanitario = ko.observable();
				self.sanitario = app.sanitario;
				self.nombre = ko.observable(self.sanitario.nombre);
				self.email = ko.observable(self.sanitario.email);
				self.dni = ko.observable(self.sanitario.dni);
				self.password = ko.observable("");
				self.centroAsignado = ko.observable("");


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
					url: "sanitario/getCentros",
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

			modificarSanitarios() {
				var self = this;
				
				var key3 = document.getElementById("menuCentros");
				let info = {
					nombre: this.nombre(),
					email: this.email(),
					dni: this.dni(),
					password: this.password(),
					centroAsignado: key3.options[key3.selectedIndex].value,
					
				};
				let data = {
					data: JSON.stringify(info),
					url: "sanitario/modificarSanitarios/"+app.sanitario.dni,
					type: "post",
					contentType: 'application/json',
					success: function(response) {
						
						$.confirm({
							title: 'Confirmado',
							content: 'Sanitario modificado',
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
