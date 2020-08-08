package com.example.myretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IService {

    @GET("albums/1/photos")
    Call<List<Album>> getAlbum();

    @GET("albums/{id}/photos")
    Call<List<Album>> getUserAlbum(@Path("id") int userId);

}
