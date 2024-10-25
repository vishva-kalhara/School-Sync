/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.SQLException;
import utils.AppConnection;
import java.sql.ResultSet;
import utils.ErrorException;

/**
 *
 * @author vishv
 */
public class ResourceController {
    
    public void createClass(int grade, String className) throws SQLException, ErrorException {
        
        ResultSet rs = AppConnection.search("SELECT * FROM `grades_has_classes` WHERE `grades_id` = '"+ grade +"' AND `class` = '"+ className +"'");
        if(rs.next()) throw new ErrorException("There is already a class");
        
        AppConnection.iud("INSERT INTO `grades_has_classes` (`grades_id`, `class`, `is_active`) VALUES ('"+ grade +"', '"+ className +"', '1')");
    }
}
