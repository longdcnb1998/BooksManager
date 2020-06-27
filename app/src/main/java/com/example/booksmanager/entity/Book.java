package com.example.booksmanager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_books",foreignKeys = @ForeignKey(entity = Category.class,parentColumns = "id",childColumns = "catId"),indices = {@Index(value = "catId")})
public class Book {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "catId")
    private int catId;
    @ColumnInfo(name =  "name")
    private String name;
    @ColumnInfo(name = "des")
    private String des;


    public Book(int catId, String name, String des) {
        this.catId = catId;
        this.name = name;
        this.des = des;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
