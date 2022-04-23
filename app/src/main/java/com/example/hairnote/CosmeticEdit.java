package com.example.hairnote;

import static com.example.hairnote.CosmeticDetails.COSMETIC_ID_KEY;

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CosmeticEdit extends AppCompatActivity {

    private EditText edt_cosmeticName, edt_cosmeticBrand, edt_cosmeticDesc, edt_cosmeticImgPath;
    Button btn_editIngredientsInInciList, btn_edtCosmetic;
    AutoCompleteTextView edt_autoCompleteTVPehType, edt_autoCompleteTVCosType;
    ArrayAdapter adapterPehTypes, adapterCosTypes;
    String edtPehType, edtCosType;
    String[] edt_pehTypes, edt_cosTypes, edt_listIngredients;
    boolean[] edt_checkedIngredients;
    ArrayList<Integer> edt_chosenIngredients;
    HashMap<Integer, String> allIngredients;

    DataBaseHelper dataBaseHelper;

    Cosmetic cosmetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_edit);

        dataBaseHelper = new DataBaseHelper(CosmeticEdit.this);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            int cosmeticID = intent.getIntExtra(COSMETIC_ID_KEY,-1);
            if(cosmeticID != -1) {
                cosmetic = dataBaseHelper.findCosmetic(cosmeticID);
                if (cosmetic != null) {
                    setData(cosmetic);
                }
            }
        }

        edt_autoCompleteTVPehType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtPehType = edt_autoCompleteTVPehType.getText().toString();
            }
        });

        edt_autoCompleteTVCosType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtCosType = edt_autoCompleteTVCosType.getText().toString();
            }
        });

        btn_editIngredientsInInciList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticEdit.this);
                builder.setTitle("Wybierz składniki z listy");
                builder.setMultiChoiceItems(edt_listIngredients, edt_checkedIngredients, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            edt_chosenIngredients.add(position);
                            Toast.makeText(CosmeticEdit.this, "dodano "+position, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            edt_chosenIngredients.remove(Integer.valueOf(position));
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
                        for (int i = 0; i < edt_checkedIngredients.length; i++) {
                            edt_checkedIngredients[i] = false;
                            edt_chosenIngredients.clear();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_edtCosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cosmetic updatedCosmetic;

                try {
                    updatedCosmetic = new Cosmetic(
                            cosmetic.getId(),
                            edt_cosmeticName.getText().toString(),
                            edt_cosmeticBrand.getText().toString(),
                            edtPehType,
                            edtCosType,
                            edt_cosmeticDesc.getText().toString(),
                            edt_cosmeticImgPath.getText().toString(),
                            edt_chosenIngredients);

                }catch (Exception e) {
                    updatedCosmetic = new Cosmetic(-1,"","","","","","");
                }

                /*Toast.makeText(CosmeticEdit.this,
                        updatedCosmetic.getName()+" "
                                +updatedCosmetic.getBrand()+" "
                                +updatedCosmetic.getPehType()+" "
                                +updatedCosmetic.getCosmeticType()+" "
                                +inciListToString(updatedCosmetic)+" "
                                +updatedCosmetic.getDescription()+" "
                                +updatedCosmetic.getImgPath(),
                        Toast.LENGTH_SHORT).show();*/

                boolean success = dataBaseHelper.updateCosmetic(updatedCosmetic);
                dataBaseHelper.deleteAllIngredientsInCosmetic(updatedCosmetic.getId());

                for (int i = 0; i < edt_chosenIngredients.size(); i++) {
                    int ingredientID = findIngredientID(allIngredients,edt_listIngredients[edt_chosenIngredients.get(i)]);
                    boolean success2 = dataBaseHelper.addCosmeticIngredient(updatedCosmetic.getId(),ingredientID);
                }

                Intent intent = new Intent(CosmeticEdit.this, CosmeticActivity.class);
                CosmeticEdit.this.startActivity(intent);
                finish();


            }
        });




    }

    private void setData(Cosmetic cosmetic){
        edt_cosmeticName.setText(cosmetic.getName());
        edt_cosmeticBrand.setText(cosmetic.getBrand());
        edtPehType = cosmetic.getPehType();
        edtCosType = cosmetic.getCosmeticType();
        edt_cosmeticDesc.setText(cosmetic.getDescription());
        edt_cosmeticImgPath.setText(cosmetic.getImgPath());

        Collections.sort(cosmetic.getInciList());

        for (int i = 0; i < cosmetic.getInciList().size(); i++) {
            int idx = cosmetic.getInciList().get(i);
            edt_checkedIngredients[idx-1] = true;
            edt_chosenIngredients.add(idx-1);
        }

    }

    private void initViews(){

        edt_pehTypes = getResources().getStringArray(R.array.PehTypes);
        edt_cosTypes = getResources().getStringArray(R.array.CosTypes);

        allIngredients = dataBaseHelper.getAllIngredientsNameAndID();

        edt_listIngredients = new String[allIngredients.size()];
        int index = 0;
        for (HashMap.Entry<Integer, String> mapEntry : allIngredients.entrySet()) {
            edt_listIngredients[index] = mapEntry.getValue();
            index++;
        }

        adapterPehTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_peh_type, edt_pehTypes);
        adapterCosTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_cos_type, edt_cosTypes);

        edt_checkedIngredients = new boolean[edt_listIngredients.length];
        edt_chosenIngredients = new ArrayList<>();

        btn_edtCosmetic = findViewById(R.id.edt_btnEditCosmetic);
        btn_editIngredientsInInciList = findViewById(R.id.edt_btnEditIngredientsInInciList);
        edt_cosmeticName = findViewById(R.id.edt_cosmeticName);
        edt_cosmeticBrand = findViewById(R.id.edt_cosmeticBrand);
        edt_cosmeticDesc = findViewById(R.id.edt_cosmeticDesc);
        edt_cosmeticImgPath = findViewById(R.id.edt_cosmeticImgPath);
        edt_autoCompleteTVPehType = findViewById(R.id.edt_autoCompleteTVPehType);
        edt_autoCompleteTVCosType = findViewById(R.id.edt_autoCompleteTVCosType);

        edt_autoCompleteTVPehType.setAdapter(adapterPehTypes);
        edt_autoCompleteTVCosType.setAdapter(adapterCosTypes);
    }

    private String inciListToString(Cosmetic cosmetic){

        String inciList = "";
        int ingredientID;

        for (int i = 0;  i < cosmetic.getInciList().size(); i++) {

            ingredientID = cosmetic.getInciList().get(i)+1;
            Ingredient ingredient = dataBaseHelper.findIngredient(ingredientID);

            if ( i+1 == cosmetic.getInciList().size()) {
                inciList = inciList + ingredient.getName();
            }
            else{
                inciList = inciList + ingredient.getName() + ", ";
            }
        }
        return inciList;
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