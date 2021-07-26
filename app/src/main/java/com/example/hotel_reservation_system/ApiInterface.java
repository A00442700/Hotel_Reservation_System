package com.example.hotel_reservation_system;

import retrofit.ResponseCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public interface ApiInterface {

    // API's endpoints
    @GET("/getHotelList")
    public void getHotelsLists(Callback<List<HotelListData>> callback);

    @POST("/reservationConfirmation")
    public void reserveHotel(@Body TypedInput body, Callback<HotelConfirmationData> callback);




    
}
