package com.example.caffycart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
    }

    private void createOrderSummaryAndPayment(){
        OrderValues order = (OrderValues) getIntent().getSerializableExtra("Summary");
        TextView textView = findViewById(R.id.order_summary_text_view);

        String summary="Name: " + order.name;
        summary+="\nSelected Coffee Type: " + order.coffeeType;
        summary+="\nSelected Cup Size: " + order.cupSize;
        summary +=  "\nQuantity: " + order.quantity;
        summary+=   "\nTotal: "+ order.price + " Rs";
        summary+=  "\nThank You :) for using Caffy Cart!";

        textView.setText(summary);
    }

}