package com.example.user.a7cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.android.volley.Response;
import com.example.user.a7cook.adapters.BannerAdapter;
import com.example.user.a7cook.adapters.CategoryAdapter;
import com.example.user.a7cook.adapters.RecipeAdapter;
import com.example.user.a7cook.data.Banner;
import com.example.user.a7cook.data.Category;
import com.example.user.a7cook.data.Recipe;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new ApiService(this);

        setupViews();
    }

    private void setupViews() {

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getBanners();
        getCategories();
        getRecipes();
    }

    private void getRecipes() {
        apiService.getRecipes(new Response.Listener<List<Recipe>>() {
            @Override
            public void onResponse(List<Recipe> response) {
                RecyclerView recyclerView = findViewById(R.id.rv_main_recipes);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                        2, StaggeredGridLayoutManager.VERTICAL
                ));
                recyclerView.setAdapter(new RecipeAdapter(response));
            }
        });
    }

    private void getCategories() {
        apiService.getCategories(new Response.Listener<List<Category>>() {
            @Override
            public void onResponse(List<Category> categories) {
                RecyclerView categoriesRv = findViewById(R.id.rv_main_categories);
                categoriesRv.setLayoutManager(new LinearLayoutManager(
                        MainActivity.this, LinearLayoutManager.HORIZONTAL, false
                ));
                categoriesRv.setAdapter(new CategoryAdapter(categories));
            }
        });
    }

    private void getBanners() {
        apiService.getBanners(new Response.Listener<List<Banner>>() {
            @Override
            public void onResponse(List<Banner> banners) {
                RecyclerView bannerRv = findViewById(R.id.rv_main_slider);
                bannerRv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                bannerRv.setAdapter(new BannerAdapter(banners));

                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(bannerRv);
            }
        });
    }
}
