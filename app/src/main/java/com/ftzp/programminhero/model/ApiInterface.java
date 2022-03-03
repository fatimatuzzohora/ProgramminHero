package com.ftzp.programminhero.model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("quiz.json")
    Call<Questions> questionList();

}
