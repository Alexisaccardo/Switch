import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class Ejemplo {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*****BIENVENIDOS*****");

        boolean inicio = true;

        while (inicio){

            System.out.println("1. Insert");
            System.out.println("2. Select");
            System.out.println("3. select_one");
            System.out.println("4. Editar");
            System.out.println("5. Eliminar");
            System.out.println("6. terminar");

            System.out.println("Ingrese un numero entre 1 - 6: ");

        int respuesta = Integer.parseInt(scanner.nextLine());

        switch (respuesta){
            case 1:

                System.out.print("Ingrese codigo del producto: ");
                String codigo = scanner.nextLine();

                System.out.print("Ingrese nombre del producto: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese cantidad disponible: ");
                String cantidad = scanner.nextLine();

                System.out.print("Ingrese su valor: ");
                String valor = scanner.nextLine();

                System.out.print("Ingrese su descripcion: ");
                String descripcion = scanner.nextLine();

                // ESTA LINEA SOLO VA CUANDO TRABAJAMOS CON CLASES
                Insert(codigo, nombre, cantidad, valor, descripcion); //

                break;

            case 2: System.out.println("Estos son todos los registros: ");

                Select();
                break;

            case  3: System.out.println("Ingrese codigo de consulta: ");
                String codigo_consulta = scanner.nextLine();
                Select_One(codigo_consulta);

                System.out.println("Este es el registro del codigo ingresado: ");
                break;

            case 4: 
                System.out.print("Ingrese codigo del producto: ");
                codigo = scanner.nextLine();

                System.out.print("Ingrese nuevo nombre del producto: ");
                nombre = scanner.nextLine();

                System.out.print("Ingrese nueva cantidad disponible: ");
                cantidad = scanner.nextLine();

                System.out.print("Ingrese nuevo su valor: ");
                valor = scanner.nextLine();

                System.out.print("Ingrese nueva su descripcion: ");
                descripcion = scanner.nextLine();

                Editar(codigo, nombre, cantidad, valor, descripcion);

                break;

            case 5: System.out.println("Que codigo de producto deseas eliminar? ");
                String producto_cod = scanner.nextLine();
                Eliminar(producto_cod);
                
                break;

            case 6:
                System.out.println("Finalizando...");

                inicio = false;

                break;

            default: System.out.println("Ingrese un caso valido");
        }

        }
    }

    private static void Eliminar(String producto_cod) throws SQLException, ClassNotFoundException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/equipos";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM productos WHERE codigo = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, producto_cod);
        prepareStatement.executeUpdate();

        System.out.println("Producto eliminado correctamente");
    }

    private static void Editar(String codigo, String nombre, String cantidad, String valor, String descripcion) throws SQLException, ClassNotFoundException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/equipos";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE productos SET nombre = ?, cantidad = ?, valor = ?, descripcion = ? WHERE codigo = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setString(1, nombre);
        preparedStatement.setString(2, cantidad);
        preparedStatement.setString(3, valor);
        preparedStatement.setString(4, descripcion);
        preparedStatement.setString(5, codigo);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Producto actualizado exitosamente");
        } else {
            System.out.println("No se encontró el registro para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }

    private static void Select_One(String codigo_consulta) throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/equipos";
        String username = "root";
        String password = "";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM productos WHERE codigo = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, codigo_consulta); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String codigo = resultSet.getString("codigo");
            String nombre = resultSet.getString("nombre");
            String cantidad = resultSet.getString("cantidad");
            String valor = resultSet.getString("valor");
            String descripcion = resultSet.getString("descripcion");

            System.out.println("Este es el codigo del producto a consultar: " + codigo + " Con nombre: " + nombre);

        } else {
            System.out.println("No se encontró un registro con el codigo especificado.");
        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();

    }

    private static void Select() throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/equipos";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM productos");

        while(resultSet2.next()){
            String codigo = resultSet2.getString("codigo");
            String nombre = resultSet2.getString("nombre");
            String cantidad = resultSet2.getString("cantidad");
            String valor = resultSet2.getString("valor");
            String descripcion = resultSet2.getString("descripcion");

            System.out.println("este es el codigo: " + codigo +  " nombre: " + nombre + " cantidad: "+ cantidad + " valor: "+ valor + " descripcion: " + descripcion);
        }
    }

    private static void Insert(String codigo, String nombre, String cantidad, String valor, String descripcion) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/equipos";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM productos");


            // Sentencia INSERT
            String sql = "INSERT INTO productos (codigo, nombre, cantidad, valor, descripcion) VALUES (?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, codigo);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, cantidad);
            preparedStatement.setString(4, valor);
            preparedStatement.setString(5, descripcion);

            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("producto agregado exitosamente.");
            } else {
                System.out.println("No se pudo agregar el producto.");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
