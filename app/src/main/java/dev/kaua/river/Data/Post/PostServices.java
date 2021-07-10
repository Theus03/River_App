package dev.kaua.river.Data.Post;

import java.util.ArrayList;
import java.util.List;

import dev.kaua.river.Data.Account.DtoAccount;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostServices {

    @FormUrlEncoded
    @POST("post/list/recommended")
    Call<ArrayList<DtoPost>> getRecommendedPosts(@Field("Test") String msg);
}
