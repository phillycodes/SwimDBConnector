package com.example.advanceddatabase_lab;

import java.sql.*;
import java.util.Scanner;

public class App {
// login variables
    private final static String url = "jdbc:postgresql://localhost/postgres";
    private final static String user = "postgres";
    private final static String password = "Postgres21";

    public static Connection conn = null;

    /*
    * connect() creates a connection to the database using the provided credentials.
    * The connection attempt must be surrounded by a try/catch statement in case the connection fails.
     */
    public static Connection connect() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return conn;
    }

    /*
    * displaySwimmerAtLevel(level) queries the swimmingorg database to find the swimmer(s) who are at a certain level
    * Output columns: fname, lname, phone
     */
    public static void displaySwimmersAtLevel(String level) {
        System.out.println("fname" + " | " + "lname" + " | " + "phone");
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT fname, lname, phone FROM swimmer WHERE currentlevelid = " + level)) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("fname") + " | " +
                        resultSet.getString("lname") + "\t| " + resultSet.getString("phone"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println(e.getMessage());
        }


    }

    /*
    * displayMultipleCaretakers() queries the database swimmingorg to return the names of the swimmer(s) who has multiple
    * caretakers.
    * Output columns: fname, lname
     */
    public static void displayMultipleCaretakers() {
        System.out.println("fname" + " | " + "lname");

        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT DISTINCT c.fname, c.lname\r\n FROM caretaker c, swimmer s1, swimmer s2\r\n" +
                     "where c.ct_id = s1.main_ct_id\r\n AND c.ct_id = s2.main_ct_id\r\n AND s1.swimmerid <> s2.swimmerid")) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("fname") + " | " +
                        resultSet.getString("lname") + "\t| ");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    /*
    * displayVolunteeredCaretakers() will query the database swimmingorg to find the name of the caretakers who have
    * volunteered for the task 'Recording' but not the task 'Officiating'.
    * Output columns: fname, lname
     */
    public static void displayVolunteeredCaretakers() {
        System.out.println("Names of all caretakers who have volunteered for the task 'Recording' but not the task 'Officiating':");
        System.out.println("fname" + " | " + "lname");
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
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
                     "  where v.Name ='Officiating')")) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("fname") + " | " + resultSet.getString("lname"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (Connection conn = connect()) {

            System.out.println("Java JDBC PostgreSQL swimmingorg");
            System.out.println("Connected to PostgreSQL database!");
            System.out.println("Reading swimming records...");

            char choice = '1';
            int level;
            Scanner sc = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            while (choice != '4') {
                // Create a Scanner object to read input.
                System.out.println("1. Names and phones of all swimmers currently in level");
                System.out.println("2. Name(s) of caretakers who are the primary (main) caretakers of at least two swimmers:");
                System.out.println("3. Names of all caretakers who have volunteered for the task 'Recording' but not the task 'Officiating':");
                System.out.println("4. Quit");
                System.out.print("Please enter your choice: ");
                choice = sc.next().charAt(0);

                switch (choice) {
                    case '1' -> {
                        System.out.print("Enter Level: ");
                        level = scanner.nextInt();
                        System.out.println("");
                        Integer levelInt = level;
                        String levelString = levelInt.toString();
                        displaySwimmersAtLevel(levelString);
                        System.out.println();
                    }
                    case '2' -> displayMultipleCaretakers();
                    case '3' -> displayVolunteeredCaretakers();
                    case '4' -> {
                        sc.close();
                        scanner.close();
                        System.out.println("Goodbye!");
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("You've entered an invalid character!");
                        sc.close();
                        scanner.close();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}


