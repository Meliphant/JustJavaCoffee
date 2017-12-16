/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 *
 */

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //check the checkboxes state
        boolean isWhippedCream = whippedCreamCheckBox();
        boolean isChocolate = chocolateCheckBox();

        //work with user name
        EditText getName = (EditText) findViewById(R.id.name);
        String customerName = getName.getText().toString();

        int price = calculatePrice(isWhippedCream, isChocolate);
        String priceMessage = createOrderSummary(customerName, price, isWhippedCream, isChocolate);
        composeEmail("Just Java Order App", priceMessage);
        //displayMessage(priceMessage);
    }

    private Boolean whippedCreamCheckBox () {
        CheckBox whippedCreamState = (CheckBox) findViewById(R.id.whipped_cream_check);
        return whippedCreamState.isChecked();
    }
    private Boolean chocolateCheckBox () {
        CheckBox chocolateState = (CheckBox) findViewById(R.id.chocolate_check);
        return chocolateState.isChecked();
    }

    public void checkBox(View view){
        Boolean isWhippedCream = whippedCreamCheckBox();
        Boolean isChocolate = chocolateCheckBox();
        int price = calculatePrice (isWhippedCream, isChocolate);
        String priceMessage = "$" + price;
        displayMessage(priceMessage);
    }

    public void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(String customerName, int price, boolean isWhippedCream, boolean isChocolate){
      return "Name: " + customerName + "\nAdd whipped cream? " + isWhippedCream + "\nAdd chocolate? " + isChocolate + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";
    }
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        boolean isWhippedCream = whippedCreamCheckBox();
        boolean isChocolate = chocolateCheckBox();
        int price = calculatePrice (isWhippedCream, isChocolate);
        String priceMessage = "$" + price;
        display(quantity);
        displayMessage(priceMessage);
    }
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        boolean isWhippedCream = whippedCreamCheckBox();
        boolean isChocolate = chocolateCheckBox();
        int price = calculatePrice (isWhippedCream, isChocolate);
        String priceMessage = "$" + price;
        display(quantity);
        displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }
    private int calculatePrice(boolean isWhippedCream, boolean isChocolate) {
        int price = 5;
        if (isWhippedCream){
            price += 1;
        }
        if (isChocolate){
            price += 2;
        }
        price *= quantity;
        return price;
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}