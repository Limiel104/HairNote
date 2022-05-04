package com.example.hairnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class WashDetails extends AppCompatActivity {

    public static final String WASH_ID_KEY = "washID";
    private TextView det_washDate, det_washIsCleansing, det_washUsedPeeling, det_washUsedOiling, det_washUsedCosmetics, det_washDesc;

    DataBaseHelper dataBaseHelper;

    Wash wash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_details);

        getSupportActionBar().setTitle("Szczegóły");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        dataBaseHelper = new DataBaseHelper(WashDetails.this);

        Intent intent = getIntent();
        if (intent != null) {
            int washID = intent.getIntExtra(WASH_ID_KEY,-1);
            if(washID != -1) {
                wash = dataBaseHelper.findWash(washID);
                if (wash != null) {
                    setData(wash);
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
                Intent intent = new Intent(WashDetails.this, WashEdit.class);
                intent.putExtra(WASH_ID_KEY,wash.getId());
                WashDetails.this.startActivity(intent);
                return true;
            case R.id.action_delete:
                dataBaseHelper.deleteWash(wash);
                intent = new Intent(WashDetails.this, MainActivity.class);
                WashDetails.this.startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setData(Wash wash){
        det_washDate.setText(wash.getDate());
        det_washIsCleansing.setText(wash.isCleansing() == true ? "tak" : "nie");
        det_washUsedPeeling.setText(wash.isUsedPeeling() == true ? "tak" : "nie");
        det_washUsedOiling.setText(wash.isUsedOiling() == true ? "tak" : "nie");
        det_washUsedCosmetics.setText(cosmeticListToString());
        det_washDesc.setText(wash.getDescription());
    }

    private void initViews(){
        det_washDate = findViewById(R.id.det_washDate2);
        det_washIsCleansing = findViewById(R.id.det_washIsCleansing2);
        det_washUsedPeeling = findViewById(R.id.det_washUsedPeeling2);
        det_washUsedOiling = findViewById(R.id.det_washUsedOiling2);
        det_washUsedCosmetics = findViewById(R.id.det_washUsedCosmetics2);
        det_washDesc = findViewById(R.id.det_washDesc2);
    }

    private String cosmeticListToString(){

        String cosmeticList = "";
        int cosmeticID;

        for (int i = 0; i < wash.getUsedCosmetics().size(); i++) {

            cosmeticID = wash.getUsedCosmetics().get(i);
            Cosmetic cosmetic = dataBaseHelper.findCosmetic(cosmeticID);

            if ( i+1 == wash.getUsedCosmetics().size()) {
                cosmeticList = cosmeticList + cosmetic.getName();
            }
            else{
                cosmeticList = cosmeticList + cosmetic.getName() + "\n";
            }
        }
        return cosmeticList;
    }

}