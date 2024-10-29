/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import models.Visitor;
import utils.AppConnection;
import utils.Sanitize;

/**
 *
 * @author Dini
 */
public class VisitorController {

    public void assignVisitor(Visitor visitor) throws SQLException {

        Sanitize sanitize = new Sanitize();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(visitor.getDate());

        AppConnection.iud("INSERT INTO `visitor_details` ("
                + "`has_attended`, "
                + "`student_id`, "
                + "`date` ) VALUES('" + 0 + "','" + visitor.getStudentId() + "','" + date + "')");

    }

}
