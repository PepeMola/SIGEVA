define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {


	class InicioViewModel {
		constructor() {
			var self = this;
			
			self.administradores = ko.observableArray([]);
			self.nombre = ko.observable("");
			self.email = ko.observable("");
			self.dni = ko.observable("");
			self.password = ko.observable("");		
			

			
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
		
			
		getAdministrador() {
			let self = this;
			let data = {
				url : "administrador/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.administradores([]);
					 for (let i=0; i<response.length; i++) {
						let administrador = {
							nombre : response[i].nombre,
							email: response[i].email,
							dni : response[i].dni,
							password : response[i].password,
							
							modificarAdministrador : function() {
								app.administrador = this;
								app.router.go({ path: "modificarAdministrador" });
							},					
						};
						self.administradores.push(administrador);
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

			
		add() {
			var self = this;
			let info = {
				nombre : this.nombre(),
				email: this.email(),
				dni : this.dni(),
				password: this.password(),
			};
			
			let data = {
				data : JSON.stringify(info),
				url : "administrador/add",
				type : "put",
				contentType : 'application/json',
				success : function(response) {
					
					$.confirm({
						title: 'Confirmado',
						content: 'Administrador guardado',
						type: 'green',
						typeAnimated: true,
						buttons: {
							Cerrar: function () {
							}
						}
					});
					self.getAdministrador();
				},
				error : function(response) {
					$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
					
				}
			};
			$.ajax(data);
		}	

		modificarAdministrador(dniUsuario){
			app.idc = dniUsuario;
			app.administrador = this;
			app.router.go({ path: "modificarAdministrador" });
		}	
		
		connected() {
			accUtils.announce('Inicio page loaded.');
			document.title = "Inicio";
			this.comprobarRol();
			this.getAdministrador();
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
