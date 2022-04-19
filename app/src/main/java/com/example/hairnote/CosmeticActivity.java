package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CosmeticActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button btn_addCosmeticMain;

    private RecyclerView cosmeticsRecView;
    CosmeticsRecViewAdapter cosmeticAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic);

        drawerLayout = findViewById(R.id.drawer_layout);

        //cosmeticAdapter = new CosmeticsRecViewAdapter(this);
        cosmeticsRecView = findViewById(R.id.cosmeticsRecView);
        btn_addCosmeticMain = findViewById(R.id.btnAddCosmeticMain);

        dataBaseHelper = new DataBaseHelper(CosmeticActivity.this);

        //cosmeticsRecView.setAdapter(cosmeticAdapter);
        //cosmeticsRecView.setLayoutManager(new GridLayoutManager(this, 2));

        //ArrayList<Cosmetic> cosmetics = new ArrayList<>();
        //cosmetics.add(new Cosmetic(1,"sdbakj","OnlyBio","Proteiny","Odżywka","asdad,sadsa,dasd,as,d,as,dasd","dasfdasdfsafsa", "https://www.hebe.pl/on/demandware.static/-/Sites-PL_Master_Catalog/default/dw7b35f56f/images/hi-res/389490__odzywka_proteinowa_do_wlosow_kazdej_porowatosci__200_ml__abc__2__reviewed__p.png"));
        //cosmetics.add(new Cosmetic(2,"fddsf","OnlyBio","Humektanty","Odżywka","dfgs","dasfdasdfsafsa","https://darmarsklep.pl/img/product_media/46001-47000/5902811787765.png"));

        //cosmeticAdapter.setCosmetics(cosmetics);

        ShowAllCosmeticsOnRecView(dataBaseHelper);

        btn_addCosmeticMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"Dodaj1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CosmeticActivity.this, CosmeticAdd.class);
                CosmeticActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    public void ShowAllCosmeticsOnRecView(DataBaseHelper dataBaseHelper) {
        cosmeticAdapter = new CosmeticsRecViewAdapter(this);
        //cosmeticAdapter.setCosmetics(dataBaseHelper.getAllCosmetics());
        cosmeticsRecView.setAdapter(cosmeticAdapter);
        cosmeticsRecView.setLayoutManager(new GridLayoutManager(this, 2));
        //cosmeticAdapter.setCosmetics(cosmetics);
        cosmeticAdapter.setCosmetics(dataBaseHelper.getAllCosmetics());
    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void  ClickWash(View view){
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void ClickCosmetic(View view){
        recreate();
    }

    public void ClickIngredient(View view){
        //Redirect activity to Skladniki
        MainActivity.redirectActivity(this,IngredientActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}