# Universidad Politécnica Salesiana


![Logo Institucional](https://upload.wikimedia.org/wikipedia/commons/b/b0/Logo_Universidad_Polit%C3%A9cnica_Salesiana_del_Ecuador.png)  

---


# 🚀 DOCUMENTACIÓN TÉCNICA: SISTEMA "LEMIBIT" V2.0
## Gestión Integral de Portafolios y Asesorías Técnicas con Arquitectura Distribuida


**Autor:** Leo Vásconez  
**Carrera:** Ingeniería en Ciencias de la Computación  
**Sede:** Cuenca, Ecuador  
**Materia:** Programación Web  
**Fecha:** Febrero 2026  

---

## 1. INTRODUCCIÓN Y JUSTIFICACIÓN DEL PROYECTO

En el ecosistema actual del desarrollo de software, la visibilidad del talento técnico es fundamental. **LeMiBit** nace como una solución para centralizar el portafolio de desarrolladores, permitiendo una transición fluida entre la academia y el mundo laboral. 

La versión 2.0 representa una evolución crítica: se migró de un modelo Serverless básico hacia una arquitectura **Monolito Modular** con un backend robusto en Spring Boot y una base de datos relacional PostgreSQL, permitiendo un control total sobre la seguridad y la persistencia de los datos.


---


## 2. ESPECIFICACIONES DEL STACK TECNOLÓGICO (FULL STACK)


### **2.1. Backend: El Motor con Spring Boot 3.4**
Se implementó un backend basado en Java 21, aprovechando las últimas mejoras en rendimiento y gestión de hilos.
* **Gestor de Dependencias:** Se utilizó **Gradle con Kotlin DSL (`build.gradle.kts`)**, lo que permitió una configuración más limpia y tipada, facilitando la detección de errores en tiempo de compilación.
* **Seguridad:** Implementación de **Spring Security** con una política de sesión **Stateless** mediante **JSON Web Tokens (JWT)**.
* **Documentación:** Integración de **SpringDoc OpenAPI 2.8.5**, configurada para ser compatible con el filtro de seguridad JWT, permitiendo la interacción con la API en tiempo real.




### **2.2. Frontend: Interfaz Reactiva con Angular 20**
El frontend fue diseñado para ser una **Single Page Application (SPA)** de alto rendimiento.
* **Gestión de Estado:** Migración total a **Angular Signals**, eliminando la necesidad de chequeos de cambios globales y optimizando la velocidad de respuesta de la UI.
* **Estilos:** Uso de **TailwindCSS** para un diseño atómico y **DaisyUI** para componentes de interfaz consistentes y accesibles.
* **Librerías Externas:** **Chart.js** para la capa analítica y **XLSX (SheetJS)** para la transformación de datos JSON a archivos binarios de Excel.


### **2.3. Base de Datos: PostgreSQL 16**
Se eligió PostgreSQL por su robustez en el manejo de relaciones complejas.
* **Esquema Relacional:** Definición estricta de tablas para Usuarios, Roles, Proyectos y Asesorías, garantizando que no existan datos huérfanos mediante llaves foráneas y restricciones de integridad.



---



## 3. ARQUITECTURA DE SEGURIDAD (JWT HANDSHAKE)


La seguridad es el pilar de LeMiBit. Se diseñó un flujo de autenticación cerrado para proteger los datos de los programadores:
1.  **Encriptación:** Las contraseñas se procesan mediante el algoritmo **BCrypt** antes de tocar la base de datos.
2.  **Generación de Token:** Tras un login exitoso, el backend emite un token firmado con una clave secreta personalizada.
3.  **Interceptor de Peticiones:** En Angular, se configuró un `AuthInterceptor` que añade automáticamente el encabezado `Authorization: Bearer <token>` a cada solicitud hacia el servidor.
4.  **Filtro de Seguridad:** El backend intercepta la petición, valida la firma del token y el rol del usuario antes de entregar los datos.


---

## 4. MÓDULOS DEL SISTEMA Y LÓGICA DE NEGOCIO

### **4.1. Dashboard Analítico (Business Intelligence)**
El Administrador tiene acceso a un panel visual donde se consumen los datos de las asesorías para generar métricas:
* **Algoritmo de Filtrado:** Los datos se agrupan en el frontend para alimentar gráficos de pastel que muestran el porcentaje de efectividad de los programadores (Aceptadas vs. Rechazadas).

### **4.2. Sistema de Reportería Pro**
Se implementó un servicio de exportación que permite:
* Mapear objetos de la base de datos a celdas de Excel.
* Generar archivos descargables con metadatos del sistema, permitiendo auditorías externas del rendimiento de los asesores.

### **4.3. Notificaciones Duales (WhatsApp & EmailJS)**
Al procesar una solicitud de asesoría, el sistema ejecuta una lógica de notificación inmediata:
* **EmailJS:** Se configuró un servicio que dispara un correo electrónico profesional al cliente, ocultando los detalles del servidor SMTP para mayor seguridad.
* **Deep Linking:** El sistema construye una URL de WhatsApp dinámica basada en el número del cliente y el mensaje personalizado, optimizando el tiempo de respuesta del programador.

---

## 5. RESOLUCIÓN DE DESAFÍOS TÉCNICOS (LOG DE DESARROLLO)

Durante la ejecución del proyecto, se resolvieron problemas críticos de ingeniería:
* **Conflicto de Versiones en Swagger:** Se detectó un error `NoSuchMethodError` debido a una incompatibilidad entre Spring Boot 3 y versiones antiguas de SpringDoc. Se resolvió forzando la versión **2.8.5** en el archivo de Gradle.
* **Error de Permisos EPERM:** Durante la migración de **NPM** a **pnpm**, se experimentaron bloqueos de archivos en Windows. La solución técnica fue revertir a NPM y limpiar la caché de `node_modules` para restaurar la integridad de los enlaces simbólicos de Angular.
* **Políticas de Ejecución:** Se configuró el entorno de PowerShell mediante `Set-ExecutionPolicy` para permitir la ejecución de scripts del CLI de Angular, esencial para el despliegue local.
* **Migración de Datos Remota:** Se implementaron flujos de respaldo mediante `pg_dump` y túneles remotos (Parsec) para mantener la consistencia de la base de datos entre diferentes estaciones de trabajo.



---

## 6. CONCLUSIONES Y TRABAJO FUTURO

El proyecto LeMiBit V2.0 demuestra que es posible integrar un stack tecnológico moderno (Spring + Angular) para crear aplicaciones escalables y seguras. La implementación de **JWT**, la documentación con **Swagger** y la analítica con **Chart.js** elevan el proyecto a un nivel profesional.

**Próximos Pasos:**
* Implementación de contenedores **Docker** para el despliegue simplificado.
* Añadir almacenamiento en la nube (AWS S3) para los archivos multimedia de los proyectos.
* Integración de pasarelas de pago para las asesorías premium.

---
**© 2026 - Leo Vásconez - Universidad Politécnica Salesiana**