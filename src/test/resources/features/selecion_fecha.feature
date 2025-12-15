#language: es

Característica: Seleccionar fecha en el campo de formulario de DatePicker
  Como cristina usuaria de la aplicacion DatePicker
  Quiero seleccionar una fecha desde el calendario emergente usando datos desde un archivo excel
  Para evitar errores al ingresar fechas manualmente

  @FechaMesDiferenteAlActual
  Escenario: Seleccion de una fecha especifica en un mes diferente al actual
    Dado que la usuaria abre la pagina del datepicker
    Cuando la usuaria selecciona la fecha del mes diferente al actual definida en el archivo "fecha_mes_diferente"
    Entonces la fecha mostrada en el campo corresponde a la fecha del archivo
    Y la fecha corresponde a un mes diferente a la actual