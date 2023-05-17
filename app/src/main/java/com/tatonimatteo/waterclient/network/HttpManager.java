package com.tatonimatteo.waterclient.network;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tatonimatteo.waterclient.configuration.RecordDeserializer;
import com.tatonimatteo.waterclient.entity.Record;
import com.tatonimatteo.waterclient.entity.Sensor;
import com.tatonimatteo.waterclient.entity.Station;
import com.tatonimatteo.waterclient.network.security.AuthInterceptor;
import com.tatonimatteo.waterclient.network.security.TokenResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static final String BASE_URL = "http://192.168.1.11:8080/";
    private final HttpService httpService;

    public HttpManager() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        AuthInterceptor authInterceptor = new AuthInterceptor();
        okHttpClientBuilder.addInterceptor(authInterceptor);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Record.class, new RecordDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClientBuilder.build())
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    public void login(
            String username,
            String password,
            Callback<TokenResponse> callback
    ) {
        try {
            JSONObject loginData = new JSONObject();
            loginData.put("email", username);
            loginData.put("password", password);
            Call<TokenResponse> call = httpService.login(
                    RequestBody.create(
                            MediaType.parse("application/json"), loginData.toString()));
            call.enqueue(callback);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllStation(Callback<List<Station>> callback) {
        Call<List<Station>> call = httpService.getAllStation();
        call.enqueue(callback);
    }

    public void getAllSensor(Callback<List<Sensor>> callback) {
        Call<List<Sensor>> call = httpService.getAllSensor();
        call.enqueue(callback);
    }

    public void getSensorByStation(
            long stationID,
            Callback<List<Sensor>> callback
    ) {
        Call<List<Sensor>> call = httpService.getSensorByStation(stationID);
        call.enqueue(callback);
    }

    public void getRecordByStationAndDate(
            long stationID,
            @NonNull Date startDate,
            @NonNull Date endDate,
            Callback<List<Record>> callback
    ) {
        Call<List<Record>> call = httpService.getRecordByStationAndDate(stationID, startDate.toString(), endDate.toString());
        call.enqueue(callback);
    }

    public void getRecordBySensorAndDate(
            long stationID,
            long sensorID,
            @NonNull Date startDate,
            @NonNull Date endDate,
            Callback<List<Record>> callback
    ) {
        long id = (stationID * 1000) + sensorID;
        Call<List<Record>> call = httpService.getRecordBySensorAndDate(id, startDate.toString(), endDate.toString());
        call.enqueue(callback);
    }
}
