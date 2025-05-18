/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.SQLException;
import java.sql.ResultSet;
import utils.AppConnection;
import utils.ErrorException;

/**
 *
 * @author Dini
 */
public class AttendanceController {

    public void markAttendance( String stuid) throws SQLException, ErrorException {
        
        ResultSet rs = AppConnection.search("SELECT * FROM `attendance` WHERE DATE(makrd_at) = CURRENT_DATE() AND student_id = '" + stuid +"'");
        if(rs.next()){
            throw new ErrorException("Already marked the attendance");
        }

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
