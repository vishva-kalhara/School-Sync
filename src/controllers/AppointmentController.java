package controllers;

import java.sql.SQLException;
import models.Appoinment;
import utils.AppConnection;

public class AppointmentController {

    public void createAppoinment(Appoinment appoinment) throws SQLException {

        AppConnection.iud("INSERT INTO `appointments` ("
                + "`title`, "
                + "`grades_has_classes_id`, "
                + "`student_id`, "
                + "`users_id`, "
                + "`created_at`, "
                + "`is_active` "
                + ") VALUES ('"
                + appoinment.getTitle() + "', '"
                + appoinment.getGrade() + "', '"
                + appoinment.getStudentId() + "', '"
                + appoinment.getUserId() + "', "
                + "CURRENT_TIMESTAMP, "
                + "'1')");

    }

}
