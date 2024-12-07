/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package validation;

import models.User;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.ErrorException;

/**
 *
 * @author vishv
 */
public class AuthValidatorTest {

    private final AuthValidator validator = new AuthValidator();

    @Test
    public void validateSignIn_ValidUser_DoesNotThrowException() {
        User payLoad = new User();
        payLoad.setSysUsername("validUser");
        payLoad.setSysPassword("validPassword");

        try {
            validator.validateSignIn(payLoad);
        } catch (ErrorException e) {
            fail("Exception should not have been thrown for a valid user.");
        }
    }

    @Test
    public void validateSignIn_BlankUsername_ThrowsExceptionWithCorrectMessage() {
        User payLoad = new User();
        payLoad.setSysUsername("");
        payLoad.setSysPassword("validPassword");

        try {
            validator.validateSignIn(payLoad);
            fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }
    
    @Test
    public void validateSignIn_BlankPassword_ThrowsExceptionWithCorrectMessage() {
        User payLoad = new User();
        payLoad.setSysUsername("validUser");
        payLoad.setSysPassword("");

        try {
            validator.validateSignIn(payLoad);
            fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            assertEquals("Password cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void validateSignIn_BlankUsernameAndPassword_ThrowsExceptionForFirstValidation() {
        User payLoad = new User();
        payLoad.setSysUsername("");
        payLoad.setSysPassword("");

        try {
            validator.validateSignIn(payLoad);
            fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }
}
