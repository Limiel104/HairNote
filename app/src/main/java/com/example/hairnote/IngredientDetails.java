package com.example.hairnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class IngredientDetails extends AppCompatActivity {

    public static final String INGREDIENT_ID_KEY = "ingredientID";
    private TextView det_ingName, det_ingType, det_ingDesc;

    DataBaseHelper dataBaseHelper;

    Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

        getSupportActionBar().setTitle("Szczegóły");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        dataBaseHelper = new DataBaseHelper(IngredientDetails.this);

        Intent intent = getIntent();
        if (intent != null) {
            int ingredientID = intent.getIntExtra(INGREDIENT_ID_KEY,-1);
            if(ingredientID != -1) {
                ingredient = dataBaseHelper.findIngredient(ingredientID);
                if (ingredient != null) {
                    setData(ingredient);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.more_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(IngredientDetails.this, IngredientEdit.class);
                intent.putExtra(INGREDIENT_ID_KEY,ingredient.getId());
                IngredientDetails.this.startActivity(intent);
                return true;
            case R.id.action_delete:
                dataBaseHelper.deleteIngredient(ingredient);
                intent = new Intent(IngredientDetails.this, IngredientActivity.class);
                IngredientDetails.this.startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setData(Ingredient ingredient){
        det_ingName.setText(ingredient.getName());
        det_ingType.setText(ingredient.getType());
        det_ingDesc.setText(ingredient.getDescription());
    }

    private void initViews(){
        det_ingName = findViewById(R.id.det_ingName);
        det_ingType = findViewById(R.id.det_ingType2);
        det_ingDesc = findViewById(R.id.det_ingDesc2);
    }

}