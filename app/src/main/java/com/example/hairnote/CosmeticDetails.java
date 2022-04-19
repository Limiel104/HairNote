package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CosmeticDetails extends AppCompatActivity {

    public static final String COSMETIC_ID_KEY = "cosmeticID";
    private TextView det_cosName, det_cosBrand, det_cosPehType, det_cosCosmeticType, det_cosInciList, det_cosDesc;
    private ImageView imgCosDet;
    private Button det_editCosBtn, det_deleteCosBtn;

    DataBaseHelper dataBaseHelper;

    Cosmetic cosmetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_details);

        initViews();

        dataBaseHelper = new DataBaseHelper(CosmeticDetails.this);

        //Cosmetic cosmetic;
        /*Cosmetic cosmetic = new Cosmetic(2,"fddsf","OnlyBio","Humektanty",
                "Odżywka","dfgjgl idhsd hgldkf hgdkfhgkdjh glkhgweri;gsdjkfh;webgou erwogbq guqg ergqwoipqer gnerbopqwg remw g;erg r;jdfsalknf;wai weqfonfew;lkf welef jwaeokf ewaieonf;oa wefl;aik erw igpr gkwg kjweogle rgs",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et libero sodales dui imperdiet lobortis. Etiam turpis augue, faucibus eu consectetur a, volutpat sed mi. Proin tellus turpis, finibus ut urna sit amet, tristique blandit lectus. Ut eu dolor non libero placerat mollis sed eu nibh. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In libero sapien, mollis vitae posuere at, lacinia sed ante. Maecenas eleifend id turpis id ullamcorper. Pellentesque laoreet fermentum ligula. Suspendisse ante elit, bibendum posuere maximus eget, semper quis dolor. Donec at cursus felis. Sed purus erat, efficitur ac metus id, elementum interdum leo. Phasellus et felis felis. Sed molestie dignissim orci, vel posuere risus commodo eget. Morbi eu purus quis lorem sollicitudin sodales. Sed consequat, sapien quis pulvinar varius, ex erat sagittis massa, eget mollis justo ante nec leo.",
                "https://darmarsklep.pl/img/product_media/46001-47000/5902811787765.png");*/

        Intent intent = getIntent();
        if (intent != null) {
            int cosmeticID = intent.getIntExtra(COSMETIC_ID_KEY, -1);
            if(cosmeticID != -1) {
                cosmetic = dataBaseHelper.findCosmetic(cosmeticID);
                if(cosmetic != null) {
                    setData(cosmetic);
                }
            }
        }

        det_editCosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CosmeticDetails.this, "edytuj",Toast.LENGTH_SHORT).show();
            }
        });

        det_deleteCosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CosmeticDetails.this, "usun",Toast.LENGTH_SHORT).show();

                dataBaseHelper.deleteCosmetic(cosmetic);
                Intent intent = new Intent(CosmeticDetails.this, CosmeticActivity.class);
                CosmeticDetails.this.startActivity(intent);
                finish();
            }
        });


    }

    private void setData(Cosmetic cosmetic){
        det_cosName.setText(cosmetic.getName());
        det_cosBrand.setText(cosmetic.getBrand());
        det_cosPehType.setText(cosmetic.getPehType());
        det_cosCosmeticType.setText(cosmetic.getCosmeticType());
        //det_cosCG.setText(String.valueOf(cosmetic.isCgCompatible()));
        det_cosInciList.setText(cosmetic.getInciList());
        det_cosDesc.setText(cosmetic.getDescription());
        Glide.with(this)
                .asBitmap()
                .load(cosmetic.getImgPath())
                .into(imgCosDet);
    }

    private void initViews(){
        det_editCosBtn = findViewById(R.id.det_editCosBtn);
        det_deleteCosBtn = findViewById(R.id.det_deleteCosBtn);
        det_cosName = findViewById(R.id.det_cosName2);
        det_cosBrand = findViewById(R.id.det_cosBrand2);
        det_cosPehType = findViewById(R.id.det_cosPehType2);
        det_cosCosmeticType = findViewById(R.id.det_cosCosmeticType2);
        //det_cosCG = findViewById(R.id.det_cosCG);
        det_cosInciList = findViewById(R.id.det_cosInciList2);
        det_cosDesc = findViewById(R.id.det_cosDesc2);

        imgCosDet = findViewById(R.id.imgCosDet);
    }
}