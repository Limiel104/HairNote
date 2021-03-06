package com.example.hairnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class WashAdd extends AppCompatActivity {

    private TextView tv_washDate;
    private EditText et_washDesc;
    private CheckBox cb_washIsCleansing, cb_washUsedPeeling, cb_washUsedOiling;
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

        getSupportActionBar().setTitle("Dodaj Mycie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        et_washDesc = findViewById(R.id.editWashDescField);
        cb_washIsCleansing = findViewById(R.id.cbWashIsCleansing);
        cb_washUsedPeeling = findViewById(R.id.cbWashUsedPeeling);
        cb_washUsedOiling = findViewById(R.id.cbWashUsedOiling);

        setTodaysDate();

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_washDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(WashAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(WashAdd.this, R.style.AlertDialogTheme);
                builder.setTitle("Wybierz u??yte kosmetyki z listy");
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

                builder.setNegativeButton("Odrzu??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Wyczy????", new DialogInterface.OnClickListener() {
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
                            cb_washIsCleansing.isChecked(),
                            cb_washUsedPeeling.isChecked(),
                            cb_washUsedOiling.isChecked(),
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

    public void setTodaysDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        setDate(day, month, year);
    }

    public void setDate(int day, int month, int year){

        if (day < 10) {
            if ((month+1) < 10) {
                chosenDate = "0" + day + " - 0" + (month+1) + " - " + year;
            }
            else {
                chosenDate = "0" + day + " - " + (month+1) + " - " + year;
            }
        }
        else{
            if ((month+1) < 10) {
                chosenDate = day + " - 0" + (month+1) + " - " + year;
            }
            else {
                chosenDate = day + " - " + (month+1) + " - " + year;
            }
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