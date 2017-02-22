/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapde.model;

/**
 *
 * @author Kurt
 */
public class CalendarDTO {
    private int id;
    private String title;
    private String start;
    private String end;
    private String color;
    private String textColor;
    
    public CalendarDTO(int id, String title, String start, String color, String textColor){
        this.id = id;
        this.title = title;
        this.start = start;
        this.color = color;
        this.textColor = textColor;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    
  public void setFontColor(String textColor){
      this.textColor = textColor;
  }
  
  public String getTextColor(){
      return this.textColor;
  }
}
