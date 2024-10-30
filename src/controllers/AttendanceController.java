/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.SQLException;
import utils.AppConnection;

/**
 *
 * @author Dini
 */
public class AttendanceController {

    public void markAttendance( String stuid) throws SQLException {

        AppConnection.iud("INSERT INTO `attendance` ("
                + "`makrd_at`, "
                + "`student_id` "
                + ") VALUES ("
                +" CURRENT_TIMESTAMP , '"
                + stuid + "' "
                + ")"
        );

    }

}
