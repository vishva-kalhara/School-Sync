/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author vishv
 */
public class Student {

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the guardian1FullName
     */
    public String getGuardian1FullName() {
        return guardian1FullName;
    }

    /**
     * @param guardian1FullName the guardian1FullName to set
     */
    public void setGuardian1FullName(String guardian1FullName) {
        this.guardian1FullName = guardian1FullName;
    }

    /**
     * @return the guardian2FullName
     */
    public String getGuardian2FullName() {
        return guardian2FullName;
    }

    /**
     * @param guardian2FullName the guardian2FullName to set
     */
    public void setGuardian2FullName(String guardian2FullName) {
        this.guardian2FullName = guardian2FullName;
    }

    /**
     * @return the mobile1
     */
    public String getMobile1() {
        return mobile1;
    }

    /**
     * @param mobile1 the mobile1 to set
     */
    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    /**
     * @return the mobile2
     */
    public String getMobile2() {
        return mobile2;
    }

    /**
     * @param mobile2 the mobile2 to set
     */
    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    /**
     * @return the genderId
     */
    public int getGenderId() {
        return genderId;
    }

    /**
     * @param genderId the genderId to set
     */
    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    /**
     * @return the genderValue
     */
    public String getGenderValue() {
        return genderValue;
    }

    /**
     * @param genderValue the genderValue to set
     */
    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private String id;
    private String fullName;
    private Date createdAt;
    private String guardian1FullName;
    private String guardian2FullName;
    private String mobile1;
    private String mobile2;
    private String email;

    private int genderId;
    private String genderValue;
    
    private int statusId;
    private String statusValue;
    
    private int gradeId;
    private String gradeValue;
    private String classValue;

    /**
     * @return the statusId
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the statusValue
     */
    public String getStatusValue() {
        return statusValue;
    }

    /**
     * @param statusValue the statusValue to set
     */
    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    /**
     * @return the gradeId
     */
    public int getGradeId() {
        return gradeId;
    }

    /**
     * @param gradeId the gradeId to set
     */
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * @return the classValue
     */
    public String getClassValue() {
        return classValue;
    }

    /**
     * @param classValue the classValue to set
     */
    public void setClassValue(String classValue) {
        this.classValue = classValue;
    }

    /**
     * @return the gradeValue
     */
    public String getGradeValue() {
        return gradeValue;
    }

    /**
     * @param gradeValue the gradeValue to set
     */
    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

}
