/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import java.util.Date;
import models.Visitor;
import utils.ErrorException;

/**
 *
 * @author Dini
 */
public class VisitorValidator {

    public void validateVisitor(Integer stuId, Date selectedDate  ) throws ErrorException {

        if (stuId == 0) {
            throw new ErrorException("Please select the student!");
        }

        if (selectedDate == null) {
            throw new ErrorException("Date cannot be empty!");
        } else if (selectedDate.before(new Date())) {
            throw new ErrorException("Date must be today or a future date!");

        }

    }

}
