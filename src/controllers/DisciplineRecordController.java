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
import java.sql.ResultSet;

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

    public DescplineRecord getRecord(String id) throws SQLException {
        ResultSet rs = AppConnection.search("SELECT * FROM `decipline_records` "
                + "INNER JOIN `student` ON `decipline_records`.`student_id` = `student`.`id` "
                + "INNER JOIN `grades_has_classes` ON `decipline_records`.`grades_has_classes_id` = `grades_has_classes`.`id` "
                + "WHERE `decipline_records`.`student_id` = '" + id + "'"
        );

        if (!rs.next()) {
            return null;
        }

        DescplineRecord record = new DescplineRecord();
        record.setsName(rs.getString("student.full_name"));
        record.setDescription(rs.getString("description"));
        record.setTitle(rs.getString("title"));
        record.setGradeValue(rs.getString("grades_has_classes.grades_id"));
        record.setClassName(rs.getString("grades_has_classes.class"));
        record.setStudentId(id);

        return record;

    }

    public void updateRecord(DescplineRecord record) throws SQLException {
        
        String stuId = record.getStudentId();

        AppConnection.iud("UPDATE `decipline_records` SET "
                + "`description` = '" + record.getDescription() + "', "
                + "`title` = '" + record.getTitle() + "', "
                + "`student_id` = '" + record.getStudentId() + "',"
                + "`grades_has_classes_id` = '" + record.getClassId() + "',"
                + "`users_id` = '" + AppLayout.loggedUserId + "' "
                + "WHERE `decipline_records`.`student_id` = '" + stuId + "'");

    }

}
