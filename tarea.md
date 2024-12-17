# Tareas para la Implementación de Reservas de Pistas Deportivas

Una vez hemos visto una aplicación básica con Spring Boot, ahora nos toca preparar la parte de **reservas de pistas deportivas**.

## 1. Seguridad

- **Objetivo:** Terminar la configuración de seguridad para que cada persona vea solo su parte correspondiente, y finalmente, las reservas.

## 2. CRUD de Reservas

- **Ruta:** `/reservas`
- **Objetivo:** Completar el CRUD de reservas para los roles **ADMIN** y **OPERARIO**.
- **Acciones a realizar:**
  - Hacer el controlador correspondiente.
  - Proteger la ruta adecuadamente.

## 3. Reservas del Usuario

- **Ruta:** `/mis-datos/mis-reservas`
- **Objetivo:** Mostrar las reservas del usuario que ha iniciado sesión.
- **Acciones a realizar:**
  - Ampliar el controlador para gestionar esta funcionalidad.
  - Proteger la ruta adecuadamente.

## Reglas de Reserva

### 4. Solo una reserva al día

- **Condición:** Un usuario solo podrá realizar **una reserva por día**.
- **Validación:** Si el usuario ya tiene una reserva en la misma fecha, se lanzará una excepción con un mensaje específico.

### 5. No se puede reservar con más de una semana de antelación

- **Condición:** Las reservas no podrán hacerse con más de **una semana de antelación**.
- **Validación:** Se garantiza que las fechas de las reservas sean válidas. Si se intenta hacer una reserva con más de una semana de antelación o en una fecha pasada, se lanzará una excepción con un mensaje descriptivo.

### 6. No se puede actualizar reservas que ya han pasado

- **Condición:** No se pueden actualizar reservas que ya han pasado.
- **Objetivo:** Evitar que se borren reservas pasadas para no tener que pagarlas. También se evitará actualizar reservas programadas para el **día actual**.

---
Este esquema organiza las tareas y reglas de manera clara, facilitando la implementación y el seguimiento de cada apartado. 


==================================================================================================


Una vez hemos visto una aplicación básica con Spring Boot ahora nos toca preparar la parte de reservas de pistas deportivas. 


·Hay que terminar la seguridad para que cada persona vea sólo su parte y finalmente las reservas.


·Hay que completar el CRUD de reservas completo para ADMIN y OPERARIO en "/reservas". 
·Hacer controlador y proteger la ruta.


·Hay que hacer las reservas del usuario que ha hecho LOGIN en "/mis-datos/mis-reservas". 
·Ampliar controlador y proteger ruta.


- Sólo una reserva al día
- Hay que garantizar que no se realice una reserva si el usuario ya tiene una reserva en la misma fecha, lanzando una excepción con un mensaje específico en caso de que la condición se cumpla. Esto ayuda a mantener la integridad de los datos y a aplicar una lógica de negocio específica en el nivel de la base de datos.


- No se puede reservar con más de una semana de antelación
- Hay que garantizar que las fechas de las reservas sean válidas, evitando reservas en fechas anteriores a la actual y con más de dos semanas de antelación. Si alguna de estas condiciones no se cumple, se lanzará una excepción con un mensaje descriptivo.


- No se puede actualizar reservas que ya han pasado
- Para evitar que alguien borre una reserva pasada para no tener que pagarla, el objetivo es evitar que se actualicen reservas que ya han pasado o que están programadas para el día actual.