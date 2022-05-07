package com.example.hairnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hairnote.databinding.ActivityWashDetailsBinding;

public class WashDetails extends AppCompatActivity {

    public static final String WASH_ID_KEY = "washID";
    DataBaseHelper dataBaseHelper;

    Wash wash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_details);

        getSupportActionBar().setTitle("Szczegóły");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityWashDetailsBinding activityWashDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_wash_details);

        dataBaseHelper = new DataBaseHelper(WashDetails.this);

        Intent intent = getIntent();
        if (intent != null) {
            int washID = intent.getIntExtra(WASH_ID_KEY,-1);
            if(washID != -1) {
                wash = dataBaseHelper.findWash(washID);
                if (wash != null) {
                    activityWashDetailsBinding.setCosmeticsList(cosmeticListToString());
                    activityWashDetailsBinding.setWash(wash);
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

    private String cosmeticListToString(){

        String cosmeticList = "";
        int cosmeticID;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(WashDetails.this);

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