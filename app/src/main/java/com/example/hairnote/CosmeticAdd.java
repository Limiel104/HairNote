package com.example.hairnote;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CosmeticAdd extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    private EditText et_cosmeticName, et_cosmeticBrand, et_cosmeticDesc;
    Button btn_addCosmetic, btn_addIngredientsToInciList, btn_addShopsToShopList, btn_addCosImg;
    AutoCompleteTextView autoCompleteTVPehType, autoCompleteTVCosType;
    ArrayAdapter adapterPehTypes, adapterCosTypes;
    String chosenPehType, chosenCosType, imgPath;
    String[] pehTypes, cosTypes, shopBrands, listIngredients;
    boolean[] checkedIngredients, checkedShopBrands;
    ArrayList<Integer> chosenIngredients, chosenShopBrands;
    ArrayList<String> shopList;
    HashMap<Integer, String> allIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_add);

        getSupportActionBar().setTitle("Dodaj Kosmetyk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(CosmeticAdd.this);

        pehTypes = getResources().getStringArray(R.array.PehTypes);
        cosTypes = getResources().getStringArray(R.array.CosTypes);
        shopBrands = getResources().getStringArray(R.array.ShopBrands);

        allIngredients = dataBaseHelper.getAllIngredientsNameAndID();

        listIngredients = new String[allIngredients.size()];
        int index = 0;
        for (HashMap.Entry<Integer, String> mapEntry : allIngredients.entrySet()) {
            listIngredients[index] = mapEntry.getValue();
            index++;
        }

        shopList = new ArrayList<>();

        adapterPehTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_peh_type, pehTypes);
        adapterCosTypes = new ArrayAdapter<>(this, R.layout.drop_down_item_cosmetic_cos_type, cosTypes);

        chosenPehType = "";
        chosenCosType = "";
        imgPath = "";

        checkedIngredients = new boolean[listIngredients.length];
        chosenIngredients = new ArrayList<>();

        checkedShopBrands = new boolean[shopBrands.length];
        chosenShopBrands = new ArrayList<>();

        btn_addCosmetic = findViewById(R.id.btnAddCosmetic);
        btn_addIngredientsToInciList = findViewById(R.id.btnAddIngredientsToInciList);
        btn_addShopsToShopList = findViewById(R.id.btnAddShopsToShopList);
        btn_addCosImg = findViewById(R.id.btnAddCosImg);
        et_cosmeticName = findViewById(R.id.editCosmeticNameField);
        et_cosmeticBrand = findViewById(R.id.editCosmeticBrandField);
        et_cosmeticDesc = findViewById(R.id.editCosmeticDescField);
        autoCompleteTVPehType = findViewById(R.id.autoCompleteTVPehType);
        autoCompleteTVCosType = findViewById(R.id.autoCompleteTVCosType);

        autoCompleteTVPehType.setAdapter(adapterPehTypes);
        autoCompleteTVCosType.setAdapter(adapterCosTypes);

        autoCompleteTVPehType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenPehType = autoCompleteTVPehType.getText().toString();
            }
        });

        autoCompleteTVCosType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenCosType = autoCompleteTVCosType.getText().toString();
            }
        });

        btn_addIngredientsToInciList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticAdd.this, R.style.AlertDialogTheme);
                builder.setTitle("Wybierz sk??adniki z listy");
                builder.setMultiChoiceItems(listIngredients, checkedIngredients, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            chosenIngredients.add(position);
                        }
                        else {
                            chosenIngredients.remove(Integer.valueOf(position));
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
                        for (int i = 0; i < checkedIngredients.length; i++) {
                            checkedIngredients[i] = false;
                            chosenIngredients.clear();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_addShopsToShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticAdd.this, R.style.AlertDialogTheme);
                builder.setTitle("Wybierz sklepy z listy");
                builder.setMultiChoiceItems(shopBrands, checkedShopBrands, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            chosenShopBrands.add(position);
                        }
                        else {
                            chosenShopBrands.remove(Integer.valueOf(position));
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
                        for (int i = 0; i < checkedShopBrands.length; i++) {
                            checkedShopBrands[i] = false;
                            chosenShopBrands.clear();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_addCosImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions(CosmeticAdd.this)) {
                    chooseImg(CosmeticAdd.this);
                }
            }
        });

        btn_addCosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cosmetic newCosmetic;

                for (int i = 0; i < chosenShopBrands.size(); i++) {
                    shopList.add(shopBrands[chosenShopBrands.get(i)]);
                }

                try {
                    newCosmetic = new Cosmetic(-1,
                            et_cosmeticName.getText().toString(),
                            et_cosmeticBrand.getText().toString(),
                            chosenPehType,
                            chosenCosType,
                            et_cosmeticDesc.getText().toString(),
                            imgPath,
                            chosenIngredients,
                            shopList);

                }catch (Exception e) {
                    newCosmetic = new Cosmetic(-1,"error","","","","","", chosenIngredients, shopList);
                }

                int cosmeticID = (int) dataBaseHelper.addCosmetic(newCosmetic);

                for (int i = 0; i < chosenIngredients.size(); i++) {
                    int ingredientID = findIngredientID(allIngredients,listIngredients[chosenIngredients.get(i)]);
                    boolean success = dataBaseHelper.addCosmeticIngredient(cosmeticID,ingredientID);
                }

                for (int i = 0; i < chosenShopBrands.size(); i++) {
                    String shopBrandName = shopList.get(i);
                    boolean success = dataBaseHelper.addShopCosmetic(cosmeticID, shopBrandName);
                }
                Toast.makeText(CosmeticAdd.this, "Dodano Kosmetyk", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CosmeticAdd.this, CosmeticActivity.class);
                CosmeticAdd.this.startActivity(intent);
                finish();

            }
        });

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
                if (ContextCompat.checkSelfPermission(CosmeticAdd.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                }
                else if (ContextCompat.checkSelfPermission(CosmeticAdd.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                }
                else {
                   chooseImg(CosmeticAdd.this);
                }
                break;
        }
    }

    private void chooseImg(Context context) {

        final CharSequence[] optionsMenu = {"Zr??b zdj??cie", "Wybierz z galerii", "Wyjd??"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (optionsMenu[i].equals("Zr??b zdj??cie")) {
                    Intent takeImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takeImg, 0);
                }
                else if (optionsMenu[i].equals("Wybierz z galerii")) {
                    Intent pickImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickImg, 1);
                }
                else if (optionsMenu[i].equals("Wyjd??")) {
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

                            ContextWrapper contextWrapper = new ContextWrapper(CosmeticAdd.this);
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
                                Toast.makeText(CosmeticAdd.this,picturePath, Toast.LENGTH_SHORT).show();
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