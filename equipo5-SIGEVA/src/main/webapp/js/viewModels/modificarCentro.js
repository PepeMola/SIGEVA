define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class ModCenterViewModel {
		constructor() {
			var self = this;
			
			self.centro = ko.observable();
			self.centro = app.centro;
			
			self.id = ko.observable(self.centro.id);
			self.nombre = ko.observable(self.centro.nombre);
			self.dosisTotales = ko.observable(self.centro.dosisTotales);
			self.aforo = ko.observable(self.centro.aforo);
			self.localidad = ko.observable(self.centro.localidad);
			self.provincia = ko.observable(self.centro.provincia);		
			
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
		
		
		
		modificarCentro(){
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
				url : "centro/modificarCentros",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.error("");
					self.message("Centro modificado");
				},
				error : function(response) {
					self.message("");
					self.error(response.responseJSON.errorMessage);
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

	return ModCenterViewModel;
});
