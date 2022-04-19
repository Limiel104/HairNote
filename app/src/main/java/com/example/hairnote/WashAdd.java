package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class WashAdd extends AppCompatActivity {

    private EditText et_washDate, et_washDesc;
    private Switch sw_washIsCleansing, sw_washUsedPeeling, sw_washUsedOiling;
    Button btn_addWash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_add);

        btn_addWash = findViewById(R.id.btnAddWash);
        et_washDate = findViewById(R.id.editWashDate);
        et_washDesc = findViewById(R.id.editWashDesc);
        sw_washIsCleansing = findViewById(R.id.swWashIsCleansing);
        sw_washUsedPeeling = findViewById(R.id.swWashUsedPeeling);
        sw_washUsedOiling = findViewById(R.id.swWashUsedOiling);

        btn_addWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Wash newWash;

                try {
                    newWash = new Wash(-1,
                            et_washDate.getText().toString(),
                            sw_washIsCleansing.isChecked(),
                            sw_washUsedPeeling.isChecked(),
                            sw_washUsedOiling.isChecked(),
                            et_washDesc.getText().toString());
                    //Toast.makeText(WashAdd.this,"elo",Toast.LENGTH_SHORT).show();

                }catch (Exception e) {
                    newWash = new Wash(-1, "",false,false,false,"");
                    //Toast.makeText(WashAdd.this,"elo2",Toast.LENGTH_SHORT).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(WashAdd.this);
                boolean success = dataBaseHelper.addWash(newWash);
                Toast.makeText(WashAdd.this, "Dodano Mycie", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WashAdd.this,MainActivity.class);
                WashAdd.this.startActivity(intent);
                finish();
            }

        });

    }

}