/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import models.User;
import utils.ErrorException;

/**
 *
 * @author vishv
 */
public class AuthValidator {

    public void validateSignIn(User user) throws ErrorException {

        if (user.getSysUsername().isBlank()) {
            throw new ErrorException("Username cannot be empty!");
        }
        if (user.getSysPassword().isBlank()) {
            throw new ErrorException("Password cannot be empty!");
        }
    }
}
