
$(document).ready(function() {
    $('#datepicker').datepicker({
		clearBtn: true,
		language: "es",
		orientation: "bottom auto"
	});
	    
	obtenerConsumos();
});

function obtenerConsumos() {

	const desde = $('[name="desde"]').val(), hasta = $('[name="hasta"]').val();

	$.get("/admin/consumos",
		{ desde: desde, hasta: hasta },
		function(data, status) {
			const htmlMedicionesXCliente = data.map((cliente) => obtenerLiMedicionXCliente(cliente)).join();
			$('#clientes').html(htmlMedicionesXCliente);			
    	}, 
    	"json" // dataType expected
    );
}

function obtenerLiMedicionXCliente(objeto) {
	return '' +				    
	 	'<li class="list-group-item">' +
	   		'<div class="d-flex w-100 justify-content-between">' +
	     		' <h5 class="mb-1">' +  objeto[1] +'</h5>' +
      			'<small class="text-muted">Consumo ' + objeto[3] + '</small>' +
   			'</div>' +
  	   		'<p class="mb-1">Categor&iacute;a: ' + objeto[0] + '</p>' +
      		'<small class="text-muted">Cliente desde ' + objeto[2] + '</small>' +
	 	'</li>';
}

function limpiarFiltros() {
	$(".datepicker").datepicker("setDate", "");
	obtenerConsumos();
}