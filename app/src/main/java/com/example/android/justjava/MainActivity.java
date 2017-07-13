package com.example.android.justjava;

 import android.content.Intent;
 import android.net.Uri;
 import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.View;
import android.widget.CheckBox;
 import android.widget.EditText;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        boolean hasChocolate = chocolate.isChecked();
        boolean hasWhippedCream = whippedCream.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject,name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity( getPackageManager()) != null ) {
            startActivity(intent);
        }
    } 

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this,getString(R.string.toast2), Toast.LENGTH_SHORT ).show();
            return;
        }
        quantity = quantity + 1 ;
        display(quantity);
    }


    /**
     * Calculates the total price
     */
    private String createOrderSummary (String name,int price, boolean addWhipppedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage += "\n" + getString(R.string.add_whipped_cream,addWhipppedCream);
        priceMessage +="\n"+ getString(R.string.addChocolate, addChocolate);
        priceMessage +="\n" + getString(R.string.order_quantity,quantity);
        priceMessage +="\n" + getString(R.string.total,price);
        priceMessage +="\n" + getString(R.string.thank_you);
        return priceMessage;
    }

     private int calculatePrice (boolean hasWhippedCream, boolean hasChocolate){
         int basePrice = 5;

         if (hasWhippedCream){
             basePrice += 1;
         }
         if (hasChocolate){
             basePrice += 2;
         }
         int price = quantity * basePrice;
         return price;
     }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.toast1),Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1 ;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}