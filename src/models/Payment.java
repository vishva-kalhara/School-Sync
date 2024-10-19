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
public class Payment {

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the studentId
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the addtionalFeesId
     */
    public int getAddtionalFeesId() {
        return addtionalFeesId;
    }

    /**
     * @param addtionalFeesId the addtionalFeesId to set
     */
    public void setAddtionalFeesId(int addtionalFeesId) {
        this.addtionalFeesId = addtionalFeesId;
    }

    /**
     * @return the paidAmount
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * @param paidAmount the paidAmount to set
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * @return the paidAt
     */
    public Date getPaidAt() {
        return paidAt;
    }

    /**
     * @param paidAt the paidAt to set
     */
    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }
    
    private int id;
    private String studentId;
    private int addtionalFeesId;
    private double paidAmount;
    private Date paidAt;
}
