package com.example.quocthao.supership;

/**
 * Created by Quoc Thao on 3/19/2017.
 */

public class ObjData {

    String name;
    String address;
    String phone;

    public ObjData() {
    }

    public ObjData(String _name, String _address, String _phone){
        name = _name;
        address = _address;
        phone = _phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
