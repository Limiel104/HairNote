package com.example.hairnote;

import static com.example.hairnote.WashDetails.WASH_ID_KEY;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class WashEdit extends AppCompatActivity {

    TextView edt_tvWashDate;
    private EditText edt_washDesc;
    private Switch edt_swWashIsCleansing, edt_swWashUsedPeeling, edt_swWashUsedOiling;
    Button btn_editCosmeticsInUsedCosmeticsList, btn_edtWash;
    Calendar calendar;
    String edt_chosenDate;
    String[] edt_listCosmetics;
    boolean[] edt_checkedCosmetics;
    ArrayList<Integer> edt_chosenCosmetics;
    HashMap<Integer, String> allCosmetics;

    DataBaseHelper dataBaseHelper;

    Wash wash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_edit);

        dataBaseHelper = new DataBaseHelper(WashEdit.this);

        initViews();

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                DatePickerDialog dialog = new DatePickerDialog(WashEdit.this, new DatePickerDialog.OnDateSetListener() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(WashEdit.this);
                builder.setTitle("Wybierz kosmetyki z listy");
                builder.setMultiChoiceItems(edt_listCosmetics, edt_checkedCosmetics, new DialogInterface.OnMultiChoiceClickListener() {
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

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
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
                            edt_swWashIsCleansing.isChecked(),
                            edt_swWashUsedPeeling.isChecked(),
                            edt_swWashUsedOiling.isChecked(),
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

    private void setData(Wash wash){
        edt_chosenDate = wash.getDate();
        edt_tvWashDate.setText(wash.getDate());
        edt_washDesc.setText(wash.getDescription());
        edt_swWashIsCleansing.setChecked(wash.isCleansing());
        edt_swWashUsedPeeling.setChecked(wash.isUsedPeeling());
        edt_swWashUsedOiling.setChecked(wash.isUsedOiling());

        for (int i = 0; i < wash.getUsedCosmetics().size(); i++) {
            int idx = wash.getUsedCosmetics().get(i);
            edt_checkedCosmetics[idx-1] = true;
            edt_chosenCosmetics.add(idx-1);
        }
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
        edt_washDesc = findViewById(R.id.edt_washDesc);
        edt_swWashIsCleansing = findViewById(R.id.edt_swWashIsCleansing);
        edt_swWashUsedPeeling = findViewById(R.id.edt_swWashUsedPeeling);
        edt_swWashUsedOiling = findViewById(R.id.edt_swWashUsedOiling);
    }

    public void setDate(int day, int month, int year){

        switch (month) {
            case 0:
                edt_chosenDate = day +" STY " + year;
                break;
            case 1:
                edt_chosenDate = day +" LUT " + year;
                break;
            case 2:
                edt_chosenDate = day +" MAR " + year;
                break;
            case 3:
                edt_chosenDate = day +" KWI " + year;
                break;
            case 4:
                edt_chosenDate = day +" MAJ " + year;
                break;
            case 5:
                edt_chosenDate = day +" CZE " + year;
                break;
            case 6:
                edt_chosenDate = day +" LIP " + year;
                break;
            case 7:
                edt_chosenDate = day +" SIE " + year;
                break;
            case 8:
                edt_chosenDate = day +" WRZ " + year;
                break;
            case 9:
                edt_chosenDate = day +" PAŹ " + year;
                break;
            case 10:
                edt_chosenDate = day +" LIS " + year;
                break;
            case 11:
                edt_chosenDate = day +" GRU " + year;
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