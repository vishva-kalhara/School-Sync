/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import models.Student;
import utils.ErrorException;

/**
 *
 * @author vishv
 */
public class StudentValidator {

    public void validate(Student student) throws ErrorException {

        // FullName Validation
        if (student.getFullName().isBlank()) {
            throw new ErrorException("Name cannot be empty!");
        }

        // Email Validation
        if (student.getEmail().isBlank()) {
            throw new ErrorException("Name cannot be empty!");
        }
        if (!student.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new ErrorException("Invalid Email Format!");
        }

        // Gardian 1
        if (student.getGuardian1FullName().isBlank()) {
            throw new ErrorException("Guardian name 1 cannot be empty!");
        }

        // Gardian 2
        if (student.getGuardian2FullName().isBlank()) {
            throw new ErrorException("Guardian name 2 cannot be empty!");
        }

        // Mobile 1 validation
        if (student.getMobile1().isBlank()) {
            throw new ErrorException("Mobile-1 cannot be empty!");
        }
        if (student.getMobile1().length() != 10) {
            throw new ErrorException("Mobile-1 is invalid!");
        }

        // Mobile 2 Validation
        if (!student.getMobile2().isBlank() && student.getMobile2().length() != 10) {
            throw new ErrorException("Mobile-2 is invalid!");
        }
        
        // Gender Validation
        if(student.getGenderId() == 0){
            throw new ErrorException("Select a gender!");
        }
        
        // Grade Validation
        if(student.getGradeId() == 0){
            throw new ErrorException("Select a grade!");
        }
        
        // Class Validation
        if(student.getClassValue().equals("Select")){
            throw new ErrorException("Select a class!");
        }
    }
}
