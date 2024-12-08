/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package validation;

import org.junit.Assert;
import org.junit.Test;
import utils.ErrorException;

/**
 *
 * @author vishv
 */
public class NoticesValidatorTest {

    private final NoticesValidator validator = new NoticesValidator();
    
    @Test
    public void validateEmailData_HappyPath() {
        String validateEmailData = "valid_ref";
        String emailSubject = "email_subject";
        String emailBody = "email_body";

        try {
            validator.validateEmailData(validateEmailData, emailSubject, emailBody);
            
        } catch (ErrorException e) {
            Assert.fail("Unexpected ErrorException thrown.");
        }
    }

    @Test
    public void validateEmailData_ToRefCannotBeNull() {

        String validateEmailData = null;
        String emailSubject = "email_subject";
        String emailBody = "email_body";

        try {
            validator.validateEmailData(validateEmailData, emailSubject, emailBody);
            Assert.fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            Assert.assertEquals("Select the to address!", e.getMessage());
        }
    }
    
    @Test
    public void validateEmailData_ToRefCannotBeEmpty() {

        String validateEmailData = "";
        String emailSubject = "email_subject";
        String emailBody = "email_body";

        try {
            validator.validateEmailData(validateEmailData, emailSubject, emailBody);
            Assert.fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            Assert.assertEquals("Select the to address!", e.getMessage());
        }
    }
    
    @Test
    public void validateEmailData_EmailSubjectCannotBeNull() {

        String validateEmailData = "valid_ref";
        String emailSubject = null;
        String emailBody = "email_body";

        try {
            validator.validateEmailData(validateEmailData, emailSubject, emailBody);
            Assert.fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            Assert.assertEquals("Email subject is required!", e.getMessage());
        }
    }
    
    @Test
    public void validateEmailData_EmailSubjectCannotBeEmpty() {

        String validateEmailData = "valid_ref";
        String emailSubject = "";
        String emailBody = "email_body";

        try {
            validator.validateEmailData(validateEmailData, emailSubject, emailBody);
            Assert.fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            Assert.assertEquals("Email subject is required!", e.getMessage());
        }
    }
    
    @Test
    public void validateEmailData_EmailBodyCannotBeNull() {

        String validateEmailData = "valid_ref";
        String emailSubject = "email_subject";
        String emailBody = null;

        try {
            validator.validateEmailData(validateEmailData, emailSubject, emailBody);
            Assert.fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            Assert.assertEquals("Email Body is required!", e.getMessage());
        }
    }
    
    @Test
    public void validateEmailData_EmailBodyCannotBeEmpty() {

        String validateEmailData = "valid_ref";
        String emailSubject = "email_subject";
        String emailBody = "";

        try {
            validator.validateEmailData(validateEmailData, emailSubject, emailBody);
            Assert.fail("Expected an ErrorException to be thrown.");
        } catch (ErrorException e) {
            Assert.assertEquals("Email Body is required!", e.getMessage());
        }
    }
    
}
