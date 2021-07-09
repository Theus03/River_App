package dev.kaua.river.Data.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountServices {

    @POST("user/register")
    Call<DtoAccount> registerUser (@Body DtoAccount account);

    @POST("user/login")
    Call<DtoAccount> login (@Body DtoAccount account);
}
