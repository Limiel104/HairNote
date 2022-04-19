package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class WashAdd extends AppCompatActivity {

    private TextView tv_washDate;
    private EditText et_washDesc;
    private Switch sw_washIsCleansing, sw_washUsedPeeling, sw_washUsedOiling;
    Button btn_addWash;
    Calendar calendar;
    String chosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_add);

        btn_addWash = findViewById(R.id.btnAddWash);
        tv_washDate = findViewById(R.id.tvWashDate);
        et_washDesc = findViewById(R.id.editWashDesc);
        sw_washIsCleansing = findViewById(R.id.swWashIsCleansing);
        sw_washUsedPeeling = findViewById(R.id.swWashUsedPeeling);
        sw_washUsedOiling = findViewById(R.id.swWashUsedOiling);

        chosenDate = "";

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_washDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(WashAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        setDate(day,month,year);
                        tv_washDate.setText(chosenDate);
                    }
                },year, month,day);
                dialog.show();
            }
        });


        btn_addWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Wash newWash;

                try {
                    newWash = new Wash(-1,
                            chosenDate,
                            sw_washIsCleansing.isChecked(),
                            sw_washUsedPeeling.isChecked(),
                            sw_washUsedOiling.isChecked(),
                            et_washDesc.getText().toString());

                }catch (Exception e) {
                    newWash = new Wash(-1, "",false,false,false,"");
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

    public void setDate(int day, int month, int year){

        switch (month) {
            case 0:
                chosenDate = day +" STY " + year;
                break;
            case 1:
                chosenDate = day +" LUT " + year;
                break;
            case 2:
                chosenDate = day +" MAR " + year;
                break;
            case 3:
                chosenDate = day +" KWI " + year;
                break;
            case 4:
                chosenDate = day +" MAJ " + year;
                break;
            case 5:
                chosenDate = day +" CZE " + year;
                break;
            case 6:
                chosenDate = day +" LIP " + year;
                break;
            case 7:
                chosenDate = day +" SIE " + year;
                break;
            case 8:
                chosenDate = day +" WRZ " + year;
                break;
            case 9:
                chosenDate = day +" PAŹ " + year;
                break;
            case 10:
                chosenDate = day +" LIS " + year;
                break;
            case 11:
                chosenDate = day +" GRU " + year;
                break;
        }

    }

}