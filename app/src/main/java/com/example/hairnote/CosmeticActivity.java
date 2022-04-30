package com.example.hairnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CosmeticActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button btn_addCosmeticMain;

    private RecyclerView cosmeticsRecView;
    CosmeticsRecViewAdapter cosmeticAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic);

        drawerLayout = findViewById(R.id.drawer_layout);
        setActionBar();

        cosmeticsRecView = findViewById(R.id.cosmeticsRecView);
        btn_addCosmeticMain = findViewById(R.id.btnAddCosmeticMain);

        dataBaseHelper = new DataBaseHelper(CosmeticActivity.this);

        ShowAllCosmeticsOnRecView(dataBaseHelper);

        btn_addCosmeticMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CosmeticActivity.this, CosmeticAdd.class);
                CosmeticActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    public void ShowAllCosmeticsOnRecView(DataBaseHelper dataBaseHelper) {
        cosmeticAdapter = new CosmeticsRecViewAdapter(this);
        cosmeticsRecView.setAdapter(cosmeticAdapter);
        cosmeticsRecView.setLayoutManager(new GridLayoutManager(this, 2));
        cosmeticAdapter.setCosmetics(dataBaseHelper.getAllCosmetics());
    }

    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Kosmetyki");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cosmeticAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void  ClickWash(View view){
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void ClickCosmetic(View view){
        recreate();
    }

    public void ClickIngredient(View view){
        //Redirect activity to Skladniki
        MainActivity.redirectActivity(this,IngredientActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}