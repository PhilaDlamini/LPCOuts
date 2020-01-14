package com.example.lpcouts;

public class Outs implements Comparable<Outs> {
  private String block;
  
  private String date;
  
  private String name;
  
  private String time;
  
  public Outs() {}
  
  public Outs(String paramString) {
    String[] arrayOfString = paramString.split(";");
    this.name = arrayOfString[0];
    this.block = arrayOfString[1];
    this.time = arrayOfString[2];
    this.date = arrayOfString[3];
  }
  
  public Outs(String paramString1, String paramString2, String paramString3, String paramString4) {
    this.name = paramString1;
    this.block = paramString2;
    this.date = paramString4;
    this.time = paramString3;
  }
  
  public int compareTo(Outs paramOuts) { return this.name.compareTo(paramOuts.getName()); }
  
  public String getBlock() { return this.block; }
  
  public String getDate() { return this.date; }
  
  public String getName() { return this.name; }
  
  public String getTime() { return this.time; }
  
  public String toString() {
    return name + ";" + block + ";" + time + ";" + date + ";null";
  }
}
