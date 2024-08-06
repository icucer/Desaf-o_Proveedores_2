<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Proveedores</title>
    <%@ include file="/assets/html/head.jsp" %>
</head>
<body class="bg-dark mt-60">
<%@ include file="/assets/html/header.jsp" %>
<div class="container bg-dark text-white mt-120">
    <h3>Registrar un nuevo proveedor</h3>
    <hr class="my-4">
    <form action="servlet-provedor" method="post">
        <section  class="container">
            <table class="table table-hover">
                <tbody>
                <tr>
                    <td><strong><label for="name">Nomre:</label></strong></td>
                    <td><input type="text" id="name" name="name" placeholder="Nombre completo" required></td>
                    <td><strong><label for="rut">RUT:</label></strong></td>
                    <td><input type="text" id="rut" name="rut" placeholder="Formato XX.XXX.XXX-X" required></td>
                </tr>
                <tr>
                    <td><strong><label for="direction">Direccion:</label></strong></td>
                    <td><input type="text" id="direction" name="direction" placeholder="Direccion del proveedor" required></td>
                    <td><strong><label for="email">Correo:</label></strong></td>
                    <td><input type="text" id="email" name="email" placeholder="Correo del provedor" required></td>
                </tr>
                <tr>
                    <td><strong><label for="phone">Telefono proveedor:</label></strong></td>
                    <td><input type="text" id="phone" name="phone" placeholder="Formato +569XXXXXXXX" required></td>
                    <td><strong><label for="contact">Contacto:</label></strong></td>
                    <td><input type="text" id="contact" name="contact" placeholder="Nomre del contacto" required></td>
                </tr>
                <tr>
                    <td><strong><label for="contact_phone_number">Telefono de contacto:</label></strong></td>
                    <td><input type="text" id="contact_phone_number" name="contact_phone_number" placeholder="Formato +569XXXXXXXX" required></td>
                    <td></td>
                    <td><button type="submit" class="btn btn-primary">Guardar proveedor</button></td>
                </tr>
                </tbody>
            </table>
        </section>
    </form>
    <section class="container mt-60">
        <h3>Lista de Proveedores</h3>
        <hr class="my-4">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>RUT</th>
                <th>Dirección</th>
                <th>Correo</th>
                <th>Teléfono</th>
                <th>Contacto</th>
                <th>Teléfono de contacto</th>
                <th>Modificar o Eliminar</th>
            </tr>
            </thead>
            <tbody>
            <%
                java.util.List<String[]> suppliers = (java.util.List<String[]>) request.getAttribute("suppliers");
                if (suppliers != null && !suppliers.isEmpty()) {
                    for (String[] supplier : suppliers) {
            %>
            <tr>
                <td><%= supplier[0] %></td>
                <td><%= supplier[1] %></td>
                <td><%= supplier[2] %></td>
                <td><%= supplier[3] %></td>
                <td><%= supplier[4] %></td>
                <td><%= supplier[5] %></td>
                <td><%= supplier[6] %></td>
                <td><%= supplier[7] %></td>
                <td>
                    <button style="background-color: yellow; color: black;"
                            onclick="window.location.href='servlet-provedor?id=<%= supplier[0] %>'">
                        Modificar
                    </button>
                    <button style="background-color: red; color: white;"
                            onclick="if(confirm('¿Está seguro de que desea eliminar este proveedor?')) { window.location.href='servlet-provedor?action=delete&id=<%= supplier[0] %>'; }">
                        Eliminar
                    </button>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="8">No hay proveedores registrados.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </section>

</div>
<div class="mt-120">
    <%@ include file="assets/html/footer.jsp" %>
</div>
</body>
</html>