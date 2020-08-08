package com.example.myretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    IService service;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        IService service = RetrofitInstance.getRetrofitInstance().create(IService.class);
        //Call<List<Album>> albums = service.getAlbum();
        Call<List<Album>> albums = service.getUserAlbum(1);
        albums.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {

                extractResponse(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    public void extractResponse(List<Album> list){

        if(list != null) {
            for (Album album : list) {

                Log.d(TAG, "album id - " + album.getAlbumId());
                Log.d(TAG, "id - " + album.getId());
                Log.d(TAG, "title - " + album.getTitle());
                Log.d(TAG, "URL - " + album.getUrl());
                Log.d(TAG, "Thumnail Url - " + album.getThumbnailUrl());
                Log.d(TAG, "----------------------------------");
            }

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(list, this);
            recyclerView.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(manager);
        }
    }


}
