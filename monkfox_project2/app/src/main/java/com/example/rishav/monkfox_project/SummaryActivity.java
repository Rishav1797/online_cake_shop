package com.example.rishav.monkfox_project;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishav.monkfox_project.Model.Data;
import com.example.rishav.monkfox_project.Model.Pages;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class SummaryActivity extends Activity {

    private String mDateString;
    private String mTimeString;
    private TextView mDateText;
    private TextView mTimeText;
    private TextView mSugarFreeText;
    private TextView mFlavourText;
    private TextView mToppingText;
    private TextView mCostText;
    private TextView mThemeText;
    private TextView mWeightText;
    private Button mChangeButton;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setDateAndTime();
        mIntent = getIntent();
    }

    private void setLayout() {
        int cost = 0;

        //flavours ops

        mFlavourText = (TextView) findViewById(R.id.flavourLabel);
        int flavourPos = Data.baseIndex;
        mFlavourText.setText("Base Flavour: " +
                Pages.cakePage.getObjects()[flavourPos].getDisplayName());
        mFlavourText.setVisibility(View.VISIBLE);

        //toppings ops

        mToppingText = (TextView) findViewById(R.id.toppingLabel);
        int toppingPos = Data.topIndex;
        mToppingText.setText("Topping: " +
                Pages.creamPage.getObjects()[toppingPos].getDisplayName());
        mToppingText.setVisibility(View.VISIBLE);

        //weight ops

        mWeightText = (TextView) findViewById(R.id.weightLabel);
        int weightPos = Data.sizeIndex;
        mWeightText.setText(Pages.weights.getObjects()[weightPos].getDisplayName());
        cost = Pages.weights.getObjects()[weightPos].getPrice();

        //theme ops

        mThemeText = (TextView) findViewById(R.id.themeLabel);
        String theme = Data.cakeTheme;
        if (!theme.isEmpty()){
            mThemeText.setText("Personal Theme: " + theme + " (add Rs. 400)");
            cost+=400;
        }
        else{
            mThemeText.setVisibility(View.GONE);
        }
        //sugarfree settings
        mSugarFreeText = (TextView) findViewById(R.id.sugarFreeLabel);
        int sugarIndex = Data.sugarIndex;
        mSugarFreeText.setText("Sugarfree: " +
                Pages.sugarPage.getObjects()[sugarIndex].getDisplayName());
        if (sugarIndex == 0){
            cost+=100;
        }


        mCostText = (TextView) findViewById(R.id.finalCostLabel);
        mCostText.setText("Total Cost: " + cost);

        mChangeButton = (Button) findViewById(R.id.changeDateButton);
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateAndTime();
            }
        });
    }

    private void setDateAndTime() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int
                    month, int day) {
                GregorianCalendar date = new GregorianCalendar(year, month, day);
                SimpleDateFormat formatter = new SimpleDateFormat("d MMMM y");
                mDateString = formatter.format(date.getTime());
                //date operation
                mDateText = (TextView) findViewById(R.id.dateLabel);
                mDateText.setText("Delivery Date: " + mDateString);
                mDateText.setVisibility(View.VISIBLE);
                setTime();
            }
        };
        DatePickerDialog dpd =DatePickerDialog.newInstance(
                listener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(true);
        dpd.show(getFragmentManager(), "Select You Delivery Date");
    }

    private void setTime() {
    Calendar now = Calendar.getInstance();
    TimePickerDialog timepickerdialog = TimePickerDialog.newInstance((TimePickerDialog.OnTimeSetListener) SummaryActivity.this,
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                timepickerdialog.setThemeDark(false); //Dark Theme?
                timepickerdialog.vibrate(false); //vibrate on choosing time?
                timepickerdialog.dismissOnPause(false); //dismiss the dialog onPause() called?
                timepickerdialog.enableSeconds(true); //show seconds?

    //Handling cancel event
                timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            Toast.makeText(SummaryActivity.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
        }
    });
                timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog
        };

public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = "You picked the following time: " + hourString + "h" + minuteString + "m" + secondString + "s";
        mTimeText.setText(time);
        }
public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        mTimeText.setText(date);
        }
}