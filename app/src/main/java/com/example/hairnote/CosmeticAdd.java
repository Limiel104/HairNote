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

import java.util.ArrayList;
import java.util.List;

public class CosmeticAdd extends AppCompatActivity {

    private EditText et_cosmeticName, et_cosmeticBrand, et_cosmeticInciList, et_cosmeticDesc, et_cosmeticImgPath;
    Button btn_addCosmetic;
    AutoCompleteTextView autoCompleteTVPehType, autoCompleteTVCosType;
    ArrayAdapter adapterPehTypes, adapterCosTypes;
    String chosenPehType, chosenCosType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_add);

        String[] pehTypes = getResources().getStringArray(R.array.PehTypes);
        String[] cosTypes = getResources().getStringArray(R.array.CosTypes);

        adapterPehTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_peh_type, pehTypes);
        adapterCosTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_cos_type, cosTypes);

        chosenPehType = "";
        chosenCosType = "";

        btn_addCosmetic = findViewById(R.id.btnAddCosmetic);
        et_cosmeticName = findViewById(R.id.editCosmeticName);
        et_cosmeticBrand = findViewById(R.id.editCosmeticBrand);
        et_cosmeticInciList = findViewById(R.id.editCosmeticInciList);
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


        btn_addCosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cosmetic newCosmetic;
                int aaa = 1;
                List test = new ArrayList();
                test.add(aaa);

                try {
                    newCosmetic = new Cosmetic(-1,
                            et_cosmeticName.getText().toString(),
                            et_cosmeticBrand.getText().toString(),
                            chosenPehType,
                            chosenCosType,
                            et_cosmeticInciList.getText().toString(),
                            et_cosmeticDesc.getText().toString(),
                            et_cosmeticImgPath.getText().toString(),test);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //Toast.makeText(CosmeticAdd.this,"elo",Toast.LENGTH_SHORT).show();

                }catch (Exception e) {
                    newCosmetic = new Cosmetic(-1,"error","","","","","","", test);
                    //Toast.makeText(CosmeticAdd.this,"elo2",Toast.LENGTH_SHORT).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(CosmeticAdd.this);
                boolean success = dataBaseHelper.addCosmetic(newCosmetic);
                Toast.makeText(CosmeticAdd.this, "Dodano Kosmetyk", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CosmeticAdd.this, CosmeticActivity.class);
                CosmeticAdd.this.startActivity(intent);
                finish();

            }
        });



    }
}