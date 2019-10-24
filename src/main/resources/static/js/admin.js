 /*<![CDATA[*/

	calcLength = function( message,subject )
	{
		var messLength=message.length;
		var subLength = subject.length;
		var special_chars = '|^{}[]\\€';
		for (var char in message)
		{
			if (special_chars.indexOf(message.charAt(char))>-1)
			{
				messLength++;
			};		
			
		}
		
		for (var char in subject)
		{
			if (special_chars.indexOf(subject.charAt(char))>-1)
			{
				subLength++;
			};		
			
		}
			
		return messLength+subLength;
	};

	$(document).ready(function(){
		
		$('#adminNewModif').prop('disabled', true);
		$('#adminBorrar').prop('disabled', true);		
		
		$('table tr').click(function () {			    
			$('#adminNewModif').prop('disabled', false);
			$('#adminBorrar').prop('disabled', false);		

			$('#adminUser').val((this.cells[0]).innerText);
			var role = this.cells[1].innerText;
			var roleId = role.split('-')[0];
		    $("#rolesOpt").val(roleId);
		});
		
		
		$('#adminNewModif').on('click',function()
		{			
			$("#adminAction").val("NEWMODIF");
			$('#adminForm').submit();
		});
		
		$('#adminBorrar').on('click',function()
		{			
			$("#adminAction").val("DELETE");
			$('#adminForm').submit();
		});
		

		$('#bchangePass').on('click',function()
		{			
			var div = document.getElementById("changeDiv");
			var divMaster = document.getElementById("masterDiv");
			var editTxt = document.getElementById("newpass");
			var editTxtConfirm = document.getElementById("newpassConfirm");
			div.style.visibility="visible";
			divMaster.style.display="none";
			editTxt.value="";
			editTxtConfirm.value="";
			$('#remname').html('0/20');			  	
			$('#remname').css('color', 'black');
			$('#remnameConfirm').html('0/20');			  	
			$('#remnameConfirm').css('color', 'black');
			$('#guardarChangePass').prop('disabled', false);
		});
		
		$('#closeChangePass').on('click',function()
		{			
			var div = document.getElementById("changeDiv");
			var divMaster = document.getElementById("masterDiv");
			div.style.visibility="hidden";
			divMaster.style.display="inline";
		});
	
		 
		$('#adminUser').on('input', function() 
		{
			if ($('#adminUser').val().length>0)
				{
					$('#adminNewModif').prop('disabled', false);
					$('#adminBorrar').prop('disabled', false);	
				}else{
					$('#adminNewModif').prop('disabled', true);
					$('#adminBorrar').prop('disabled', true);		
				}
		});
		
		
		$('#newpass').keyup( function() 
		{
			len = calcLength($('#newpass').val(),"");
							
			if (len >10)
			{
				$('#remname').css('color', 'red');
				$('#guardarChangePass').prop('disabled', true);

			}else{											
				$('#remname').css('color', 'black');
				$('#guardarChangePass').prop('disabled', false);
			};	
			$('#remname').html(len + '/20');			  	
		});

		$('#newpassConfirm').keyup( function() 
		{
			len = calcLength($('#newpassConfirm').val(),"");
									
			if (len >10)
			{
				$('#remnameConfirm').css('color', 'red');
				$('#guardarChangePass').prop('disabled', true);
			}else{											
				$('#remnameConfirm').css('color', 'black');
				$('#guardarChangePass').prop('disabled', false);
			};	
			$('#remnameConfirm').html(len + '/20');			  	
		});
		
		$('#adminNewModif').on('click',function()
		{			
			$("#adminAction").val("NEWMODIF");
			$('#adminForm').submit();
		});
		
		
		$('#guardarChangePass').on('click',function()
		{			
			var editPass = document.getElementById("newpass");
			var editPassConfirm = document.getElementById("newpassConfirm");
			if (editPass.value==null || editPass.value=='')
			{
				alert('El nuevo password no puede estar vacio.')
				return;
			}else{
				if (editPass.value!=editPassConfirm.value)
				{
					alert('La confirmacion no coincide con el nuevo password ingresado.')
					return;
				}else{
					$("#adminPass").val(editPass.value);
					$('#changepassForm').submit();				
				}
			}
		});
		
	});
/*]]>*/