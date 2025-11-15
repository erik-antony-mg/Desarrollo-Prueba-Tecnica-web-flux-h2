# 🏦 API Reactiva de Tipo de Cambio — CASO 01

Solución completa al **CASO 01** solicitada en la prueba técnica.  
El proyecto implementa un API reactiva para gestión de tipos de cambio, auditoría y autenticación mediante JWT.

---

# 🚀 Tecnologías utilizadas
- **Java 17**
- **Spring Boot 3 (WebFlux)**
- **Spring Security + JWT**
- **R2DBC + H2**
- **Maven**
- **Postman para pruebas**

---

# 📌 NOTA DE ENTREGA (REQUERIDA POR EL CASO)

Este repositorio contiene la solución completa del **CASO 01**, cumpliendo los requerimientos funcionales y no funcionales:

### ✔ Funcionalidades incluidas
- Realizar conversión de montos usando tipo de cambio.
- CRUD del tipo de cambio (crear, actualizar, buscar).
- Autenticación basada en **JWT**.
- Registro de **auditoría funcional**:  
  Cada operación (CREAR, MODIFICAR, CONVERTIR) queda registrada indicando:
    - Usuario (extraído del JWT)
    - Acción realizada
    - Detalle
    - Fecha y hora

### ✔ Requerimientos no funcionales cumplidos
- Java + Spring Boot.
- Programación reactiva (Spring WebFlux + R2DBC).
- Base de datos **H2**.
- Seguridad con **JWT**.
- Colección Postman incluida.
- Documentación de arquitectura incluida.

---

# 🔧 Cómo ejecutar el proyecto localmente

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/erik-antony-mg/Desarrollo-Prueba-Tecnica-web-flux-h2.git
```
## 2️⃣ Cómo ejecutar y probar el proyecto (con autenticación JWT)

---

### ▶️ Ejecutar el proyecto

```bash
mvn clean package
mvn spring-boot:run
```


### Crear un usuario (No requiere autenticación)

Este endpoint permite **registrar un nuevo usuario en el sistema**.  
**No requiere autenticación previa**, por lo tanto puede ser llamado directamente desde Postman.

**Endpoint:**  
```bash
POST http://localhost:8080/api/auth/register
```
<img width="1347" height="563" alt="Image" src="https://github.com/user-attachments/assets/617b2518-d898-4d2b-91a8-3fc98d532995" />
<img width="1353" height="563" alt="Image" src="https://github.com/user-attachments/assets/a3b5bf36-1a73-41f5-8f4b-c2d6a67be4fd" />

### Listar Usuarios (requiere autenticación)

Este endpoint permite **listar los usuarios de la base de datos**.  
**requiere autenticación y permisos de ADMIN**.

**Endpoint:**  
```bash
GET http://localhost:8080/api/usuarios/listar
```
## Usuario con Permido de ADMIN
<img width="1357" height="827" alt="Image" src="https://github.com/user-attachments/assets/b2d23ecb-ec62-4b84-8e1e-dd2198bccc4b" />

## Usuario con Permido de USER
<img width="1160" height="506" alt="Image" src="https://github.com/user-attachments/assets/f95ca739-5d76-4459-8c4f-133d5feae05d" />

### Crear Tipo de Cambio (requiere autenticación)

Este endpoint permite **Crear tipo de cambio**.  
**requiere autenticación y permisos de ADMIN**.

**Endpoint:**  
```bash
POST http://localhost:8080/api/cambio/registrar
```
## Usuario con Permido de ADMIN
<img width="1363" height="555" alt="Image" src="https://github.com/user-attachments/assets/c1159c99-effd-43bb-9f3d-fbb61948e981" />

## Usuario con Permido de USER
<img width="1160" height="506" alt="Image" src="https://github.com/user-attachments/assets/f95ca739-5d76-4459-8c4f-133d5feae05d" />

### Convierte la moneda al tipo de cambio (requiere autenticación)

Este endpoint permite **Convierte tipo de cambio**.  
**requiere autenticación pero cualquier roll puede hacerlo**.

**Endpoint:**  
```bash
POST http://localhost:8080/api/cambio/registrar
```
## Usuario con cualquier Rol
<img width="1392" height="628" alt="image" src="https://github.com/user-attachments/assets/02d43d14-7e36-4449-b291-4f2e55f396f6" />

### Modifica el tipo de cambio (requiere autenticación)

Este endpoint permite **Modificar tipo de cambio**.  
**requiere autenticación y permisos de ADMIN**.

**Endpoint:**  
```bash
PUT http://localhost:8080/api/cambio/actualizar
```
## Usuario con Permido de ADMIN
<img width="1378" height="680" alt="image" src="https://github.com/user-attachments/assets/637de2b4-e72a-474a-97f8-8d7f665d04ad" />



## Usuario con Permido de USER
<img width="1385" height="580" alt="image" src="https://github.com/user-attachments/assets/d6adcd4b-beac-42dd-83b6-e0b41d649964" />


### Muesta la lista de Auditoria  (requiere autenticación)

Este endpoint permite **Lista Reguistro de auditoria**.  
**requiere autenticación y permisos de ADMIN**.

**Endpoint:**  
```bash
GET http://localhost:8080/api/auditoria/listar
```
## Usuario con Permido de ADMIN
<img width="1400" height="841" alt="image" src="https://github.com/user-attachments/assets/8d5ffc4f-d6cc-4ff0-9eb8-038d1346c537" />


## Usuario con Permido de USER
<img width="1376" height="454" alt="image" src="https://github.com/user-attachments/assets/59c05fab-ba86-47ae-867d-acac329be06a" />




