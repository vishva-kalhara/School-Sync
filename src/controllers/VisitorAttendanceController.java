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
public class VisitorAttendanceController {

    public void markVisitorAttendance(int id) throws SQLException {

        AppConnection.iud("UPDATE `visitor_details` SET `has_attended` = '" + 1 + "'  WHERE `id`='" + id + "'");
    }

}
