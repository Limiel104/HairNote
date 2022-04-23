package com.example.hairnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosmeticAdd extends AppCompatActivity {

    private EditText et_cosmeticName, et_cosmeticBrand, et_cosmeticDesc, et_cosmeticImgPath;
    Button btn_addCosmetic, btn_AddIngredientsToInciList;
    AutoCompleteTextView autoCompleteTVPehType, autoCompleteTVCosType;
    ArrayAdapter adapterPehTypes, adapterCosTypes;
    String chosenPehType, chosenCosType;
    String[] pehTypes, cosTypes, listIngredients;
    boolean[] checkedIngredients;
    ArrayList<Integer> chosenIngredients;
    HashMap<Integer, String> allIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_add);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(CosmeticAdd.this);

        pehTypes = getResources().getStringArray(R.array.PehTypes);
        cosTypes = getResources().getStringArray(R.array.CosTypes);

        allIngredients = dataBaseHelper.getAllIngredientsNameAndID();

        listIngredients = new String[allIngredients.size()];
        int index = 0;
        for (HashMap.Entry<Integer, String> mapEntry : allIngredients.entrySet()) {
            listIngredients[index] = mapEntry.getValue();
            index++;
        }

        adapterPehTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_peh_type, pehTypes);
        adapterCosTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_cos_type, cosTypes);

        chosenPehType = "";
        chosenCosType = "";

        checkedIngredients = new boolean[listIngredients.length];
        chosenIngredients = new ArrayList<>();

        btn_addCosmetic = findViewById(R.id.btnAddCosmetic);
        btn_AddIngredientsToInciList = findViewById(R.id.btnAddIngredientsToInciList);
        et_cosmeticName = findViewById(R.id.editCosmeticName);
        et_cosmeticBrand = findViewById(R.id.editCosmeticBrand);
        et_cosmeticDesc = findViewById(R.id.editCosmeticDesc);
        et_cosmeticImgPath = findViewById(R.id.editCosmeticImgPath);
        autoCompleteTVPehType = findViewById(R.id.autoCompleteTVPehType);
        autoCompleteTVCosType = findViewById(R.id.autoCompleteTVCosType);

        autoCompleteTVPehType.setAdapter(adapterPehTypes);
        autoCompleteTVCosType.setAdapter(adapterCosTypes);

        autoCompleteTVPehType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenPehType = autoCompleteTVPehType.getText().toString();
            }
        });

        autoCompleteTVCosType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenCosType = autoCompleteTVCosType.getText().toString();
            }
        });

        btn_AddIngredientsToInciList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticAdd.this);
                builder.setTitle("Wybierz składniki z listy");
                builder.setMultiChoiceItems(listIngredients, checkedIngredients, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            chosenIngredients.add(position);
                        }
                        else {
                            chosenIngredients.remove(Integer.valueOf(position));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedIngredients.length; i++) {
                            checkedIngredients[i] = false;
                            chosenIngredients.clear();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        btn_addCosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cosmetic newCosmetic;

                try {
                    newCosmetic = new Cosmetic(-1,
                            et_cosmeticName.getText().toString(),
                            et_cosmeticBrand.getText().toString(),
                            chosenPehType,
                            chosenCosType,
                            et_cosmeticDesc.getText().toString(),
                            et_cosmeticImgPath.getText().toString(),
                            chosenIngredients);

                }catch (Exception e) {
                    newCosmetic = new Cosmetic(-1,"error","","","","","", chosenIngredients);
                }

                int cosmeticID = (int) dataBaseHelper.addCosmetic(newCosmetic);
                for (int i = 0; i < chosenIngredients.size(); i++) {
                    int ingredientID = findIngredientID(allIngredients,listIngredients[chosenIngredients.get(i)]);
                    boolean success = dataBaseHelper.addCosmeticIngredient(cosmeticID,ingredientID);
                }
                Toast.makeText(CosmeticAdd.this, "Dodano Kosmetyk", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CosmeticAdd.this, CosmeticActivity.class);
                CosmeticAdd.this.startActivity(intent);
                finish();

            }
        });

    }

    public int findIngredientID(HashMap<Integer, String> hashMap, String ingredientName){

        int ingredientID = -1;

        for(HashMap.Entry<Integer, String> entry: hashMap.entrySet()) {
            if(entry.getValue() == ingredientName) {
                return ingredientID = entry.getKey();
            }
        }
        return ingredientID;
    }

}