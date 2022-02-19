package com.example.caffycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class OrderCreatingActivity extends AppCompatActivity {

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creating);
        // This will remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        setupCoffeeList();
        setupOzSpinner();
        setupCoffeeTypeSpinner();
        Button showOrder = findViewById(R.id.showOrderSummary);
        showOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent summaryPage = new Intent(OrderCreatingActivity.this, OrderSummaryActivity.class);
                OrderValues summary = generateSummary();
                summaryPage.putExtra("Summary", summary);
                startActivity(summaryPage);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(OrderCreatingActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderCreatingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void inc(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        display(quantity);


    }

    public void dec(View view) {
        if (quantity == 1) {
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void setupCoffeeList() {
        RecyclerView list = findViewById(R.id.recycler_view_coffee_list);

        CoffeeBucket[] coffees = {
                new CoffeeBucket("Affogato Coffee", "Price : 60 Rs", R.drawable.affogatocoffee),
                new CoffeeBucket("Americano Coffee", "Price : 60 Rs", R.drawable.americanocoffee),
                new CoffeeBucket("Black Coffee", "Price : 60 Rs", R.drawable.blackcofee),
                new CoffeeBucket("Cappuccino Coffee", "Price : 60 Rs", R.drawable.cappuccinocoffee),
                new CoffeeBucket("Cortado Coffee", "Price : 60 Rs", R.drawable.cortadocoffee),
                new CoffeeBucket("Doppio Coffee", "Price : 60 Rs", R.drawable.doppiocoffee),
                new CoffeeBucket("Espresso Coffee", "Price : 60 Rs", R.drawable.espressocoffee),
                new CoffeeBucket("Flat White Coffee", "Price : 60 Rs", R.drawable.flat_whitecoffee),
                new CoffeeBucket("Galão Coffee", "Price : 60 Rs", R.drawable.gal_ocoffee),
                new CoffeeBucket("Irish Coffee", "Price : 60 Rs", R.drawable.irishcoffee),
                new CoffeeBucket("Latte Coffee", "Price : 60 Rs", R.drawable.lattecofee),
                new CoffeeBucket("Lungo Coffee", "Price : 60 Rs", R.drawable.lungocoffee),
                new CoffeeBucket("Macchiato Coffee", "Price : 60 Rs", R.drawable.macchiatocoffee),
                new CoffeeBucket("Mocha Coffee", "Price : 60 Rs", R.drawable.mochacoffee),
                new CoffeeBucket("Red Eye Coffee", "Price : 60 Rs", R.drawable.red_eyecoffee)

        };

        BucketAdapter adapter = new BucketAdapter(coffees);
        list.setAdapter(adapter);
    }

    private void setupOzSpinner() {
        Spinner cupSpinner = findViewById(R.id.spinner_oz_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cup_size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cupSpinner.setAdapter(adapter);
    }

    private void setupCoffeeTypeSpinner() {
        Spinner cupSpinner = findViewById(R.id.spinner_coffee_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.coffee_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cupSpinner.setAdapter(adapter);
    }


    private OrderValues generateSummary() {
        CheckBox WhippedCreamCheckBox = findViewById(R.id.Whipped_cream_checkBox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.Chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        Spinner coffeeTypeSpinner = findViewById(R.id.spinner_coffee_select);
        String coffeeType = coffeeTypeSpinner.getSelectedItem().toString();
        Spinner coffeeCupSpinner = findViewById(R.id.spinner_oz_select);
        String coffeeCup = coffeeCupSpinner.getSelectedItem().toString();

        String price = "" + calculatePrice(hasWhippedCream, hasChocolate, coffeeType, coffeeCup);
        return new OrderValues(name, hasWhippedCream, hasChocolate, coffeeType, coffeeCup, "" + quantity, price);
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, String coffeeType, String cupSize) {
        int basePrice = 5;
        double cupPrice = 5;
        int coffeePrice = 20;
        switch (cupSize) {
            case "3oz":
                cupPrice = cupPrice * 1.5;
                break;
            case "4.5oz":
                cupPrice = cupPrice * 1.7;
                break;
            case "5.5oz":
                cupPrice = cupPrice * 1.9;
                break;
            case "12oz":
                cupPrice = cupPrice * 2;
                break;
            case "14oz":
                cupPrice = cupPrice * 2.3;
                break;
            case "16oz":
                cupPrice = cupPrice * 2.4;
                break;
            default:
                cupPrice = cupPrice * 2.5;
                break;
        }

        switch (coffeeType) {
            case "Affogato":
                coffeePrice = coffeePrice * 3;
                break;
            case "Americano":
                coffeePrice = coffeePrice * 3;
                break;
            case "Black":
                coffeePrice = coffeePrice * 2;
                break;
            case "Cappuccino":
                coffeePrice = coffeePrice * 2;
                break;
            case "Cortado":
                coffeePrice = coffeePrice * 3;
                break;
            case "Doppio":
                coffeePrice = coffeePrice * 3;
                break;

            case "Espresso":
                coffeePrice = coffeePrice * 4;
                break;
            case "Flat White":
                coffeePrice = coffeePrice * 2;
                break;
            case "Galão":
                coffeePrice = coffeePrice * 4;
                break;
            case "Irish":
                coffeePrice = coffeePrice * 5;
                break;
            case "Latte":
                coffeePrice = coffeePrice * 3;
                break;
            case "Lungo":
                coffeePrice = coffeePrice * 5;
                break;
            case "Macchiato":
                coffeePrice = coffeePrice * 5;
                break;
            case "Mocha":
                coffeePrice = coffeePrice * 3;
                break;
            default:
                coffeePrice = coffeePrice * 3;
                break;
        }


        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return (int) (quantity * basePrice + coffeePrice + cupPrice);
    }


}