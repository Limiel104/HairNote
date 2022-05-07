package com.example.hairnote;

import static com.example.hairnote.WashDetails.WASH_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class WashEdit extends AppCompatActivity {

    TextView edt_tvWashDate;
    private EditText edt_washDesc;
    private CheckBox edt_cbWashIsCleansing, edt_cbWashUsedPeeling, edt_cbWashUsedOiling;
    Button btn_editCosmeticsInUsedCosmeticsList, btn_edtWash;
    Calendar calendar;
    String edt_chosenDate;
    String[] edt_listCosmetics;
    boolean[] edt_checkedCosmetics;
    ArrayList<Integer> edt_chosenCosmetics;
    HashMap<Integer, String> allCosmetics;
    int year, month, day;

    DataBaseHelper dataBaseHelper;

    Wash wash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_edit);

        getSupportActionBar().setTitle("Edytuj Mycie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataBaseHelper = new DataBaseHelper(WashEdit.this);

        initViews();

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

        edt_tvWashDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(WashEdit.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        setDate(day,month,year);
                        edt_tvWashDate.setText(edt_chosenDate);
                    }
                },year, month, day);
                dialog.show();
            }
        });

        btn_editCosmeticsInUsedCosmeticsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WashEdit.this, R.style.AlertDialogTheme);
                builder.setTitle("Wybierz kosmetyki z listy");
                builder.setMultiChoiceItems(edt_listCosmetics, edt_checkedCosmetics,  new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            edt_chosenCosmetics.add(position);
                        }
                        else {
                            edt_chosenCosmetics.remove(Integer.valueOf(position));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });

                builder.setNegativeButton("Odrzuć", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Wyczyść", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < edt_checkedCosmetics.length; i++) {
                            edt_checkedCosmetics[i] = false;
                            edt_chosenCosmetics.clear();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_edtWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Wash updatedWash;

                try {
                    updatedWash = new Wash(
                            wash.getId(),
                            edt_chosenDate,
                            edt_cbWashIsCleansing.isChecked(),
                            edt_cbWashUsedPeeling.isChecked(),
                            edt_cbWashUsedOiling.isChecked(),
                            edt_washDesc.getText().toString(),
                            edt_chosenCosmetics);

                }catch (Exception e) {
                    updatedWash = new Wash(-1, "",false,false,false,"", edt_chosenCosmetics);
                }

                boolean success = dataBaseHelper.updateWash(updatedWash);
                dataBaseHelper.deleteAllCosmeticsInWash(updatedWash.getId());

                for (int i = 0; i < edt_chosenCosmetics.size(); i++) {
                    int cosmeticID = findCosmeticID(allCosmetics,edt_listCosmetics[edt_chosenCosmetics.get(i)]);
                    boolean success2 = dataBaseHelper.addWashCosmetic(updatedWash.getId(),cosmeticID);
                }

                Intent intent = new Intent(WashEdit.this, MainActivity.class);
                WashEdit.this.startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    private void setData(Wash wash){
        edt_chosenDate = wash.getDate();
        edt_tvWashDate.setText(wash.getDate());
        edt_washDesc.setText(wash.getDescription());
        edt_cbWashIsCleansing.setChecked(wash.isCleansing());
        edt_cbWashUsedPeeling.setChecked(wash.isUsedPeeling());
        edt_cbWashUsedOiling.setChecked(wash.isUsedOiling());

        for (int i = 0; i < wash.getUsedCosmetics().size(); i++) {
            int idx = wash.getUsedCosmetics().get(i);
            edt_checkedCosmetics[idx-1] = true;
            edt_chosenCosmetics.add(idx-1);
        }

        day = Integer.parseInt(wash.getDate().substring(0,2));
        month = Integer.parseInt(wash.getDate().substring(5,7))-1;
        year = Integer.parseInt(wash.getDate().substring(10,14));
    }

    private void initViews(){
        allCosmetics = dataBaseHelper.getAllCosmeticsNameAndID();

        edt_listCosmetics = new String[allCosmetics.size()];
        int index = 0;
        for (HashMap.Entry<Integer, String> mapEntry : allCosmetics.entrySet()) {
            edt_listCosmetics[index] = mapEntry.getValue();
            index++;
        }

        edt_checkedCosmetics = new boolean[edt_listCosmetics.length];
        edt_chosenCosmetics = new ArrayList<>();

        btn_edtWash = findViewById(R.id.edt_btnEditWash);
        btn_editCosmeticsInUsedCosmeticsList = findViewById(R.id.edt_btnEditCosmeticsInUsedCosmeticsList);
        edt_tvWashDate = findViewById(R.id.edt_tvWashDate);
        edt_washDesc = findViewById(R.id.edt_washDescField);
        edt_cbWashIsCleansing = findViewById(R.id.edt_cbWashIsCleansing);
        edt_cbWashUsedPeeling = findViewById(R.id.edt_cbWashUsedPeeling);
        edt_cbWashUsedOiling = findViewById(R.id.edt_cbWashUsedOiling);
    }

    public void setDate(int day, int month, int year){

        if (day < 10) {
            if ((month+1) < 10) {
                edt_chosenDate = "0" + day + " - 0" + (month+1) + " - " + year;
            }
            else {
                edt_chosenDate = "0" + day + " - " + (month+1) + " - " + year;
            }
        }
        else{
            if ((month+1) < 10) {
                edt_chosenDate = day + " - 0" + (month+1) + " - " + year;
            }
            else {
                edt_chosenDate = day + " - " + (month+1) + " - " + year;
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