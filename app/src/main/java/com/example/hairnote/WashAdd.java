package com.example.hairnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WashAdd extends AppCompatActivity {

    private TextView tv_washDate;
    private EditText et_washDesc;
    private Switch sw_washIsCleansing, sw_washUsedPeeling, sw_washUsedOiling;
    Button btn_addWash, btn_AddCosmeticsToUsedCosmetics;
    Calendar calendar;
    String chosenDate;
    String[] listCosmetics;
    boolean[] checkedCosmetics;
    ArrayList<Integer> chosenCosmetics;
    HashMap<Integer, String> allCosmetics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_add);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(WashAdd.this);

        allCosmetics = dataBaseHelper.getAllCosmeticsNameAndID();

        listCosmetics = new String[allCosmetics.size()];
        int index = 0;
        for (HashMap.Entry<Integer, String> mapEntry : allCosmetics.entrySet()) {
            listCosmetics[index] = mapEntry.getValue();
            index++;
        }

        checkedCosmetics = new boolean[listCosmetics.length];
        chosenCosmetics = new ArrayList<>();

        btn_addWash = findViewById(R.id.btnAddWash);
        btn_AddCosmeticsToUsedCosmetics = findViewById(R.id.btnAddCosmeticsToUsedCosmetics);
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

        btn_AddCosmeticsToUsedCosmetics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WashAdd.this);
                builder.setTitle("Wybierz użyte kosmetyki z listy");
                builder.setMultiChoiceItems(listCosmetics, checkedCosmetics, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            chosenCosmetics.add(position);
                        }
                        else {
                            chosenCosmetics.remove(Integer.valueOf(position));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });

                builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedCosmetics.length; i++) {
                            checkedCosmetics[i] = false;
                            chosenCosmetics.clear();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
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
                            et_washDesc.getText().toString(),
                            chosenCosmetics);

                }catch (Exception e) {
                    newWash = new Wash(-1, "",false,false,false,"", chosenCosmetics);
                }

                int washID = (int) dataBaseHelper.addWash(newWash);
                for (int i = 0; i < chosenCosmetics.size(); i++) {
                    int cosmeticID = findCosmeticID(allCosmetics,listCosmetics[chosenCosmetics.get(i)]);
                    boolean success = dataBaseHelper.addWashCosmetic(washID,cosmeticID);
                }
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

    public int findCosmeticID(HashMap<Integer, String> hashMap, String cosmeticName){

        int cosmeticID = -1;

        for(HashMap.Entry<Integer, String> entry: hashMap.entrySet()) {
            if(entry.getValue() == cosmeticName) {
                return cosmeticID = entry.getKey();
            }
        }
        return cosmeticID;
    }

}