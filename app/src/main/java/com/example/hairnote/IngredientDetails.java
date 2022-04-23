package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IngredientDetails extends AppCompatActivity {

    public static final String INGREDIENT_ID_KEY = "ingredientID";
    private TextView det_ingName, det_ingType, det_ingDesc;
    private Button det_editIngBtn, det_deleteIngBtn;

    DataBaseHelper dataBaseHelper;

    Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

        initViews();

        dataBaseHelper = new DataBaseHelper(IngredientDetails.this);

        //Ingredient ingredient;

        Intent intent = getIntent();
        if (intent != null) {
            int ingredientID = intent.getIntExtra(INGREDIENT_ID_KEY,-1);
            //Toast.makeText(IngredientDetails.this,"Dostałam " + ingredientID,Toast.LENGTH_SHORT).show();
            if(ingredientID != -1) {
                ingredient = dataBaseHelper.findIngredient(ingredientID);
                if (ingredient != null) {
                    setData(ingredient);
                }
            }
        }

        det_editIngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(IngredientDetails.this, "edytuj",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IngredientDetails.this, IngredientEdit.class);
                intent.putExtra(INGREDIENT_ID_KEY,ingredient.getId());
                IngredientDetails.this.startActivity(intent);
            }
        });

        det_deleteIngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(IngredientDetails.this, "usun",Toast.LENGTH_SHORT).show();
                dataBaseHelper.deleteIngredient(ingredient);
                Intent intent = new Intent(IngredientDetails.this, IngredientActivity.class);
                IngredientDetails.this.startActivity(intent);
                finish();
            }
        });


    }

    private void setData(Ingredient ingredient){
        det_ingName.setText(ingredient.getName());
        det_ingType.setText(ingredient.getType());
        det_ingDesc.setText(ingredient.getDescription());
    }

    private void initViews(){
        det_editIngBtn = findViewById(R.id.det_editIngBtn);
        det_deleteIngBtn = findViewById(R.id.det_deleteIngBtn);
        det_ingName = findViewById(R.id.det_ingName2);
        det_ingType = findViewById(R.id.det_ingType2);
        det_ingDesc = findViewById(R.id.det_ingDesc2);
    }

}