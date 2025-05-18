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
 * @author Chamod
 */
public class AdditionalFeeController {

    public void issueAdditionalFee(AdditionalFee additional) throws SQLException {

        int status = additional.getIsActive() ? 1 : 0;

        AppConnection.iud("INSERT INTO `additional_fees` ("
                + "`title`, "
                + "`price`,"
                + "`grades_id`, "
                + "`is_active` ) VALUES('" + additional.getTitle() + "','" + additional.getPrice() + "','" + additional.getGradesId() + "','" + status + "')");

    }

    public AdditionalFee getRecord(int id) throws SQLException {
        ResultSet rs = AppConnection.search("SELECT * FROM `additional_fees` WHERE `id` = '" + id + "' ");

        if (!rs.next()) {
            return null;
        }

        AdditionalFee record = new AdditionalFee();
        record.setId(rs.getInt("id"));
        record.setTitle(rs.getString("title"));
        record.setPrice(rs.getDouble("price"));
        record.setGradesId(rs.getInt("grades_id"));
        record.setIsActive(rs.getBoolean("is_active"));

        return record;
    }

    public void updateAdditionalFee(AdditionalFee additionalFee, int id) throws SQLException {

        int status = additionalFee.getIsActive() ? 1 : 0;
        
        AppConnection.iud("UPDATE `additional_fees` SET"
                + " `title` = '" + additionalFee.getTitle() + "',"
                + " `price` = '" + additionalFee.getPrice() + "', "
                + " `grades_id` = '" + additionalFee.getGradesId() + "', "
                + " `is_active` = '" + status + "' "
                + " WHERE `additional_fees`.`id` = '" + id + "' ");

    }

    public void addPayment(String studentId, String additionalFeeId) throws SQLException {

        ResultSet rs = AppConnection.search("SELECT `price` FROM `additional_fees` WHERE `id` = " + additionalFeeId);
        rs.next();

        AppConnection.iud("INSERT INTO `payments` (`student_id`, `additional_fees_id`, `paid_amoint`)"
                + "VALUES ('" + studentId + "', '" + additionalFeeId + "', '" + rs.getString("price") + "')");
    }
}
