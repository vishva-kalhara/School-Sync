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
import java.util.logging.Level;
import javax.mail.MessagingException;
import utils.AppConnection;
import views.dialogs.DlgError;
import views.forms.FrmSplashScreen;
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

    private void sendNotice(String studentId, String studentEmail, String heading, String details) throws Exception {

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

    public void sendNoticeToStudent(String studentId, String heading, String details) throws ErrorException, SQLException, Exception {

        ResultSet rs = AppConnection.search("SELECT `email` FROM `student` WHERE `id` = '" + studentId + "'");

        String studentEmail = rs.getString("email");

        if (studentEmail == null || studentEmail.isEmpty()) {
            throw new ErrorException("Student does not have a valid email address.");
        }

        sendNotice(studentId, studentEmail, heading, details);
    }

    public void sendToClass(String classId, String heading, String details) throws ErrorException, SQLException, MessagingException {

        try {

            // Fetch student Ids and emails from db from a specific class
            ResultSet studentsRs = AppConnection.search(""
                    + "SELECT `id` AS `student_id`, `email` "
                    + "FROM school_sync_v1.student "
                    + "WHERE status_id = 1 AND `grades_has_classes_id` = " + classId);

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
            
            if(1 != 0)
                throw new ErrorException("new error");
            
            new Email().sendAsBCC(heading, details, emailList.toString());
            
            AppConnection.commitChanges();
            
        } catch (SQLException | MessagingException | ErrorException e) {
            AppConnection.rollbackCommits();
            throw e;
        } finally {
            AppConnection.enableAutoCommits();
        }
    }
}