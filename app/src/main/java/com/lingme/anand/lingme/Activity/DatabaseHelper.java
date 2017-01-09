package com.lingme.anand.lingme.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lingme.anand.lingme.Activity.Pojo.BasList;
import com.lingme.anand.lingme.Activity.Pojo.FavList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 22/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    ArrayList<FavList> listProducts = new ArrayList<>();
    ArrayList<BasList> listProductsbas = new ArrayList<>();
    public static final String DATABASE_NAME = "osaapasaa.db";
    public static final String TABLE_NAME_FAV = "favourite";
    public static final String TABLE_NAME_BAS = "basket";
    public static final String PRODUCT_ID = "product_id";
    public static final String BRAND = "brand";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String STOCK = "stock";
    public static final String TABL_NAME = "table_name";
    public static final String IMAGE = "image";
    public static final String SIZE = "size";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME_FAV + "("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PRODUCT_ID + " TEXT," + BRAND
                + " TEXT,"
                + NAME + " TEXT," + PRICE + " INTEGER ,"
                + DESCRIPTION + " TEXT,"
                + STOCK + " TEXT,"
                + SIZE + " TEXT,"
                + TABL_NAME + " TEXT,"
                + IMAGE + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + TABLE_NAME_BAS + "("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PRODUCT_ID + " TEXT," + BRAND
                + " TEXT,"
                + NAME + " TEXT," + PRICE + " INTEGER ,"
                + DESCRIPTION + " TEXT,"
                + STOCK + " TEXT,"
                + SIZE + " TEXT,"
                + TABL_NAME + " TEXT,"
                + IMAGE + " TEXT" + ")");
        listProducts = new ArrayList<>();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME_FAV);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME_BAS);
        onCreate(db);
    }

    public boolean insert(String product_id, String brand, String name, int price, String description, String stock, String table_name, String image, String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", (byte[]) null);
        contentValues.put(PRODUCT_ID, product_id);
        contentValues.put(BRAND, brand);
        contentValues.put(NAME, name);
        contentValues.put(PRICE, price);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(STOCK, stock);
        contentValues.put(SIZE, size);
        contentValues.put(TABL_NAME, table_name);
        contentValues.put(IMAGE, image);

        long result = db.insert(TABLE_NAME_FAV, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public ArrayList<FavList> getProducts(int page) {
        int itemPerPage = 4;
        int offset = (page - 1) * itemPerPage;
        int i = 0;
        String query = ("SELECT * FROM " + TABLE_NAME_FAV + " LIMIT " + offset + "," + itemPerPage);

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
                FavList product = new FavList();
                product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                product.setProductId(cursor.getString(cursor.getColumnIndex(PRODUCT_ID)));
                product.setBrand(cursor.getString(cursor.getColumnIndex(BRAND)));
                product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                product.setPrice(cursor.getInt(cursor.getColumnIndex(PRICE)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                product.setStock(cursor.getString(cursor.getColumnIndex(STOCK)));
                product.setTable_name(cursor.getString(cursor.getColumnIndex(TABL_NAME)));
                product.setImg1(cursor.getString(cursor.getColumnIndex(IMAGE)));
                product.setSize(cursor.getString(cursor.getColumnIndex(SIZE)));
                listProducts.add(product);
        }
        return listProducts;
    }

    public boolean insertBas(String product_id, String brand, String name, int price, String description, String stock, String table_name, String image, String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", (byte[]) null);
        contentValues.put("product_id", product_id);
        contentValues.put("brand", brand);
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("description", description);
        contentValues.put("stock", stock);
        contentValues.put("size", size);
        contentValues.put("table_name", table_name);
        contentValues.put("image", image);

        long result = db.insert(TABLE_NAME_BAS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<BasList> getProductsBas(int page) {
        int itemPerPage = 4;
        int offset = (page - 1) * itemPerPage;
        String query = ("SELECT * FROM " + TABLE_NAME_BAS + " LIMIT " + offset + "," + itemPerPage);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            BasList product = new BasList();
            product.setId(cursor.getInt(cursor.getColumnIndex("id")));
            product.setProductId(cursor.getString(cursor.getColumnIndex(PRODUCT_ID)));
            product.setBrand(cursor.getString(cursor.getColumnIndex(BRAND)));
            product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            product.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRICE))));
            product.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            product.setStock(cursor.getString(cursor.getColumnIndex(STOCK)));
            product.setTable_name(cursor.getString(cursor.getColumnIndex(TABL_NAME)));
            product.setImg1(cursor.getString(cursor.getColumnIndex(IMAGE)));
            product.setSize(cursor.getString(cursor.getColumnIndex(SIZE)));
            Log.i("error",cursor.getString(cursor.getColumnIndex(PRODUCT_ID))+cursor.getString(cursor.getColumnIndex(IMAGE)));
            listProductsbas.add(product);
        }
        return listProductsbas;
    }

    public ArrayList<BasList> getAllProducts() {

        String query = ("SELECT * FROM "+TABLE_NAME_BAS);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            BasList product = new BasList();
            product.setId(cursor.getInt(cursor.getColumnIndex("id")));
            product.setProductId(cursor.getString(cursor.getColumnIndex(PRODUCT_ID)));
            product.setBrand(cursor.getString(cursor.getColumnIndex(BRAND)));
            product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            product.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRICE))));
            product.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            product.setStock(cursor.getString(cursor.getColumnIndex(STOCK)));
            product.setTable_name(cursor.getString(cursor.getColumnIndex(TABL_NAME)));
            product.setImg1(cursor.getString(cursor.getColumnIndex(IMAGE)));
            product.setSize(cursor.getString(cursor.getColumnIndex(SIZE)));
            Log.i("error",cursor.getString(cursor.getColumnIndex(PRODUCT_ID))+cursor.getString(cursor.getColumnIndex(IMAGE)));
            listProductsbas.add(product);
        }
        return listProductsbas;
    }

    public ArrayList<FavList> getAllProductsFav() {

        String query = ("SELECT * FROM "+TABLE_NAME_FAV);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            FavList product = new FavList();
            product.setId(cursor.getInt(cursor.getColumnIndex("id")));
            product.setProductId(cursor.getString(cursor.getColumnIndex(PRODUCT_ID)));
            product.setBrand(cursor.getString(cursor.getColumnIndex(BRAND)));
            product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            product.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRICE))));
            product.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            product.setStock(cursor.getString(cursor.getColumnIndex(STOCK)));
            product.setTable_name(cursor.getString(cursor.getColumnIndex(TABL_NAME)));
            product.setImg1(cursor.getString(cursor.getColumnIndex(IMAGE)));
            product.setSize(cursor.getString(cursor.getColumnIndex(SIZE)));
            Log.i("error",cursor.getString(cursor.getColumnIndex(PRODUCT_ID))+cursor.getString(cursor.getColumnIndex(IMAGE)));
            listProducts.add(product);
        }
        return listProducts;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_BAS,null,null);
        db.close();
    }

}
