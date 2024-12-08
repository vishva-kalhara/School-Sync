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
import utils.AppConnection;
import views.dialogs.DlgError;
import views.forms.FrmSplashScreen;
import views.layouts.AppLayout;

/**
 *
 * @author Chamod
 */
public class NoticesController {

    public void sendNotice(String studentId, String heading, String details) throws ErrorException, SQLException {

        ResultSet rs = AppConnection.search("SELECT `email` FROM `student` WHERE `id` = '" + studentId + "'");

        if (!rs.next()) {
            new DlgError(AppLayout.appLayout, true, "Invalid Email").setVisible(true);
        }

        String studentEmail = rs.getString("email");

        if (studentEmail == null || studentEmail.isEmpty()) {
            throw new ErrorException("Student does not have a valid email address.");
        }

        try {

            if (Env.IS_PRODUCTION) {
                Email email = new Email();
                email.send(heading, details, studentEmail);
            }

            String userId = AppLayout.loggedUserId;
            AppConnection.iud("INSERT INTO `notices` (`subject`, `content`, `notices_type_id`, `student_id`, `users_id`, `created_at`) VALUES ('"
                    + heading + "', '" + details + "', '" + 1 + "', '" + studentId + "', '" + userId + "', NOW())");

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
            throw new ErrorException("Failed to send the email. Please try again.");
        }

    }

}
