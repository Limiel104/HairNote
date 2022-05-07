package com.example.hairnote;

import static com.example.hairnote.CosmeticDetails.COSMETIC_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CosmeticEdit extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    private EditText edt_cosmeticName, edt_cosmeticBrand, edt_cosmeticDesc;
    Button btn_editIngredientsInInciList, btn_edtCosmetic, btn_editCosImg;
    AutoCompleteTextView edt_autoCompleteTVPehType, edt_autoCompleteTVCosType;
    ArrayAdapter adapterPehTypes, adapterCosTypes;
    String edtPehType, edtCosType, imgPath;
    String[] edt_pehTypes, edt_cosTypes, edt_listIngredients;
    boolean[] edt_checkedIngredients;
    ArrayList<Integer> edt_chosenIngredients;
    HashMap<Integer, String> allIngredients;

    DataBaseHelper dataBaseHelper;

    Cosmetic cosmetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_edit);

        getSupportActionBar().setTitle("Edytuj Kosmetyk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataBaseHelper = new DataBaseHelper(CosmeticEdit.this);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            int cosmeticID = intent.getIntExtra(COSMETIC_ID_KEY,-1);
            if(cosmeticID != -1) {
                cosmetic = dataBaseHelper.findCosmetic(cosmeticID);
                if (cosmetic != null) {
                    setData(cosmetic);
                }
            }
        }

        edt_autoCompleteTVPehType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtPehType = edt_autoCompleteTVPehType.getText().toString();
            }
        });

        edt_autoCompleteTVCosType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtCosType = edt_autoCompleteTVCosType.getText().toString();
            }
        });

        btn_editIngredientsInInciList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticEdit.this, R.style.AlertDialogTheme);
                builder.setTitle("Wybierz składniki z listy");
                builder.setMultiChoiceItems(edt_listIngredients, edt_checkedIngredients, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            edt_chosenIngredients.add(position);
                        }
                        else {
                            edt_chosenIngredients.remove(Integer.valueOf(position));
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
                        for (int i = 0; i < edt_checkedIngredients.length; i++) {
                            edt_checkedIngredients[i] = false;
                            edt_chosenIngredients.clear();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_editCosImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions(CosmeticEdit.this)) {
                    chooseImg(CosmeticEdit.this);
                }
            }
        });

        btn_edtCosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cosmetic updatedCosmetic;

                try {
                    updatedCosmetic = new Cosmetic(
                            cosmetic.getId(),
                            edt_cosmeticName.getText().toString(),
                            edt_cosmeticBrand.getText().toString(),
                            edtPehType,
                            edtCosType,
                            edt_cosmeticDesc.getText().toString(),
                            imgPath,
                            edt_chosenIngredients);

                }catch (Exception e) {
                    updatedCosmetic = new Cosmetic(-1,"","","","","","");
                }

                boolean success = dataBaseHelper.updateCosmetic(updatedCosmetic);
                dataBaseHelper.deleteAllIngredientsInCosmetic(updatedCosmetic.getId());

                for (int i = 0; i < edt_chosenIngredients.size(); i++) {
                    int ingredientID = findIngredientID(allIngredients,edt_listIngredients[edt_chosenIngredients.get(i)]);
                    boolean success2 = dataBaseHelper.addCosmeticIngredient(updatedCosmetic.getId(),ingredientID);
                }

                Intent intent = new Intent(CosmeticEdit.this, CosmeticActivity.class);
                CosmeticEdit.this.startActivity(intent);
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

    private void setData(Cosmetic cosmetic){
        edt_cosmeticName.setText(cosmetic.getName());
        edt_cosmeticBrand.setText(cosmetic.getBrand());
        edtPehType = cosmetic.getPehType();
        edtCosType = cosmetic.getCosmeticType();
        edt_cosmeticDesc.setText(cosmetic.getDescription());
        imgPath = cosmetic.getImgPath();

        Collections.sort(cosmetic.getInciList());

        for (int i = 0; i < cosmetic.getInciList().size(); i++) {
            int idx = cosmetic.getInciList().get(i);
            edt_checkedIngredients[idx-1] = true;
            edt_chosenIngredients.add(idx-1);
        }
    }

    private void initViews(){

        edt_pehTypes = getResources().getStringArray(R.array.PehTypes);
        edt_cosTypes = getResources().getStringArray(R.array.CosTypes);

        allIngredients = dataBaseHelper.getAllIngredientsNameAndID();

        edt_listIngredients = new String[allIngredients.size()];
        int index = 0;
        for (HashMap.Entry<Integer, String> mapEntry : allIngredients.entrySet()) {
            edt_listIngredients[index] = mapEntry.getValue();
            index++;
        }

        adapterPehTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_peh_type, edt_pehTypes);
        adapterCosTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_cos_type, edt_cosTypes);

        edt_checkedIngredients = new boolean[edt_listIngredients.length];
        edt_chosenIngredients = new ArrayList<>();

        btn_edtCosmetic = findViewById(R.id.edt_btnEditCosmetic);
        btn_editIngredientsInInciList = findViewById(R.id.edt_btnEditIngredientsInInciList);
        btn_editCosImg = findViewById(R.id.edt_btnEditCosImg);
        edt_cosmeticName = findViewById(R.id.edt_cosmeticNameField);
        edt_cosmeticBrand = findViewById(R.id.edt_cosmeticBrandField);
        edt_cosmeticDesc = findViewById(R.id.edt_cosmeticDescField);
        edt_autoCompleteTVPehType = findViewById(R.id.edt_autoCompleteTVPehType);
        edt_autoCompleteTVCosType = findViewById(R.id.edt_autoCompleteTVCosType);

        edt_autoCompleteTVPehType.setAdapter(adapterPehTypes);
        edt_autoCompleteTVCosType.setAdapter(adapterCosTypes);
    }


    public int findIngredientID(HashMap<Integer, String> hashMap, String ingredientName){

        int ingredientID = -1;

        for(HashMap.Entry<Integer, String> entry: hashMap.entrySet()) {
            if(entry.getValue() == ingredientName) {
                return ingredientID = entry.getKey();
            }
        }
        return ingredientID;
    }

    public static boolean checkAndRequestPermissions(final Activity context){

        int extStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

        List<String> permissionList = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (extStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(context, permissionList.toArray(new String[permissionList.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(CosmeticEdit.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                }
                else if (ContextCompat.checkSelfPermission(CosmeticEdit.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                }
                else {
                    chooseImg(CosmeticEdit.this);
                }
                break;
        }
    }

    private void chooseImg(Context context) {

        final CharSequence[] optionsMenu = {"Zrób zdjęcie", "Wybierz z galerii", "Wyjdź"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (optionsMenu[i].equals("Zrób zdjęcie")) {
                    Intent takeImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takeImg, 0);
                }
                else if (optionsMenu[i].equals("Wybierz z galerii")) {
                    Intent pickImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickImg, 1);
                }
                else if (optionsMenu[i].equals("Wyjdź")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");

                        ContextWrapper contextWrapper = new ContextWrapper(CosmeticEdit.this);
                        File directory = contextWrapper.getDir("imgs", MODE_PRIVATE);

                        if (!directory.exists()){
                            directory.mkdir();
                        }

                        String fileName = new SimpleDateFormat("'Img_'yyyyMMddHHmmss'.jpg'").format(new Date());
                        File file = new File(directory,fileName);
                        FileOutputStream fos = null;

                        try {
                            fos = new FileOutputStream(file);
                            selectedImage.compress(Bitmap.CompressFormat.JPEG,50,fos);
                            fos.close();

                        }catch (Exception e){
                            Log.e("SAVE_IMAGE", e.getMessage(), e);
                        }

                        imgPath = file.getPath();
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {

                        Uri selectedImg = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        if (selectedImg != null) {
                            Cursor cursor = getContentResolver().query(selectedImg, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIdx = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIdx);
                                Toast.makeText(CosmeticEdit.this,picturePath, Toast.LENGTH_SHORT).show();
                                imgPath = cursor.getString(columnIdx);
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }

}