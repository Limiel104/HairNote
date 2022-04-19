package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WashDetails extends AppCompatActivity {

    public static final String WASH_ID_KEY = "washID";
    private TextView det_washDate, det_washIsCleansing, det_washUsedPeeling, det_washUsedOiling, det_washDesc;
    private Button det_editWashBtn, det_deleteWashBtn;

    DataBaseHelper dataBaseHelper;

    Wash wash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_details);

        initViews();

        dataBaseHelper = new DataBaseHelper(WashDetails.this);

        //Wash wash;

        Intent intent = getIntent();
        if (intent != null) {
            int washID = intent.getIntExtra(WASH_ID_KEY,-1);
            //Toast.makeText(IngredientDetails.this,"Dostałam " + ingredientID,Toast.LENGTH_SHORT).show();
            if(washID != -1) {
                wash = dataBaseHelper.findWash(washID);
                if (wash != null) {
                    setData(wash);
                }
            }
        }

        det_editWashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WashDetails.this, "edytuj",Toast.LENGTH_SHORT).show();
            }
        });

        det_deleteWashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(WashDetails.this, "usun",Toast.LENGTH_SHORT).show();
                dataBaseHelper.deleteWash(wash);
                Intent intent = new Intent(WashDetails.this, MainActivity.class);
                WashDetails.this.startActivity(intent);
                finish();
            }
        });

    }

    private void setData(Wash wash){
        det_washDate.setText(wash.getDate());
        det_washIsCleansing.setText(wash.isCleansing() == true ? "tak" : "nie");
        det_washUsedPeeling.setText(wash.isUsedPeeling() == true ? "tak" : "nie");
        det_washUsedOiling.setText(wash.isUsedOiling() == true ? "tak" : "nie");
        det_washDesc.setText(wash.getDescription());
    }

    private void initViews(){
        det_editWashBtn = findViewById(R.id.det_editWashBtn);
        det_deleteWashBtn = findViewById(R.id.det_deleteWashBtn);
        det_washDate = findViewById(R.id.det_washDate2);
        det_washIsCleansing = findViewById(R.id.det_washIsCleansing2);
        det_washUsedPeeling = findViewById(R.id.det_washUsedPeeling2);
        det_washUsedOiling = findViewById(R.id.det_washUsedOiling2);
        det_washDesc = findViewById(R.id.det_washDesc2);
    }

}