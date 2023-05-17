package com.tatonimatteo.waterclient.network;

import com.tatonimatteo.waterclient.entity.Record;
import com.tatonimatteo.waterclient.entity.Sensor;
import com.tatonimatteo.waterclient.entity.Station;
import com.tatonimatteo.waterclient.network.security.TokenResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HttpService {
    @POST("auth/login")
    Call<TokenResponse> login(
            @Body RequestBody loginData
    );

    @GET("stations/all")
    Call<List<Station>> getAllStation();

    @GET("sensors/all")
    Call<List<Sensor>> getAllSensor();

    @GET("sensors/filter/station/{id}")
    Call<List<Sensor>> getSensorByStation(
            @Path("id") long stationID
    );

    @GET("records/filter/station-date/{id}/{start}/{end}")
    Call<List<Record>> getRecordByStationAndDate(
            @Path("id") long stationID,
            @Path("start") String startDate,
            @Path("end") String endDate
    );

    @GET("records/filter/sensor-date/{id}/{start}/{end}")
    Call<List<Record>> getRecordBySensorAndDate(
            @Path("id") long stationSensorID,
            @Path("start") String startDate,
            @Path("end") String endDate
    );
}
