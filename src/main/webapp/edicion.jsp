
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Proveedor</title>
    <%@ include file="/assets/html/head.jsp" %>
</head>
<body class="bg-dark mt-60">
<%@ include file="/assets/html/header.jsp" %>

<div class="container bg-dark text-white mt-120">
    <h3>Editar Proveedor</h3>
    <hr class="my-4">
    <form action="servlet-provedor" method="post">
        <%
            String[] supplier = (String[]) request.getAttribute("supplier");
        %>
        <input type="hidden" name="id" value="<%= supplier != null ? supplier[0] : "" %>">
        <section class="container">
            <table class="table table-striped">
                <tbody>
                <tr>
                    <td><strong><label for="name">Nombre:</label></strong></td>
                    <td><input type="text" id="name" name="name" value="<%= supplier != null ? supplier[1] : "" %>" required></td>
                </tr>
                <tr>
                    <td><strong><label for="rut">RUT:</label></strong></td>
                    <td><input type="text" id="rut" name="rut" value="<%= supplier != null ? supplier[2] : "" %>" required></td>
                </tr>
                <tr>
                    <td><strong><label for="direction">Dirección:</label></strong></td>
                    <td><input type="text" id="direction" name="direction" value="<%= supplier != null ? supplier[3] : "" %>" required></td>
                </tr>
                <tr>
                    <td><strong><label for="email">Correo:</label></strong></td>
                    <td><input type="text" id="email" name="email" value="<%= supplier != null ? supplier[4] : "" %>" required></td>
                </tr>
                <tr>
                    <td><strong><label for="phone">Teléfono:</label></strong></td>
                    <td><input type="text" id="phone" name="phone" value="<%= supplier != null ? supplier[5] : "" %>" required></td>
                </tr>
                <tr>
                    <td><strong><label for="contact">Contacto:</label></strong></td>
                    <td><input type="text" id="contact" name="contact" value="<%= supplier != null ? supplier[6] : "" %>" required></td>
                </tr>
                <tr>
                    <td><strong><label for="contact_phone_number">Teléfono de Contacto:</label></strong></td>
                    <td><input type="text" id="contact_phone_number" name="contact_phone_number" value="<%= supplier != null ? supplier[7] : "" %>" required></td>
                </tr>
                </tbody>
            </table>
        </section>
        <button type="submit" class="btn btn-primary">Guardar</button>
        <a href="servlet-provedor" class="btn btn-secondary">Cancelar</a>

    </form>
</div>
<div class="mt-120">
    <%@ include file="assets/html/footer.jsp" %>
</div>
</body>
</html>