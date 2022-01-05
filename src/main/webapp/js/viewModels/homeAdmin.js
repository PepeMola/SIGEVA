define(['knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
	'jquery'], function(ko, app, moduleUtils, accUtils, $) {




		class InicioViewModel {
			constructor() {
				var self = this;



				self.mostrarUsuario = ko.observable(0);
				self.mostrarCentro = ko.observable(0);



				self.centros = ko.observableArray([]);
				self.usuarios = ko.observableArray([]);
				self.nombre = ko.observable("");
				self.apellidos = ko.observable("");
				self.dni = ko.observable("");
				self.tipoUsuario = ko.observable("");
				self.centroAsignado = ko.observable("");
				self.dosisAdministradas = ko.observable("");
				self.localidad = ko.observable("");
				self.provincia = ko.observable("");

				var hoy = new Date();
				self.fecha = ko.observable(hoy.toLocaleString().split(' ')[0]); 

				
				var dropdown = document.getElementsByClassName("dropdown-btn");
				var i;
				for (i = 0; i < dropdown.length; i++) {
					dropdown[i].addEventListener("click", function() {
						this.classList.toggle("active");
						var dropdownContent = this.nextElementSibling;
						if (dropdownContent.style.display === "block") {
							dropdownContent.style.display = "none";
						} else {
							dropdownContent.style.display = "block";
						}
					});
				}



				self.message = ko.observable(null);
				self.error = ko.observable(null);



				// Header Config
				self.headerConfig = ko.observable({
					'view': [],
					'viewModel': null
				});
				moduleUtils.createView({
					'viewPath': 'views/header.html'
				}).then(function(view) {
					self.headerConfig({
						'view': view,
						'viewModel': app.getHeaderModel()
					})
				})
			}




			w3_open() {
				document.getElementById("mySidebar").style.display = "block";
				document.getElementById("myOverlay").style.display = "block";
			}



			w3_close() {
				document.getElementById("mySidebar").style.display = "none";
				document.getElementById("myOverlay").style.display = "none";
			}



			onClick(element) {
				document.getElementById("img01").src = element.src;
				document.getElementById("modal01").style.display = "block";
				var captionText = document.getElementById("caption");
				captionText.innerHTML = element.alt;
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




			logout() {
				let self = this;
				let data = {
					url: "login/logout",
					type: "post",
					contentType: 'application/json',
					success: function(response) {
						app.router.go({ path: "login" });
					},
					error: function(response) {
						$.confirm({title: 'Error',content: response.responseJSON.message,type: 'red',typeAnimated: true,buttons: {tryAgain: {text: 'Cerrar',btnClass: 'btn-red',action: function(){}}}});
						
					}
				};
				$.ajax(data);
			}
			
			comprobarCentros() {
				let self = this;
				let data = {
					url: "centro/comprobarCentros",
					type: "get",
					contentType: 'application/json',
					success: function(response) {
						console.log(response);
						if(response==1){
							$.confirm({
								title: 'Alerta',
								content: 'No hay centros creados',
								type: 'red',
								typeAnimated: true,
								buttons: {
									Crear: function () {
									app.router.go({ path: "crearCentros" });
									}
								}
						});
							
						}
					},
					error: function(response) {
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



			connected() {
				accUtils.announce('Inicio page loaded.');
				document.title = "Inicio";
				this.comprobarRol();
				this.getUserConnect();
				this.comprobarCentros();
			};



			disconnected() {
				// Implement if needed
			};



			transitionCompleted() {
				// Implement if needed
			};

		
		w3_open() {
            document.getElementById("mySidebar").style.display = "block";
            document.getElementById("myOverlay").style.display = "block";
        }

        w3_close() {
            document.getElementById("mySidebar").style.display = "none";
            document.getElementById("myOverlay").style.display = "none";
        }

        // Modal Image Gallery
        onClick(element) {
            document.getElementById("img01").src = element.src;
            document.getElementById("modal01").style.display = "block";
            var captionText = document.getElementById("caption");
            captionText.innerHTML = element.alt;


		var dropdown = document.getElementsByClassName("dropdown-btn");
        var i;
        for (i = 0; i < dropdown.length; i++) {
            dropdown[i].addEventListener("click", function () {
                this.classList.toggle("active");
                var dropdownContent = this.nextElementSibling;
                if (dropdownContent.style.display === "block") {
                    dropdownContent.style.display = "none";
                } else {
                    dropdownContent.style.display = "block";
                }
            });
        }
        }

		
        


		}



		return InicioViewModel;
	});