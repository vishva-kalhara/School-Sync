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
public class User {

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
     * @return the classId
     */
    public int getClassId() {
        return classId;
    }

    /**
     * @param classId the classId to set
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * @return the serviceNumber
     */
    public String getServiceNumber() {
        return serviceNumber;
    }

    /**
     * @param serviceNumber the serviceNumber to set
     */
    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the sysUsername
     */
    public String getSysUsername() {
        return sysUsername;
    }

    /**
     * @param sysUsername the sysUsername to set
     */
    public void setSysUsername(String sysUsername) {
        this.sysUsername = sysUsername;
    }

    /**
     * @return the sysPassword
     */
    public String getSysPassword() {
        return sysPassword;
    }

    /**
     * @param sysPassword the sysPassword to set
     */
    public void setSysPassword(String sysPassword) {
        this.sysPassword = sysPassword;
    }

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
     * @return the userRoleId
     */
    public int getUserRoleId() {
        return userRoleId;
    }

    /**
     * @param userRoleId the userRoleId to set
     */
    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * @return the userRoleValue
     */
    public String getUserRoleValue() {
        return userRoleValue;
    }

    /**
     * @param userRoleValue the userRoleValue to set
     */
    public void setUserRoleValue(String userRoleValue) {
        this.userRoleValue = userRoleValue;
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
    
    private Date createdAt;
    private String id;
    private String fullName;
    private int classId;
    private String serviceNumber;
    private String address;
    private String mobile1;
    private String mobile2;
    private String sysUsername;
    private String sysPassword;
    
    private int statusId;
    private String statusValue;
    
    private int userRoleId;
    private String userRoleValue;
    
    private int genderId;
    private String genderValue;
   
}
