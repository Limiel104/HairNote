package com.example.hairnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String INGREDIENT_TABLE = "INGREDIENT_TABLE";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    public static final String COLUMN_INGREDIENT_TYPE = "INGREDIENT_TYPE";
    public static final String COLUMN_INGREDIENT_DESC = "INGREDIENT_DESC";
    public static final String COLUMN_INGREDIENT_ID = "ID";
    public static final String WASH_TABLE = "WASH_TABLE";
    public static final String COLUMN_WASH_ID = "WASH_ID";
    public static final String COLUMN_WASH_DATE = "WASH_DATE";
    public static final String COLUMN_WASH_IS_CLEANSING = "WASH_IS_CLEANSING";
    public static final String COLUMN_WASH_USED_PEELING = "WASH_USED_PEELING";
    public static final String COLUMN_WASH_USED_OILING = "WASH_USED_OILING";
    public static final String COLUMN_WASH_DESC = "WASH_DESC";
    public static final String COSMETIC_TABLE = "COSMETIC_TABLE";
    public static final String COLUMN_COSMETIC_ID = "COSMETIC_ID";
    public static final String COLUMN_COSMETIC_NAME = "COSMETIC_NAME";
    public static final String COLUMN_COSMETIC_BRAND = "COSMETIC_BRAND";
    public static final String COLUMN_COSMETIC_PEHTYPE = "COSMETIC_PEHTYPE";
    public static final String COLUMN_COSMETIC_COSMETICTYPE = "COSMETIC_COSMETICTYPE";
    public static final String COLUMN_COSMETIC_INCILIST = "COSMETIC_INCILIST";
    public static final String COLUMN_COSMETIC_DESC = "COSMETIC_DESC";
    public static final String COLUMN_COSMETIC_IMGPATH = "COSMETIC_IMGPATH";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "appDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableStatement1 = "CREATE TABLE " + INGREDIENT_TABLE + " ("
                + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_INGREDIENT_NAME + " TEXT, "
                + COLUMN_INGREDIENT_TYPE + " TEXT, "
                + COLUMN_INGREDIENT_DESC + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement1);

        String createTableStatement2 = "CREATE TABLE " + WASH_TABLE + " ( "
                + COLUMN_WASH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WASH_DATE + " TEXT, "
                + COLUMN_WASH_IS_CLEANSING + " BOOL, "
                + COLUMN_WASH_USED_PEELING + " BOOL, "
                + COLUMN_WASH_USED_OILING + " BOOL, "
                + COLUMN_WASH_DESC + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement2);

        String createTableStatement3 = "CREATE TABLE " + COSMETIC_TABLE + " ("
                + COLUMN_COSMETIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COSMETIC_NAME + " TEXT, "
                + COLUMN_COSMETIC_BRAND + " TEXT, "
                + COLUMN_COSMETIC_PEHTYPE + " TEXT, "
                + COLUMN_COSMETIC_COSMETICTYPE + " TEXT, "
                + COLUMN_COSMETIC_INCILIST + " TEXT, "
                + COLUMN_COSMETIC_DESC + " TEXT, "
                + COLUMN_COSMETIC_IMGPATH + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //---------------------------------------INGREDIENT METHODS ------------------------------------------------

    public boolean addIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INGREDIENT_NAME, ingredient.getName());
        cv.put(COLUMN_INGREDIENT_TYPE, ingredient.getType());
        cv.put(COLUMN_INGREDIENT_DESC, ingredient.getDescription());

        long insert = db.insert(INGREDIENT_TABLE, null, cv);

        if (insert == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Ingredient> getAllIngredients() {

        ArrayList<Ingredient> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + INGREDIENT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                int ingredientID = cursor.getInt(0);
                String ingredientName = cursor.getString(1);
                String ingredientType = cursor.getString(2);
                String ingredientDesc = cursor.getString(3);

                Ingredient newIngredient = new Ingredient(ingredientID, ingredientName, ingredientType, ingredientDesc);
                returnAList.add(newIngredient);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public Ingredient findIngredient(int ingredientID) {

        String queryString = "SELECT * FROM " + INGREDIENT_TABLE + " WHERE " + COLUMN_INGREDIENT_ID + "=" + ingredientID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            String ingredientName = cursor.getString(1);
            String ingredientType = cursor.getString(2);
            String ingredientDesc = cursor.getString(3);

            Ingredient returnIngredient= new Ingredient(ingredientID, ingredientName, ingredientType, ingredientDesc);
            cursor.close();
            db.close();
            return returnIngredient;
        }else{
            cursor.close();
            db.close();
            return null;
        }
    }

    public boolean deleteIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + INGREDIENT_TABLE + " WHERE " + COLUMN_INGREDIENT_ID + " = " + ingredient.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    //---------------------------------------WASH METHODS ------------------------------------------------

    public boolean addWash(Wash wash) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WASH_DATE, wash.getDate());
        cv.put(COLUMN_WASH_IS_CLEANSING, wash.isCleansing());
        cv.put(COLUMN_WASH_USED_PEELING, wash.isUsedPeeling());
        cv.put(COLUMN_WASH_USED_OILING, wash.isUsedOiling());
        cv.put(COLUMN_WASH_DESC, wash.getDescription());

        long insert = db.insert(WASH_TABLE, null, cv);

        if (insert == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Wash> getAllWashes() {

        ArrayList<Wash> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + WASH_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                int washID = cursor.getInt(0);
                String washDate = cursor.getString(1);
                boolean washIsCleansing = cursor.getInt(2) == 1 ? true : false;
                boolean washUsedPeeling = cursor.getInt(3) == 1 ? true : false;
                boolean washUsedOiling = cursor.getInt(4) == 1 ? true : false;
                String washDesc = cursor.getString(5);

                Wash newWash = new Wash(washID, washDate, washIsCleansing, washUsedPeeling, washUsedOiling, washDesc);
                returnAList.add(newWash);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public Wash findWash(int washID) {

        String queryString = "SELECT * FROM " + WASH_TABLE + " WHERE " + COLUMN_WASH_ID + "=" + washID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            String washDate = cursor.getString(1);
            boolean washIsCleansing = cursor.getInt(2) == 1 ? true : false;
            boolean washUsedPeeling = cursor.getInt(3) == 1 ? true : false;
            boolean washUsedOiling = cursor.getInt(4) == 1 ? true : false;
            String washDesc = cursor.getString(5);

            Wash returnWash= new Wash(washID, washDate, washIsCleansing, washUsedPeeling, washUsedOiling, washDesc);
            cursor.close();
            db.close();
            return returnWash;
        }else{
            cursor.close();
            db.close();
            return null;
        }
    }

    public boolean deleteWash(Wash wash) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + WASH_TABLE + " WHERE " + COLUMN_WASH_ID + " = " + wash.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    //---------------------------------------COSMETIC METHODS ------------------------------------------------

    public boolean addCosmetic(Cosmetic cosmetic) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COSMETIC_NAME, cosmetic.getName());
        cv.put(COLUMN_COSMETIC_BRAND, cosmetic.getBrand());
        cv.put(COLUMN_COSMETIC_PEHTYPE, cosmetic.getPehType());
        cv.put(COLUMN_COSMETIC_COSMETICTYPE, cosmetic.getCosmeticType());
        cv.put(COLUMN_COSMETIC_INCILIST, cosmetic.getInciList());
        cv.put(COLUMN_COSMETIC_DESC, cosmetic.getDescription());
        cv.put(COLUMN_COSMETIC_IMGPATH, cosmetic.getImgPath());

        long insert = db.insert(COSMETIC_TABLE, null, cv);

        if (insert == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Cosmetic> getAllCosmetics() {

        ArrayList<Cosmetic> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + COSMETIC_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        List inciList2 = new ArrayList();
        inciList2.add(1);

        if (cursor.moveToFirst()) {
            do{
                int cosmeticID = cursor.getInt(0);
                String cosmeticName = cursor.getString(1);
                String cosmeticBrand = cursor.getString(2);
                String cosmeticPehType = cursor.getString(3);
                String cosmeticCosmeticType = cursor.getString(4);
                String cosmeticInciList = cursor.getString(5);
                String cosmeticDesc = cursor.getString(6);
                String cosmeticImgPath = cursor.getString(7);

                Cosmetic newCosmetic= new Cosmetic(cosmeticID, cosmeticName, cosmeticBrand, cosmeticPehType, cosmeticCosmeticType, cosmeticInciList, cosmeticDesc, cosmeticImgPath, inciList2);
                returnAList.add(newCosmetic);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public Cosmetic findCosmetic(int cosmeticID) {

        String queryString = "SELECT * FROM " + COSMETIC_TABLE + " WHERE " + COLUMN_COSMETIC_ID + "=" + cosmeticID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        List inciList2 = new ArrayList();
        inciList2.add(1);

        if(cursor.moveToFirst()){
            //int cosmeticID2 = cursor.getInt(0);
            String cosmeticName = cursor.getString(1);
            String cosmeticBrand = cursor.getString(2);
            String cosmeticPehType = cursor.getString(3);
            String cosmeticCosmeticType = cursor.getString(4);
            String cosmeticInciList = cursor.getString(5);
            String cosmeticDesc = cursor.getString(6);
            String cosmeticImgPath = cursor.getString(7);

            Cosmetic returnCosmetic= new Cosmetic(cosmeticID, cosmeticName, cosmeticBrand, cosmeticPehType, cosmeticCosmeticType, cosmeticInciList, cosmeticDesc, cosmeticImgPath, inciList2);
            cursor.close();
            db.close();
            return returnCosmetic;
        }else{
            cursor.close();
            db.close();
            return null;
        }
    }

    public boolean deleteCosmetic(Cosmetic cosmetic) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + COSMETIC_TABLE + " WHERE " + COLUMN_COSMETIC_ID + " = " + cosmetic.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }











}
