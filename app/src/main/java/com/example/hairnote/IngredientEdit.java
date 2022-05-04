package com.example.hairnote;

import static com.example.hairnote.IngredientDetails.INGREDIENT_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class IngredientEdit extends AppCompatActivity {

    private EditText edt_ingredientName, edt_ingredientDesc;
    Button btn_edtIngredient;
    AutoCompleteTextView edt_autoCompleteTVIngrType;
    ArrayAdapter adapterIngrTypes;
    String edtIngrType;

    DataBaseHelper dataBaseHelper;

    Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_edit);

        getSupportActionBar().setTitle("Edytuj Składnik");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataBaseHelper = new DataBaseHelper(IngredientEdit.this);

        initViews();

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

        edt_autoCompleteTVIngrType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtIngrType = edt_autoCompleteTVIngrType.getText().toString();
            }
        });

        btn_edtIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ingredient updatedIngredient;

                try {
                    updatedIngredient = new Ingredient(
                            ingredient.getId(),
                            edt_ingredientName.getText().toString(),
                            edtIngrType,
                            edt_ingredientDesc.getText().toString());

                }catch (Exception e) {
                    updatedIngredient = new Ingredient(-1,"","","");
                }

                boolean success = dataBaseHelper.updateIngredient(updatedIngredient);

                Intent intent = new Intent(IngredientEdit.this, IngredientActivity.class);
                IngredientEdit.this.startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    private void setData(Ingredient ingredient){
        edt_ingredientName.setText(ingredient.getName());
        edtIngrType = ingredient.getType();
        edt_ingredientDesc.setText(ingredient.getDescription());
    }

    private void initViews(){

        String[] ingrTypes = getResources().getStringArray(R.array.IngrTypes);

        adapterIngrTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_ingredient_type, ingrTypes);

        edtIngrType = "";

        btn_edtIngredient = findViewById(R.id.edt_btnEditIngredient);
        edt_ingredientName = findViewById(R.id.edt_ingredientName);
        edt_ingredientDesc = findViewById(R.id.edt_ingredientDesc);
        edt_autoCompleteTVIngrType = findViewById(R.id.edt_autoCompleteTVIngrType);

        edt_autoCompleteTVIngrType.setAdapter(adapterIngrTypes);
    }
}