package com.example.lpcouts;

public class Extension implements Comparable<Extension> {

    //The member variables for the class
    String name;
    String returnDate;
    String returnTime;

    /* ever used?
    public Extension() {}*/

    //Builds an extension instance from a string
    public Extension(String paramString) {
        String[] arrayOfString = paramString.split(";");
        this.name = arrayOfString[0];
        this.returnTime = arrayOfString[1];
        this.returnDate = arrayOfString[2];
    }

    //Creates an extension instance with known values for member variables
    public Extension(String paramString1, String paramString2, String paramString3) {
        this.name = paramString1;
        this.returnTime = paramString2;
        this.returnDate = paramString3;
    }

    public int compareTo(Extension paramExtension) { return this.name.compareTo(paramExtension.getName()); }

    //Getters for the class
    public String getName() { return this.name; }
    public String getReturnDate() { return this.returnDate; }
    public String getReturnTime() { return this.returnTime; }

    //Converts the extension instance into a string
    public String toString() {
        return this.name + ";"
                + this.returnTime + ";"
                + this.returnDate + ";null";
    }
}
