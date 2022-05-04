package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IngredientAdd extends AppCompatActivity {

    private EditText et_ingredientName, et_ingredientDesc;
    Button btn_addIngredient;
    AutoCompleteTextView autoCompleteTVIngrType;
    ArrayAdapter adapterIngrTypes;
    String chosenIngrType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_add);

        getSupportActionBar().setTitle("Dodaj Składnik");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] ingrTypes = getResources().getStringArray(R.array.IngrTypes);

        adapterIngrTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_ingredient_type, ingrTypes);

        chosenIngrType = "";

        btn_addIngredient = findViewById(R.id.btnAddIngredient);
        et_ingredientName = findViewById(R.id.editIngredientName);
        et_ingredientDesc = findViewById(R.id.editIngredientDesc);
        autoCompleteTVIngrType = findViewById(R.id.autoCompleteTVIngrType);

        autoCompleteTVIngrType.setAdapter(adapterIngrTypes);

        autoCompleteTVIngrType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenIngrType = autoCompleteTVIngrType.getText().toString();
            }
        });

        btn_addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ingredient newIngredient;
                Toast.makeText(IngredientAdd.this,"elo",Toast.LENGTH_SHORT).show();

                try {
                    newIngredient = new Ingredient(-1,
                            et_ingredientName.getText().toString(),
                            chosenIngrType,
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