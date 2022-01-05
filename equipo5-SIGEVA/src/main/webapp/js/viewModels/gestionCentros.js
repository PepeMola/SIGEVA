define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class GestionCenterViewModel {
		constructor() {
			var self = this;
			
			self.centros = ko.observableArray([]);
			self.id = ko.observable("");
			self.nombre = ko.observable("");
			self.dosisTotales = ko.observable("");
			self.aforo = ko.observable("");
			self.localidad = ko.observable("");
			self.provincia = ko.observable("");		
			

			self.mensaje= ko.observable(2);
			self.mostrarSolicitarCita = ko.observable(1);
			
			self.message = ko.observable(null);
			self.error = ko.observable(null);
			
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
					self.centros([]);
					 for (let i=0; i<response.length; i++) {
						let centro = {
							id : response[i].id,
							nombre : response[i].nombre,
							dosisTotales: response[i].dosisTotales,
							aforo : response[i].aforo,
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
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}

		eliminarCentro(id) {
			let self = this;
			console.log("Hey");
			let data = {
				url : "centro/eliminarCentro/" + id,
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Centro eliminado ");
					self.getCentros();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}

		add() {
			var self = this;
			
			let info = {
				nombre : this.nombre(),
				dosisTotales: this.dosisTotales(),
				aforo : this.aforo(),
				localidad: this.localidad(),
				provincia: this.provincia(),
				
			};
			let data = {
				data : JSON.stringify(info),
				url : "centro/add",
				type : "put",
				contentType : 'application/json',
				success : function(response) {
					self.message("Centro guardado");
					self.getCentros();
					self.error(null);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		modificarCentro(){
			app.router.go({ path: "modificarCentro" });
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

	return GestionCenterViewModel;
});
