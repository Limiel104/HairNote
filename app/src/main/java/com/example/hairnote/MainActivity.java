package com.example.hairnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FloatingActionButton btn_addWashMain, btn_goToMap;

    private RecyclerView washesRecView;
    WashesRecViewAdapter washesAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        setActionBar();

        washesRecView = findViewById(R.id.washesRecView);
        btn_addWashMain = findViewById(R.id.btnAddWashMain);
        btn_goToMap = findViewById(R.id.btnGoToMap);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowAllWashesOnRecView(dataBaseHelper);

        btn_addWashMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WashAdd.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btn_goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    public void ShowAllWashesOnRecView(DataBaseHelper dataBaseHelper) {
        washesAdapter = new WashesRecViewAdapter(this);
        washesAdapter.setWashes(dataBaseHelper.getAllWashes());
        washesRecView.setAdapter(washesAdapter);
        washesRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Mycia");
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
                washesAdapter.getFilter().filter(newText);
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
        //Open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        //Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        //close drawer layout
        //check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickWash(View view){
        //Recreate this activity
        //recreate();
        closeDrawer(drawerLayout);
    }

    public void ClickCosmetic(View view){
        //Redirect activity to Kosmetyki
        redirectActivity(this, CosmeticActivity.class);
        finish();
    }

    public void ClickIngredient(View view){
        //Redirect activity to Skladniki
        redirectActivity(this,IngredientActivity.class);
        finish();
    }

    public static void redirectActivity(Activity activity, Class aClass){
        //Initialize intent
        Intent intent = new Intent(activity,aClass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }
}