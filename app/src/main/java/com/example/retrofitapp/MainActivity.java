package com.example.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.retrofitapp.Adapter.CustomAdapter;
import com.example.retrofitapp.Model.RetroPhoto;
import com.example.retrofitapp.Network.GetDataService;
import com.example.retrofitapp.Network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;

    private RecyclerView recyclerView;

    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();


        /*Create handle for the RetrofitInstance interface*/
       GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<RetroPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<RetroPhoto> photoList) {
        //Lookup the recyclerview in activity layout
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);

        //
        adapter = new CustomAdapter(this,photoList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        //
        recyclerView.setLayoutManager(layoutManager);

        //Attach the adapter to recyclerview to populate item
        recyclerView.setAdapter(adapter);
    }
}