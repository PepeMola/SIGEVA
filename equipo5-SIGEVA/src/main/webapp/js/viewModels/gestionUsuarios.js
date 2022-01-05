define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class GestionUserViewModel {
		constructor() {
			var self = this;
			
			self.usuarios = ko.observableArray([]);
			self.nombre = ko.observable("");
			self.apellidos = ko.observable("");
			self.dni = ko.observable("");
			self.tipoUsuario = ko.observable("");
			self.centroAsignado = ko.observable("");
			self.dosisAdministradas = ko.observable("");
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
		
		
		enable() {
				document.getElementById("menuCentros").disabled = false;
			}

			disable() {
				document.getElementById("menuCentros").disabled = true;
			}
		
		
		getUsuarios() {
			let self = this;
			let data = {
				url : "paciente/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.usuarios([]);
					 for (let i=0; i<response.length; i++) {
						let paciente = {
							nombre : response[i].nombre,
							apellidos: response[i].apellidos,
							dni : response[i].dni,
							tipoUsuario : response[i].tipoUsuario,
							centroAsignado : response[i].centroAsignado,
							dosisAdministradas : response[i].dosisAdministradas,
							localidad : response[i].localidad,
							provincia : response[i].provincia,
							eliminar : function() {
								self.eliminarUsuario(response[i].dni); 
							},
							modificarUsuarios : function() {
								app.paciente = this;
								app.router.go({ path: "modificarUsuario" });
							},						
						};
						self.usuarios.push(paciente);
					}
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
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

		eliminarUsuario(dni) {
			let self = this;
			console.log("Hey");
			let data = {
				url : "paciente/eliminarUsuario/" + dni,
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Usuario eliminado ");
					self.getUsuarios();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}

		add() {
			var self = this;
			var key = document.getElementsByName("tipoUsuario");
				for (var i = 0; i < key.length; i++) {
					if (key[i].checked)
						var key2= key[i].value;
				}
			var key3 = document.getElementById("menuCentros");
			let info = {
				nombre : this.nombre(),
				apellidos: this.apellidos(),
				dni : this.dni(),
				tipoUsuario: key2,
				centroAsignado: key3.options[key3.selectedIndex].value,
				dosisAdministradas: this.dosisAdministradas(),
				localidad: this.localidad(),
				provincia: this.provincia(),
				
			};
			let data = {
				data : JSON.stringify(info),
				url : "paciente/add",
				type : "put",
				contentType : 'application/json',
				success : function(response) {
					self.message("Usuario guardado");
					self.getUsuarios();
					self.error(null);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		modificarUsuario(){
			app.router.go({ path: "modificarUsuario" });
		}	

		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Inicio";
			this.getUsuarios();
			this.getCentros();
		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return GestionUserViewModel;
});
