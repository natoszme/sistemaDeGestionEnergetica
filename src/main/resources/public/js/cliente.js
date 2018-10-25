	$(document).ready(function() {
    $('#datepicker').datepicker({
		clearBtn: true,
		language: "es",
		orientation: "bottom auto"
	});

	filtrarMediciones();
});

function filtrarMediciones() {
	const desde = $('[name="desde"]').val(), hasta = $('[name="hasta"]').val();

	$.get("/cliente/mediciones",
		{ desde: desde, hasta: hasta },
		function(data, status) {
			const rowsMediciones = data.map((medicion) => obtenerRowMedicion(medicion)).join();
			$('#mediciones tbody').html(rowsMediciones);
			$('#mediciones').DataTable();
    	}, 
    	"json" // dataType expected
    );
}

function obtenerRowMedicion(medicion) {
	return '' +
		'<tr>' +
	      '<td>' + medicion.fecha + '</td>' +
	      '<td>' + medicion.consumo + ' KW</td>' +
	    '</tr>';			
}

function limpiarFiltros() {
	$(".datepicker").datepicker("setDate", "");
	filtrarMediciones();
}	

$("#optimizadorDiferidoButton").click(function(e) {
	e.preventDefault();		
	$("#formOp").submit();
});