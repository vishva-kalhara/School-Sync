/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author Dini
 */
public class Visitor {
    
    private int id;
    private int hasAttended;
    private String studentId;
    private Date date;
    private int ClassId;
    

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
     * @return the hasAttended
     */
    public int getHasAttended() {
        return hasAttended;
    }

    /**
     * @param hasAttended the hasAttended to set
     */
    public void setHasAttended(int hasAttended) {
        this.hasAttended = hasAttended;
    }

 

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the ClassId
     */
    public int getClassId() {
        return ClassId;
    }

    /**
     * @param ClassId the ClassId to set
     */
    public void setClassId(int ClassId) {
        this.ClassId = ClassId;
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


    
}
