package dev.kaua.river.Data.Post.Actions;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import dev.kaua.river.Adapters.Posts_Adapters;
import dev.kaua.river.Data.Post.DtoPost;
import dev.kaua.river.Data.Post.PostServices;
import dev.kaua.river.LocalDataBase.DaoPosts;
import dev.kaua.river.R;
import dev.kaua.river.Security.EncryptHelper;
import dev.kaua.river.Tools.Methods;
import dev.kaua.river.Tools.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecommendedPosts {

    final static Retrofit retrofit = Methods.GetRetrofitBuilder();
    private static Parcelable recyclerViewState;

    //  Method to get RecommendedPosts
    public static void getRecommendedPosts(Context context, RecyclerView recyclerView, RelativeLayout loadingPanel){
        PostServices services = retrofit.create(PostServices.class);
        Call<ArrayList<DtoPost>> call = services.getRecommendedPosts("RecommendedPosts-And-FollowingPosts");
        recyclerViewState = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
        ArrayList<DtoPost> arraylist = new ArrayList<>();
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        DaoPosts daoPosts = new DaoPosts(context);

        ArrayList<DtoPost> listPostDB = daoPosts.get_post(0);
        if(listPostDB.size() > 0){
            Posts_Adapters posts_adapters = new Posts_Adapters(listPostDB, context);
            posts_adapters.notifyDataSetChanged();
            recyclerView.setAdapter(posts_adapters);
            recyclerView.getRecycledViewPool().clear();
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            loadingPanel.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        //  Checking if user is connected to a network
        if(Methods.isOnline(context)){
            call.enqueue(new Callback<ArrayList<DtoPost>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<DtoPost>> call, @NotNull Response<ArrayList<DtoPost>> response) {
                    //swipe_main.setRefreshing(false);
                    ArrayList<DtoPost> list = response.body();
                    daoPosts.DropTable();
                    for (int i = 0; i < Objects.requireNonNull(list).get(0).getPosts().size(); i++){
                        DtoPost.Posts_Search dtoPost = list.get(0).getPosts().get(i);
                        DtoPost post = new DtoPost();
                        post.setPost_id(EncryptHelper.decrypt(dtoPost.getPost_id()));
                        post.setAccount_id(EncryptHelper.decrypt(dtoPost.getAccount_id()));
                        post.setVerification_level(EncryptHelper.decrypt(dtoPost.getVerification_level()));
                        post.setName_user(EncryptHelper.decrypt(dtoPost.getName_user()));
                        post.setUsername(EncryptHelper.decrypt(dtoPost.getUsername()));
                        post.setProfile_image(EncryptHelper.decrypt(dtoPost.getProfile_image()));
                        post.setPost_date(EncryptHelper.decrypt(dtoPost.getPost_date()));
                        post.setPost_time(EncryptHelper.decrypt(dtoPost.getPost_time()));
                        post.setPost_content(EncryptHelper.decrypt(dtoPost.getPost_content()));
                        if(dtoPost.getPost_images().size() != 0) post.setPost_images(dtoPost.getPost_images());
                        else post.setPost_images(null);
                        post.setPost_likes(EncryptHelper.decrypt(dtoPost.getPost_likes()));
                        post.setPost_comments_amount(EncryptHelper.decrypt(dtoPost.getPost_comments_amount()));
                        post.setPost_topic(EncryptHelper.decrypt(dtoPost.getPost_topic()));
                        arraylist.add(post);
                    }
                    daoPosts.Register_Home_Posts(arraylist);

                    Posts_Adapters posts_adapters = new Posts_Adapters(arraylist, context);
                    posts_adapters.notifyDataSetChanged();
                    recyclerView.setAdapter(posts_adapters);
                    recyclerView.getRecycledViewPool().clear();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    loadingPanel.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(@NotNull Call<ArrayList<DtoPost>> call, @NotNull Throwable t) {}
            });
        }else ToastHelper.toast((Activity)context , context.getString(R.string.you_are_without_internet), 0);


    }
}