# Reto Técnico SQA – Automatización DatePicker (HU-001)

Automatización de escenarios de prueba para la historia de usuario **HU-001: Selección de fecha en un campo de formulario** usando **Serenity BDD + Screenplay + Cucumber + Selenium + Gradle** sobre la URL pública:  
`https://jqueryui.com/datepicker/`

---

## Historia de Usuario (HU-001)

**Título:** Selección de fecha en un campo de formulario  
**Descripción:** Como usuario de la aplicación, quiero poder seleccionar una fecha desde un calendario emergente en el campo de entrada, para evitar errores al ingresar fechas manualmente.

### Criterios de aceptación
- Al hacer clic en el campo de fecha se debe mostrar el calendario emergente.
- El usuario puede seleccionar una fecha dentro del rango permitido.
- La fecha seleccionada se refleja en el input en el formato esperado (**MM/dd/yyyy**).
- El usuario puede navegar entre mes y año dentro del selector.
- Funciona en navegadores modernos (alcance: web/Chrome).
- Si hay rango restringido, fechas fuera del rango no deben ser seleccionables.
- La fecha seleccionada debe persistir y enviarse correctamente al sistema al enviar formulario (simulado).

---

## Punto 1 – Identificación de escenarios para automatizar

### Escenarios propuestos (derivados de HU-001)
1. **Apertura del calendario emergente** al hacer clic en el campo de fecha.
2. **Selección de fecha en el mes actual** y validación del formato en el input.
3. **Selección de una fecha específica en un mes diferente al actual** (navegando meses/años).
4. **Validación de rango restringido** (fechas fuera del rango no seleccionables).
5. **Validación de formato y persistencia** (fecha permanece en el campo y se “envía” correctamente al sistema).
6. **Compatibilidad web** (navegador moderno; alcance de este reto: Chrome).

### Priorización (Matriz de priorización)
| Caso de prueba | Nombre | Puntaje | Prioridad | Justificación resumida |
|---|---|---:|---|---|
| HU-001_CP001 | Selección fecha mismo mes | 0,69 | Media | Flujo base estable, alta repetición en regresión. |
| HU-001_CP002 | Selección fecha mes diferente | 0,77 | Alta | Alto valor: valida navegación mes/año + selección. |
| HU-001_CP003 | Validación formato y persistencia | 0,45 | Media | Asegura dato final en input con formato esperado. |
| HU-001_CP004 | Validación rango restringido | 0,38 | Baja | Requiere configuración/rango; valor menor en este reto. |

### Escenarios seleccionados para automatizar (2)
- ✅ **HU-001_CP002 – Selección de fecha en un mes diferente al actual** (prioridad Alta – 0,77)  
- ✅ **HU-001_CP003 – Validación de formato y persistencia** (prioridad Media – 0,45)

---

## Enfoque y arquitectura

### Patrón de diseño
- **Screenplay Pattern** (Serenity BDD)

### Tecnologías
- Java 17
- Serenity BDD
- Selenium WebDriver
- Cucumber (Gherkin)
- Gradle
- Apache POI (Data Driven desde Excel)

### Estructura del proyecto (alto nivel)
- `features/` → Escenarios en Gherkin
- `tasks/` → Acciones del usuario (abrir página, seleccionar fecha, etc.)
- `questions/` → Validaciones (valor del input, mes diferente al actual, etc.)
- `userinterfaces/` → Targets / localizadores centralizados
- `utils/` → Data Driven (lectura desde Excel), excepciones

---

## Datos de prueba (Data Driven)

Los datos se leen desde Excel:

- Ruta (classpath): `src/test/resources/data/DatosPruebaDatepicker.xlsx`
- Columnas requeridas:
  - `case`
  - `date` (formato: `MM/dd/yyyy`)

Ejemplo:

| case | date |
|---|---|
| fecha_mes_diferente | 02/15/2026 |

---

## Configuración por variables de entorno (Buenas prácticas)

El proyecto está preparado para ejecutar sin hardcodear URIs en el código, usando variables de entorno.

### Variables
- `DATEPICKER_URL` → URL del sitio a automatizar  
  Ej: `https://jqueryui.com/datepicker/`
- `EXCEL_DATEPICKER_PATH` → Ruta en classpath del Excel  
  Ej: `/data/DatosPruebaDatepicker.xlsx`

> En ejecución local (IntelliJ), configurar en **Run Configuration → Environment variables**.

---

## Ejecución

### Desde consola (Gradle)
```bash
./gradlew clean test aggregate

## Evidence and Reports

### Quick links
- Sonar evidence: `evidence/sonar/sonar-evidence.pdf`
- Serenity report: `evidence/serenity/index.html`

