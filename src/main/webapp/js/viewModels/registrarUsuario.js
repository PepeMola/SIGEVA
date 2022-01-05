define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class GestionUserViewModel {
		constructor() {
			var self = this;
			
			self.usuarios = ko.observableArray([]);
			self.nombre = ko.observable("");
			self.email = ko.observable("");
			self.dni = ko.observable("");
			self.password = ko.observable("");
			self.centroAsignado = ko.observable("");
			self.tipoUsuario = ko.observable("");
			self.dosisAdministradas= ko.observable("");
			self.localidad = ko.observable("");
			self.provincia = ko.observable("");
			

			self.mensaje= ko.observable(2);
			self.mostrarSolicitarCita = ko.observable(1);
			
			
			
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
		
	
		
		
		getUsuarios() {
			let self = this;
			let data = {
				url : "Usuario/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
				console.log(response);
					self.usuarios([]);
					 for (let i=0; i<response.length; i++) {
						let usuario = {
							nombre : response[i].nombre,
							email : response[i].email,
							password : response[i].password,
							dni : response[i].dni,						
							tipoUsuario : response[i].tipoUsuario,
							eliminar : function() {
								self.eliminarUsuario(response[i].dni); 
							},
							modificarUsuarios : function() {
								app.paciente = this;
								app.router.go({ path: "modificarUsuario" });
							},						
						};
						self.usuarios.push(usuario);
					}
				},
				error : function(response) {
					$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
					
				}
			};
			$.ajax(data);
		}
		

		eliminarUsuario(dni) {
			let self = this;
			console.log("Hey");
			let data = {
				url : "Usuario/eliminarUsuario/" + dni,
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					
					$.confirm({
						title: 'Confirmado',
						content: 'Usuario eliminado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							}
						}
					});
					self.getUsuarios();
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
				email : this.email(),
				dni : this.dni(),
				tipoUsuario: key2,
				password : this.password(),
				centroAsignado: key3.options[key3.selectedIndex].value,
				dosisAdministradas: this.dosisAdministradas(),
				localidad: this.localidad(),
				provincia: this.provincia(),
				
			};
			let data = {
				data : JSON.stringify(info),
				url : "Usuario/add",
				type : "put",
				contentType : 'application/json',
				success : function(response) {
					
					$.confirm({
						title: 'Confirmado',
						content: 'Usuario guardado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							self.getUsuarios();
							}
						}
					});
					self.message("Usuario guardado");
					self.getUsuarios();
				},
				error : function(response) {
					$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
					
				}
			};
			$.ajax(data);
		}
		
		getCentros() {
				let self = this;
				let data = {
					url: "Usuario/getCentros",
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
