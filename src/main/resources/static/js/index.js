/**
 * 
 */
   /*<![CDATA[*/
		
	$(document).ready(function(){

	    $(".dropdown").hover(            
		        function() {
		            $('.dropdown-menu', this).stop( true, true ).slideDown("fast");
		            $(this).toggleClass('open');        
		        },
		        function() {
		            $('.dropdown-menu', this).stop( true, true ).slideUp("fast");
		            $(this).toggleClass('open');
		        }
   		);

	    
		$('body').on('click','.option li',function(){
		    var i = $(this).parents('.select').attr('id');
		    var v = $(this).children().text();
		    var o = $(this).attr('id');
		    $('#'+i+' .selected').attr('id',o);
		    $('#'+i+' .selected').text(v);
		});
		
		
		$('li').click(function(){
			var end = this.innerText;
		 });
	    
		
		//LISTADO DE PAISES==========================================================================================================
		//Seleccionar una fila		
		$('#clubesTable tbody').on( 'click', 'tr', function () {		
   			table.$('tr.info').removeClass('info');
   			$(this).addClass('info');
		});
		
			
		$("#checknull").change(function(){
			var self = this;
			$("#clubesTable td.borrado").toggle(!self.checked); 
		}).change();
		
		//BOTON BORRAR=============================================================================================================
		$('#borrar').on('click',function()
		{
			var dataArr = [];
			
			var n = dataArr.length;
			if (n != 1)		
			{
				var msg='';
				if (n < 1)
				{
					msg = 'Seleccione la campaña que desee anular';
				}else if (n > 1 ){
					msg = 'Solo se puede anular de a una campaña';
				}
				
				BootstrapDialog.show({
		            title: 'Anular Campaña',
		            message: msg,
		            animate: false,
		            buttons: [{
		                label: 'Ok',		                
		                action: function(dialog) {
		                    dialog.close();
		                }
		            }]
		        });
				return;
			}
			
   		 	var mess='Seguro de anular la campaña: '+dataArr+'?';
			
			BootstrapDialog.show({
		        title: 'Anular Campaña(s)',
		        message: mess,
		        cssClass: 'dialogclass',
		        animate: false,
		        buttons: [
				   
				   {
		            label: 'Cancelar',
		            action: function(dialog) {
		                dialog.close();
		            }
		        },{
		            label: 'Anular',
		            icon: 'glyphicon glyphicon-remove',
		            cssClass: 'btn-primary',
		            autospin: true,
		            action: function(dialog) {
		            	$.ajax({
		            		  method: "GET",
		            		  url: "/CampaignFrontEnd/ajax/anular?campanaId="+dataArr,
		            		})
		            		  .done(function( msg ) {
		  		            	table.ajax.reload();
				            	dialog.close();
		            		   
		            		  });
		            }
		        }

		        ]
		    });
			
		});
		
		$('#linkclubes').on('click',function()
		{
			/*$("#idCampaign").val("");
	    	$("#esquema").val("");
	    	$("#externalName").val("");
	    	$("#fecha_inicio").val("");
	    	$("#fecha_fin").val("");
	    	$("#mensaje").val("");
	    	$("#subject").val("");
	    	$("#callback").val("");
	    	$("#status").val("");
	    	$("#paisId").val("");
	    	$("#anchobanda").val("");*/
			$('#modForm').submit();
					
		});
		
		$('#admUsr').on('click',function()
		{			
			
			$('#admForm').submit();
							
		});
	});

	
	//LINK ASOCIACIONES
	function selectAsoc()
	{
		$('#selectAsocForm').submit();
	}	
	
	
	//BOTON MODIFICAR=========================================================================================================
	function modificarFnc(obj)
	{
		var str = obj+"";
		var json = JSON.parse(str);
    	$("#idCampaign").val(json["id"]);
    	$("#esquema").val(json["esquema"]);
    	$("#externalName").val(json["externalName"].split("||").join(" "));
    	$("#fecha_inicio").val(json["fecha_inicio"].split("||").join(" "));
    	$("#fecha_fin").val(json["fecha_fin"].split("||").join(" "));
    	//$("#fecha_expiracion").val(json["fecha_expiracion"].split("||").join(" "));
    	$("#mensaje").val(json["texto"].split("||").join(" "));
    	$("#subject").val(json["subject"].split("||").join(" "));
    	$("#callback").val(json["callback"].split("||").join(" "));
    	$("#status").val(json["status"].split("||").join(" "));
    	$("#paisId").val(json["paisId"].split("||").join(" "));
    	$("#anchobanda").val(json["anchobanda"]);
    
    	$('#modForm').submit();
		
	};		

	
	//BOTON ELIMINAR=========================================================================================================
	function eliminarFnc(id)
	{
		var msj = "Desea eliminar la campaña " + id + "?";	  
		BootstrapDialog.confirm({
		      size: "size-small",
		      type: BootstrapDialog.TYPE_WARNING, 
		      title:  "Advertencia",
		      message:  msj,
		      cssClass: 'delete-row-dialog',
		      closable: true,
	          closeByBackdrop: false,
	          closeByKeyboard: false,	        
		      callback: function(result) {
		          if(result) {  
		        	  eliminarCampania(id);
		          }
		      }
		});
	};		
	
	
	function show_popupClick(campId, campEsquema, campExternalName, campActivo, campFechaIni, campFechaFin, campFechaExp, campTexto, campSubject, campCallback, campPais, campDestinos,userId ,insertDate,campAnchoBanda)
	{
		var divA = document.getElementById("editorForm");
		
		divA.style.display = "";
		
		document.getElementById("campId").innerHTML=campId;
		document.getElementById("campEsquema").innerHTML=campEsquema;
		document.getElementById("campExternalName").innerHTML=campExternalName;		
		
		var classe = $("#span"+campId).attr("class");

		var objStatus = $("[id='status"+campId+"']");
		
			
		if((objStatus[0]).innerText==='Inactivo')
		{
			document.getElementById("campActivo").innerHTML = "Anulada";
		}	else{
			document.getElementById("campActivo").innerHTML = "Habilitada";
		}
		
		
		document.getElementById("campFechaIni").innerHTML=campFechaIni;
		document.getElementById("campFechaFin").innerHTML=campFechaFin;
		document.getElementById("campFechaExp").innerHTML=campFechaExp;
		document.getElementById("campTexto").innerHTML=campTexto;
		document.getElementById("campSubject").innerHTML=campSubject;
		document.getElementById("campCallback").innerHTML=campCallback;
		var pais = "";
		
		if(campPais=="1")
			{
				pais="Argentina";
			}
		if (campPais=="2")
			{
				pais="Uruguay";
			}
		
		if (campPais=="3"){
				pais="Paraguay";
		}
		
		document.getElementById("campPais").innerHTML=pais;		
		document.getElementById("nroDestinos").innerHTML=campDestinos;
		
		document.getElementById("userId").innerHTML=userId;
		document.getElementById("insertDate").innerHTML=insertDate;
		
		document.getElementById("campAnchoBanda").innerHTML=campAnchoBanda;
		
		$("#containerA" ).find( "*" ).removeAttr("disabled");
	}

	
	function cambiarEstado(action, id)
	{
		
	  var msj = "Desea deshabilitar campaña?";
	  
	  if(action=="0"){
		  msj = "Desea habilitar campaña?";
	  }	
	  BootstrapDialog.confirm({
	        size: "size-small",
	        type: BootstrapDialog.TYPE_WARNING, 
	        title:  "Advertencia",
	        message:  msj,
	        cssClass: 'delete-row-dialog',
	        closable: true,
            closeByBackdrop: false,
            closeByKeyboard: false,	        
	        callback: function(result) {
	            if(result) {  
	            	cambiarEstadoCampania(action,id);
	            }
	            $("#linkclubes").removeAttr("disabled");
	        }
	    });
	}


	function optionReport1()
	{
    	$("#optReport").val("1");
		$('#reportForm').submit();
	}
	

	function optionReport2()
	{
    	$("#optReport").val("2");
		$('#reportForm').submit();
	}

	
	function cambiarEstadoCampania(action,idCamp)
	{
		var mensj = ""; 
		  $.ajax({
		    	url: "/CampaignFrontEnd/ajax/cambiarEstado",
		    	data: {"id": idCamp, "action":action},
		    	type : "GET",
		    	success: function(data){
		    		
		    		if (data.startsWith('success')){
		    			var status = data.split('-')[1];
		    			var obj = $("[name='a"+idCamp+"']");
		    			var objStatus = $("[id='status"+idCamp+"']");
		    			if(action=="1")//Estado actual habilitada (1)
		    			{
		    				(objStatus[0]).innerText='Inactivo';
		    				
		    				
		    				$("#campaignTable tr.info").addClass('borrado');
		    				obj.attr("href", "javascript:cambiarEstado('0','"+idCamp+"')");
		    				obj.attr("title", "Habilitar");				    				
		    				$("#span"+idCamp).removeClass("glyphicon glyphicon-ok-circle").addClass("glyphicon glyphicon-remove-circle");			    				
		    				mensj = "Campaña deshabilitada con exito";
		    			} else 	{ //Estado actual deshabilitada (0)
		    				(objStatus[0]).innerText=status;	    				
		    				$("#campaignTable tr.info").removeClass('borrado');
		    				mensj = "Campaña habilitada con exito";

		    				obj.attr("href", "javascript:cambiarEstado('1','"+idCamp+"')");
		    				obj.attr("title", "Deshabilitar");				    				
		    				$("#span"+idCamp).removeClass("glyphicon glyphicon-remove-circle").addClass("glyphicon glyphicon-ok-circle");
		    			}
		    			
			            BootstrapDialog.show({
			                       title: 'Cambio de estado',
			                       message: mensj,
			                       animate: false,
			                       buttons: [{
			                           label: 'Ok',                                          
			                           action: function(dialog) {
			                               dialog.close();
			                           }
			                       }]
			                   });

		    		} else {
		    			BootstrapDialog.show({
		                       title: 'Error',
		                       type: BootstrapDialog.TYPE_DANGER,
		                       message: mensj,
		                       animate: false,
		                       buttons: [{
		                           label: 'Ok',                                          
		                           action: function(dialog) {
		                               dialog.close();
		                           }
		                       }]
		                   });
	    				
		    		}},
		    	error: function(data){
		    		BootstrapDialog.show({
	                       title: 'Error',
	                       type: BootstrapDialog.TYPE_DANGER,
	                       message: mensj,
	                       animate: false,
	                       buttons: [{
	                           label: 'Ok',                                          
	                           action: function(dialog) {
	                               dialog.close();
	                           }
	                       }]
	                   });

		    		}	
		    });
		    $("#containerA" ).find( "*" ).removeAttr("disabled");		
	}

	
	function eliminarCampania(idCamp)
	{
		var mensj = "Campaña eliminada con exito"; 		    			
		$.ajax({
		    	url: "/CampaignFrontEnd/ajax/eliminarCampana",
		    	data: {"id": idCamp},
		    	type : "GET",
		    	success: function(data){
		    		
		    		if (data.startsWith('success')){
			            BootstrapDialog.show({
			                       title: 'Eliminar',
			                       message: mensj,
			         		       closable: true,
			        	           closeByBackdrop: false,
 			        	           closeByKeyboard: false,	        
			                       animate: false,
			                       buttons: [{
			                           label: 'Ok',                                          
			                           action: function(dialog) {
			                        	   dialog.close();
			                           }
			                       }]
			                   });
                 	    table.row($("#campaignTable tr.info")).remove().draw(false);

		    		} else {
		    			mensj = "Error al eliminar la campaña."; 		    			
		    			BootstrapDialog.show({
		                       title: 'Error',
		                       type: BootstrapDialog.TYPE_DANGER,
		                       message: mensj,
		         		       closable: true,
		        	           closeByBackdrop: false,
		        	           closeByKeyboard: false,	        
		                       animate: false,
		                       buttons: [{
		                           label: 'Ok',                                          
		                           action: function(dialog) {
		                               dialog.close();
		                           }
		                       }]
		                   });
	    				
		    		}},
		    	error: function(data){
	    			mensj = "Error al eliminar la campaña."; 		    			
		    		BootstrapDialog.show({
	                       title: 'Error',
	                       type: BootstrapDialog.TYPE_DANGER,
	                       message: mensj,
	         		       closable: true,
	        	           closeByBackdrop: false,
	        	           closeByKeyboard: false,	        
	                       animate: false,
	                       buttons: [{
	                           label: 'Ok',                                          
	                           action: function(dialog) {
	                               dialog.close();
	                           }
	                       }]
	                   });

		    		}	
		});
	}
	
/*]]>*/
