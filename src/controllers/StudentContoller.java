/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.SQLException;
import models.Student;
import utils.AppConnection;
import java.sql.ResultSet;

/**
 *
 * @author Chamod
 */
public class StudentContoller {

    private int getGradesHasClassesId(int gradeId, String classValue) throws SQLException {

        ResultSet rs = AppConnection.search(
                "SELECT id FROM `grades_has_classes` WHERE `grades_id` = " + gradeId
                + " AND `class` = '" + classValue + "' AND `is_active` = 1"
        );

        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("Grade-Class combination not found or inactive: Grade " + gradeId + " Class " + classValue);
        }
    }

    public void createStudent(Student student, int gradeId, String classValue) throws SQLException {

        int gradesHasClassesId = getGradesHasClassesId(gradeId, classValue);

        AppConnection.iud("INSERT INTO `student` ("
                + "`full_name`, "
                + "`guardian_1_full_name`, "
                + "`guardian_2_full_name`, "
                + "`email`, "
                + "`mobile1`, "
                + "`mobile_2`, "
                + "`genders_id`, "
                + "`grades_has_classes_id`"
                + ") VALUES ('"
                + student.getFullName() + "', '"
                + student.getGuardian1FullName() + "', '"
                + student.getGuardian2FullName() + "', '"
                + student.getEmail() + "', '"
                + student.getMobile1() + "', '"
                + student.getMobile2() + "', '"
                + student.getGenderId() + "', "
                + gradesHasClassesId + ")");
    }

}
