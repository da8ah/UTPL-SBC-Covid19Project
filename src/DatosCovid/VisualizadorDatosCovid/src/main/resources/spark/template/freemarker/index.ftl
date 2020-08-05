<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <title>UTPL | Covid-19 Visualizador</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image/x-icon" href="img/favicon.ico">
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>
	
	<!-- Toolbar -->
	<div class="toolbar" role="banner">
	  <img width="150" alt="UTPL Logo" src="img/utpl.png" />
	  <span>Covid-19 Data</span>
	</div>	

	<div class="content">
		<div class="queries">Recursos del mes de febrero en United States.</div>
		<div class="datos">
			<table id="tablaDatos">
				<thead>
					<tr>
						<td>N</td>
						<td>Recurso</td>
						<td>Fecha</td>
					</tr>
				</thead>
				<tbody>
					${datos}
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>
