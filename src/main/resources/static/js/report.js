 /*<![CDATA[*/
var titulo='';
	$(document).ready(function(){
		

////////////////////DETAILS//////////////////////
		
		
		//Formato de la fecha para la tabla
		$.fn.dataTable.moment( 'DD/MM/YYYY HH:mm:ss' );
		
		//Inicializacion del listado de estadisticas
		var detailsTable = $('#campaignDetails').DataTable( {
		//	"ajaxSource": "/CampaignFrontEnd/ajax/getallstats",
			"ajaxDataProp": "",
			"processing": true,
			"language": {
							
							"url": "datatables.net/js/Spanish.json"
							
						},				
			"pagingType": "full_numbers",
		    "paging":    true,		    
			"order": [[ 1, "asc" ]],
			"bAutoWidth": true,
			"columnDefs": [{className: "text-center", "targets": [0,1]}],				
			"columns": [
			            
	                    { "sTitle": "Bill Number","data": "bill_number","width":"8%"},
						{ "sTitle": "Codigo error","data": "sec_error_code","width":"20%"},								
	                    { "sTitle": "Descripcion","data": "error_description","width":"20%"}
			          ],
			          "dom": '<"row" <"col-lg-12"B>> <"row" <"col-lg-6"l> <"col-lg-6"f>  > <"row" tipr>',
			          "buttons": [
			                   {
			                    	  extend: 'copy',
			                    	  messageTop: function(){return ''+titulo;},
						                title: 'Errores por linea',
						                footer: 'true'
			                      },			                    			                    
			                      {
			                    	  extend: 'excel',
			                    	  messageTop: function(){return ''+titulo;},
						                title: 'Errores por linea',
						                footer: 'true'				                    
			                      },
			                      {
				                	extend: 'csvHtml5',

			                      },
			                      {
			                    	extend: 'pdfHtml5',
					                messageTop: function(){return ''+titulo;},
					                title: 'Errores por linea',
					                footer: 'true'
			                      }
			                ],
			                
			               
						          
			 "fnDrawCallback":function(){
				 $('table#campaignStatsTable td').bind('mouseenter', function () { $(this).parent().children().each(function(){$(this).addClass('active');}); });
				 $('table#campaignStatsTable td').bind('mouseleave', function () { $(this).parent().children().each(function(){$(this).removeClass('active');}); });
				
			 }
	    });
		
		
	
		//Inicializacion del listado de estadisticas
		var table = $('#campaignStatsTable').DataTable( {
			"ajaxSource": "/CampaignFrontEnd/ajax/getallstats",
			"ajaxDataProp": "",
			 "processing": true,
			"language": {
				"decimal": ",",
	            "thousands": ".",
				"url": "datatables.net/js/Spanish.json"
			},				
			"pagingType": "full_numbers",
		    "paging":    true,		    
			"order": [[ 2, "asc" ]],
			"bAutoWidth": true,
			"columnDefs": [{className: "text-center", "targets": [1,2,3,4,5,6,7,8]}],				
			"columns": [			
			            {
			                "className":      'details-control',
			                "orderable":      false,
			                "data":           null,
			                "defaultContent": '',
			                "width" : "2%"
			            },
	                    { "sTitle": "Id","data": "msg_id","width":"2%"},
						{ "sTitle": "Nombre Campaña","data": "name_campaign","width":"20%"},
						{ "sTitle": "Inicio","data": "send_begin_date","width":"5%"},
						{ "sTitle": "Fin","data": "send_end_date","width":"5%"},
						{ "sTitle": "Cantidad Total","data": "send_subs_qty","width":"5%"},
						{ "sTitle": "Correctos","data": "processed_qty","width":"5%"},
						{ "sTitle": "Fallidos","data": "error_qty","width":"2%"},
						{ "sTitle": "Efectividad","data": "efectividad","width":"10%"}
						
			          ],
			          "dom": '<"row" <"col-lg-12"B>> <"row" <"col-lg-6"l> <"col-lg-6"f>  > <"row" tipr>',
			          "buttons": [
			                      	{
			                      		extend: 'copy',
			                      		title: 'Estadisticas generales',
			                      		footer: 'true'
			                      	},			                    			                    
			                      	{
			                      		extend: 'excel',			                      		
			                      		title: 'Estadisticas generales',
			                      		footer: 'true'				                    
			                      	},
			                      	{
			                      		extend: 'csvHtml5',
			                      		
			                      	},
			                      	{
			                      		extend: 'pdfHtml5',
			                      		title: 'Estadisticas generales',
			                      		footer: 'true'
			                      	}
			                ],
						          
			 "fnDrawCallback":function(){
				 $('table#campaignStatsTable td').bind('mouseenter', function () { $(this).parent().children().each(function(){$(this).addClass('active');}); });
				 $('table#campaignStatsTable td').bind('mouseleave', function () { $(this).parent().children().each(function(){$(this).removeClass('active');}); });
				
			 }
	    });
		
		
		
	    /*    $('#campaignStatsTable tbody').on( 'click', 'a', function () {            
        var data = table.row( $(this).parents('tr') ).data();
        var msg_id= data.msg_id;
        alert(msg_id);
        $.ajax({
            method: "GET",
            url: "/CampaignFrontEnd/ajax/statistics/msgerrdetails?campId="+msg_id,
          })
          .done(function( msg ) {
              alert(msg);
              
         });
        
    	} );*/

		
		
			$('#campaignStatsTable tbody').on('click', 'td.details-control', function () {			
			var dataArr = [];
			var tr = $(this).closest('tr');
		    var row = table.row( tr );		
		    var camp_id = row.data().msg_id;
		    titulo = camp_id + ' - ' +row.data().name_campaign; 
		     if ( row.child.isShown() ) {
		            row.child.hide();
		            tr.removeClass('shown');
		            var div = document.getElementById("detailDiv");
		            div.style.visibility="hidden";
		            
		            detailsTable.clear();
		        	detailsTable.draw();		        
		        }
		        else {		        	
		        	$.ajax({
		        		  method: "GET",
		        		  url: "/CampaignFrontEnd/ajax/statistics/errdetails?campId="+camp_id,
		        		})
		        		.done(function( msg ) {
		        			row.child( format(msg) ).show();
				            tr.addClass('shown');
		        		    //table.ajax.reload();
		  	         });
		        	
		        	detailsTable.ajax.url('/CampaignFrontEnd/ajax/statistics/msgerrdetails?campId='+camp_id);
		        	detailsTable.ajax.reload();
		        	detailsTable.draw();
		       
		        }
		
		});
		
		
		
		
	});
	function format ( data ) {	 
		var toPrint=data[0];
		var arrayLength = data.length;
		
		var tabla = '<table cellpadding="5" cellspacing="0" border="0" class="table" style="text-align: center;padding-left:50px;">';
		if (arrayLength > 0)
		{
			tabla += '<tr><td colspan="3"><b>Detalle errores</b> (<a style="cursor:pointer" id="detlnk" onClick="getDetStats()">Ver detalles por linea</a>) </td></tr>'+
					 '<tr><td width="15%"><b>Error ID</b></td><td width="60%" align="left"><b>Descripcion</b></td><td width="25%"><b>Cantidad errores</b></td></tr>';		
			for (var i = 0; i < arrayLength; i++) {
				var toPrint=data[i];
				tabla +=  	'<tr><td>'+toPrint.error_id+'</td>'+
							'<td align="left">'+toPrint.error_description+'</td>'+
							'<td> '+toPrint.error_qty+'</td></tr>';
			}			
		}
		else
		{
			tabla += '<tr><td colspan="3"><b>Detalle errores</b></td></tr>'+
					 '<tr><td>Ningún dato disponible en esta tabla</td></tr>';					
		}
		tabla += '</table>';
		
	    return tabla;
	}
	
	function getDetStats( camp )
	{
		var div = document.getElementById("detailDiv");
		var divMaster = document.getElementById("masterDiv");
		var subtitle = document.getElementById("det_subtitle");
		var lnk= document.getElementById("detlnk");
		if (lnk.text==="Ocultar detalles por linea")
		{
			lnk.text="Ver detalles por linea";
			div.style.visibility="hidden";
			divMaster.style.display="inline";
			return;
		}
		lnk.text="Ocultar detalles por linea";
		var titleSplit = titulo.split("-");		
		subtitle.innerText='Id: '+titleSplit[0]+" - Nombre de campaña: "+titleSplit[1];	
		
		div.style.visibility="visible";
		divMaster.style.display="none";
		
		
	}
/*]]>*/