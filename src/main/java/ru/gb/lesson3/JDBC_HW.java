package ru.gb.lesson3;
import java.sql.*;


public class JDBC_HW {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            acceptConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void acceptConnection(Connection connection) throws SQLException {
        createTable(connection);
        insertData(connection);
        deleteRandomRow(connection);

       updateRow(connection, "Igor", 10);

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id, name, clas from students");

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer clas = resultSet.getInt("class");

                System.out.println("id = " + id + ", name = " + name + ", class = " + clas);
            }
        }
    }

    private static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate("""
        insert into students(id, name) values
        (1, 'Igor'), 
        (2, 'Person #2'), 
        (3, 'John'), 
        (4, 'Alex'), 
        (5, 'Peter') 
        """);

            System.out.println("INSERT: affected rows: " + affectedRows);
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
      create table students (
        id bigint,
        name varchar(256)
       clas varchar(256)
      )
      """);
        }
    }

    private static void deleteRandomRow(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            System.out.println("DELETED: " + statement.executeUpdate("delete from person where id = 1"));
        }
    }


    private static void updateRow(Connection connection, String name, Integer clas) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("update person set second_name = $1 where name = $2")) {
            stmt.setInt(1, clas);
            stmt.setString(2, name);

            stmt.executeUpdate();
        }
    }


}
