/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import config.Env;
import utils.Email;
import utils.ErrorException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;
import utils.AppConnection;
import views.layouts.AppLayout;

/**
 *
 * @author Chamod
 */
public class NoticesController {

    private Email email;

    public NoticesController() {
        this.email = new Email();
    }

    public void sendNoticeToStudent(String studentId, String heading, String details) throws ErrorException, SQLException, Exception {

        ResultSet rs = AppConnection.search("SELECT `email` FROM `student` WHERE `id` = '" + studentId + "'");

        rs.next();

        String studentEmail = rs.getString("email");

        if (studentEmail == null || studentEmail.isEmpty()) {
            throw new ErrorException("Student does not have a valid email address.");
        }

        if (Env.IS_PRODUCTION) {
            email.send(heading, details, studentEmail);
        }

        String userId = AppLayout.loggedUserId;
        AppConnection.iud(
                "INSERT INTO `notices` ("
                + "`subject`, "
                + "`content`, "
                + "`notices_type_id`, "
                + "`student_id`, "
                + "`users_id`, "
                + "`created_at`"
                + ") VALUES ('"
                + heading + "', '"
                + details + "', '"
                + 1 + "', '"
                + studentId + "', '"
                + userId + "', "
                + "NOW())"
        );
    }

    public void sendToClass(String classId, String heading, String details) throws ErrorException, SQLException, MessagingException {

        try {

            // Fetch student Ids and emails from db from a specific class
            ResultSet studentsRs = AppConnection.search(""
                    + "SELECT `id` AS `student_id`, `email` "
                    + "FROM school_sync_v1.student "
                    + "WHERE status_id = 1 AND `grades_has_classes_id` = " + classId);

            // Send Emails and commits to DB
            sendToMany(studentsRs, heading, details);

        } catch (SQLException | MessagingException | ErrorException e) {
            AppConnection.rollbackCommits();
            throw e;
        } finally {
            AppConnection.enableAutoCommits();
        }
    }

    public void sendToSection(String gradeId, String heading, String details) throws ErrorException, SQLException, MessagingException {

        try {

            // Fetch student Ids and emails from db from a specific class
            ResultSet studentsRs = AppConnection.search(
                    "SELECT `id` AS `student_id`, `email` "
                    + "FROM school_sync_v1.student "
                    + "WHERE `grades_has_classes_id` IN (SELECT `id` FROM `grades_has_classes` WHERE `grades_id` = " + gradeId + ");");

            // Send Emails and commits to DB
            sendToMany(studentsRs, heading, details);

        } catch (SQLException | MessagingException | ErrorException e) {
            AppConnection.rollbackCommits();
            throw e;
        } finally {
            AppConnection.enableAutoCommits();
        }
    }

    private void sendToMany(ResultSet studentsRs, String heading, String details) throws SQLException, MessagingException, ErrorException {

        // If there is no students on the class:
        if (!studentsRs.next()) {
            throw new ErrorException("There is no students to send");
        }

        // Disable auto commit
        AppConnection.disableAutoCommits();

        // Query static parts
        String queryPrefix = "INSERT INTO `notices` (`subject`, `content`, `notices_type_id`, `student_id`, `users_id`, `created_at`) VALUES ";
        String querySuffix1 = "('" + heading + "', '" + details + "', '1', '";
        String querySuffix2 = "', '" + AppLayout.loggedUserId + "', NOW())";

        // Insert the first row
        StringBuilder emailList = new StringBuilder(studentsRs.getString("email"));
        StringBuilder query = new StringBuilder(queryPrefix)
                .append(querySuffix1)
                .append(studentsRs.getString("student_id"))
                .append(querySuffix2);

        // Insert the rest of the rows
        while (studentsRs.next()) {

            emailList.append(", ").append(studentsRs.getString("email"));
            query.append(", ")
                    .append(querySuffix1)
                    .append(studentsRs.getString("student_id"))
                    .append(querySuffix2);
        }

        AppConnection.iud(query.toString());

        if (Env.IS_PRODUCTION) {
            this.email.sendAsBCC(heading, details, emailList.toString());
        }
        AppConnection.commitChanges();
    }
}
