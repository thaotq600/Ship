package com.example.quocthao.supership.shop.services;

import com.example.quocthao.supership.objects.ObjBill;
import com.example.quocthao.supership.objects.ObjInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GameNet on 3/23/2017.
 */

public interface IRestService {

    @GET("/infomation/customer.json")
    Call<HashMap<String, ObjInfo>> loadData();

    @GET("infomation/shop/{idShop}/bills.json")
    Call<HashMap<String, ObjBill>>  loadBill(@Path("idShop") String idShop);
}
