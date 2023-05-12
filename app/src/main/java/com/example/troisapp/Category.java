package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Category extends AppCompatActivity implements View.OnClickListener{
    ImageButton clothing;
    ImageButton electronic;
    ImageButton book;
    ImageButton makeup;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        clothing = findViewById(R.id.button_clothing);
        electronic = findViewById(R.id.button_electronic);
        book = findViewById(R.id.button_book);
        makeup = findViewById(R.id.button_makeup);

        clothing.setOnClickListener(this);
        electronic.setOnClickListener(this);
        book.setOnClickListener(this);
        makeup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_clothing){
            this.type = "Produk/Clothing";
            Intent intent = new Intent(Category.this, ProductList.class);
            startActivity(intent);
        }else if(view.getId() == R.id.button_electronic){
            this.type = "electronic";
            Intent intent = new Intent(Category.this, ProductList.class);
            startActivity(intent);
        }else if(view.getId() == R.id.button_book){
            this.type = "book";
            Intent intent = new Intent(Category.this, ProductList.class);
            startActivity(intent);
        }else if(view.getId() == R.id.button_makeup){
            this.type = "makeup";
            Intent intent = new Intent(Category.this, ProductList.class);
            startActivity(intent);
        }
    }

    public String getType(){
        if(this.type == "clothing"){
            return "Produk/Clothing";
        }
        else if(this.type == "electronic"){
            return "Produk/Elektronik";
        }else{
            return this.type;
        }
    }
}