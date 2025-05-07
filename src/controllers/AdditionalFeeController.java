/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.SQLException;
import models.AdditionalFee;
import utils.AppConnection;
import java.sql.ResultSet;

/**
 *
 * @author Dini
 */
public class AdditionalFeeController {

    public void issueAdditionalFee(AdditionalFee additional) throws SQLException {

//        Sanitize sanitize = new Sanitize();
//        String title = sanitize.removeApos(additional.getTitle());
        int status = additional.getIsActive() ? 1 : 0;

        AppConnection.iud("INSERT INTO `additional_fees` ("
                + "`title`, "
                + "`price`,"
                + "`grades_id`, "
                + "`is_active` ) VALUES('" + additional.getTitle() + "','" + additional.getPrice() + "','" + additional.getGradesId() + "','" + status + "')");

    }
    
    public void addPayment(String studentId, String additionalFeeId) throws SQLException {
        
        ResultSet rs = AppConnection.search("SELECT `price` FROM `additional_fees` WHERE `id` = " + additionalFeeId);
        rs.next();
        
        AppConnection.iud("INSERT INTO `payments` (`student_id`, `additional_fees_id`, `paid_amoint`)"
                          + "VALUES ('"+ studentId +"', '"+ additionalFeeId +"', '"+ rs.getString("price") +"')");
    }
}
