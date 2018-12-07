package com.sdzshn3.android.news247.Repositories;

import android.util.Log;

import com.sdzshn3.android.news247.Fragments.EntertainmentNewsFragment;
import com.sdzshn3.android.news247.Retrofit.ApiService;
import com.sdzshn3.android.news247.Retrofit.Article;
import com.sdzshn3.android.news247.Retrofit.Client;
import com.sdzshn3.android.news247.Retrofit.NewsModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntertainmentRepository {
    private static MutableLiveData<List<Article>> data = new MutableLiveData<>();

    private final ApiService apiService;
    private static Call<NewsModel> call;

    public EntertainmentRepository(){
        apiService = Client.getApiService();
        loadData();
    }

    private void loadData() {
        call = apiService.getResponse(EntertainmentNewsFragment.URL);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Log.e("EntertainmentRepository", "onFailure", t);
                data.postValue(null);
            }
        });
    }

    public void refresh() {
        call = apiService.getResponse(EntertainmentNewsFragment.URL);
        call.clone().enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Log.e("EntertainmentRepository", "onFailure", t);
                data.postValue(null);
            }
        });
    }

    public LiveData<List<Article>> getData(){
        return data;
    }
}
