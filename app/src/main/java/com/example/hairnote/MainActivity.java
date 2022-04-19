package com.example.hairnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //AKTYWNOSC Z MYCIAMI

    //Initialize variable
    DrawerLayout drawerLayout;
    Button btn_addWashMain;

    private RecyclerView washesRecView;

    WashesRecViewAdapter washesAdapter;
    DataBaseHelper dataBaseHelper;
    //adapter.setWashes(washes);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        washesRecView = findViewById(R.id.washesRecView);
        btn_addWashMain = findViewById(R.id.btnAddWashMain);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);


        /*ArrayList<Wash> washes = new ArrayList<>();
        washes = dataBaseHelper.getAllWashes();
        washesAdapter = new WashesRecViewAdapter();
        washesAdapter.setWashes(washes);
        washesRecView.setAdapter(washesAdapter);
        washesRecView.setLayoutManager(new LinearLayoutManager(this));*/

        ShowAllWashesOnRecView(dataBaseHelper);

        btn_addWashMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"Dodaj1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WashAdd.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    public void ShowAllWashesOnRecView(DataBaseHelper dataBaseHelper) {
        //ArrayList<Wash> washes = new ArrayList<>();
        //washes = dataBaseHelper.getAllWashes();
        washesAdapter = new WashesRecViewAdapter(this);
        washesAdapter.setWashes(dataBaseHelper.getAllWashes()); //tutaj skracamy tak ze nie potrzebujemy tych 2 wyrzej
        washesRecView.setAdapter(washesAdapter);
        washesRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void ClickMenu(View view){
        //Open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        //Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        //close drawer layout
        //check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickWash(View view){
        //Recreate this activity
        recreate();
    }

    public void ClickCosmetic(View view){
        //Redirect activity to Kosmetyki
        redirectActivity(this, CosmeticActivity.class);
    }

    public void ClickIngredient(View view){
        //Redirect activity to Skladniki
        redirectActivity(this,IngredientActivity.class);
    }

    public static void redirectActivity(Activity activity, Class aClass){
        //Initialize intent
        Intent intent = new Intent(activity,aClass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }
}