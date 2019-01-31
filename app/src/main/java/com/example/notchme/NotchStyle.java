package com.example.notchme;

public enum NotchStyle {
    IPHONEX, HUAWEIP20, PIXEL3;

    public String name;
    NotchStyle()
    {
        this.name = this.toString();
    }
}
