package com.example.hairnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IngredientActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private RecyclerView ingredientsRecView;

    Button btn_addIngredientMain;
    IngredientsRecViewAdapter ingredientAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        drawerLayout = findViewById(R.id.drawer_layout);
        setActionBar();

        ingredientsRecView = findViewById(R.id.ingredientsRecView);
        btn_addIngredientMain = findViewById(R.id.btnAddIngredientMain);

        dataBaseHelper = new DataBaseHelper(IngredientActivity.this);

        ShowAllIngredientsOnRecView(dataBaseHelper);

        btn_addIngredientMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IngredientActivity.this, IngredientAdd.class);
                IngredientActivity.this.startActivity(intent);
                finish();

            }
        });
    }

    private void ShowAllIngredientsOnRecView(DataBaseHelper dataBaseHelper) {
        ingredientAdapter = new IngredientsRecViewAdapter(this);
        ingredientAdapter.setIngredients(dataBaseHelper.getAllIngredients());
        ingredientsRecView.setAdapter(ingredientAdapter);
        ingredientsRecView.setLayoutManager(new LinearLayoutManager(IngredientActivity.this));
    }

    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Składniki");
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
                ingredientAdapter.getFilter().filter(newText);
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
        MainActivity.redirectActivity(this,MainActivity.class);
    }

    public void ClickCosmetic(View view){
        MainActivity.redirectActivity(this, CosmeticActivity.class);
    }

    public void ClickIngredient(View view){
        recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}