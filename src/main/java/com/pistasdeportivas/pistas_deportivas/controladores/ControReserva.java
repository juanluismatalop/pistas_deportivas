<!DOCTYPE html>
<html lang="es">
<head th:replace="~{plantilla/fragmentos.html :: headfiles}">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Reservas</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container-fluid">
        <div th:replace="plantilla/fragmentos.html :: navigation"></div>

        <h3>Reservas Deportivas</h3>
        
        <!-- Listado de Reservas -->
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Instalación</th>
                    <th>Fecha</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="reserva : ${reservas}">
                    <td th:text="${reserva.id}"></td>
                    <td th:text="${reserva.instalacion.nombre}"></td>
                    <td th:text="${reserva.fecha}"></td>
                    <td>
                        <a th:href="@{/reserva/edit/{id}(id=${reserva.id})}" class="btn btn-warning">Editar</a>
                        <a th:href="@{/reserva/del/{id}(id=${reserva.id})}" class="btn btn-danger">Eliminar</a>
                    </td>
                </tr>
            </tbody>
        </table>
        
        <a href="/reserva/add" class="btn btn-success">Añadir Reserva</a>

        <h3>Crear / Editar Reserva</h3>
        <form method="post" th:object="${reserva}" class="needs-validation" novalidate>
            <input type="hidden" name="id" th:value="${reserva.id}" />
            
            <div class="mb-3">
                <label for="instalacion" class="form-label">Instalación</label>
                <select id="instalacion" name="instalacion" class="form-control" th:attr="disabled=${borrando} != null ? 'disabled' : null">
                    <option th:each="instalacion : ${instalaciones}" th:value="${instalacion.id}" th:text="${instalacion.nombre}" th:selected="${instalacion.id == reserva.instalacion.id}"></option>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="fecha" class="form-label">Fecha</label>
                <input type="date" id="fecha" name="fecha" class="form-control" th:value="${reserva.fecha}" />
            </div>
            
            <button type="submit" class="btn btn-primary">Guardar</button>
        </form>

        <div th:replace="plantilla/fragmentos.html ::footer"></div>
    </div>
</body>
</html>
