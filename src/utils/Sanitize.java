/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author vishv
 */
public class Sanitize {
    
    public String removeApos(String input){
        
        String[] parts = input.split("'");
        
        StringBuilder output = new StringBuilder(parts[0]);
        for(int i = 1; i < parts.length; i++){
            
            output.append("''").append(parts[i]);
        }
        
        return output.toString();
    }
}
