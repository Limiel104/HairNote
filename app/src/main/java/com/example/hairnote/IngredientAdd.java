package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IngredientAdd extends AppCompatActivity {

    private EditText et_ingredientName, et_ingredientType, et_ingredientDesc;
    Button btn_addIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_add);

        btn_addIngredient = findViewById(R.id.btnAddIngredient);
        et_ingredientName = findViewById(R.id.editIngredientName);
        et_ingredientType = findViewById(R.id.editIngredientType);
        et_ingredientDesc = findViewById(R.id.editIngredientDesc);

        btn_addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ingredient newIngredient;
                Toast.makeText(IngredientAdd.this,"elo",Toast.LENGTH_SHORT).show();

                try {
                    newIngredient = new Ingredient(-1,
                            et_ingredientName.getText().toString(),
                            et_ingredientType.getText().toString(),
                            et_ingredientDesc.getText().toString());

                }catch (Exception e) {
                    newIngredient = new Ingredient(-1,"","","");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(IngredientAdd.this);
                boolean success = dataBaseHelper.addIngredient(newIngredient);
                Toast.makeText(IngredientAdd.this, "Dodano Składnik", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(IngredientAdd.this, IngredientActivity.class);
                IngredientAdd.this.startActivity(intent);
                finish();

            }
        });

    }
}