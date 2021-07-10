package dev.kaua.river.Data.Post.Actions;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import dev.kaua.river.Adapters.Posts_Adapters;
import dev.kaua.river.Data.Post.DtoPost;
import dev.kaua.river.Data.Post.PostServices;
import dev.kaua.river.Methods;
import dev.kaua.river.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecommendedPosts {

    final static Retrofit retrofit = Methods.GetRetrofitBuilder();
    private static Parcelable recyclerViewState;

    public static void getRecommendedPosts(Context context, RecyclerView recyclerView/*, SwipeRefreshLayout swipe_main*/){
        PostServices services = retrofit.create(PostServices.class);
        Call<ArrayList<DtoPost>> call = services.getRecommendedPosts("Test");
        recyclerViewState = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
        ArrayList<DtoPost> arraylist = new ArrayList<>();
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        call.enqueue(new Callback<ArrayList<DtoPost>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoPost>> call, Response<ArrayList<DtoPost>> response) {
                //swipe_main.setRefreshing(false);
                ArrayList<DtoPost> list = response.body();
                for (int i = 0; i < list.get(0).getPosts().size(); i++){
                    DtoPost.Posts_Search dtoPost = list.get(0).getPosts().get(i);
                    DtoPost post = new DtoPost();
                    post.setName_user(dtoPost.getName_user());
                    post.setUsername(dtoPost.getUsername());
                    post.setPost_content(dtoPost.getPost_content());
                    post.setProfile_image(dtoPost.getProfile_image());
                    post.setPost_images(dtoPost.getPost_images());
                    post.setPost_likes(dtoPost.getPost_likes());
                    post.setPost_comments_amount(dtoPost.getPost_comments_amount());
                    post.setVerification_level(dtoPost.getVerification_level());
                    arraylist.add(post);
                }

                Posts_Adapters posts_adapters = new Posts_Adapters(arraylist, context);
                posts_adapters.notifyDataSetChanged();
                recyclerView.setAdapter((RecyclerView.Adapter) posts_adapters);
                recyclerView.getRecycledViewPool().clear();
                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            }

            @Override
            public void onFailure(Call<ArrayList<DtoPost>> call, Throwable t) {
                ToastHelper.toast(((Activity)context), "" + t.getMessage(), 0);

            }
        });


        /*

                List<DtoPost> list = response.body();
                ToastHelper.toast(((Activity)context), "" + list, 0);
        * */
    }
}
