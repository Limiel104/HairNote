package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CosmeticAdd extends AppCompatActivity {

    private EditText et_cosmeticName, et_cosmeticBrand, et_cosmeticPehType, et_cosmeticType, et_cosmeticInciList, et_cosmeticDesc, et_cosmeticImgPath;
    Button btn_addCosmetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_add);

        btn_addCosmetic = findViewById(R.id.btnAddCosmetic);
        et_cosmeticName = findViewById(R.id.editCosmeticName);
        et_cosmeticBrand = findViewById(R.id.editCosmeticBrand);
        et_cosmeticPehType = findViewById(R.id.editCosmeticPehType);
        et_cosmeticType = findViewById(R.id.editCosmeticType);
        et_cosmeticInciList = findViewById(R.id.editCosmeticInciList);
        et_cosmeticDesc = findViewById(R.id.editCosmeticDesc);
        et_cosmeticImgPath = findViewById(R.id.editCosmeticImgPath);

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
                            et_cosmeticPehType.getText().toString(),
                            et_cosmeticType.getText().toString(),
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