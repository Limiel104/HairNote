package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IngredientActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private RecyclerView ingredientsRecView;

    Button btn_addIngredientMain, btn_viewAll;
    EditText et_name, et_type, et_description;
    IngredientsRecViewAdapter ingredientAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        drawerLayout = findViewById(R.id.drawer_layout);

        ingredientsRecView = findViewById(R.id.ingredientsRecView);
        btn_addIngredientMain = findViewById(R.id.btnAddIngredientMain);

        dataBaseHelper = new DataBaseHelper(IngredientActivity.this);

        ShowAllIngredientsOnRecView(dataBaseHelper);

        //ponizej jet z innego tutoriala

        //ArrayList<Ingredient> ingredients = new ArrayList<>();
        //ingredients.add(new Ingredient(1,"lorem","olej","dsf sfdger er er ty sreteter"));
        //ingredients.add(new Ingredient(2,"ipsum","antystatyk","lsbdiusdud sdffsdf"));
        //ingredients.add(new Ingredient(3,"asdbu","proteiny","sdfafsdfausdud sdfdg gdfgd"));
        //ingredients.add(new Ingredient(4,"effe","olej","ewtqegea errw we yt wree wrt"));

        //IngredientsRecViewAdapter adapter = new IngredientsRecViewAdapter();
        //adapter.setIngredients(ingredients);

        //ingredientsRecView.setAdapter(adapter);
        //ingredientsRecView.setLayoutManager(new LinearLayoutManager(this));

        //tu sie konczy

        btn_addIngredientMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Ingredient ingredient;

                try {
                    ingredient = new Ingredient(-1,et_name.getText().toString(),et_type.getText().toString(),et_description.getText().toString());
                    //Toast.makeText(IngredientActivity.this,ingredient.toString(),Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    //Toast.makeText(IngredientActivity.this,"Wystąpił błąd",Toast.LENGTH_SHORT).show();
                    ingredient = new Ingredient(-1, "error","","");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(IngredientActivity.this);
                boolean success = dataBaseHelper.addIngredient(ingredient);
                Toast.makeText(IngredientActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowAllIngredientsOnRecView(dataBaseHelper);*/

                Intent intent = new Intent(IngredientActivity.this, IngredientAdd.class);
                IngredientActivity.this.startActivity(intent);
                finish();

            }
        });

        /*btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(IngredientActivity.this);
                ShowAllIngredientsOnRecView(dataBaseHelper);

                //Toast.makeText(IngredientActivity.this, allIngredients.toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(IngredientActivity.this,"Wszystkie",Toast.LENGTH_SHORT).show();

                /*ingredientAdapter = new IngredientsRecViewAdapter();
                ingredientAdapter.setIngredients(allIngredients);
                ingredientsRecView.setAdapter(ingredientAdapter);
                ingredientsRecView.setLayoutManager(new LinearLayoutManager(IngredientActivity.this));*/

           /* }
        });

        */


    }

    private void ShowAllIngredientsOnRecView(DataBaseHelper dataBaseHelper) {
        //ArrayList<Ingredient> allIngredients = dataBaseHelper.getAllIngredients();
        ingredientAdapter = new IngredientsRecViewAdapter(this);
        ingredientAdapter.setIngredients(dataBaseHelper.getAllIngredients());
        ingredientsRecView.setAdapter(ingredientAdapter);
        ingredientsRecView.setLayoutManager(new LinearLayoutManager(IngredientActivity.this));
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