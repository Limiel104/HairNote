package com.example.hairnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CosmeticDetails extends AppCompatActivity {

    public static final String COSMETIC_ID_KEY = "cosmeticID";
    private TextView det_cosName, det_cosBrand, det_cosPehType, det_cosCosmeticType, det_cosInciList, det_cosDesc;
    private ImageView imgCosDet;

    DataBaseHelper dataBaseHelper;

    Cosmetic cosmetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_details);

        getSupportActionBar().setTitle("Szczegóły");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        dataBaseHelper = new DataBaseHelper(CosmeticDetails.this);

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
                Intent intent = new Intent(CosmeticDetails.this, CosmeticEdit.class);
                intent.putExtra(COSMETIC_ID_KEY,cosmetic.getId());
                CosmeticDetails.this.startActivity(intent);
                return true;
            case R.id.action_delete:
                dataBaseHelper.deleteCosmetic(cosmetic);
                intent = new Intent(CosmeticDetails.this, CosmeticActivity.class);
                CosmeticDetails.this.startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setData(Cosmetic cosmetic){
        det_cosName.setText(cosmetic.getName());
        det_cosBrand.setText(cosmetic.getBrand());
        det_cosPehType.setText(cosmetic.getPehType());
        det_cosCosmeticType.setText(cosmetic.getCosmeticType());
        //det_cosCG.setText(String.valueOf(cosmetic.isCgCompatible()));
        det_cosInciList.setText(inciListToString());
        det_cosDesc.setText(cosmetic.getDescription());
        Glide.with(this)
                .asBitmap()
                .load(cosmetic.getImgPath())
                .into(imgCosDet);
    }

    private void initViews(){
        det_cosName = findViewById(R.id.det_cosName2);
        det_cosBrand = findViewById(R.id.det_cosBrand2);
        det_cosPehType = findViewById(R.id.det_cosPehType2);
        det_cosCosmeticType = findViewById(R.id.det_cosCosmeticType2);
        //det_cosCG = findViewById(R.id.det_cosCG);
        det_cosInciList = findViewById(R.id.det_cosInciList2);
        det_cosDesc = findViewById(R.id.det_cosDesc2);
        imgCosDet = findViewById(R.id.imgCosDet);
    }

    private String inciListToString(){

        String inciList = "";
        int ingredientID;

        for (int i = 0;  i < cosmetic.getInciList().size(); i++) {

            ingredientID = cosmetic.getInciList().get(i);
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

}