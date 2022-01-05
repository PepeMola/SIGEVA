define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class ModCitaViewModel {
		constructor() {
			var self = this;

			self.cita = ko.observable();
			self.cita = app.cita;
			self.botonFecha1 = ko.observable(1);
			var date = app.cita.fechaPrimeraDosis.toString().split(" ",1);
			var newDate = date.toString().split("/").reverse().join("-");
			var fPrimera = new Date(newDate);
			var final = fPrimera.toISOString().slice(0, 10);
			self.inputFechaPrimera=ko.observable(true);
			var today = Date.now();
			var final3 =  app.cita.fechaPrimeraDosis;
			self.nombre = ko.observable("");
			self.tipoUsuario = ko.observable("");

			if(today>fPrimera.getTime()){
				console.log("No se puede eliminar");
				self.botonFecha1(2);
			}else{
				console.log("Se puede eliminar");
				self.botonFecha1(1);
			}
			var final2 = "";
			if(app.cita.fechaSegundaDosis != 0){
				var date2 = app.cita.fechaSegundaDosis.toString().split(" ",1);
				var newDate2 = date2.toString().split("/").reverse().join("-");
				var fSegunda = new Date(newDate2);
				var fSegunda = new Date(newDate2);
				final2 = fSegunda.toISOString().slice(0, 10);
				var final4 =  app.cita.fechaSegundaDosis;
			}
			

			//self.fechaPrimeraDosis = ko.observable(final);
			//self.fechaSegundaDosis = ko.observable(final2);
			
			self.fechaPrimeraDosis = ko.observable(final3);
			self.fechaSegundaDosis = ko.observable(final4);
			
			self.dniPaciente = ko.observable();




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

//		modificarCita() {
//		var self = this;

//		let info = {
//		id : app.cita.id,
//		dniPaciente : app.cita.dniPaciente,
//		fechaPrimeraDosis: this.fechaPrimeraDosis(),
//		fechaSegundaDosis: this.fechaSegundaDosis(),
//		centrosSanitarios : app.cita.centroAsignado,
//		};
//		let data = {
//		data : JSON.stringify(info),
//		url : "cita/modificarCita/" + app.cita.id,
//		type : "put",
//		contentType : 'application/json',
//		success : function(response) {
//		app.router.go({ path: "homePaciente" });
//		$.confirm({
//		title: 'Confirmado',
//		content: 'Cita modificada',
//		type: 'green',
//		typeAnimated: true,
//		buttons: {
//		Cerrar: function () {
//		}
//		}
//		});
//		},
//		error: function(response) {
//		$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
//		}
//		};
//		$.ajax(data);
//		}

		eliminarPrimeraCita() {
			var self = this;

			let info = {
					id : app.cita.id,
					dniPaciente : app.cita.dniPaciente,
					fechaPrimeraDosis: app.cita.fechaSegundaDosis,
					centrosSanitarios : app.cita.centroAsignado,
			};
			let data = {
					data : JSON.stringify(info),
					url : "cita/eliminarCita/" + app.cita.id,
					type : "put",
					contentType : 'application/json',
					success : function(response) {
						app.router.go({ path: "homePaciente" });
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
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
					}
			};
			$.ajax(data);
		}

		eliminarSegundaCita() {
			var self = this;

			let info = {
					id : app.cita.id,
					dniPaciente : app.cita.dniPaciente,
					fechaPrimeraDosis: app.cita.fechaPrimeraDosis,
					centrosSanitarios : app.cita.centroAsignado,
			};
			let data = {
					data : JSON.stringify(info),
					url : "cita/eliminarCita/" + app.cita.id,
					type : "put",
					contentType : 'application/json',
					success : function(response) {
						app.router.go({ path: "homePaciente" });
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

		getUserConnect() {
			let self = this;
			let data = {
					url: "login/getUser",
					type: "get",
					contentType: 'application/json',
					success: function(response) {

						self.nombre(response[0]);
						self.tipoUsuario(response[1]);
					},
					error: function(response) {
						self.error(response.responseJSON.errorMessage);

					}
			};
			$.ajax(data);
		}

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Modificar cita";
			this.getUserConnect();
		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return ModCitaViewModel;
});
