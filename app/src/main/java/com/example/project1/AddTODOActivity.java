package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class AddTODOActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton abortButton, nextButton, calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        abortButton = findViewById(R.id.abortAddButton);
        nextButton = findViewById(R.id.nextAddButton);
        calendarButton = findViewById(R.id.calendarAddButton);

        abortButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar;
                DatePickerDialog datePickerDialog;

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(AddTODOActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        EditText editTextDate = (EditText) findViewById(R.id.editTextDate);
                        setTextFormat(editTextDate, mYear, mMonth, mDay);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.abortAddButton: {
                Intent intent = new Intent();

                setResult(RESULT_CANCELED, intent);
                finish();
            }

            case R.id.nextAddButton: { // TODO : Handle case where the input is incomplete
                Intent intent = new Intent();

                EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);
                String strTitle = editTextTitle.getText().toString();
                if(!strTitle.isEmpty()) {
                    intent.putExtra("title", strTitle);
                } else {

                }

                EditText editTextDate = (EditText) findViewById(R.id.editTextDate);
                String strDate = editTextDate.getText().toString();
                if(!strDate.isEmpty()) {
                    intent.putExtra("date", strDate);
                } else {

                }

                CheckBox checkBoxTrivial = (CheckBox) findViewById(R.id.checkBoxTrivial);
                intent.putExtra("isTrivial", checkBoxTrivial.isChecked());

                CheckBox checkBoxUrgent = (CheckBox) findViewById(R.id.checkBoxUrgent);
                intent.putExtra("isUrgent", checkBoxUrgent.isChecked());

                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private void setTextFormat(EditText editText, int year, int month, int day) {
        if(month<9) {
            if(day < 10) {
                editText.setText(year+"0"+(month+1)+"0"+day);
            }
            else {
                editText.setText(year+"0"+(month+1)+""+day);
            }
        }

        else{
            if(day < 10) {
                editText.setText(year+""+(month+1)+"0"+day);
            }
            else {
                editText.setText(year+""+(month+1)+""+day);
            }
        }
    }
}
