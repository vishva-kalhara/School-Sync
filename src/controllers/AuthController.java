/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.SQLException;
import models.User;
import utils.AppConnection;

/**
 *
 * @author vishv
 */
public class AuthController {

    public void createUser(User user) throws SQLException {

        AppConnection.iud("INSERT INTO `users` ("
                + "`full_name`, "
                + "`status_id`, "
                + "`user_roles_id`, "
                + "`nic`, "
                + "`address`, "
                + "`mobile1`, "
                + "`mobile2`, "
                + "`genders_id`, "
                + "`sys_username`, "
                + "`sys_password`"
                + ") VALUES ('"
                + user.getFullName() + "', '"
                + user.getStatusId() + "', '"
                + user.getUserRoleId() + "', '"
                + user.getNic() + "', '"
                + user.getAddress() + "', '"
                + user.getMobile1() + "', '"
                + user.getMobile2() + "', '"
                + user.getGenderId() + "', '"
                + user.getSysUsername() + "', '"
                + user.getSysPassword() + "')");
    }
}
