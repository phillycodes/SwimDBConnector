package com.example.advanceddatabase_lab;

import java.sql.*;
import java.util.Scanner;

public class App {

    private final static String url ="jdbc:postgresql://localhost/postgres";
    private final static String user = "postgres";
    private final static String password = "Postgres21";
    public Connection conn = null;

    public Connection connect() {

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        try (Connection connection =
                     DriverManager.getConnection(url, user, password)) { //"1122" is the password, and you need to replace it with your password

            System.out.println("Java JDBC PostgreSQL swimmingorg");
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.println("Reading swimming records...");

        char choice = '1';
        while (choice != '4') {
            // Create a Scanner object to read input.
            Scanner console = new Scanner(System.in);
            System.out.println("Please enter your choice:");
            System.out.println("1. Names and phones of all swimmers currently in level");
            System.out.println("2. Name(s) of caretakers who are the primary (main) caretakers of at least two swimmers:");
            System.out.println("3. Names of all caretakers who have volunteered for the task 'Recording' but not the task 'Officiating':");
            System.out.println("4. Quit");
            choice = console.next().charAt(0);

            if (choice == '1') {
                displaySwimmersAtLevel(level);
                System.out.println("Names and phones of all swimmers currently in level (of id) 3:");
                System.out.println("fname" + " | " + "lname" + " | " +
                        "phone");
//Please insert your SELECT statement for query the data (Names and phones of all swimmers currently in level (of id) 3) into "statement.executeQuery()"
                ResultSet resultSet = statement.executeQuery("");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("fname") + " | " + resultSet.getString("lname") + " | " + resultSet.getString("phone"));
                }
                console.close();
            } else if (choice == '2') {
                System.out.println("Name(s) of caretakers who are the primary (main) caretakers of at least two swimmers:");
                System.out.println("fname" + " | " + "lname");
//Please insert your SELECT statement for query the data (Name(s) of caretakers who are the primary (main) caretakers of at least two swimmers) into "statement.executeQuery()"
                ResultSet resultSet = statement.executeQuery("");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("fname") + " | " + resultSet.getString("lname"));
                }
                console.close();
            } else if (choice == '3') {
                System.out.println("Names of all caretakers who have volunteered for the task 'Recording' but not the task 'Officiating':");
                System.out.println("fname" + " | " + "lname");
                ResultSet resultSet = statement.executeQuery("select distinct c.FName, c.LName\r\n" +
                        "from Caretaker c \r\n" +
                                "where c.CT_Id in\r\n" +
                                " (select distinct c.CT_Id\r\n" +
                                "  from V_Task v join Commitment c\r\n" +
                                " on (v.VT_Id = c.VT_Id)\r\n" +
                                "  where v.Name ='Recording')\r\n" +
                                "and c.CT_Id not in\r\n" +
                                " (select distinct c.CT_Id\r\n" +
                                "  from V_Task v join Commitment c\r\n" +
                                " on (v.VT_Id = c.VT_Id)\r\n" +
                                "  where v.Name ='Officiating')");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("fname") + " | " + resultSet.getString("lname"));
                }
                console.close();
            } else if (choice == '4') {
                console.close();
                System.exit(0);
            }
            console.close();
        }
    } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.out.println(e.getMessage());
        }
}
}

public static void displaySwimmersAtLevel() {

}

/*
public static void main(String[] args) {
        try (Connection connection = 
DriverManager.getConnection("jdbc:postgresql://localhost:5432/swimmingorg", 
"postgres", "1122")) { //"1122" is the password, and you need to replace it with 
your password
 
            System.out.println("Java JDBC PostgreSQL swimmingorg");
            // When this class first attempts to establish a connection, it 
automatically loads any JDBC 4.0 drivers found within 
            // the class path. Note that your application must manually load any 
JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver"); 
 
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.println("Reading swimming records...");
            
            char choice = '1';
            while (choice != '4') {
             // Create a Scanner object to read input.
             Scanner console = new Scanner(System.in);
             System.out.println("Please enter your choice:");
             System.out.println("1. Names and phones of all swimmers currently
in level (of id) 3:");
             System.out.println("2. Name(s) of caretakers who are the primary 
(main) caretakers of at least two swimmers:");
             System.out.println("3. Names of all caretakers who have 
volunteered for the task 'Recording' but not the task 'Officiating':");
             System.out.println("4. quit");
             choice = console.next().charAt(0);
            
             if (choice == '1') {
             System.out.println("Names and phones of all swimmers 
currently in level (of id) 3:");
             System.out.println("fname" + " | " + "lname" + " | " + 
"phone");
//Please insert your SELECT statement for query the 
data (Names and phones of all swimmers currently in level (of id) 3) into 
"statement.executeQuery()"
             ResultSet resultSet = statement.executeQuery("");
             while (resultSet.next()) {
             System.out.println(resultSet.getString("fname") + " |
" + resultSet.getString("lname") + " | " + resultSet.getString("phone"));
                    }
             //console.close();
            
             } else if (choice == '2') {
             System.out.println("Name(s) of caretakers who are the 
primary (main) caretakers of at least two swimmers:");
             System.out.println("fname" + " | " + "lname");
//Please insert your SELECT statement for query the 
data (Name(s) of caretakers who are the primary (main) caretakers of at least two 
swimmers) into "statement.executeQuery()"
             ResultSet resultSet = statement.executeQuery("");
             while (resultSet.next()) {
             System.out.println(resultSet.getString("fname") + " |
" + resultSet.getString("lname"));
                    }
             //console.close();
             } else if (choice == '3') {
             System.out.println("Names of all caretakers who have 
volunteered for the task 'Recording' but not the task 'Officiating':");
             System.out.println("fname" + " | " + "lname");
             ResultSet resultSet = statement.executeQuery("select 
distinct c.FName, c.LName\r\n" + 
             "from Caretaker c \r\n" + 
             "where c.CT_Id in\r\n" + 
             " (select distinct c.CT_Id\r\n" + 
             "  from V_Task v join Commitment c\r\n" + 
             " on (v.VT_Id = c.VT_Id)\r\n" + 
             "  where v.Name ='Recording')\r\n" + 
             "and c.CT_Id not in\r\n" + 
             " (select distinct c.CT_Id\r\n" + 
             "  from V_Task v join Commitment c\r\n" + 
             " on (v.VT_Id = c.VT_Id)\r\n" + 
             "  where v.Name ='Officiating')");
             while (resultSet.next()) {
             System.out.println(resultSet.getString("fname") + " |
" + resultSet.getString("lname"));
                    }
             //console.close();
             } else if (choice == '4') {
             //console.close();
                 System.exit(0); 
                }
             //console.close();
            } 
        } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ /*catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}
*/