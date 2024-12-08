/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import utils.ErrorException;

/**
 *
 * @author vishv
 */
public class NoticesValidator {
    
    public void validateEmailData(String toRef, String emailSubject, String emailBody) throws ErrorException {
        
        if(toRef == null || toRef.isBlank()) throw new ErrorException("Select the to address!");
        
        if(emailSubject == null || emailSubject.isBlank()) throw new ErrorException("Email subject is required!");
        
        if(emailBody == null || emailBody.isBlank()) throw new ErrorException("Email Body is required!");
    }
}
