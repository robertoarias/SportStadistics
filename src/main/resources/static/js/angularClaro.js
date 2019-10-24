//var app = angular.module("claro", []);
var app = angular.module("claro", ['angularFileUpload']);

var global_process_tail = null;
var global_process_tail_index = null;

app.controller("claroController", function ($scope, $http, FileUploader) 
{
	$scope.servicescheduleInfo = [];

	$scope.devPag = {desde:0,hasta:0};

	$scope.currentPage = 1;
	$scope.pageSize = 5;
	
	$scope.lstEsquemasCmp = [];
	
	$scope.cmp = null;

	//------------------------------------------------------------------------------
	// FILE UPLOADER
	//------------------------------------------------------------------------------
	var uploader = $scope.uploader = new FileUploader(
	{
		url: 'upload.php'
	});
	
	// FILTERS
      
	// a sync filter
	uploader.filters.push({
		name: 'syncFilter',
		fn: function(item /*{File|FileLikeObject}*/, options) {
			//console.log('syncFilter');
			return this.queue.length < 10;
		}
	});
  
	// an async filter
	uploader.filters.push({
		name: 'asyncFilter',
		fn: function(item /*{File|FileLikeObject}*/, options, deferred) {
			//console.log('asyncFilter');
			setTimeout(deferred.resolve, 1e3);
		}
	});
	
	// CALLBACKS
	uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options)
	{
		//console.info('onWhenAddingFileFailed', item, filter, options);
	};
	uploader.onAfterAddingFile = function(fileItem)
	{
		//console.info('onAfterAddingFile', fileItem);
	};
	uploader.onAfterAddingAll = function(addedFileItems)
	{
		//console.info('onAfterAddingAll', addedFileItems);
	};
	uploader.onBeforeUploadItem = function(item)
	{
		//console.info('onBeforeUploadItem', item);
	};
	uploader.onProgressItem = function(fileItem, progress)
	{
		//console.info('onProgressItem', fileItem, progress);
	};
	uploader.onProgressAll = function(progress)
	{
		//console.info('onProgressAll', progress);
	};
	uploader.onSuccessItem = function(fileItem, response, status, headers)
	{
		//console.info('onSuccessItem', fileItem, response, status, headers);
		//console.info('onSuccessItem');
		//console.log(response);
		if( typeof(response.success)!="undefined" &&
			typeof(response.file)!="undefined"
		)
		{
			if( response.success!="true" &&
				response.success!=true
			)
			{
				alert("Error al recibir el archivo por lote. Cod[3]");
			}
			else
			{
				$scope.enviarCamapanaPaso2(response.file);
			}
		}
		else
		{
			if( typeof(response.msg)!="undefined" )
			{
				alert("Error al recibir el archivo por lote: '"+response.msg+"'. Cod[2]" );
			}
			else
			{
				alert("Error al recibir el archivo por lote. Cod[1]" );
			}
		}
	};
	uploader.onErrorItem = function(fileItem, response, status, headers)
	{
		//console.info('onErrorItem', fileItem, response, status, headers);
	};
	uploader.onCancelItem = function(fileItem, response, status, headers)
	{
		//console.info('onCancelItem', fileItem, response, status, headers);
	};
	uploader.onCompleteItem = function(fileItem, response, status, headers)
	{
		//console.info('onCompleteItem', fileItem, response, status, headers);
		//console.info('onCompleteItem');
		//$scope.enviarCamapanaPaso2();
	};
	uploader.onCompleteAll = function()
	{
		//console.info('onCompleteAll');
	};
	//------------------------------------------------------------------------------

	$scope.init = function (moduleName)
	{
		//Inicializar Formulario
		$scope.loadForm();

		$scope.lstEsquemasCmp = [{esquemaid:'Argentina SAS Desarrollo',id:1},{esquemaid:'Otra',id:2}];

		$('#datetimepicker1').datetimepicker({locale: 'es', format:'DD/MM/YYYY HH:mm:ss'});
		$('#datetimepicker2').datetimepicker({locale: 'es', format:'DD/MM/YYYY HH:mm:ss'});
		$('#datetimepicker3').datetimepicker({locale: 'es', format:'DD/MM/YYYY HH:mm:ss'});
		
	};
	
	$scope.processFnTail = function (init)
	{
		if(typeof(init)!="undefined"&&init){global_process_tail_index=0;}
		else{global_process_tail_index++;}
		
		
		if( global_process_tail_index > global_process_tail.length )
		{
			//Se alcanzo el final de la cola de procesos
			global_process_tail = [];
			global_process_tail_index=false;
			return true;
		}
		
		var index = global_process_tail_index;
		if( typeof(global_process_tail[index])!="undefined" )
		{
			if( global_process_tail[index].status )
			{
				global_process_tail[index].status = false;//Se proceso la llamada

				var fn = eval("$scope." + global_process_tail[index].fn);
				var param = global_process_tail[index].params;
				try
				{
					//console.log(global_process_tail[index]);
					if( param != null )
					{
						fn(param);
					}
					else
					{
						fn();
					}
				}
				catch(e)
				{
					
				}
			}
		}
	}

	$scope.loadModule = function (moduleName)
	{
		loadModuleRedirect(moduleName);
	};
	
	$scope.transformInput = function(list,index,fn,newIndex)
	{
		if( angular.isArray(index) )
		{
			for(i=0;i<list.length;i++)
			{
				for(j=0;j<index.length;j++)
				{
					list[i][index[j]] = fn(list[i][index[j]]);
				}
			}
			return list;
		}
		else
		{
			if( typeof(newIndex)=="undefined" )
			{
				for(i=0;i<list.length;i++)
				{
					list[i][index] = fn(list[i][index]);
				}
			}
			else
			{
				for(i=0;i<list.length;i++)
				{
					list[i][newIndex] = fn(list[i][index]);
				}
			}
			return list;
		}
	}
	
	/*
	* -------------------------------------
	* BEGIN DASHBOARD
	* -------------------------------------
	*/

	/*
	* Inicializar el formulario
	*/
	$scope.cleanForm = function()
	{
		$scope.cmp = {
			 mensaje: ''
			,subject: ''
			,callback: ''
			,limitediario: ''
			,fechainicio: ''
			,fechafin: ''
			,fechaexpiracion: ''
		};
		
		$("#resultadook").hide();
		$("#resultadoerror").hide();
		$("#resultadoErrorMsg").html('');
	}

	/*
	* Inicializar el formulario
	*/
	$scope.loadForm = function()
	{
		$scope.cleanForm();
	}
	
	/*
	* Validar el formulario
	*/
	$scope.validateForm = function()
	{
		return true;
	}
		
	/*
	* Enviar el formulario: Paso 1 - Enviar el archivo
	*/
	$scope.enviarCamapanaPaso1 = function()
	{
		$("#loadcampana").show();
		
		$("#resultadook").hide();
		$("#resultadoerror").hide();
		$("#resultadoErrorMsg").html('');
		
		uploader.queue[0].upload();
	};
	
	/*$scope.handlerResponse = function(data)
	{
		if(data.resultCode==20)
		{
			exitApp();
		}
		else
		{
			loadEffect(data.errorDes +" - Code["+ data.resultCode+"]","warning");
		}
	}*/
	
	/*
	* Enviar el formulario: Paso 2 - Datos del formulario
	*/
	$scope.enviarCamapanaPaso2 = function(filename)
	{
		try
		{
			var params = "savecampana=true";
			params+= "&mensage="	+ $scope.cmp.mensaje;
			params+= "&class="		+ $scope.cmp.esquema;
			params+= "&subject="	+ $scope.cmp.subject;
			params+= "&callback="	+ $scope.cmp.callback;
			params+= "&limdiario="	+ $scope.cmp.limitediario;
			params+= "&fini="		+ $("#fini").val(); //$scope.cmp.fechainicio;
			params+= "&ffin="		+ $("#ffin").val(); //$scope.cmp.fechafin;
			params+= "&fexp="		+ $("#fexp").val(); //$scope.cmp.fechaexpiracion;
			params+= "&file="		+ filename;

			var urlAPI = API_CAMPANAS + params;
			//alert(urlAPI);
			
			$http.get(urlAPI).then(function (response)
			{
				//var result = handlerResponse(response);
				//alert(result);
				var flagError = true;
				var msg = 'Se produjo un error inesperado.';
				if( typeof(response.data.success)!="undefined" )
				{
					if( response.data.success=="true" ||
						response.data.success==true
					)
					{
						flagError = false;
						msg = response.data.msg;
						$("#resultadook").show();
						$("#resultadoOkMsg").html(msg);
					}
					else
					{
						msg = response.data.msg;
					}
				}

				$("#loadcampana").hide();

				if( flagError )
				{
					$("#resultadoerror").show();
					$("#resultadoErrorMsg").html(msg);
				}
			});
		}
		catch(err)
		{
			$("#loadcampana").hide();
			$("#resultadoerror").show();
			$("#resultadoErrorMsg").html("Se produjo un error en la aplicación.");
		}
	};
	

	/*
	* ---------------------
	* END DASHBOARD
	* ---------------------
	*/
	
});

/*app.directive('file', function () {
    return {
        scope: {
            file: '='
        },
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var file = event.target.files[0];
                scope.file = file ? file : undefined;
                scope.$apply();
            });
        }
    };
});*/

