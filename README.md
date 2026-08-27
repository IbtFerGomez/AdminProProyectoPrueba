# AdminPro - RecuperaT Gestion Clinica

AdminPro es una plataforma de gestion administrativa para RecuperaT, una clinica de rehabilitacion. El proyecto centraliza en una sola interfaz la gestion de pacientes, tratamientos, citas, caja, inventario, autorizaciones y roles de usuario.

Actualmente el proyecto se encuentra en fase de prototipo funcional: el frontend tiene las pantallas y flujos principales implementados con datos simulados en memoria, mientras que el backend contiene la configuracion inicial de Spring Boot, PostgreSQL y el modelo JPA base.

> Estado importante: todavia no existe una API REST conectada al frontend. Las operaciones realizadas desde la interfaz se pierden al recargar la pagina.

## Indice

- [Arquitectura](#arquitectura)
- [Tecnologias](#tecnologias)
- [Estructura del repositorio](#estructura-del-repositorio)
- [Frontend](#frontend)
- [Backend](#backend)
- [Modelo de datos](#modelo-de-datos)
- [Base de datos](#base-de-datos)
- [Instalacion](#instalacion)
- [Ejecucion](#ejecucion)
- [Scripts disponibles](#scripts-disponibles)
- [Funcionalidades](#funcionalidades)
- [Configuracion](#configuracion)
- [Estado de implementacion](#estado-de-implementacion)
- [Proximos pasos](#proximos-pasos)

## Arquitectura

La solucion esta separada en tres partes:

```text
Usuario
  |
  v
Frontend React/Vite  --->  API REST Spring Boot  --->  PostgreSQL
       |                         |
       |                         +-- JPA/Hibernate
       |
       +-- Datos mock locales mientras la API no esta implementada

Docker Compose ejecuta PostgreSQL durante el desarrollo.
```

### Frontend

Aplicacion SPA construida con React y TypeScript. `App.tsx` funciona como orquestador principal y controla:

- Sesion del usuario.
- Navegacion entre pantallas mediante `currentScreen`.
- Estado local de pacientes, citas, autorizaciones, roles, inventario y transacciones.
- Apertura y cierre de modales.
- Menu responsive para dispositivos moviles.
- Busqueda global.
- Notificaciones tipo toast.

No se utiliza React Router actualmente. La navegacion se resuelve mediante estado local.

### Backend

Aplicacion Spring Boot configurada con Gradle y Java 21. Incluye dependencias para:

- Web MVC.
- Spring Data JPA.
- Spring Security.
- Validacion.
- PostgreSQL.
- Pruebas de Spring.

El backend tiene la clase de arranque y las entidades principales, pero aun no contiene controladores REST, servicios o repositorios visibles.

### Persistencia

PostgreSQL se ejecuta mediante Docker Compose. Hibernate esta configurado con `ddl-auto: update`, por lo que actualiza el esquema a partir de las entidades JPA durante el desarrollo.

## Tecnologias

### Frontend

- React `19.0.1`.
- React DOM `19.0.1`.
- TypeScript `5.8.x`.
- Vite `6.2.x`.
- Tailwind CSS `4.1.x`.
- `@tailwindcss/vite`.
- Lucide React para iconos.
- Motion para animaciones.
- Express y dotenv declarados para posibles necesidades de servidor o integracion.
- Google GenAI declarado para futuras funcionalidades con Gemini.

### Backend

- Java 21.
- Spring Boot `4.1.1`.
- Gradle mediante Gradle Wrapper.
- Spring Web MVC.
- Spring Data JPA.
- Spring Security.
- Spring Validation.
- PostgreSQL JDBC Driver.
- JUnit Platform.

### Infraestructura

- Docker Compose `3.8`.
- PostgreSQL `15`.
- Volumen Docker persistente `pgdata`.

## Estructura del repositorio

```text
.
├── docker-compose.yml
├── README.md
├── backend/
│   ├── build.gradle
│   ├── settings.gradle
│   ├── gradlew
│   ├── gradlew.bat
│   └── src/
│       ├── main/
│       │   ├── java/com/recuperat/adminpro/
│       │   │   ├── AdminProApplication.java
│       │   │   └── model/
│       │   │       ├── Paciente.java
│       │   │       ├── Rol.java
│       │   │       ├── Sucursal.java
│       │   │       └── Usuario.java
│       │   │   └── resources/
│       │   │       └── application.yml
│       │   └── test/
│       │       └── java/com/recuperat/adminpro/
│       │           └── AdminProApplicationTests.java
│       └── ...
├── frontend/
│   ├── package.json
│   ├── index.html
│   ├── vite.config.ts
│   ├── tsconfig.json
│   └── src/
│       ├── App.tsx
│       ├── main.tsx
│       ├── index.css
│       ├── types.ts
│       ├── data/mockData.ts
│       └── assets/components/
│           ├── AdminProLogo.tsx
│           ├── AgendaScreen.tsx
│           ├── AuthorizationRequestsScreen.tsx
│           ├── CreateRoleScreen.tsx
│           ├── DashboardScreen.tsx
│           ├── FinanzasScreen.tsx
│           ├── InventarioScreen.tsx
│           ├── LoginScreen.tsx
│           ├── PatientsScreen.tsx
│           ├── Sidebar.tsx
│           ├── TopBar.tsx
│           └── modals/
│               ├── CheckoutModal.tsx
│               ├── NewAppointmentModal.tsx
│               ├── NewPatientModal.tsx
│               └── PatientDetailModal.tsx
└── ...
```

La ubicacion actual de los componentes es `frontend/src/assets/components`. Si los imports del frontend apuntan a `frontend/src/components`, hay que unificar la estructura antes del build.

## Frontend

### Punto de entrada

- `frontend/src/main.tsx`: monta `App` dentro de `React.StrictMode`.
- `frontend/src/App.tsx`: componente raiz y coordinador de la aplicacion.
- `frontend/src/index.css`: Tailwind, estilos base, patrones visuales y scrollbar.
- `frontend/index.html`: documento HTML, idioma espanol, metadatos y fuentes.

### Tipos de dominio

Los contratos del frontend estan en `frontend/src/types.ts`:

- `ScreenType`.
- `User`.
- `Patient`.
- `AuthorizationRequest`.
- `RolePermission`.
- `Role`.
- `Appointment`.
- `InventoryItem`.
- `Transaction`.

### Pantallas

#### Login

`LoginScreen.tsx` incluye:

- Formulario de correo y contrasena.
- Validacion de campos obligatorios.
- Estado visual de carga.
- Login rapido para demostracion.
- Recuperacion de contrasena simulada.
- Seleccion de rol demo.

La autenticacion no esta conectada a Spring Security. Cualquier usuario que introduzca valores no vacios puede acceder al prototipo.

#### Dashboard

`DashboardScreen.tsx` muestra:

- Pacientes activos.
- Citas.
- Autorizaciones pendientes.
- Ingresos.
- Solicitudes recientes.
- Pacientes con paquetes por renovar.
- Accesos rapidos a las areas principales.

#### Pacientes

`PatientsScreen.tsx` ofrece:

- Listado de pacientes.
- Busqueda por nombre, folio, tratamiento o terapeuta.
- Filtros por estado: todos, activos, por renovar y finalizados.
- Filtro por terapeuta.
- Paginacion local.
- Alta de pacientes.
- Consulta y edicion del detalle.
- Programacion de citas.
- Renovacion de paquetes.

#### Agenda

`AgendaScreen.tsx` gestiona visualmente:

- Citas.
- Paciente asociado.
- Terapeuta.
- Tratamiento.
- Fecha y hora.
- Estado de la cita.
- Numero de sesion.

#### Finanzas

`FinanzasScreen.tsx` contiene:

- Ingresos del mes.
- Ticket promedio.
- Lista de transacciones.
- Metodos de pago.
- Cobro en caja.
- Acceso al flujo de autorizaciones.
- Exportacion de corte simulada.

#### Inventario

`InventarioScreen.tsx` controla visualmente:

- Codigo del articulo.
- Nombre.
- Categoria.
- Stock actual.
- Stock minimo.
- Unidad.
- Estado normal, bajo o critico.

La modificacion del stock solo actualiza el estado local.

#### Autorizaciones

`AuthorizationRequestsScreen.tsx` gestiona solicitudes de:

- Descuento especial.
- Modificacion de factura.
- Anulacion de pago.
- Ajuste de saldo.

Cada solicitud puede tener estado pendiente, aprobado o rechazado, incluyendo autor de resolucion, motivo de rechazo, importe y detalles.

#### Roles

`CreateRoleScreen.tsx` permite crear perfiles con:

- Nombre.
- Descripcion.
- Usuarios asignados.
- Permisos seleccionados.
- Indicador de rol del sistema.

La gestion de roles es actualmente visual y local.

### Modales

- `NewPatientModal.tsx`: registra pacientes.
- `PatientDetailModal.tsx`: consulta y actualiza expedientes.
- `NewAppointmentModal.tsx`: agenda citas.
- `CheckoutModal.tsx`: procesa cobros y solicita autorizaciones de descuentos.

### Datos de demostracion

`frontend/src/data/mockData.ts` contiene datos iniciales para:

- Usuario actual.
- Pacientes.
- Solicitudes de autorizacion.
- Roles.
- Citas.
- Inventario.
- Transacciones.

Estos datos no sustituyen una base de datos y se reinician al recargar la aplicacion.

## Backend

### Arranque

La clase principal es:

```text
com.recuperat.adminpro.AdminProApplication
```

La configuracion de build se encuentra en `backend/build.gradle` y el nombre del proyecto en `backend/settings.gradle`.

### Paquetes actuales

El paquete base es:

```text
com.recuperat.adminpro
```

Actualmente contiene:

- `AdminProApplication.java`.
- `model/` con las entidades JPA.

Las capas siguientes estan pendientes de implementacion:

```text
controller/
service/
repository/
dto/
config/
exception/
security/
```

### Pruebas

El backend contiene una prueba de contexto:

```java
@SpringBootTest
void contextLoads()
```

Esta prueba confirma la carga del contexto de Spring, pero no cubre reglas de negocio ni endpoints.

## Modelo de datos

### `Paciente`

Tabla: `paciente`.

- `id`: UUID generado por JPA.
- `folio`: obligatorio y unico.
- `nombre`: obligatorio.
- `telefono`: obligatorio.
- `correo`.
- `direccion`.
- `contactoEmergencia`.
- `sucursalOrigen`: relacion `ManyToOne` con `Sucursal`.
- `fechaIngreso`: `LocalDate`.
- `tipoIngreso`: derivacion o valoracion interna.
- `aseguradora`: booleano.

### `Usuario`

Tabla: `usuario`.

- `id`: UUID generado por JPA.
- `nombre`: obligatorio.
- `credenciales`: obligatorio y unico; representa email o username.
- `rol`: relacion `ManyToOne` con `Rol`.
- `sucursalesAsignadas`: relacion `ManyToMany` con `Sucursal`.
- `activo`.
- `twoFaHabilitado`.

Tabla intermedia de usuarios y sucursales: `usuario_sucursal`.

### `Rol`

Tabla: `rol`.

- `id`: UUID generado por JPA.
- `nombre`: obligatorio y unico.

La matriz de permisos todavia no esta modelada en el backend.

### `Sucursal`

Tabla: `sucursal`.

- `id`: UUID generado por JPA.
- `nombre`: obligatorio.
- `direccion`.
- `telefono`.
- `whatsappBusiness`.

## Base de datos

El archivo `docker-compose.yml` define el servicio `db`:

| Propiedad | Valor de desarrollo |
|---|---|
| Imagen | `postgres:15` |
| Contenedor | `adminpro-db` |
| Base de datos | `adminpro_db` |
| Usuario | `adminpro` |
| Puerto | `5432` |
| Volumen | `pgdata:/var/lib/postgresql/data` |
| Reinicio | `unless-stopped` |

La conexion del backend se configura en `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/adminpro_db
    username: adminpro
    password: adminpro_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

Estas credenciales son solo para desarrollo local. En produccion deben utilizarse variables de entorno o un gestor de secretos.

## Requisitos previos

- Windows, macOS o Linux.
- Java 21.
- Docker Desktop con Docker Compose.
- Node.js y npm compatibles con Vite 6.
- Git, recomendado.

Verificar herramientas:

```powershell
java -version
docker --version
docker compose version
node --version
npm --version
```

## Instalacion

### 1. Clonar o abrir el proyecto

Situarse en la carpeta raiz del proyecto:

```powershell
cd "I:\Mi unidad\DeutchUltra\MIS PROYECTOS\Recuperat-AdminPro\proyecto prueba"
```

### 2. Levantar PostgreSQL

```powershell
docker compose up -d db
```

Comprobar el contenedor:

```powershell
docker compose ps
```

### 3. Instalar dependencias del frontend

```powershell
cd frontend
npm install
```

Volver a la raiz cuando sea necesario:

```powershell
cd ..
```

## Ejecucion

### Frontend

Desde `frontend/`:

```powershell
npm run dev
```

Vite utiliza el puerto `3000` y escucha en todas las interfaces:

```text
http://localhost:3000
```

Para abrir la aplicacion en la red local, Vite utiliza la configuracion `--host 0.0.0.0`.

### Backend

Desde `backend/` en Windows:

```powershell
cd backend
.\gradlew.bat bootRun
```

En macOS o Linux:

```bash
cd backend
./gradlew bootRun
```

El backend utiliza la configuracion de Spring Boot y conecta a PostgreSQL en `localhost:5432`. Todavia no hay endpoints REST documentados para consumir desde el frontend.

### Orden recomendado

1. Iniciar Docker Desktop.
2. Ejecutar `docker compose up -d db`.
3. Iniciar el backend con Gradle.
4. Iniciar el frontend con Vite.
5. Abrir `http://localhost:3000`.

## Scripts disponibles

### Frontend

Ejecutar desde `frontend/`:

| Comando | Descripcion |
|---|---|
| `npm run dev` | Inicia el servidor de desarrollo Vite en el puerto 3000. |
| `npm run build` | Genera el build de produccion. |
| `npm run preview` | Sirve localmente el build generado. |
| `npm run lint` | Ejecuta TypeScript sin emitir archivos. |
| `npm run clean` | Elimina `dist` y `server.js`; el script usa sintaxis Unix. |

### Backend

Ejecutar desde `backend/`:

| Comando | Descripcion |
|---|---|
| `.\gradlew.bat bootRun` | Inicia Spring Boot en Windows. |
| `.\gradlew.bat test` | Ejecuta las pruebas. |
| `.\gradlew.bat build` | Compila y empaqueta el backend. |
| `.\gradlew.bat clean` | Limpia los artefactos de Gradle. |

## Configuracion y variables de entorno

El frontend incluye `frontend/.env.example` con variables preparadas para futuras integraciones:

- `GEMINI_API_KEY`: clave para llamadas a Gemini.
- `APP_URL`: URL de despliegue de la aplicacion.

No se deben subir claves reales al repositorio. Crear un archivo `.env` local a partir del ejemplo cuando la integracion lo requiera.

La configuracion actual de PostgreSQL esta escrita en `application.yml` para desarrollo. Para produccion se recomienda migrarla a variables como:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

## Estado de implementacion

| Area | Estado actual |
|---|---|
| Interfaz visual | Implementada para el prototipo |
| Navegacion SPA | Implementada con estado local |
| Pacientes | Flujo visual y datos mock |
| Agenda | Flujo visual y datos mock |
| Finanzas | Flujo visual y datos mock |
| Inventario | Flujo visual y datos mock |
| Autorizaciones | Flujo visual y datos mock |
| Roles | Flujo visual y datos mock |
| PostgreSQL | Configurado con Docker Compose |
| Entidades JPA | Modelo inicial implementado |
| API REST | Pendiente |
| Repositorios | Pendiente |
| Servicios de negocio | Pendiente |
| DTOs | Pendiente |
| Autenticacion real | Pendiente |
| Autorizacion por permisos | Pendiente |
| Persistencia desde frontend | Pendiente |
| Migraciones de base de datos | Pendiente |
| Exportacion PDF real desde la aplicacion | Pendiente |
| Pruebas frontend | Pendiente |
| Pruebas de negocio y endpoints | Pendiente |
| Docker para backend/frontend | Pendiente |

## Seguridad

Antes de utilizar el sistema con datos reales deben implementarse al menos:

- Autenticacion real con contrasenas almacenadas de forma segura.
- Hash de contrasenas con un algoritmo apropiado.
- JWT o sesiones seguras.
- Expiracion y renovacion de sesiones.
- Control de acceso por rol y permiso.
- Proteccion de endpoints.
- Validacion de DTOs en servidor.
- Auditoria de aprobaciones, anulaciones y cambios clinicos.
- Gestion de secretos fuera del codigo fuente.
- HTTPS.
- Politica de copias de seguridad de PostgreSQL.
- Proteccion de datos personales y expedientes clinicos.
- Revisión de cumplimiento normativo aplicable, incluida NOM-004-SSA3 si corresponde al uso real del sistema.

No usar las credenciales incluidas en Docker Compose en un entorno productivo.

## Proximos pasos recomendados

1. Instalar las dependencias del frontend y ejecutar `npm run build`.
2. Confirmar y unificar la ruta de los imports de componentes (`src/components` frente a `src/assets/components`).
3. Crear repositorios Spring Data para las entidades existentes.
4. Implementar servicios de negocio.
5. Definir DTOs de entrada y salida.
6. Crear controladores REST para pacientes, citas, usuarios, roles, sucursales, inventario, finanzas y autorizaciones.
7. Implementar autenticacion y autorizacion real.
8. Sustituir `mockData.ts` por servicios HTTP desde el frontend.
9. Agregar entidades pendientes para citas, tratamientos, sesiones, inventario, transacciones y autorizaciones.
10. Crear migraciones versionadas con Flyway o Liquibase.
11. Añadir pruebas unitarias, de repositorio, seguridad y endpoints.
12. Crear una configuracion separada para desarrollo, pruebas y produccion.
13. Containerizar backend y frontend junto con PostgreSQL.
14. Configurar observabilidad, logs y copias de seguridad.

## Documentacion adicional

- `backend/HELP.md`: ayuda generada por Spring Initializr/Gradle.
- `frontend/.env.example`: variables preparadas para integraciones del frontend.
- `resumen-proyecto-adminpro.pdf`: resumen visual generado del estado del proyecto, si se encuentra disponible en la carpeta del proyecto.

## Licencia

No se ha definido una licencia de distribucion para este repositorio.
