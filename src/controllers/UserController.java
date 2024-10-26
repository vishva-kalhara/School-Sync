/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.User;
import utils.AppConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vishv
 */
public class UserController {

    public User getSystemUser(String id) throws SQLException {

        ResultSet rs = AppConnection.search(
                "SELECT * FROM `users` "
                + "INNER JOIN `status` ON `users`.`status_id` = `status`.`id` "
                + "INNER JOIN `user_roles` ON `users`.`user_roles_id` = `user_roles`.`id` "
                + "INNER JOIN `genders` ON `users`.`genders_id` = `genders`.`id` "
                + "WHERE `users`.`id` = '" + id + "'"
        );
        if (!rs.next()) {
            return null;
        }

        User user = new User();
        user.setId(rs.getString("id"));
        user.setFullName(rs.getString("full_name"));
        user.setStatusId(rs.getInt("status_id"));
        user.setStatusValue(rs.getString("status.value"));
        user.setUserRoleId(rs.getInt("user_roles_id"));
        user.setUserRoleValue(rs.getString("user_roles.value"));
        user.setNic(rs.getString("nic"));
        user.setAddress(rs.getString("address"));
        user.setMobile1(rs.getString("mobile1"));
        user.setMobile2(rs.getString("mobile2"));
        user.setGenderId(rs.getInt("genders_id"));
        user.setGenderValue(rs.getString("genders.value"));
        user.setSysUsername(rs.getString("sys_username"));
        user.setSysPassword(rs.getString("sys_password"));

        return user;
    }

    public void updateUser(User user, String id) throws SQLException {
        
        System.out.println("UPDATE `users` SET "
                + "`full_name` = '" + user.getFullName() + "', "
                + "`status_id` = '" + user.getStatusId() + "', "
                + "`user_roles_id` = '" + user.getUserRoleId() + "', "
                + "`address` = '" + user.getAddress() + "', "
                + "`mobile1` ='" + user.getMobile1() + "', "
                + "`mobile2` = '" + user.getMobile2() + "', "
                + "`sys_username` = '" + user.getSysUsername() + "', "
                + "`sys_password` = '" + user.getSysPassword() + "' "
                + "WHERE `users`.`id` = '" + id + "'");

        AppConnection.iud(
                "UPDATE `users` SET "
                + "`full_name` = '" + user.getFullName() + "', "
                + "`status_id` = '" + user.getStatusId() + "', "
                + "`user_roles_id` = '" + user.getUserRoleId() + "', "
                + "`address` = '" + user.getAddress() + "', "
                + "`mobile1` ='" + user.getMobile1() + "', "
                + "`mobile2` = '" + user.getMobile2() + "', "
                + "`sys_username` = '" + user.getSysUsername() + "', "
                + "`sys_password` = '" + user.getSysPassword() + "' "
                + "WHERE `users`.`id` = '" + id + "'");
    }
}
