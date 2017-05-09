package com.example.quocthao.supership.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GameNet on 3/23/2017.
 */

public class ObjInfo {

    @SerializedName("address")
    private String infoAddress;
    @SerializedName("name")
    private String infoName;
    @SerializedName("phone")
    private String infoPhone;
    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(String infoAddress) {
        this.infoAddress = infoAddress;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoPhone() {
        return infoPhone;
    }

    public void setInfoPhone(String infoPhone) {
        this.infoPhone = infoPhone;
    }
}
