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
                + "`status_id`, "
                + "`grades_has_classes_id`"
                + ") VALUES ('"
                + student.getFullName() + "', '"
                + student.getGuardian1FullName() + "', '"
                + student.getGuardian2FullName() + "', '"
                + student.getEmail() + "', '"
                + student.getMobile1() + "', '"
                + student.getMobile2() + "', '"
                + student.getGenderId() + "', "
                + "(1),"
                + gradesHasClassesId + ")");

    }

    public Student getStudent(String id) throws SQLException {
        ResultSet rs = AppConnection.search(
                "SELECT * FROM `student` "
                + "INNER JOIN `status` ON `student`.`status_id` = `status`.`id` "
                + "INNER JOIN `genders` ON `student`.`genders_id` = `genders`.`id`"
                + "INNER JOIN `grades_has_classes` ON `student`.`grades_has_classes_id` = `grades_has_classes`.`id` "
                + "WHERE `student`.`id` = '" + id + "' "
        );
        if (!rs.next()) {
            return null;
        }

        Student student = new Student();
        student.setId(rs.getString("id"));
        student.setFullName(rs.getString("full_name"));
        student.setGuardian1FullName(rs.getString("guardian_1_full_name"));
        student.setGuardian2FullName(rs.getString("guardian_2_full_name"));
        student.setMobile1(rs.getString("mobile1"));
        student.setMobile2(rs.getString("mobile_2"));
        student.setGenderId(rs.getInt("genders_id"));
        student.setGenderValue(rs.getString("genders.value"));
        student.setEmail(rs.getString("email"));
        student.setStatusId(rs.getInt("status_id"));
        student.setStatusValue(rs.getString("status.value"));
        student.setGradeId(rs.getInt("grades_has_classes.grades_id"));
        student.setClassValue(rs.getString("grades_has_classes.class"));

        return student;

    }

    public void updateStudent(Student student, String id, int gradeId, String classValue, int UpdatedStatusId) throws SQLException {

        int gradesHasClassesId = getGradesHasClassesId(gradeId, classValue);

        AppConnection.iud("UPDATE `student` SET"
                + " `full_name` = '" + student.getFullName() + "', "
                + " `guardian_1_full_name` = '" + student.getGuardian1FullName() + "',"
                + " `guardian_2_full_name` = '" + student.getGuardian2FullName() + "',"
                + " `mobile1` = '" + student.getMobile1() + "', "
                + " `mobile_2` = '" + student.getMobile2() + "', "
                + " `genders_id` = '" + student.getGenderId() + "',"
                + " `email` = '" + student.getEmail() + "',"
                + " `grades_has_classes_id` = '" + gradesHasClassesId + "',"
                + " `status_id` = '" + UpdatedStatusId + "'"
                + "WHERE `student`.`id` = '" + id + "' ");
    }

}
