/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import config.Env;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import views.dialogs.DlgError;
import views.forms.FrmSplashScreen;
import views.layouts.AppLayout;

/**
 *
 * @author vishv
 */
public class AppConnection {

    private static Connection connection;

    private static void setUpConnection() {

        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + Env.MYSQL_DB, Env.MYSQL_USERNAME, Env.MYSQL_PASSWORD);

                connection.setAutoCommit(true);

            } catch (SQLException | ClassNotFoundException e) {
                FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    public static ResultSet search(String query) {

        if (!query.startsWith("SELECT") && !query.startsWith("CALL")) {
            throw new IllegalArgumentException("Use ResultSet utils.AppConnection.iud()");
        }

        if (connection == null) {
            setUpConnection();
        }

        try {
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            return null;
        }
    }

    public static boolean iud(String query) throws SQLException {

        if (query.startsWith("SELECT")) {
            throw new IllegalArgumentException("Use ResultSet utils.AppConnection.search()");
        }

        if (connection == null) {
            setUpConnection();
        }

        int result = connection.createStatement().executeUpdate(query);

        return result == 1;
    }

    public static void closeConnection() throws SQLException {

        if (connection != null) {
            connection.close();
        }
    }

    public static void disableAutoCommits() throws SQLException {

        if (AppConnection.connection == null) {
            setUpConnection();
        }

        AppConnection.connection.setAutoCommit(false);
    }

    public static void enableAutoCommits() throws SQLException {

        if (AppConnection.connection == null) {
            setUpConnection();
        }

        AppConnection.connection.setAutoCommit(true);
    }

    public static void commitChanges() throws SQLException {

        if (AppConnection.connection == null) {
            setUpConnection();
        }

        connection.commit();
    }

    public static void rollbackCommits() throws SQLException {

        if (AppConnection.connection == null) {
            setUpConnection();
        }

        connection.rollback();
    }

}
