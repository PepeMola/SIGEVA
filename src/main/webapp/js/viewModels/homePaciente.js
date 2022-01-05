define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class HomePacienteViewModel {
		constructor() {
			var self = this;
			self.user = app.user;
			self.botonCita = ko.observable(1);
			self.botonFecha = ko.observable(2);
			self.tablaCita = ko.observable(1);
			self.usuarios = ko.observableArray([]);
			self.nombre = ko.observable("");
			self.apellidos = ko.observable("");
			self.dniUsuario = ko.observable("");
			self.tipoUsuario = ko.observable("");
			self.centroAsignado = ko.observable("");
			self.dosisAdministradas = ko.observable("");	
			
			self.horaInicio = ko.observable("");
			self.horaFin = ko.observable("");

			self.cita = ko.observable("");
			self.fechaPrimeraDosis = ko.observable("");
			self.fechaSegundaDosis = ko.observable("");
			self.citas = ko.observableArray([]);

			self.mensaje= ko.observable(2);
			self.mostrarSolicitarCita = ko.observable(1);
			self.getCentros();
			
			
			var hoy = new Date();
			self.fecha = ko.observable(hoy.toLocaleString().split(' ')[0]); 
			

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
		
		getCentros() {
			let self = this;
			let data = {
				url : "centro/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
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
						self.horaInicio((response[i].horaInicio < 10 ? "0":"")+response[i].horaInicio+":00:00");
						self.horaFin((response[i].horaFin < 10 ? "0":"")+response[i].horaFin+":00:00");
					}
				},
				error : function(response) {
					$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
					
				}
			};
			$.ajax(data);
		}
		
		solicitarCita() {
			let self = this;

			var time = new Date().getTime();

			let info = {
					dni : self.dniUsuario(),
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
						self.centroAsignado(app.user.centroAsignado);
						
						
						$.confirm({
							title: 'Confirmado',
							content: 'Cita Creada',
							type: 'green',
							typeAnimated: true,
							buttons: {
								Cerrar: function () {
								}
							}
						});
						self.tablaCita(1);
						self.getCitaPaciente();
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

		
		getCitaPaciente() {
			let self = this;
			let data = {
					url : "cita/getCitaPaciente/" + self.dniUsuario(),
					type : "get",
					contentType : 'application/json',
					success : function(response) {
						self.citas([]);
						
							var date = new Date(response.fechaPrimeraDosis);
							var fecha = "";
							if(response.fechaSegundaDosis == 0){
								console.log("Deberia borrar la cita");
								
								var centroAsignado = response.nombreCentro;
								let cita = {
										id : response.id,
										dniUsuario : self.dniUsuario(),
										centroAsignado: response.nombreCentro,
										fechaPrimeraDosis: date.toLocaleString(),
										fechaSegundaDosis : fecha,
										eliminarCita : function() {
											self.eliminarCitaCompleta(response.id);
										},
										modificarCita : function() {
											app.cita = this;
											app.router.go({ path: "modificarCita" });
										},	
								};
								
								if(typeof response.fechaPrimeraDosis != 'undefined'){
									self.citas.push(cita);
									self.tablaCita(1);
								}else{
									self.tablaCita(2);
								}
							}
							else{
								var date2 = new Date(response.fechaSegundaDosis);
								fecha = date2.toLocaleString();
								var centroAsignado = response.nombreCentro;
								let cita = {
										id : response.id,
										dniUsuario : self.dniUsuario(),
										centroAsignado: response.nombreCentro,
										fechaPrimeraDosis: date.toLocaleString(),
										fechaSegundaDosis : fecha,
										eliminarCita : function() {
											app.cita = this;
											app.router.go({ path: "eliminarCita" });
										},
										modificarCita : function() {
											app.cita = this;
											app.router.go({ path: "modificarCita" });
										},	
								};
								
								if(typeof response.fechaPrimeraDosis != 'undefined'){
									self.citas.push(cita);
									self.tablaCita(1);
								}else{
									self.tablaCita(2);
								}
							}

					},
					error : function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						
					}
			};
			$.ajax(data);
		}

		eliminarCitaCompleta(id) {
			let self = this;
			let data = {
					url : "cita/eliminarCitaCompleta/" + id,
					type : "delete",
					contentType : 'application/json',
					success : function(response) {
						
						$.confirm({
							title: 'Confirmado',
							content: 'Cita eliminada',
							type: 'green',
							typeAnimated: true,
							buttons: {
								Cerrar: function () {
								}
							}
						});
						//	self.getCitas();
						self.getCitaPaciente();
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
		
		getUserConnect(onComplete) {	
				let self = this;
				let data = {
					url: "login/getUser",
					type: "get",
					contentType: 'application/json',
					success: function(response) {
						self.nombre(response[0]);
						self.tipoUsuario(response[1]);
						self.centroAsignado(response[3]);
						self.dniUsuario(response[4]);
						self.dosisAdministradas(response[5]);
						onComplete();
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						
					}
				};
				$.ajax(data);
		}
		
		primerInicio() {	
				let self = this;
				let data = {
					url: "login/primerInicio",
					type: "get",
					contentType: 'application/json',
					success: function(response) {
					console.log(response);
						if(response=="1"){
							app.router.go( { path : "changePassword"} );
						}
					},
					error: function(response) {
					}
				};
				$.ajax(data);
		}


		modificarCita(id){
			app.idc = id;
			app.cita = this;
			app.router.go({ path: "modificarCita" });
			self.getCitaPaciente();
		}
		
		eliminarCita(id){
			app.idc = id;
			app.cita = this;
			app.router.go({ path: "eliminarCita" });
			self.getCitaPaciente();
		}

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Inicio";
			this.getUserConnect(() =>{
				this.comprobarRol();
				this.primerInicio();
				this.getCitaPaciente();
			});
			
			
		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return HomePacienteViewModel;
});
