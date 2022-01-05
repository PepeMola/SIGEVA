define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class ModCenterViewModel {
		constructor() {
			var self = this;

			self.centro = ko.observable();
			self.centro = app.centro;

			self.nombre = ko.observable(app.centro.nombre);
			self.dosisTotales = ko.observable(app.centro.dosisTotales);
			self.aforo = ko.observable(app.centro.aforo);
			self.horaInicio = ko.observable(app.centro.horaInicio);
			self.horaFin = ko.observable(app.centro.horaFin);
			self.localidad = ko.observable(app.centro.localidad);
			self.provincia = ko.observable(app.centro.provincia);		

			self.nombreUsuario = ko.observable("");
			self.tipoUsuario = ko.observable("");

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

		modificarCentro() {
			console.log("Llego con centro " + app.centro);
			let self = this;

			let info = {
					id : app.centro.id,
					nombre : this.nombre(),
					dosisTotales: this.dosisTotales(),
					aforo : this.aforo(),
					horaInicio : this.horaInicio(),
					horaFin : this.horaFin(),
					localidad: this.localidad(),
					provincia: this.provincia(),
			};

			let data = {
					data : JSON.stringify(info),
					url : "centro/modificarCentro/" + app.centro.id,
					type : "put",
					contentType : 'application/json',
					success : function(response) {
						app.router.go({ path: "gestionCentros" });
						
						$.confirm({
							title: 'Confirmado',
							content: 'Centro modificado',
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

	return ModCenterViewModel;
});
