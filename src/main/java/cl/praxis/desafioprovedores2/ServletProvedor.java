package cl.praxis.desafioprovedores2;


import cl.praxis.desafioprovedores2.conexion.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "servlet-provedor", value = "/servlet-provedor")
public class ServletProvedor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action) && idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                deleteSupplier(id);
                response.sendRedirect("servlet-provedor");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Error al eliminar el proveedor", e);
            }
        }

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                String[] supplier = getSupplierById(id);

                // Depuración
                if (supplier == null || supplier.length == 0) {
                    System.out.println("No se encontró el proveedor con ID: " + id);
                } else {
                    System.out.println("Proveedor cargado: " + Arrays.toString(supplier));
                }

                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("edicion.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Error al obtener el proveedor", e);
            }
        } else {
            // Lógica para listar todos los proveedores
            try {
                List<String[]> suppliers = retrieveSuppliers();
                request.setAttribute("suppliers", suppliers);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Error al procesar la solicitud", e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String[] parameters = {"name", "rut", "direction", "email", "phone", "contact", "contact_phone_number"};
        String[] values = new String[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            values[i] = request.getParameter(parameters[i]);
            String error = validateParameter(values[i], parameters[i]);
            if (error != null) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
        }

        String name = values[0];
        String rut = values[1];
        String direction = values[2];
        String email = values[3];
        String phone = values[4];
        String contact = values[5];
        String contactPhoneNumber = values[6];

        try {
            if (idParam != null && !idParam.trim().isEmpty()) {
                // Actualizar el proveedor existente
                int id = Integer.parseInt(idParam);
                updateSupplier(id, name, rut, direction, email, phone, contact, contactPhoneNumber);
            } else {
                // Insertar un nuevo proveedor
                insertSupplier(name, rut, direction, email, phone, contact, contactPhoneNumber);
            }

            response.sendRedirect("servlet-provedor");
        } catch (SQLException e) {
            throw new RuntimeException("Error al procesar la solicitud", e);
        }
    }

    private String validateParameter(String valor, String nombreParametro) {
        if (valor == null || valor.trim().isEmpty()) {
            return nombreParametro + " es requerido";
        }
        return null;
    }

    private void insertSupplier(String name, String rut, String address, String email, String phone, String contact, String contactPhoneNumber) throws SQLException {
        String query = "INSERT INTO provedores (nombre, rut, direccion, correo, telefono, contacto, telefono_contacto) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, rut);
            statement.setString(3, address);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setString(6, contact);
            statement.setString(7, contactPhoneNumber);

            statement.executeUpdate();
        }
    }

    private List<String[]> retrieveSuppliers() throws SQLException {
        String query = "SELECT * FROM provedores ORDER BY nombre ASC";
        List<String[]> suppliers = new ArrayList<>();

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String[] supplier = new String[8];
                supplier[0] = String.valueOf(resultSet.getInt("id"));
                supplier[1] = resultSet.getString("nombre");
                supplier[2] = resultSet.getString("rut");
                supplier[3] = resultSet.getString("direccion");
                supplier[4] = resultSet.getString("correo");
                supplier[5] = resultSet.getString("telefono");
                supplier[6] = resultSet.getString("contacto");
                supplier[7] = resultSet.getString("telefono_contacto");

                suppliers.add(supplier);
            }
        }
        return suppliers;
    }

    private String[] getSupplierById(int id) throws SQLException {
        String query = "SELECT * FROM provedores WHERE id = ?";
        String[] supplier = new String[8];

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    supplier[0] = String.valueOf(resultSet.getInt("id"));
                    supplier[1] = resultSet.getString("nombre");
                    supplier[2] = resultSet.getString("rut");
                    supplier[3] = resultSet.getString("direccion");
                    supplier[4] = resultSet.getString("correo");
                    supplier[5] = resultSet.getString("telefono");
                    supplier[6] = resultSet.getString("contacto");
                    supplier[7] = resultSet.getString("telefono_contacto");
                } else {
                    Arrays.fill(supplier, "");
                }
            }
        }
        return supplier;
    }

    private void updateSupplier(int id, String name, String rut, String address, String email, String phone, String contact, String contactPhoneNumber) throws SQLException {
        String query = "UPDATE provedores SET nombre = ?, rut = ?, direccion = ?, correo = ?, telefono = ?, contacto = ?, telefono_contacto = ? WHERE id = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, rut);
            statement.setString(3, address);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setString(6, contact);
            statement.setString(7, contactPhoneNumber);
            statement.setInt(8, id);

            statement.executeUpdate();
        }
    }

    private void deleteSupplier(int id) throws SQLException {
        String query = "DELETE FROM provedores WHERE id = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
