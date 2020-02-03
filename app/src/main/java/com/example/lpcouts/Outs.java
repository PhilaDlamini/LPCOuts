package com.example.lpcouts;

public class Outs implements Comparable<Outs> {
  private String block;
  private String date;
  private String name;
  private String time;
  
  public Outs() {

  }
  
  public Outs(String paramString) {
    String[] arrayOfString = paramString.split(";");
    this.name = arrayOfString[0];
    this.block = arrayOfString[1];
    this.time = arrayOfString[2];
    this.date = arrayOfString[3];
  }
  
  public Outs(String name, String block, String time, String date) {
    this.name = name;
    this.block = block;
    this.date = date;
    this.time = time;
  }
  
  public int compareTo(Outs paramOuts) { return name.compareTo(paramOuts.getName()); }
  
  public String getBlock() { return block; }
  
  public String getDate() { return date; }
  
  public String getName() { return name; }
  
  public String getTime() { return time; }
  
  public String toString() {
    return name + ";" + block + ";" + time + ";" + date + ";null";
  }
}
