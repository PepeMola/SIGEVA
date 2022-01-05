define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class InicioViewModel {
		constructor() {
			var self = this;
			
			self.sanitarios = ko.observableArray([]);
			self.nombre = ko.observable("");
			self.email = ko.observable("");
			self.dni = ko.observable("");
			self.password = ko.observable("");	
			self.centroAsignado = ko.observable("");	
			

			
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
			
		getSanitarios() {
			let self = this;
			let data = {
				url : "sanitario/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.sanitarios([]);
					 for (let i=0; i<response.length; i++) {
						let sanitario = {
							nombre : response[i].nombre,
							email: response[i].email,
							dni : response[i].dni,
							password : response[i].password,
							centroAsignado : response[i].centroAsignado,
							eliminar : function() {
								self.eliminarSanitario(response[i].dni); 
							},
							modificarSanitarios : function() {
								app.sanitario = this;
								app.router.go({ path: "modificarSanitarios" });
							},					
						};
						self.sanitarios.push(sanitario);
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
		
		

		eliminarSanitario(dni) {
			let self = this;
			let data = {
				url : "sanitario/eliminarSanitario/" + dni,
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					
					$.confirm({
						title: 'Confirmado',
						content: 'Sanitario eliminado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							}
						}
					});
					self.getSanitarios();
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
			
		add() {
			var self = this;
			var key3 = document.getElementById("menuCentros");
			let info = {
				nombre : this.nombre(),
				email: this.email(),
				dni : this.dni(),
				password: this.password(),
				centroAsignado: key3.options[key3.selectedIndex].value,
			};
			
			let data = {
				data : JSON.stringify(info),
				url : "sanitario/add",
				type : "put",
				contentType : 'application/json',
				success : function(response) {
					
					$.confirm({
						title: 'Confirmado',
						content: 'Sanitario guardado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							}
						}
					});
					self.getSanitarios();
					
				},
				error : function(response) {
					$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
					
				}
			};
			$.ajax(data);
		}	

		modificarSanitarios(dniUsuario){
			app.idc = dniUsuario;
			app.sanitario = this;
			app.router.go({ path: "modificarSanitarios" });
		}	
		
		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Inicio";
			this.getSanitarios();
			this.getCentros();
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
