package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView gridView;
    private TextView toolbarTitle;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    public static List<WishlistModel> wishlistModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbarTitle=(TextView) findViewById(R.id.toolbar_titley);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("title");
        toolbarTitle.setText(title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_2);

        recyclerView=findViewById(R.id.recycler_view);
        gridView=findViewById(R.id.grid_view);

        int layout=getIntent().getIntExtra("layout",-1);

        if(layout==0) {

            recyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList, false);
            wishlistAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(wishlistAdapter);

        }else if(layout==1) {

            gridView.setVisibility(View.VISIBLE);

            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridProductLayoutAdapter.notifyDataSetChanged();
            gridView.setAdapter(gridProductLayoutAdapter);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
