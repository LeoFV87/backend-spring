# Programación y Plataformas Web

# **Spring Boot – Paginación de Datos con Spring Data JPA: Optimización y User Experience**

# **9. Resultados y Evidencias Requeridas**

## **9.1. Datos para revisión**

**Usar un dataset de al menos 1000 productos**:
Crear un script de carga masiva para poblar la base de datos con datos variados:
- al menos 5 usuarios
- alemnos 2 categorias por producto  
- Precios variados ($10 - $5000)
- Nombres con texto buscable

**Script creado:**
 populate_db.py

## **9.2. Evidencias de funcionamiento** Caputuras de Postman Bruno o similar mostrando respuestas correctas

1. **Page response**: `GET /api/products?page=0&size=5` mostrando metadatos completos

![Page Response](assets/10/pageResponse.png)

2. **Slice response**: `GET /api/products/slice?page=0&size=5` sin totalElements

![Slice Response](assets/10/sliceResponse.png)

3. **Filtros + paginación**: `GET /api/products/search?name=laptop&page=0&size=3`

![Filtros + paginacion](assets/10/filtrosPaginacion.png)

4. **Ordenamiento**: `GET /api/products?sort=price,desc&page=1&size=5`

![Ordenamiento](assets/10/ordenamiento.png)


## **9.3. Evidencias de performance**

1. **Comparación**: Tiempos de respuesta Page vs Slice

**Consultas de prueba con volumen**: para cada uno Page y Slice

**Page**
![Consulta](assets/10/pageConTotales.png)

Tarda un total de 27ms.

**Slice**
![Consulta 2](assets/10/sliceSinTotales.png)

1. Primera página de productos (page=0, size=10)

**Page**
![Primera pagina](assets/10/primeraPaginaPage.png)

**Slice**
![Primera pagina slice](assets/10/primeraPaginaSlice.png)


2. Página intermedia (page=5, size=10) 

**Page**
![Pagina intermedia Page](assets/10/paginaIntermediaPage.png)

**Slice**

![Pagina intermedia slice](assets/10/paginaIntermediaSlice.png)

3. Últimas páginas para verificar performance

**Page**
![Ultimas paginas Page](assets/10/paginaUltimaPage.png)

**Slice**
![Ultimas paginas slice](assets/10/paginaUltimaSlice.png)


4. Búsquedas con pocos y muchos resultados

**Page**
![Busqueda muchos Page](assets/10/busquedaMuchosPage.png)

**Slice**
![Busqueda muchos slice](assets/10/busquedaMuchosSlice.png)


5. Ordenamiento por diferentes campos

Ordenamiento por ID (Ascendente):

**Page**
![Ordenamiento Id](assets/10/porIdPage.png)

**Slice**
![Ordenamiento Id](assets/10/porIdSlice.png)

Ordenamiento por nombre (Descendente):

**Page**
![Ordenamiento Id](assets/10/porNombrePage.png)

**Slice**
![Ordenamiento Id](assets/10/porNombreSlice.png)


Ordenamiento por precio (Mas caro primero):

**Page**
![Ordenamiento Id](assets/10/porPrecioPage.png)

**Slice**
![Ordenamiento Id](assets/10/porPrecioSlice.png)


# **10. Conclusiones**

Esta implementación de paginación en Spring Boot demuestra:

* **Paginación nativa**: Uso completo de Spring Data JPA Pageable
* **Flexibilidad**: Page vs Slice según necesidades de performance
* **Integración**: Filtros + paginación + ordenamiento en una sola consulta
* **Escalabilidad**: Funciona eficientemente con millones de registros
* **Usabilidad**: APIs REST estándar con metadatos completos
* **Performance**: Consultas optimizadas con índices apropiados

El enfoque implementado proporciona una base sólida para aplicaciones que requieren manejar grandes volúmenes de datos de manera eficiente, manteniendo una excelente experiencia de usuario y siguiendo las mejores prácticas de Spring Boot.