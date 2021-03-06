package com.example.hairnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String INGREDIENT_TABLE = "INGREDIENT_TABLE";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    public static final String COLUMN_INGREDIENT_TYPE = "INGREDIENT_TYPE";
    public static final String COLUMN_INGREDIENT_DESC = "INGREDIENT_DESC";
    public static final String COLUMN_INGREDIENT_ID = "INGREDIENT_ID";
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
    public static final String COLUMN_COSMETIC_DESC = "COSMETIC_DESC";
    public static final String COLUMN_COSMETIC_IMGPATH = "COSMETIC_IMGPATH";
    public static final String COSMETIC_INGREDIENT_TABLE = "COSMETIC_INREDIENT_TABLE";
    public static final String COLUMN_COSMETIC_INGREDIENT_ID = "COSMETIC_INGREDIENT_ID";
    public static final String COLUMN_COS_ID = "COS_ID";
    public static final String COLUMN_INGR_ID = "INGR_ID";
    public static final String WASH_COSMETIC_TABLE = "WASH_COSMETIC_TABLE";
    public static final String COLUMN_WASH_COSMETIC_ID = "WASH_COSMETIC_ID";
    public static final String COLUMN_WAS_ID = "WAS_ID";
    public static final String SHOP_TABLE = "SHOP_TABLE";
    public static final String COLUMN_SHOP_ID = "SHOP_ID";
    public static final String COLUMN_SHOP_NAME = "SHOP_NAME";
    public static final String COLUMN_SHOP_ADDRESS = "SHOP_ADDRESS";
    public static final String SHOP_COSMETIC_TABLE = "SHOP_COSMETIC_TABLE";
    public static final String COLUMN_SHOP_COSMETIC_ID = "SHOP_COSMETIC_ID";
    public static final String COLUMN_SHOP_BRAND_NAME = "SHOP_BRAND_NAME";
    public static final String COLUMN_SHOP_LAT = "SHOP_LAT";
    public static final String COLUMN_SHOP_LNG = "SHOP_LNG";

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
                + COLUMN_COSMETIC_DESC + " TEXT, "
                + COLUMN_COSMETIC_IMGPATH + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement3);

        String createTableStatement4 = "CREATE TABLE " + COSMETIC_INGREDIENT_TABLE + " ("
                + COLUMN_COSMETIC_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COS_ID + " INTEGER, "
                + COLUMN_INGR_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_COS_ID + ") REFERENCES " + COSMETIC_TABLE + "(" + COLUMN_COSMETIC_ID + "), "
                + "FOREIGN KEY (" + COLUMN_INGR_ID + ") REFERENCES " + INGREDIENT_TABLE + "(" + COLUMN_INGREDIENT_ID + "))";

        sqLiteDatabase.execSQL(createTableStatement4);

        String createTableStatement5 = "CREATE TABLE " + WASH_COSMETIC_TABLE + " ("
                + COLUMN_WASH_COSMETIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WAS_ID + " INTEGER, "
                + COLUMN_COS_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_COS_ID + ") REFERENCES " + COSMETIC_TABLE + "(" + COLUMN_COSMETIC_ID + "), "
                + "FOREIGN KEY (" + COLUMN_WAS_ID + ") REFERENCES " + WASH_TABLE + "(" + COLUMN_WASH_ID + "))";

        sqLiteDatabase.execSQL(createTableStatement5);

        String createTableStatement6 = "CREATE TABLE " + SHOP_TABLE + " ("
                + COLUMN_SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SHOP_NAME + " TEXT, "
                + COLUMN_SHOP_ADDRESS + " TEXT, "
                + COLUMN_SHOP_LAT + " REAL, "
                + COLUMN_SHOP_LNG + " REAL)";

        sqLiteDatabase.execSQL(createTableStatement6);

        String createTableStatement7 = "CREATE TABLE " + SHOP_COSMETIC_TABLE + " ("
                + COLUMN_SHOP_COSMETIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COS_ID + " INTEGER, "
                + COLUMN_SHOP_BRAND_NAME + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_COS_ID + ") REFERENCES " + COSMETIC_TABLE + "(" + COLUMN_COSMETIC_ID + "))";
        sqLiteDatabase.execSQL(createTableStatement7);

        /*String createTableStatement6 = "CREATE TABLE COSMETIC_INGREDIENT_TABLE (COSMETIC_INGREDIENT_ID INTEGER PRIMARY KEY AUTOINCREMENT, COS_ID INTEGER, INGR_ID INTEGER, FOREIGN KEY (COS_ID) REFERENCES COSMETIC_TABLE(COSMETIC_ID), FOREIGN KEY (INGR_ID) REFERENCES INGREDIENT_TABLE(INGREDIENT_ID))";
        sqLiteDatabase.execSQL(createTableStatement6);*/


        /*      INGREDIENTS     */

        ContentValues ingr1 = new ContentValues();
        ingr1.put(COLUMN_INGREDIENT_NAME,"12345678SKBKJB");
        ingr1.put(COLUMN_INGREDIENT_TYPE,"Alkohol");
        ingr1.put(COLUMN_INGREDIENT_DESC,"Praesent vitae metus nulla. Sed ullamcorper orci at elit semper, nec finibus" +
                " justo hendrerit. Quisque eu odio nunc. Pellentesque in mollis eros. Vestibulum sollicitudin lectus" +
                " eget orci ullamcorper pellentesque. Etiam maximus diam et turpis gravida, sit amet finibus neque" +
                " volutpat. Morbi dictum hendrerit sodales. Praesent pretium, felis a ultrices efficitur, elit mauris" +
                " vulputate lorem, at hendrerit magna elit eu nisi.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr1);

        ContentValues ingr2 = new ContentValues();
        ingr2.put(COLUMN_INGREDIENT_NAME,"Ska??dnik1");
        ingr2.put(COLUMN_INGREDIENT_TYPE,"Emolient");
        ingr2.put(COLUMN_INGREDIENT_DESC,"Praesent accumsan consectetur vehicula. Morbi vel est ut lorem consequat " +
                "commodo. Sed vitae libero erat. Mauris id nibh ut sapien bibendum faucibus elementum vitae nulla. " +
                "Ut eget ultricies turpis, vel tempus turpis. Praesent auctor at mauris non porttitor. Duis a metus" +
                " ut eros scelerisque faucibus sed eu eros. Duis tincidunt ante et dui dictum, at sodales justo consequat.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr2);

        ContentValues ingr3 = new ContentValues();
        ingr3.put(COLUMN_INGREDIENT_NAME,"Sk????dnik 2");
        ingr3.put(COLUMN_INGREDIENT_TYPE,"Kwas");
        ingr3.put(COLUMN_INGREDIENT_DESC,"Nullam vulputate, lorem sit amet viverra eleifend, diam lectus dictum quam," +
                " non auctor ante mi ac mauris. Donec venenatis dolor a laoreet laoreet. Suspendisse pulvinar, augue" +
                " ac tempus fermentum, sem velit laoreet risus, sed vehicula metus nisi vitae nisi. Suspendisse " +
                "facilisis sollicitudin tellus a molestie. Phasellus metus ante, accumsan non viverra sed, sollicitudin" +
                " in felis. Mauris ut urna iaculis, blandit leo sit amet, aliquet ipsum.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr3);

        ContentValues ingr4 = new ContentValues();
        ingr4.put(COLUMN_INGREDIENT_NAME,"Nowa nazwa");
        ingr4.put(COLUMN_INGREDIENT_TYPE,"Proteiny");
        ingr4.put(COLUMN_INGREDIENT_DESC,"Maecenas molestie mauris vel elit elementum egestas. Class aptent taciti" +
                " sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce nec velit hendrerit, " +
                "pulvinar orci non, euismod diam. Maecenas nibh enim, rutrum at enim non, ultrices vehicula magna. " +
                "Morbi elementum sed ipsum eleifend semper. Quisque velit quam, accumsan id neque sit amet, viverra " +
                "laoreet metus.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr4);

        ContentValues ingr5 = new ContentValues();
        ingr5.put(COLUMN_INGREDIENT_NAME,"Sk??sdnik");
        ingr5.put(COLUMN_INGREDIENT_TYPE,"Paraben");
        ingr5.put(COLUMN_INGREDIENT_DESC,"Maecenas placerat mi velit, ac sollicitudin massa dignissim vel. Nullam" +
                " vulputate, lorem sit amet viverra eleifend, diam lectus dictum quam, non auctor ante mi ac mauris. " +
                "Donec venenatis dolor a laoreet laoreet. Suspendisse pulvinar, augue ac tempus fermentum, sem velit " +
                "laoreet risus, sed vehicula metus nisi vitae nisi. Suspendisse facilisis sollicitudin tellus a" +
                " molestie. Phasellus metus ante, accumsan non viverra sed, sollicitudin in felis.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr5);

        ContentValues ingr6 = new ContentValues();
        ingr6.put(COLUMN_INGREDIENT_NAME,"????????????????????????");
        ingr6.put(COLUMN_INGREDIENT_TYPE,"Olej");
        ingr6.put(COLUMN_INGREDIENT_DESC,"Sed vitae libero erat. Mauris id nibh ut sapien bibendum faucibus elementum" +
                " vitae nulla. Ut eget ultricies turpis, vel tempus turpis. Praesent auctor at mauris non porttitor. " +
                "Duis a metus ut eros scelerisque faucibus sed eu eros. Duis tincidunt ante et dui dictum, at sodales" +
                " justo consequat.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr6);

        ContentValues ingr7 = new ContentValues();
        ingr7.put(COLUMN_INGREDIENT_NAME,"Gliceryna");
        ingr7.put(COLUMN_INGREDIENT_TYPE,"Humektant");
        ingr7.put(COLUMN_INGREDIENT_DESC,"Orci varius natoque penatibus et magnis dis parturient montes, nascetur" +
                " ridiculus mus. Aliquam at pharetra quam, malesuada scelerisque purus. Proin scelerisque sodales dui," +
                " laoreet feugiat odio vulputate sed. Aliquam vitae enim sit amet lectus congue sagittis in id urna.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr7);

        ContentValues ingr8 = new ContentValues();
        ingr8.put(COLUMN_INGREDIENT_NAME,"Panthenol");
        ingr8.put(COLUMN_INGREDIENT_TYPE,"Emolient");
        ingr8.put(COLUMN_INGREDIENT_DESC,"Curabitur neque tellus, tincidunt nec turpis vel, ullamcorper facilisis elit." +
                " Sed consequat nisl eget gravida viverra. Duis ac interdum lorem. Etiam in quam eget massa consectetur" +
                " sagittis. Aenean scelerisque id arcu eu mollis. Fusce vulputate elementum enim, at convallis nulla" +
                " vulputate at. Aliquam sit amet sem pulvinar, euismod elit et, tincidunt mauris. In elit lectus, feugiat" +
                " vel justo eget, efficitur consequat tortor. Morbi hendrerit mauris eget posuere placerat.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr8);

        ContentValues ingr9 = new ContentValues();
        ingr9.put(COLUMN_INGREDIENT_NAME,"Asdfsdfds");
        ingr9.put(COLUMN_INGREDIENT_TYPE,"Alkohol");
        ingr9.put(COLUMN_INGREDIENT_DESC,"Integer vehicula est mauris, sed malesuada lorem aliquam ac. Praesent accumsan" +
                " consectetur vehicula. Morbi vel est ut lorem consequat commodo. Sed vitae libero erat. Mauris id nibh" +
                " ut sapien bibendum faucibus elementum vitae nulla. Ut eget ultricies turpis, vel tempus turpis." +
                " Praesent auctor at mauris non porttitor. Duis a metus ut eros scelerisque faucibus sed eu eros. Duis" +
                " tincidunt ante et dui dictum, at sodales justo consequat.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr9);

        ContentValues ingr10 = new ContentValues();
        ingr10.put(COLUMN_INGREDIENT_NAME,"vjsb asdo ds adsod a");
        ingr10.put(COLUMN_INGREDIENT_TYPE,"Olej");
        ingr10.put(COLUMN_INGREDIENT_DESC,"Praesent vitae metus nulla. Sed ullamcorper orci at elit semper, nec " +
                "finibus justo hendrerit. Quisque eu odio nunc. Pellentesque in mollis eros. Vestibulum sollicitudin l" +
                "ectus eget orci ullamcorper pellentesque. Etiam maximus diam et turpis gravida, sit amet finibus neque " +
                "volutpat. Morbi dictum hendrerit sodales. Praesent pretium, felis a ultrices efficitur, elit mauris " +
                "vulputate lorem, at hendrerit magna elit eu nisi. Nunc tristique imperdiet nulla, et viverra quam " +
                "tincidunt vestibulum. Maecenas molestie mauris vel elit elementum egestas. Class aptent taciti sociosqu " +
                "ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce nec velit hendrerit, pulvinar orci " +
                "non, euismod diam. Maecenas nibh enim, rutrum at enim non, ultrices vehicula magna. Morbi elementum sed " +
                "ipsum eleifend semper. Quisque velit quam, accumsan id neque sit amet, viverra laoreet metus.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr10);

        ContentValues ingr11 = new ContentValues();
        ingr11.put(COLUMN_INGREDIENT_NAME,"JBASK SJKD  KJDFSJSK");
        ingr11.put(COLUMN_INGREDIENT_TYPE,"Alkohol");
        ingr11.put(COLUMN_INGREDIENT_DESC,"Praesent vitae metus nulla. Sed ullamcorper orci at elit semper, nec finibus" +
                " justo hendrerit. Quisque eu odio nunc. Pellentesque in mollis eros. Vestibulum sollicitudin lectus" +
                " eget orci ullamcorper pellentesque. Etiam maximus diam et turpis gravida, sit amet finibus neque" +
                " volutpat. Morbi dictum hendrerit sodales. Praesent pretium, felis a ultrices efficitur, elit mauris" +
                " vulputate lorem, at hendrerit magna elit eu nisi.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr11);

        ContentValues ingr12 = new ContentValues();
        ingr12.put(COLUMN_INGREDIENT_NAME,"Asdfsdfds");
        ingr12.put(COLUMN_INGREDIENT_TYPE,"Olej");
        ingr12.put(COLUMN_INGREDIENT_DESC,"Integer vehicula est mauris, sed malesuada lorem aliquam ac. Praesent accumsan" +
                " consectetur vehicula. Morbi vel est ut lorem consequat commodo. Sed vitae libero erat. Mauris id nibh" +
                " ut sapien bibendum faucibus elementum vitae nulla. Ut eget ultricies turpis, vel tempus turpis." +
                " Praesent auctor at mauris non porttitor. Duis a metus ut eros scelerisque faucibus sed eu eros. Duis" +
                " tincidunt ante et dui dictum, at sodales justo consequat.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr12);

        ContentValues ingr13 = new ContentValues();
        ingr13.put(COLUMN_INGREDIENT_NAME,"Alkohol denat");
        ingr13.put(COLUMN_INGREDIENT_TYPE,"Humektant");
        ingr13.put(COLUMN_INGREDIENT_DESC,"Orci varius natoque penatibus et magnis dis parturient montes, nascetur" +
                " ridiculus mus. Aliquam at pharetra quam, malesuada scelerisque purus. Proin scelerisque sodales dui," +
                " laoreet feugiat odio vulputate sed. Aliquam vitae enim sit amet lectus congue sagittis in id urna.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr13);

        ContentValues ingr14 = new ContentValues();
        ingr14.put(COLUMN_INGREDIENT_NAME,"sdjbdsj fsd fds fjksd");
        ingr14.put(COLUMN_INGREDIENT_TYPE,"Kwas");
        ingr14.put(COLUMN_INGREDIENT_DESC,"Praesent vitae metus nulla. Sed ullamcorper orci at elit semper, nec " +
                "finibus justo hendrerit. Quisque eu odio nunc. Pellentesque in mollis eros. Vestibulum sollicitudin l" +
                "ectus eget orci ullamcorper pellentesque. Etiam maximus diam et turpis gravida, sit amet finibus neque " +
                "volutpat. Morbi dictum hendrerit sodales. Praesent pretium, felis a ultrices efficitur, elit mauris " +
                "vulputate lorem, at hendrerit magna elit eu nisi. Nunc tristique imperdiet nulla, et viverra quam " +
                "tincidunt vestibulum. Maecenas molestie mauris vel elit elementum egestas. Class aptent taciti sociosqu " +
                "ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce nec velit hendrerit, pulvinar orci " +
                "non, euismod diam. Maecenas nibh enim, rutrum at enim non, ultrices vehicula magna. Morbi elementum sed " +
                "ipsum eleifend semper. Quisque velit quam, accumsan id neque sit amet, viverra laoreet metus.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr14);

        ContentValues ingr15 = new ContentValues();
        ingr15.put(COLUMN_INGREDIENT_NAME,"Sk??adnik 15");
        ingr15.put(COLUMN_INGREDIENT_TYPE,"Paraben");
        ingr15.put(COLUMN_INGREDIENT_DESC,"Maecenas placerat mi velit, ac sollicitudin massa dignissim vel. Nullam" +
                " vulputate, lorem sit amet viverra eleifend, diam lectus dictum quam, non auctor ante mi ac mauris. " +
                "Donec venenatis dolor a laoreet laoreet. Suspendisse pulvinar, augue ac tempus fermentum, sem velit " +
                "laoreet risus, sed vehicula metus nisi vitae nisi. Suspendisse facilisis sollicitudin tellus a" +
                " molestie. Phasellus metus ante, accumsan non viverra sed, sollicitudin in felis.");
        sqLiteDatabase.insert(INGREDIENT_TABLE, null, ingr15);


        /*      Cosmetics     */

        ContentValues cos1 = new ContentValues();
        cos1.put(COLUMN_COSMETIC_NAME,"Wcierka kawowa");
        cos1.put(COLUMN_COSMETIC_BRAND,"Anwen");
        cos1.put(COLUMN_COSMETIC_PEHTYPE,"E");
        cos1.put(COLUMN_COSMETIC_COSMETICTYPE,"Wcierka");
        cos1.put(COLUMN_COSMETIC_DESC,"Pellentesque eu nulla fermentum, bibendum nisl nec, dictum nulla. Sed erat tellus," +
                " ornare quis semper sit amet, consequat eget lacus. Suspendisse ipsum enim, vestibulum eget quam quis, " +
                "feugiat scelerisque ipsum. Donec in elementum leo, et eleifend justo. Fusce consectetur urna scelerisque " +
                "risus congue condimentum at in orci. Integer eu purus dictum, lobortis lacus vitae, pellentesque lorem.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos1);

        ContentValues cos2 = new ContentValues();
        cos2.put(COLUMN_COSMETIC_NAME,"Od??ywka nawil??aj??ca");
        cos2.put(COLUMN_COSMETIC_BRAND,"Derma Cosmetics");
        cos2.put(COLUMN_COSMETIC_PEHTYPE,"H");
        cos2.put(COLUMN_COSMETIC_COSMETICTYPE,"Od??ywka");
        cos2.put(COLUMN_COSMETIC_DESC,"Donec purus est, dignissim vel mattis et, fermentum at arcu. Maecenas suscipit velit" +
                " et neque dapibus convallis. Nullam vitae gravida massa, quis ultrices dui. Duis et nisi auctor, vestibulum" +
                " arcu ac, pellentesque odio. In at massa in ipsum consectetur hendrerit. Phasellus vehicula, purus nec" +
                " sollicitudin laoreet, odio libero semper tortor, at egestas lectus risus aliquam arcu. Quisque tempor" +
                " tempor sapien, id hendrerit lorem venenatis in. Interdum et malesuada fames ac ante ipsum primis in faucibus." +
                " Sed volutpat vel mi quis iaculis. Sed et sem faucibus, semper dui ac, sollicitudin massa. Sed pharetra" +
                " elementum augue, in semper justo convallis eu. Sed lobortis at ligula in aliquet. Etiam faucibus massa " +
                "eget mi elementum, dictum blandit dui consectetur.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos2);

        ContentValues cos3 = new ContentValues();
        cos3.put(COLUMN_COSMETIC_NAME,"Delikatny szampon z koziego mleka");
        cos3.put(COLUMN_COSMETIC_BRAND,"Pantene");
        cos3.put(COLUMN_COSMETIC_PEHTYPE,"EH");
        cos3.put(COLUMN_COSMETIC_COSMETICTYPE,"Szampon Delikatny");
        cos3.put(COLUMN_COSMETIC_DESC,"Phasellus tincidunt, quam a malesuada placerat, lorem est consectetur est, at " +
                "imperdiet nisl magna eget neque. Fusce in feugiat felis. Fusce scelerisque ex risus, vitae mattis " +
                "augue rhoncus dignissim. Proin rhoncus semper massa eget sodales. Cras venenatis ut ipsum vel lacinia.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos3);

        ContentValues cos4 = new ContentValues();
        cos4.put(COLUMN_COSMETIC_NAME,"2138598 Szampon oczyszczaj??cy");
        cos4.put(COLUMN_COSMETIC_BRAND,"Vianek");
        cos4.put(COLUMN_COSMETIC_PEHTYPE,"PEH");
        cos4.put(COLUMN_COSMETIC_COSMETICTYPE,"Szampon Mocny");
        cos4.put(COLUMN_COSMETIC_DESC,"Vestibulum sodales vehicula ante, sed pretium lorem finibus quis. Suspendisse a " +
                "felis vitae mi fringilla tempor. Aliquam in neque eu arcu lacinia suscipit. Donec eleifend hendrerit ipsum " +
                "eu egestas. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas " +
                "ullamcorper nisl nec tincidunt pulvinar. Duis eu risus dignissim, eleifend metus a, iaculis est. Maecenas " +
                "ultricies dui justo, vel accumsan magna tincidunt semper.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos4);

        ContentValues cos5 = new ContentValues();
        cos5.put(COLUMN_COSMETIC_NAME,"Maska z asbjk sjkda");
        cos5.put(COLUMN_COSMETIC_BRAND,"Anwen");
        cos5.put(COLUMN_COSMETIC_PEHTYPE,"P");
        cos5.put(COLUMN_COSMETIC_COSMETICTYPE,"Maska");
        cos5.put(COLUMN_COSMETIC_DESC,"Cras tincidunt consequat imperdiet. Sed vel massa sed quam faucibus varius vel et felis." +
                " Pellentesque consequat ultricies ultrices. Maecenas lacinia dapibus vestibulum. In aliquet placerat " +
                "tortor eget eleifend. Fusce elementum interdum eros, sit amet pretium arcu sagittis id. Sed velit tellus," +
                " venenatis sollicitudin condimentum ac, dictum id nulla. Sed faucibus, magna ac feugiat blandit, risus " +
                "est finibus lorem, ut tincidunt libero magna a quam.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos5);

        ContentValues cos6 = new ContentValues();
        cos6.put(COLUMN_COSMETIC_NAME,"sabd U DKJAS jsa dasj");
        cos6.put(COLUMN_COSMETIC_BRAND,"Joanna");
        cos6.put(COLUMN_COSMETIC_PEHTYPE,"PE");
        cos6.put(COLUMN_COSMETIC_COSMETICTYPE,"Pianka");
        cos6.put(COLUMN_COSMETIC_DESC,"Sed rutrum ultricies lacinia. Curabitur non finibus magna. Cras magna massa, cursus" +
                " ut justo quis, interdum faucibus erat. Quisque tellus urna, pharetra at commodo ultrices, ultrices et diam." +
                " Sed vitae tellus in sem pellentesque accumsan. Proin viverra fringilla elit, a aliquam leo vehicula vitae. " +
                "Vestibulum malesuada enim in malesuada lacinia.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos6);

        ContentValues cos7 = new ContentValues();
        cos7.put(COLUMN_COSMETIC_NAME,"Ala ma kota, kot ma ale");
        cos7.put(COLUMN_COSMETIC_BRAND,"EcoBio");
        cos7.put(COLUMN_COSMETIC_PEHTYPE,"PH");
        cos7.put(COLUMN_COSMETIC_COSMETICTYPE,"??el");
        cos7.put(COLUMN_COSMETIC_DESC,"Donec purus est, dignissim vel mattis et, fermentum at arcu. Maecenas suscipit velit et" +
                " neque dapibus convallis. Nullam vitae gravida massa, quis ultrices dui. Duis et nisi auctor, vestibulum arcu" +
                " ac, pellentesque odio. In at massa in ipsum consectetur hendrerit. Phasellus vehicula, purus nec sollicitudin" +
                " laoreet, odio libero semper tortor, at egestas lectus risus aliquam arcu. Quisque tempor tempor sapien, id " +
                "hendrerit lorem venenatis in. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed volutpat vel mi" +
                " quis iaculis. Sed et sem faucibus, semper dui ac, sollicitudin massa.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos7);

        ContentValues cos8 = new ContentValues();
        cos8.put(COLUMN_COSMETIC_NAME,"Kosmetyk 8");
        cos8.put(COLUMN_COSMETIC_BRAND,"Sylveco");
        cos8.put(COLUMN_COSMETIC_PEHTYPE,"E");
        cos8.put(COLUMN_COSMETIC_COSMETICTYPE,"Krem");
        cos8.put(COLUMN_COSMETIC_DESC,"Curabitur interdum, erat sed ultrices tempus, mauris orci aliquet eros, quis volutpat turpis " +
                "libero vitae quam. Aenean dapibus, nibh vitae fringilla faucibus, justo dui facilisis lorem, sed finibus libero " +
                "tellus vitae purus. Aliquam laoreet risus neque, vitae rhoncus dolor pulvinar sit amet. Aliquam purus urna, vehicula " +
                "ac tincidunt in, tempor a lorem. Sed id tortor dui. Orci varius natoque penatibus et magnis dis parturient montes, " +
                "nascetur ridiculus mus. Cras vel risus leo.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos8);

        ContentValues cos9 = new ContentValues();
        cos9.put(COLUMN_COSMETIC_NAME,"BKSDB SDUI askj sj");
        cos9.put(COLUMN_COSMETIC_BRAND,"Nivea");
        cos9.put(COLUMN_COSMETIC_PEHTYPE,"H");
        cos9.put(COLUMN_COSMETIC_COSMETICTYPE,"Szampon ??redni");
        cos9.put(COLUMN_COSMETIC_DESC,"Curabitur nec vulputate orci. Donec quis magna ex. Aliquam tempus justo non nulla tincidunt commodo." +
                " Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce tristique cursus tellus" +
                " eu mattis. Pellentesque eu nulla fermentum, bibendum nisl nec, dictum nulla. Sed erat tellus, ornare quis semper sit" +
                " amet, consequat eget lacus. Suspendisse ipsum enim, vestibulum eget quam quis, feugiat scelerisque ipsum.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos9);

        ContentValues cos10 = new ContentValues();
        cos10.put(COLUMN_COSMETIC_NAME,"??????????????????????");
        cos10.put(COLUMN_COSMETIC_BRAND,"OnlyBio");
        cos10.put(COLUMN_COSMETIC_PEHTYPE,"PEH");
        cos10.put(COLUMN_COSMETIC_COSMETICTYPE,"Olejek");
        cos10.put(COLUMN_COSMETIC_DESC,"Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas " +
                "ullamcorper nisl nec tincidunt pulvinar. Duis eu risus dignissim, eleifend metus a, iaculis est. Maecenas ultricies " +
                "dui justo, vel accumsan magna tincidunt semper.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos10);

        ContentValues cos11 = new ContentValues();
        cos11.put(COLUMN_COSMETIC_NAME,"jkdbfkjs dkfhs fdhk fhj sfhds");
        cos11.put(COLUMN_COSMETIC_BRAND,"Nivea");
        cos11.put(COLUMN_COSMETIC_PEHTYPE,"H");
        cos11.put(COLUMN_COSMETIC_COSMETICTYPE,"Szampon ??redni");
        cos11.put(COLUMN_COSMETIC_DESC,"Curabitur nec vulputate orci. Donec quis magna ex. Aliquam tempus justo non nulla tincidunt commodo." +
                " Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce tristique cursus tellus" +
                " eu mattis. Pellentesque eu nulla fermentum, bibendum nisl nec, dictum nulla. Sed erat tellus, ornare quis semper sit" +
                " amet, consequat eget lacus. Suspendisse ipsum enim, vestibulum eget quam quis, feugiat scelerisque ipsum.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos11);

        ContentValues cos12 = new ContentValues();
        cos12.put(COLUMN_COSMETIC_NAME,"AAJKSBDJA sudb skdf skj s");
        cos12.put(COLUMN_COSMETIC_BRAND,"Derma Cosmetics");
        cos12.put(COLUMN_COSMETIC_PEHTYPE,"H");
        cos12.put(COLUMN_COSMETIC_COSMETICTYPE,"Od??ywka");
        cos12.put(COLUMN_COSMETIC_DESC,"Donec purus est, dignissim vel mattis et, fermentum at arcu. Maecenas suscipit velit" +
                " et neque dapibus convallis. Nullam vitae gravida massa, quis ultrices dui. Duis et nisi auctor, vestibulum" +
                " arcu ac, pellentesque odio. In at massa in ipsum consectetur hendrerit. Phasellus vehicula, purus nec" +
                " sollicitudin laoreet, odio libero semper tortor, at egestas lectus risus aliquam arcu. Quisque tempor" +
                " tempor sapien, id hendrerit lorem venenatis in. Interdum et malesuada fames ac ante ipsum primis in faucibus." +
                " Sed volutpat vel mi quis iaculis. Sed et sem faucibus, semper dui ac, sollicitudin massa. Sed pharetra" +
                " elementum augue, in semper justo convallis eu. Sed lobortis at ligula in aliquet. Etiam faucibus massa " +
                "eget mi elementum, dictum blandit dui consectetur.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos12);

        ContentValues cos13 = new ContentValues();
        cos13.put(COLUMN_COSMETIC_NAME,"ergz 12398 sdvdqjs 324432");
        cos13.put(COLUMN_COSMETIC_BRAND,"EcoBio");
        cos13.put(COLUMN_COSMETIC_PEHTYPE,"PH");
        cos13.put(COLUMN_COSMETIC_COSMETICTYPE,"??el");
        cos13.put(COLUMN_COSMETIC_DESC,"Donec purus est, dignissim vel mattis et, fermentum at arcu. Maecenas suscipit velit et" +
                " neque dapibus convallis. Nullam vitae gravida massa, quis ultrices dui. Duis et nisi auctor, vestibulum arcu" +
                " ac, pellentesque odio. In at massa in ipsum consectetur hendrerit. Phasellus vehicula, purus nec sollicitudin" +
                " laoreet, odio libero semper tortor, at egestas lectus risus aliquam arcu. Quisque tempor tempor sapien, id " +
                "hendrerit lorem venenatis in. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed volutpat vel mi" +
                " quis iaculis. Sed et sem faucibus, semper dui ac, sollicitudin massa.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos13);

        ContentValues cos14 = new ContentValues();
        cos14.put(COLUMN_COSMETIC_NAME,"Szampon z wyci??gu z manuka");
        cos14.put(COLUMN_COSMETIC_BRAND,"Vianek");
        cos14.put(COLUMN_COSMETIC_PEHTYPE,"PEH");
        cos14.put(COLUMN_COSMETIC_COSMETICTYPE,"Szampon Mocny");
        cos14.put(COLUMN_COSMETIC_DESC,"Vestibulum sodales vehicula ante, sed pretium lorem finibus quis. Suspendisse a " +
                "felis vitae mi fringilla tempor. Aliquam in neque eu arcu lacinia suscipit. Donec eleifend hendrerit ipsum " +
                "eu egestas. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas " +
                "ullamcorper nisl nec tincidunt pulvinar. Duis eu risus dignissim, eleifend metus a, iaculis est. Maecenas " +
                "ultricies dui justo, vel accumsan magna tincidunt semper.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos14);

        ContentValues cos15 = new ContentValues();
        cos15.put(COLUMN_COSMETIC_NAME,"Maska od??ywcza z wyci??gu z aloesu");
        cos15.put(COLUMN_COSMETIC_BRAND,"Anwen");
        cos15.put(COLUMN_COSMETIC_PEHTYPE,"P");
        cos15.put(COLUMN_COSMETIC_COSMETICTYPE,"Maska");
        cos15.put(COLUMN_COSMETIC_DESC,"Cras tincidunt consequat imperdiet. Sed vel massa sed quam faucibus varius vel et felis." +
                " Pellentesque consequat ultricies ultrices. Maecenas lacinia dapibus vestibulum. In aliquet placerat " +
                "tortor eget eleifend. Fusce elementum interdum eros, sit amet pretium arcu sagittis id. Sed velit tellus," +
                " venenatis sollicitudin condimentum ac, dictum id nulla. Sed faucibus, magna ac feugiat blandit, risus " +
                "est finibus lorem, ut tincidunt libero magna a quam.");
        sqLiteDatabase.insert(COSMETIC_TABLE, null, cos15);

        /*      Washes     */

        ContentValues wash1 = new ContentValues();
        wash1.put(COLUMN_WASH_DATE,"12 - 03 - 2022");
        wash1.put(COLUMN_WASH_IS_CLEANSING,0);
        wash1.put(COLUMN_WASH_USED_PEELING,0);
        wash1.put(COLUMN_WASH_USED_OILING,0);
        wash1.put(COLUMN_WASH_DESC,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu euismod dolor. Duis dolor" +
                " ligula, facilisis at odio vel, dignissim auctor nunc. Sed iaculis pellentesque lacus, vitae scelerisque dolor." +
                " Maecenas mollis nibh in sodales pharetra. Nulla eleifend ante pretium urna egestas, id blandit mi malesuada. " +
                "Etiam nec vestibulum orci. Aenean nec hendrerit ligula, non posuere tortor. Morbi bibendum, enim sit amet luctus " +
                "malesuada, ante libero porta justo, vel dignissim turpis eros ut lectus. Fusce iaculis imperdiet pretium. Ut elit " +
                "arcu, scelerisque et lorem ut, sodales euismod augue. Integer quis quam lectus. Nullam ut finibus metus. Vestibulum " +
                "ut elementum risus. Nunc volutpat justo odio, vitae iaculis sem condimentum a. Vivamus faucibus lorem lacinia augue " +
                "dapibus, at vestibulum neque accumsan. Vestibulum vestibulum elit id sapien accumsan, sed fringilla nisl aliquam. " +
                "Donec a vulputate nibh. Aenean non augue vulputate ante ultricies maximus. Suspendisse at diam est. Vestibulum ante " +
                "ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Proin vitae ante laoreet, vehicula neque non, " +
                "faucibus metus. Sed urna sapien, bibendum sit amet euismod at, molestie quis nibh. Phasellus sagittis vel augue " +
                "a maximus. Nullam arcu lorem, efficitur sit amet egestas eu, lobortis ut augue. Donec sem eros, maximus quis " +
                "accumsan non, accumsan id felis. Sed at dignissim nunc, vitae fermentum justo. Duis porttitor, sem id maximus " +
                "volutpat, sapien elit porttitor metus, quis pulvinar libero tortor et lorem. Aliquam nec lectus vitae est " +
                "convallis rhoncus. In vitae euismod sem. Donec a massa eu tortor malesuada sodales in sed lacus. Vestibulum " +
                "vestibulum nisl nec consequat blandit. Aenean condimentum bibendum interdum. Proin lectus justo, rhoncus eget " +
                "ex non, vehicula tempus est. Curabitur mollis maximus luctus. Aliquam tristique felis sed sapien commodo feugiat. " +
                "Cras rutrum massa mi, ac sagittis enim suscipit eget.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash1);

        ContentValues wash2 = new ContentValues();
        wash2.put(COLUMN_WASH_DATE,"17 - 03 - 2022");
        wash2.put(COLUMN_WASH_IS_CLEANSING,0);
        wash2.put(COLUMN_WASH_USED_PEELING,0);
        wash2.put(COLUMN_WASH_USED_OILING,1);
        wash2.put(COLUMN_WASH_DESC,"Sed erat tellus, ornare quis semper sit amet, consequat eget lacus. Suspendisse ipsum enim, " +
                "vestibulum eget quam quis, feugiat scelerisque ipsum. Donec in elementum leo, et eleifend justo. Fusce consectetur" +
                " urna scelerisque risus congue condimentum at in orci. Integer eu purus dictum, lobortis lacus vitae, pellentesque lorem.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash2);

        ContentValues wash3 = new ContentValues();
        wash3.put(COLUMN_WASH_DATE,"23 - 03 - 2022");
        wash3.put(COLUMN_WASH_IS_CLEANSING,1);
        wash3.put(COLUMN_WASH_USED_PEELING,0);
        wash3.put(COLUMN_WASH_USED_OILING,1);
        wash3.put(COLUMN_WASH_DESC,"Quisque tempor tempor sapien, id hendrerit lorem venenatis in. Interdum et malesuada fames ac ante " +
                "ipsum primis in faucibus. Sed volutpat vel mi quis iaculis. Sed et sem faucibus, semper dui ac, sollicitudin massa. Sed" +
                " pharetra elementum augue, in semper justo convallis eu. Sed lobortis at ligula in aliquet. Etiam faucibus massa eget " +
                "mi elementum, dictum blandit dui consectetur.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash3);

        ContentValues wash4 = new ContentValues();
        wash4.put(COLUMN_WASH_DATE,"29 - 03 - 2022");
        wash4.put(COLUMN_WASH_IS_CLEANSING,0);
        wash4.put(COLUMN_WASH_USED_PEELING,0);
        wash4.put(COLUMN_WASH_USED_OILING,0);
        wash4.put(COLUMN_WASH_DESC,"Aliquam laoreet risus neque, vitae rhoncus dolor pulvinar sit amet. Aliquam purus urna, vehicula ac " +
                "tincidunt in, tempor a lorem. Sed id tortor dui. Orci varius natoque penatibus et magnis dis parturient montes, nascetur " +
                "ridiculus mus. Cras vel risus leo. Vestibulum aliquam tincidunt tellus et venenatis.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash4);

        ContentValues wash5 = new ContentValues();
        wash5.put(COLUMN_WASH_DATE,"03 - 04 - 2022");
        wash5.put(COLUMN_WASH_IS_CLEANSING,0);
        wash5.put(COLUMN_WASH_USED_PEELING,1);
        wash5.put(COLUMN_WASH_USED_OILING,1);
        wash5.put(COLUMN_WASH_DESC,"Fusce elementum interdum eros, sit amet pretium arcu sagittis id. Sed velit tellus, venenatis sollicitudin" +
                " condimentum ac, dictum id nulla. Sed faucibus, magna ac feugiat blandit, risus est finibus lorem, ut tincidunt libero magna " +
                "a quam. Sed sagittis molestie ante vel pellentesque.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash5);

        ContentValues wash6 = new ContentValues();
        wash6.put(COLUMN_WASH_DATE,"10 - 04 - 2022");
        wash6.put(COLUMN_WASH_IS_CLEANSING,1);
        wash6.put(COLUMN_WASH_USED_PEELING,0);
        wash6.put(COLUMN_WASH_USED_OILING,0);
        wash6.put(COLUMN_WASH_DESC,"Fusce tristique cursus tellus eu mattis. Pellentesque eu nulla fermentum, bibendum nisl nec, dictum nulla." +
                " Sed erat tellus, ornare quis semper sit amet, consequat eget lacus. Suspendisse ipsum enim, vestibulum eget quam quis," +
                " feugiat scelerisque ipsum. Donec in elementum leo, et eleifend justo.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash6);

        ContentValues wash7 = new ContentValues();
        wash7.put(COLUMN_WASH_DATE,"15 - 04 - 2022");
        wash7.put(COLUMN_WASH_IS_CLEANSING,0);
        wash7.put(COLUMN_WASH_USED_PEELING,1);
        wash7.put(COLUMN_WASH_USED_OILING,1);
        wash7.put(COLUMN_WASH_DESC,"Duis et nisi auctor, vestibulum arcu ac, pellentesque odio. In at massa in ipsum consectetur hendrerit." +
                " Phasellus vehicula, purus nec sollicitudin laoreet, odio libero semper tortor, at egestas lectus risus aliquam arcu. " +
                "Quisque tempor tempor sapien, id hendrerit lorem venenatis in. Interdum et malesuada fames ac ante ipsum primis in faucibus." +
                " Sed volutpat vel mi quis iaculis. Sed et sem faucibus, semper dui ac, sollicitudin massa. Sed pharetra elementum augue, in " +
                "semper justo convallis eu. Sed lobortis at ligula in aliquet. Etiam faucibus massa eget mi elementum, dictum blandit dui" +
                " consectetur.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash7);

        ContentValues wash8 = new ContentValues();
        wash8.put(COLUMN_WASH_DATE,"20 - 04 - 2022");
        wash8.put(COLUMN_WASH_IS_CLEANSING,1);
        wash8.put(COLUMN_WASH_USED_PEELING,1);
        wash8.put(COLUMN_WASH_USED_OILING,1);
        wash8.put(COLUMN_WASH_DESC,"Aliquam purus urna, vehicula ac tincidunt in, tempor a lorem. Sed id tortor dui. Orci varius natoque " +
                "penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras vel risus leo. Vestibulum aliquam tincidunt tellus" +
                " et venenatis. Aenean varius eu tellus sed vehicula. Duis vitae commodo nibh. Donec nec odio sit amet nisi rutrum auctor. " +
                "Quisque posuere blandit aliquam.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash8);

        ContentValues wash9 = new ContentValues();
        wash9.put(COLUMN_WASH_DATE,"24 - 04 - 2022");
        wash9.put(COLUMN_WASH_IS_CLEANSING,0);
        wash9.put(COLUMN_WASH_USED_PEELING,0);
        wash9.put(COLUMN_WASH_USED_OILING,0);
        wash9.put(COLUMN_WASH_DESC,"Vivamus sit amet condimentum magna, non vestibulum felis. Phasellus a dolor eget ex finibus egestas. " +
                "Nam mollis malesuada nisl, a euismod ex vestibulum in. Duis mauris lectus, fringilla ac iaculis quis, tincidunt vel " +
                "tortor. Nunc dictum dui quis ipsum consequat pulvinar. Nam finibus urna massa, nec molestie lorem vehicula tempus. " +
                "Phasellus eget dui rutrum, blandit leo ac, finibus elit. Phasellus laoreet sem eu diam placerat laoreet.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash9);

        ContentValues wash10 = new ContentValues();
        wash10.put(COLUMN_WASH_DATE,"06 - 05 - 2022");
        wash10.put(COLUMN_WASH_IS_CLEANSING,1);
        wash10.put(COLUMN_WASH_USED_PEELING,0);
        wash10.put(COLUMN_WASH_USED_OILING,1);
        wash10.put(COLUMN_WASH_DESC,"Sed vel massa sed quam faucibus varius vel et felis. Pellentesque consequat ultricies ultrices." +
                " Maecenas lacinia dapibus vestibulum. In aliquet placerat tortor eget eleifend. Fusce elementum interdum eros," +
                " sit amet pretium arcu sagittis id. Sed velit tellus, venenatis sollicitudin condimentum ac, dictum id nulla. Sed " +
                "faucibus, magna ac feugiat blandit, risus est finibus lorem, ut tincidunt libero magna a quam. Sed sagittis" +
                " molestie ante vel pellentesque.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash10);

        ContentValues wash11 = new ContentValues();
        wash11.put(COLUMN_WASH_DATE,"07 - 03 - 2022");
        wash11.put(COLUMN_WASH_IS_CLEANSING,0);
        wash11.put(COLUMN_WASH_USED_PEELING,0);
        wash11.put(COLUMN_WASH_USED_OILING,0);
        wash11.put(COLUMN_WASH_DESC,"Maecenas eu auctor felis. Cras lacinia lacinia nibh, sed sodales sapien porttitor a. Pellentesque" +
                " egestas est ac ultrices maximus. Vivamus sit amet condimentum magna, non vestibulum felis. Phasellus a dolor eget ex" +
                " finibus egestas. Nam mollis malesuada nisl, a euismod ex vestibulum in. Duis mauris lectus, fringilla ac iaculis quis," +
                " tincidunt vel tortor.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash11);

        ContentValues wash12 = new ContentValues();
        wash12.put(COLUMN_WASH_DATE,"21 - 03 - 2022");
        wash12.put(COLUMN_WASH_IS_CLEANSING,0);
        wash12.put(COLUMN_WASH_USED_PEELING,0);
        wash12.put(COLUMN_WASH_USED_OILING,1);
        wash12.put(COLUMN_WASH_DESC,"Sed erat tellus, ornare quis semper sit amet, consequat eget lacus. Suspendisse ipsum enim, " +
                "vestibulum eget quam quis, feugiat scelerisque ipsum. Donec in elementum leo, et eleifend justo. Fusce consectetur" +
                " urna scelerisque risus congue condimentum at in orci. Integer eu purus dictum, lobortis lacus vitae, pellentesque lorem.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash12);

        ContentValues wash13 = new ContentValues();
        wash13.put(COLUMN_WASH_DATE,"16 - 03 - 2022");
        wash13.put(COLUMN_WASH_IS_CLEANSING,1);
        wash13.put(COLUMN_WASH_USED_PEELING,0);
        wash13.put(COLUMN_WASH_USED_OILING,1);
        wash13.put(COLUMN_WASH_DESC,"Quisque tempor tempor sapien, id hendrerit lorem venenatis in. Interdum et malesuada fames ac ante " +
                "ipsum primis in faucibus. Sed volutpat vel mi quis iaculis. Sed et sem faucibus, semper dui ac, sollicitudin massa. Sed" +
                " pharetra elementum augue, in semper justo convallis eu. Sed lobortis at ligula in aliquet. Etiam faucibus massa eget " +
                "mi elementum, dictum blandit dui consectetur.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash13);

        ContentValues wash14 = new ContentValues();
        wash14.put(COLUMN_WASH_DATE,"19 - 03 - 2022");
        wash14.put(COLUMN_WASH_IS_CLEANSING,0);
        wash14.put(COLUMN_WASH_USED_PEELING,0);
        wash14.put(COLUMN_WASH_USED_OILING,0);
        wash14.put(COLUMN_WASH_DESC,"Aliquam laoreet risus neque, vitae rhoncus dolor pulvinar sit amet. Aliquam purus urna, vehicula ac " +
                "tincidunt in, tempor a lorem. Sed id tortor dui. Orci varius natoque penatibus et magnis dis parturient montes, nascetur " +
                "ridiculus mus. Cras vel risus leo. Vestibulum aliquam tincidunt tellus et venenatis.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash14);

        ContentValues wash15 = new ContentValues();
        wash15.put(COLUMN_WASH_DATE,"30 - 4 - 2022");
        wash15.put(COLUMN_WASH_IS_CLEANSING,0);
        wash15.put(COLUMN_WASH_USED_PEELING,1);
        wash15.put(COLUMN_WASH_USED_OILING,1);
        wash15.put(COLUMN_WASH_DESC,"Fusce elementum interdum eros, sit amet pretium arcu sagittis id. Sed velit tellus, venenatis sollicitudin" +
                " condimentum ac, dictum id nulla. Sed faucibus, magna ac feugiat blandit, risus est finibus lorem, ut tincidunt libero magna " +
                "a quam. Sed sagittis molestie ante vel pellentesque.");
        sqLiteDatabase.insert(WASH_TABLE, null, wash5);

        /*      Cosmetic - Ingredient     */

        ContentValues cos_ingr1 = new ContentValues();
        cos_ingr1.put(COLUMN_COS_ID,1);
        cos_ingr1.put(COLUMN_INGR_ID,1);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr1);

        ContentValues cos_ingr2 = new ContentValues();
        cos_ingr2.put(COLUMN_COS_ID,1);
        cos_ingr2.put(COLUMN_INGR_ID,2);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr2);

        ContentValues cos_ingr3 = new ContentValues();
        cos_ingr3.put(COLUMN_COS_ID,1);
        cos_ingr3.put(COLUMN_INGR_ID,3);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr3);

        ContentValues cos_ingr4 = new ContentValues();
        cos_ingr4.put(COLUMN_COS_ID,1);
        cos_ingr4.put(COLUMN_INGR_ID,4);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr4);

        ContentValues cos_ingr5 = new ContentValues();
        cos_ingr5.put(COLUMN_COS_ID,1);
        cos_ingr5.put(COLUMN_INGR_ID,5);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr5);

        ContentValues cos_ingr6 = new ContentValues();
        cos_ingr6.put(COLUMN_COS_ID,2);
        cos_ingr6.put(COLUMN_INGR_ID,4);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr6);

        ContentValues cos_ingr7 = new ContentValues();
        cos_ingr7.put(COLUMN_COS_ID,2);
        cos_ingr7.put(COLUMN_INGR_ID,9);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr7);

        ContentValues cos_ingr8 = new ContentValues();
        cos_ingr8.put(COLUMN_COS_ID,2);
        cos_ingr8.put(COLUMN_INGR_ID,6);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr8);

        ContentValues cos_ingr9 = new ContentValues();
        cos_ingr9.put(COLUMN_COS_ID,2);
        cos_ingr9.put(COLUMN_INGR_ID,3);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr9);

        ContentValues cos_ingr10 = new ContentValues();
        cos_ingr10.put(COLUMN_COS_ID,2);
        cos_ingr10.put(COLUMN_INGR_ID,7);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr10);

        ContentValues cos_ingr11 = new ContentValues();
        cos_ingr11.put(COLUMN_COS_ID,2);
        cos_ingr11.put(COLUMN_INGR_ID,12);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr11);

        ContentValues cos_ingr12 = new ContentValues();
        cos_ingr12.put(COLUMN_COS_ID,2);
        cos_ingr12.put(COLUMN_INGR_ID,2);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr12);

        ContentValues cos_ingr13 = new ContentValues();
        cos_ingr13.put(COLUMN_COS_ID,2);
        cos_ingr13.put(COLUMN_INGR_ID,13);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr13);

        ContentValues cos_ingr14 = new ContentValues();
        cos_ingr14.put(COLUMN_COS_ID,3);
        cos_ingr14.put(COLUMN_INGR_ID,15);
        sqLiteDatabase.insert(COSMETIC_INGREDIENT_TABLE, null, cos_ingr14);


        /*      Wash - Cosmetic    */
        ContentValues was_cos1 = new ContentValues();
        was_cos1.put(COLUMN_WAS_ID,1);
        was_cos1.put(COLUMN_COS_ID,5);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos1);

        ContentValues was_cos2 = new ContentValues();
        was_cos2.put(COLUMN_WAS_ID,1);
        was_cos2.put(COLUMN_COS_ID,6);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos2);

        ContentValues was_cos3 = new ContentValues();
        was_cos3.put(COLUMN_WAS_ID,1);
        was_cos3.put(COLUMN_COS_ID,4);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos3);

        ContentValues was_cos4 = new ContentValues();
        was_cos4.put(COLUMN_WAS_ID,1);
        was_cos4.put(COLUMN_COS_ID,8);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos4);

        ContentValues was_cos5 = new ContentValues();
        was_cos5.put(COLUMN_WAS_ID,2);
        was_cos5.put(COLUMN_COS_ID,12);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos5);

        ContentValues was_cos6 = new ContentValues();
        was_cos6.put(COLUMN_WAS_ID,2);
        was_cos6.put(COLUMN_COS_ID,1);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos6);

        ContentValues was_cos7 = new ContentValues();
        was_cos7.put(COLUMN_WAS_ID,2);
        was_cos7.put(COLUMN_COS_ID,8);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos7);

        ContentValues was_cos8 = new ContentValues();
        was_cos8.put(COLUMN_WAS_ID,2);
        was_cos8.put(COLUMN_COS_ID,4);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos8);

        ContentValues was_cos9 = new ContentValues();
        was_cos9.put(COLUMN_WAS_ID,3);
        was_cos9.put(COLUMN_COS_ID,15);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos9);

        ContentValues was_cos10 = new ContentValues();
        was_cos10.put(COLUMN_WAS_ID,3);
        was_cos10.put(COLUMN_COS_ID,2);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos10);

        ContentValues was_cos11 = new ContentValues();
        was_cos11.put(COLUMN_WAS_ID,3);
        was_cos11.put(COLUMN_COS_ID,7);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos11);

        ContentValues was_cos12 = new ContentValues();
        was_cos12.put(COLUMN_WAS_ID,3);
        was_cos12.put(COLUMN_COS_ID,3);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos12);

        ContentValues was_cos13 = new ContentValues();
        was_cos13.put(COLUMN_WAS_ID,3);
        was_cos13.put(COLUMN_COS_ID,11);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos13);

        ContentValues was_cos14 = new ContentValues();
        was_cos14.put(COLUMN_WAS_ID,3);
        was_cos14.put(COLUMN_COS_ID,10);
        sqLiteDatabase.insert(WASH_COSMETIC_TABLE, null, was_cos14);


        /*      SHOP     */

        ContentValues shop1 = new ContentValues();
        shop1.put(COLUMN_SHOP_NAME,"Hebe");
        shop1.put(COLUMN_SHOP_ADDRESS, "Adres Hebe 1");
        shop1.put(COLUMN_SHOP_LAT,50.293429);
        shop1.put(COLUMN_SHOP_LNG,21.458889);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop1);

        ContentValues shop2 = new ContentValues();
        shop2.put(COLUMN_SHOP_NAME,"Hebe");
        shop2.put(COLUMN_SHOP_ADDRESS, "Adres Hebe 2");
        shop2.put(COLUMN_SHOP_LAT,50.306588);
        shop2.put(COLUMN_SHOP_LNG,21.463009);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop2);

        ContentValues shop3 = new ContentValues();
        shop3.put(COLUMN_SHOP_NAME,"Hebe");
        shop3.put(COLUMN_SHOP_ADDRESS, "Adres Hebe 3");
        shop3.put(COLUMN_SHOP_LAT,50.293868);
        shop3.put(COLUMN_SHOP_LNG,21.414257);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop3);

        ContentValues shop4 = new ContentValues();
        shop4.put(COLUMN_SHOP_NAME,"Hebe");
        shop4.put(COLUMN_SHOP_ADDRESS, "Adres Hebe 4");
        shop4.put(COLUMN_SHOP_LAT,50.282242);
        shop4.put(COLUMN_SHOP_LNG,19.444813);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop4);


        ContentValues shop5 = new ContentValues();
        shop5.put(COLUMN_SHOP_NAME,"Natura");
        shop5.put(COLUMN_SHOP_ADDRESS, "Adres Natura 1");
        shop5.put(COLUMN_SHOP_LAT,50.298130);
        shop5.put(COLUMN_SHOP_LNG,21.417793);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop5);

        ContentValues shop6 = new ContentValues();
        shop6.put(COLUMN_SHOP_NAME,"Natura");
        shop6.put(COLUMN_SHOP_ADDRESS, "Adres Natura 2");
        shop6.put(COLUMN_SHOP_LAT,50.280706);
        shop6.put(COLUMN_SHOP_LNG,21.440060);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop6);

        ContentValues shop7 = new ContentValues();
        shop7.put(COLUMN_SHOP_NAME,"Natura");
        shop7.put(COLUMN_SHOP_ADDRESS, "Adres Natura 3");
        shop7.put(COLUMN_SHOP_LAT,50.307684);
        shop7.put(COLUMN_SHOP_LNG,21.426960);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop7);

        ContentValues shop8 = new ContentValues();
        shop8.put(COLUMN_SHOP_NAME,"Natura");
        shop8.put(COLUMN_SHOP_ADDRESS, "Adres Natura 4");
        shop8.put(COLUMN_SHOP_LAT,50.269574);
        shop8.put(COLUMN_SHOP_LNG,21.467472);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop8);


        ContentValues shop9 = new ContentValues();
        shop9.put(COLUMN_SHOP_NAME,"Pigment");
        shop9.put(COLUMN_SHOP_ADDRESS, "Adres Pigment 1");
        shop9.put(COLUMN_SHOP_LAT,50.285533);
        shop9.put(COLUMN_SHOP_LNG,21.405331);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop9);

        ContentValues shop10 = new ContentValues();
        shop10.put(COLUMN_SHOP_NAME,"Pigment");
        shop10.put(COLUMN_SHOP_ADDRESS, "Adres Pigment 2");
        shop10.put(COLUMN_SHOP_LAT,50.292234);
        shop10.put(COLUMN_SHOP_LNG,21.452796);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop10);

        ContentValues shop11 = new ContentValues();
        shop11.put(COLUMN_SHOP_NAME,"Pigment");
        shop11.put(COLUMN_SHOP_ADDRESS, "Adres Pigment 3");
        shop11.put(COLUMN_SHOP_LAT,50.304236);
        shop11.put(COLUMN_SHOP_LNG,21.449388);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop11);


        ContentValues shop12 = new ContentValues();
        shop12.put(COLUMN_SHOP_NAME,"Rossmann");
        shop12.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 1");
        shop12.put(COLUMN_SHOP_LAT,50.276183);
        shop12.put(COLUMN_SHOP_LNG,21.448902);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop12);

        ContentValues shop13 = new ContentValues();
        shop13.put(COLUMN_SHOP_NAME,"Rossmann");
        shop13.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 2");
        shop13.put(COLUMN_SHOP_LAT,50.02438);
        shop13.put(COLUMN_SHOP_LNG,19.8342);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop13);

        ContentValues shop14 = new ContentValues();
        shop14.put(COLUMN_SHOP_NAME,"Rossmann");
        shop14.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 3");
        shop14.put(COLUMN_SHOP_LAT,50.280048);
        shop14.put(COLUMN_SHOP_LNG,19.481205);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop14);

        ContentValues shop15 = new ContentValues();
        shop15.put(COLUMN_SHOP_NAME,"Rossmann");
        shop15.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 4");
        shop15.put(COLUMN_SHOP_LAT,50.09643);
        shop15.put(COLUMN_SHOP_LNG,19.8234);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop15);

        ContentValues shop16 = new ContentValues();
        shop16.put(COLUMN_SHOP_NAME,"Rossmann");
        shop16.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 5");
        shop16.put(COLUMN_SHOP_LAT,50.09432);
        shop16.put(COLUMN_SHOP_LNG,19.8243);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop16);

        ContentValues shop17 = new ContentValues();
        shop17.put(COLUMN_SHOP_NAME,"Rossmann");
        shop17.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 6");
        shop17.put(COLUMN_SHOP_LAT,50.308780);
        shop17.put(COLUMN_SHOP_LNG,21.456829);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop17);

        ContentValues shop18 = new ContentValues();
        shop18.put(COLUMN_SHOP_NAME,"Rossmann");
        shop18.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 7");
        shop18.put(COLUMN_SHOP_LAT,50.296061);
        shop18.put(COLUMN_SHOP_LNG,21.435886);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop18);

        ContentValues shop19 = new ContentValues();
        shop19.put(COLUMN_SHOP_NAME,"Rossmann");
        shop19.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 8");
        shop19.put(COLUMN_SHOP_LAT,50.277635);
        shop19.put(COLUMN_SHOP_LNG,21.482921);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop19);

        ContentValues shop20 = new ContentValues();
        shop20.put(COLUMN_SHOP_NAME,"Rossmann");
        shop20.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 9");
        shop20.put(COLUMN_SHOP_LAT,50.07654);
        shop20.put(COLUMN_SHOP_LNG,19.9236);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop20);

        ContentValues shop21 = new ContentValues();
        shop21.put(COLUMN_SHOP_NAME,"Rossmann");
        shop21.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 10");
        shop21.put(COLUMN_SHOP_LAT,50.312069);
        shop21.put(COLUMN_SHOP_LNG,21.440693);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop21);

        ContentValues shop22 = new ContentValues();
        shop22.put(COLUMN_SHOP_NAME,"Rossmann");
        shop22.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 11");
        shop22.put(COLUMN_SHOP_LAT,50.0);
        shop22.put(COLUMN_SHOP_LNG,19.9);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop22);

        ContentValues shop23 = new ContentValues();
        shop23.put(COLUMN_SHOP_NAME,"Rossmann");
        shop23.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 12");
        shop23.put(COLUMN_SHOP_LAT,50.277854);
        shop23.put(COLUMN_SHOP_LNG,21.4557299);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop23);

        ContentValues shop24 = new ContentValues();
        shop24.put(COLUMN_SHOP_NAME,"Rossmann");
        shop24.put(COLUMN_SHOP_ADDRESS, "Adres Rossmann 13");
        shop24.put(COLUMN_SHOP_LAT,50.025843);
        shop24.put(COLUMN_SHOP_LNG,19.832432);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop24);


        ContentValues shop25 = new ContentValues();
        shop25.put(COLUMN_SHOP_NAME,"Ziaja dla Ciebie");
        shop25.put(COLUMN_SHOP_ADDRESS, "Adres Ziaja dla Ciebie 1");
        shop25.put(COLUMN_SHOP_LAT,50.305942);
        shop25.put(COLUMN_SHOP_LNG,21.432408);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop25);

        ContentValues shop26 = new ContentValues();
        shop26.put(COLUMN_SHOP_NAME,"Ziaja dla Ciebie");
        shop26.put(COLUMN_SHOP_ADDRESS, "Adres Ziaja dla Ciebie 2");
        shop26.put(COLUMN_SHOP_LAT,50.295184);
        shop26.put(COLUMN_SHOP_LNG,21.475712);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop26);

        ContentValues shop27 = new ContentValues();
        shop27.put(COLUMN_SHOP_NAME,"Ziaja dla Ciebie");
        shop27.put(COLUMN_SHOP_ADDRESS, "Adres Ziaja dla Ciebie 3");
        shop27.put(COLUMN_SHOP_LAT,50.279390);
        shop27.put(COLUMN_SHOP_LNG,21.470219);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop27);

        ContentValues shop28 = new ContentValues();
        shop28.put(COLUMN_SHOP_NAME,"Ziaja dla Ciebie");
        shop28.put(COLUMN_SHOP_ADDRESS, "Adres Ziaja dla Ciebie 4");
        shop28.put(COLUMN_SHOP_LAT,50.307903);
        shop28.put(COLUMN_SHOP_LNG,21.481892);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop28);

        ContentValues shop29 = new ContentValues();
        shop29.put(COLUMN_SHOP_NAME,"Ziaja dla Ciebie");
        shop29.put(COLUMN_SHOP_ADDRESS, "Adres Ziaja dla Ciebie 5");
        shop29.put(COLUMN_SHOP_LAT,50.269077);
        shop29.put(COLUMN_SHOP_LNG,21.481892);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop29);

        ContentValues shop30 = new ContentValues();
        shop30.put(COLUMN_SHOP_NAME,"Ziaja dla Ciebie");
        shop30.put(COLUMN_SHOP_ADDRESS, "Adres Ziaja dla Ciebie 6");
        shop30.put(COLUMN_SHOP_LAT,50.278512);
        shop30.put(COLUMN_SHOP_LNG,21.437260);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop30);

        ContentValues shop31 = new ContentValues();
        shop31.put(COLUMN_SHOP_NAME,"Ziaja dla Ciebie");
        shop31.put(COLUMN_SHOP_ADDRESS, "Adres Ziaja dla Ciebie 7");
        shop31.put(COLUMN_SHOP_LAT,50.313604);
        shop31.put(COLUMN_SHOP_LNG,21.498371);
        sqLiteDatabase.insert(SHOP_TABLE, null, shop31);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //--------------------------------------- INGREDIENT METHODS ------------------------------------------------

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

    public HashMap<Integer, String> getAllIngredientsNameAndID() {

        HashMap<Integer, String> returnHM = new HashMap<>();

        String queryString = "SELECT * FROM " + INGREDIENT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                int ingredientID = cursor.getInt(0);
                String ingredientName = cursor.getString(1);

                returnHM.put(ingredientID,ingredientName);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnHM;
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

    public boolean updateIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INGREDIENT_NAME, ingredient.getName());
        contentValues.put(COLUMN_INGREDIENT_TYPE, ingredient.getType());
        contentValues.put(COLUMN_INGREDIENT_DESC, ingredient.getDescription());

        String[] ingredientID = new String[]{String.valueOf(ingredient.getId())};

        long update = db.update(INGREDIENT_TABLE, contentValues,   COLUMN_INGREDIENT_ID + " = ? ", ingredientID);

        if (update == -1)
            return false;
        else
            return true;
    }

    public boolean deleteIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + INGREDIENT_TABLE + " WHERE " + COLUMN_INGREDIENT_ID + " = " + ingredient.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        deleteIngredientInAllCosmetics(ingredient.getId());

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    //--------------------------------------- WASH METHODS ------------------------------------------------

    public long addWash(Wash wash) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WASH_DATE, wash.getDate());
        cv.put(COLUMN_WASH_IS_CLEANSING, wash.isCleansing());
        cv.put(COLUMN_WASH_USED_PEELING, wash.isUsedPeeling());
        cv.put(COLUMN_WASH_USED_OILING, wash.isUsedOiling());
        cv.put(COLUMN_WASH_DESC, wash.getDescription());

        long insert = db.insert(WASH_TABLE, null, cv);

        return insert;
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

            ArrayList<Integer> cosmeticList = getAllWashCosmeticsIDs(washID);

            Wash returnWash= new Wash(washID, washDate, washIsCleansing, washUsedPeeling, washUsedOiling, washDesc, cosmeticList);
            cursor.close();
            db.close();
            return returnWash;
        }else{
            cursor.close();
            db.close();
            return null;
        }
    }

    public boolean updateWash(Wash wash) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WASH_DATE, wash.getDate());
        contentValues.put(COLUMN_WASH_IS_CLEANSING, wash.isCleansing());
        contentValues.put(COLUMN_WASH_USED_PEELING, wash.isUsedPeeling());
        contentValues.put(COLUMN_WASH_USED_OILING, wash.isUsedOiling());
        contentValues.put(COLUMN_WASH_DESC, wash.getDescription());

        String[] washID = new String[]{String.valueOf(wash.getId())};

        long update = db.update(WASH_TABLE, contentValues,   COLUMN_WASH_ID + " = ? ", washID);

        if (update == -1)
            return false;
        else
            return true;
    }

    public boolean deleteWash(Wash wash) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + WASH_TABLE + " WHERE " + COLUMN_WASH_ID + " = " + wash.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        deleteAllCosmeticsInWash(wash.getId());

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    //--------------------------------------- COSMETIC METHODS ------------------------------------------------

    public long addCosmetic(Cosmetic cosmetic) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COSMETIC_NAME, cosmetic.getName());
        cv.put(COLUMN_COSMETIC_BRAND, cosmetic.getBrand());
        cv.put(COLUMN_COSMETIC_PEHTYPE, cosmetic.getPehType());
        cv.put(COLUMN_COSMETIC_COSMETICTYPE, cosmetic.getCosmeticType());
        cv.put(COLUMN_COSMETIC_DESC, cosmetic.getDescription());
        cv.put(COLUMN_COSMETIC_IMGPATH, cosmetic.getImgPath());

        long insert = db.insert(COSMETIC_TABLE, null, cv);

        return insert;
    }

    public ArrayList<Cosmetic> getAllCosmetics() {

        ArrayList<Cosmetic> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + COSMETIC_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                int cosmeticID = cursor.getInt(0);
                String cosmeticName = cursor.getString(1);
                String cosmeticBrand = cursor.getString(2);
                String cosmeticPehType = cursor.getString(3);
                String cosmeticCosmeticType = cursor.getString(4);
                String cosmeticDesc = cursor.getString(5);
                String cosmeticImgPath = cursor.getString(6);

                Cosmetic newCosmetic= new Cosmetic(cosmeticID, cosmeticName, cosmeticBrand, cosmeticPehType, cosmeticCosmeticType, cosmeticDesc, cosmeticImgPath);
                returnAList.add(newCosmetic);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public HashMap<Integer, String> getAllCosmeticsNameAndID() {

        HashMap<Integer, String> returnHM = new HashMap<>();

        String queryString = "SELECT * FROM " + COSMETIC_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                int cosmeticID = cursor.getInt(0);
                String cosmeticName = cursor.getString(1);

                returnHM.put(cosmeticID,cosmeticName);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnHM;
    }

    public Cosmetic findCosmetic(int cosmeticID) {

        String queryString = "SELECT * FROM " + COSMETIC_TABLE + " WHERE " + COLUMN_COSMETIC_ID + "=" + cosmeticID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            String cosmeticName = cursor.getString(1);
            String cosmeticBrand = cursor.getString(2);
            String cosmeticPehType = cursor.getString(3);
            String cosmeticCosmeticType = cursor.getString(4);
            String cosmeticDesc = cursor.getString(5);
            String cosmeticImgPath = cursor.getString(6);

            ArrayList<Integer> inciList = getAllCosmeticIngredientsIDs(cosmeticID);
            ArrayList<String> shopList = getAllShopBrandsFromCosmetic(cosmeticID);

            Cosmetic returnCosmetic= new Cosmetic(cosmeticID, cosmeticName, cosmeticBrand, cosmeticPehType, cosmeticCosmeticType, cosmeticDesc, cosmeticImgPath, inciList, shopList);
            cursor.close();
            db.close();
            return returnCosmetic;
        }else{
            cursor.close();
            db.close();
            return null;
        }
    }

    public boolean updateCosmetic(Cosmetic cosmetic) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COSMETIC_NAME, cosmetic.getName());
        contentValues.put(COLUMN_COSMETIC_BRAND, cosmetic.getBrand());
        contentValues.put(COLUMN_COSMETIC_PEHTYPE, cosmetic.getPehType());
        contentValues.put(COLUMN_COSMETIC_COSMETICTYPE, cosmetic.getCosmeticType());
        contentValues.put(COLUMN_COSMETIC_DESC, cosmetic.getDescription());
        contentValues.put(COLUMN_COSMETIC_IMGPATH, cosmetic.getImgPath());

        String[] cosmeticID = new String[]{String.valueOf(cosmetic.getId())};

        long update = db.update(COSMETIC_TABLE, contentValues,   COLUMN_COSMETIC_ID + " = ? ", cosmeticID);

        if (update == -1)
            return false;
        else
            return true;
    }


    public boolean deleteCosmetic(Cosmetic cosmetic) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + COSMETIC_TABLE + " WHERE " + COLUMN_COSMETIC_ID + " = " + cosmetic.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        deleteAllIngredientsInCosmetic(cosmetic.getId());
        deleteAllShopsInCosmetic(cosmetic.getId());
        deleteCosmeticInAllWashes(cosmetic.getId());

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    //--------------------------------------- COSMETIC-INGREDIENT METHODS ------------------------------------------------

    public boolean addCosmeticIngredient(int cosmeticID, int ingredientID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COS_ID, cosmeticID);
        cv.put(COLUMN_INGR_ID, ingredientID);

        long insert = db.insert(COSMETIC_INGREDIENT_TABLE, null, cv);

        if (insert == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Integer> getAllCosmeticIngredientsIDs(int cosmeticID) {

        ArrayList<Integer> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + COSMETIC_INGREDIENT_TABLE + " WHERE " + COLUMN_COS_ID + " = " + cosmeticID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
                do{
                    int ingredientID = cursor.getInt(2);
                    returnAList.add(ingredientID);

                }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public boolean deleteAllIngredientsInCosmetic(int cosmeticID) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + COSMETIC_INGREDIENT_TABLE + " WHERE " + COLUMN_COS_ID + " = " + cosmeticID;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteIngredientInAllCosmetics(int ingredientID) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + COSMETIC_INGREDIENT_TABLE + " WHERE " + COLUMN_INGR_ID + " = " + ingredientID;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    //--------------------------------------- WASH-COSMETIC METHODS ------------------------------------------------

    public boolean addWashCosmetic(int washID, int cosmeticID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WAS_ID, washID);
        cv.put(COLUMN_COS_ID, cosmeticID);

        long insert = db.insert(WASH_COSMETIC_TABLE, null, cv);

        if (insert == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Integer> getAllWashCosmeticsIDs(int washID) {

        ArrayList<Integer> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + WASH_COSMETIC_TABLE+ " WHERE " + COLUMN_WAS_ID + " = " + washID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int cosmeticID = cursor.getInt(2);
                returnAList.add(cosmeticID);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public boolean deleteAllCosmeticsInWash(int washID) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + WASH_COSMETIC_TABLE + " WHERE " + COLUMN_WAS_ID + " = " + washID;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteCosmeticInAllWashes(int cosmeticID) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + WASH_COSMETIC_TABLE + " WHERE " + COLUMN_COS_ID + " = " + cosmeticID;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    //--------------------------------------- SHOP_BRANDS METHODS ------------------------------------------------


    /*public ArrayList<ShopBrand> getAllShopBrands() {

        ArrayList<ShopBrand> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + SHOP_BRAND_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                int brandID = cursor.getInt(0);
                String brandName = cursor.getString(1);

                ShopBrand newShopBrand = new ShopBrand(brandID,brandName);
                returnAList.add(newShopBrand);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }*/

    //--------------------------------------- SHOP METHODS ------------------------------------------------


    public ArrayList<Shop> getAllShops() {

        ArrayList<Shop> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + SHOP_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                int shopID = cursor.getInt(0);
                String shopName = cursor.getString(1);
                String shopAddress = cursor.getString(2);
                double shopLat = cursor.getDouble(3);
                double shopLng = cursor.getDouble(4);

                Shop newShop = new Shop(shopID, shopName, shopAddress, shopLat, shopLng);
                returnAList.add(newShop);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public Shop findShop(int shopID) {

        String queryString = "SELECT * FROM " + SHOP_TABLE + " WHERE " + COLUMN_SHOP_ID + "=" + shopID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            String shopName = cursor.getString(1);
            String shopAddress = cursor.getString(2);
            double shopLat = cursor.getDouble(3);
            double shopLng = cursor.getDouble(4);


            Shop returnShop= new Shop(shopID, shopName, shopAddress, shopLat, shopLng);
            cursor.close();
            db.close();
            return returnShop;
        }else{
            cursor.close();
            db.close();
            return null;
        }
    }

    public ArrayList<Shop> getAllShopsFromOneBrand(String brandName) {

        ArrayList<Shop> returnAList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SHOP_TABLE WHERE SHOP_NAME = ?; ", new String[] {brandName});

        if (cursor.moveToFirst()) {
            do{
                int shopID = cursor.getInt(0);
                String shopName = cursor.getString(1);
                String shopAddress = cursor.getString(2);
                double shopLat = cursor.getDouble(3);
                double shopLng = cursor.getDouble(4);

                Shop newShop = new Shop(shopID, shopName, shopAddress, shopLat, shopLng);
                returnAList.add(newShop);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public int getNumberOfAllShopsFromOneBrand(String brandName) {

        int returnNumber = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SHOP_TABLE WHERE SHOP_NAME = ?; ", new String[] {brandName});

        if (cursor.moveToFirst()) {
            do{
                returnNumber = returnNumber + 1;

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnNumber;
    }

    //--------------------------------------- SHOP_BRANDS METHODS ------------------------------------------------

    public boolean addShopCosmetic(int cosmeticID, String shopBrandName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COS_ID, cosmeticID);
        cv.put(COLUMN_SHOP_BRAND_NAME, shopBrandName);

        long insert = db.insert(SHOP_COSMETIC_TABLE, null, cv);

        if (insert == -1)
            return false;
        else
            return true;
    }

    public ArrayList<String> getAllShopBrandsFromCosmetic(int cosmeticID) {

        ArrayList<String> returnAList = new ArrayList<>();

        String queryString = "SELECT * FROM " + SHOP_COSMETIC_TABLE + " WHERE " + COLUMN_COS_ID + " = " + cosmeticID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do{
                String shopBrandName = cursor.getString(2);
                returnAList.add(shopBrandName);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnAList;
    }

    public boolean deleteAllShopsInCosmetic(int cosmeticID) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + SHOP_COSMETIC_TABLE + " WHERE " + COLUMN_COS_ID + " = " + cosmeticID;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }





}
