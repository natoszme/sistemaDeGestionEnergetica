function crearDispositivo() {
	$.post("/dispositivos", { nombre: $('#nombre').val(),
	consumo: $('#consumo').val(),
	tipoDispositivo: $('input[name=tipoDispositivo]:checked').val() })
	.done(dispositivoCreado);
}	

function dispositivoCreado() {	
	swal({
	    title: 'Dispositivo creado',
	    html: 'Listo! El dispositivo ha sido creado. Redireccionando...',
	    type: 'success',
	    timer: 2500,
	    showConfirmButton: false
	}).then((result) => {
		  window.location.replace("/admin/home");
	});
}

$('#formCreadDispositivo').submit(function(data){
	$('#botonEnviar').prop('disabled', 'disabled');
	$('#botonEnviar').text('Enviando...');
});