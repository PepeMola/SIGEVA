define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class GestionCenterViewModel {
		constructor() {
			var self = this;
			
			self.centros = ko.observableArray([]);
			self.id = ko.observable("");
			self.nombre = ko.observable("");
			self.horaInicio = ko.observable("");
			self.horaFin = ko.observable("");
			self.dosisTotales = ko.observable("");
			self.aforo = ko.observable("");
			self.localidad = ko.observable("");
			self.provincia = ko.observable("");
					
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
		
		getCentros() {
			let self = this;
			let data = {
				url : "centro/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.centros([]);
					 for (let i=0; i<response.length; i++) {
						let centro = {
							id : response[i].id,
							nombre : response[i].nombre,
							dosisTotales: response[i].dosisTotales,
							aforo : response[i].aforo,
							horaInicio : response[i].horaInicio,
							horaFin : response[i].horaFin,
							localidad : response[i].localidad,
							provincia : response[i].provincia,
							eliminar : function() {
								self.eliminarUsuario(response[i].dni); 
							},
							
							modificarCentros : function() {
								app.centro = this;
								app.router.go({ path: "modificarCentro" });
							},
												
						};
						
						self.centros.push(centro);
					}
					if(response.length>0){
						document.getElementById("horaInicio").style.display='none';
						document.getElementById("aforo").style.display='none';
						document.getElementById("horaFin").style.display='none';
					}else{
						document.getElementById("horaInicio").style.display='inline';
						document.getElementById("aforo").style.display='inline';
						document.getElementById("horaFin").style.display='inline';
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

		eliminarCentro(id) {
			let self = this;
			let data = {
				url : "centro/eliminarCentro/" + id,
				type : "delete",
				contentType : 'application/json',

				success : function(response) {
					console.log(response);
					
					$.confirm({
						title: 'Confirmado',
						content: 'Centro eliminado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							}
						}
					});
					self.getCentros();
					console.log(self.error);
					self.getCentros();
				},
				error : function(response) {
					$.confirm({
					    title: 'Error',
					    content: response.responseJSON.message,
					    type: 'red',
					    typeAnimated: true,
					    buttons: {
					        tryAgain: {
					            text: 'Cerrar',
					            btnClass: 'btn-red',
					            action: function(){
					            }
					        }
					    }
					});
					
					console.log(response.responseJSON.message);
					
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
		
		
		modificarCentro(id){
			app.idc = id;
			app.centro = this;
			app.router.go({ path: "modificarCentro" });
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
			document.title = "Gestion Centros";
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

	return GestionCenterViewModel;
});
