/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.DescplineRecord;
import java.sql.SQLException;
import utils.AppConnection;
import utils.Sanitize;
import views.layouts.AppLayout;

/**
 *
 * @author vishv
 */
public class DisciplineRecordController {

    public void issueRecord(DescplineRecord record) throws SQLException {
        
        Sanitize sanitize = new Sanitize();
        String title = sanitize.removeApos(record.getTitle());
        String description = sanitize.removeApos(record.getDescription());
        
        AppConnection.iud("INSERT INTO `decipline_records` ("
                + "`grades_has_classes_id`, "
                + "`student_id`, "
                + "`title`, "
                + "`description`, "
                + "`users_id`, "
                + "`status_id`"
                + ") VALUES ('"
                + record.getClassId() + "', '"
                + record.getStudentId() + "', '"
                + title + "', '"
                + description + "', '"
                + AppLayout.loggedUserId + "', "
                + "'1'"
                + ")"
        );
    }
}
