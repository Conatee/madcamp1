package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class EditTODOListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_DELETED = 100;
    private static final int RESULT_EDITTED = 200;

    private ImageButton abortButton, nextButton, calendarButton;
    private Button deleteButton;

    private String initialTitle, initialDate;
    private boolean initialTrivial, initialUrgent, initialDone;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_list);

        abortButton = findViewById(R.id.abortButtonE);
        nextButton = findViewById(R.id.nextButtonE);
        deleteButton = findViewById(R.id.buttonDeleteE);
        calendarButton = findViewById(R.id.calendarEditButton);

        abortButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar;
                DatePickerDialog datePickerDialog;

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(EditTODOListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        EditText editTextDate = (EditText) findViewById(R.id.editTextDateE);
                        setTextFormat(editTextDate, mYear, mMonth, mDay);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        getIncomingIntent();
        setInitialState();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.abortButtonE: {
                Intent intent = new Intent();

                setResult(RESULT_CANCELED, intent);
                finish();
            }

            case R.id.nextButtonE: {
                Intent intent = new Intent();
                if(sameAsInitial()) {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }

                else {
                    EditText titleEditText = (EditText) findViewById(R.id.editTextTitleE),
                            dateEditText = (EditText) findViewById(R.id.editTextDateE);
                    CheckBox trivialCheckBox = (CheckBox) findViewById(R.id.checkBoxTrivialE),
                            urgentCheckBox = (CheckBox) findViewById(R.id.checkBoxUrgentE),
                            doneCheckBox = (CheckBox) findViewById(R.id.checkBoxDoneE);

                    intent.putExtra("title", titleEditText.getText().toString());
                    intent.putExtra("date", dateEditText.getText().toString());
                    intent.putExtra("trivial", trivialCheckBox.isChecked());
                    intent.putExtra("urgent", urgentCheckBox.isChecked());
                    intent.putExtra("done", doneCheckBox.isChecked());
                    intent.putExtra("position", position);
                    intent.putExtra("samePosition", samePosition());
                    if(!samePosition()) {
                        intent.putExtra("initialTrivial", initialTrivial);
                        intent.putExtra("initialUrgent", initialUrgent);
                    }

                    setResult(RESULT_EDITTED, intent);
                    finish();
                }
            }

            case R.id.buttonDeleteE: {
                Intent intent = new Intent();

                intent.putExtra("initialTrivial", initialTrivial);
                intent.putExtra("initialUrgent", initialUrgent);
                intent.putExtra("position", position);

                setResult(RESULT_DELETED, intent);
                finish();
            }
        }
    }

    public void getIncomingIntent() {
        Intent intent = getIntent();

        initialTitle = intent.getStringExtra("title");
        initialDate = intent.getStringExtra("date");
        initialTrivial = intent.getBooleanExtra("trivial", false);
        initialUrgent = intent.getBooleanExtra("urgent", false);
        initialDone = intent.getBooleanExtra("done", false);
        position = intent.getIntExtra("position", 0);
    }

    public void setInitialState() {
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitleE),
                dateEditText = (EditText) findViewById(R.id.editTextDateE);
        CheckBox trivialCheckBox = (CheckBox) findViewById(R.id.checkBoxTrivialE),
                urgentCheckBox = (CheckBox) findViewById(R.id.checkBoxUrgentE),
                doneCheckBox = (CheckBox) findViewById(R.id.checkBoxDoneE);

        titleEditText.setText(initialTitle);
        dateEditText.setText(initialDate);
        trivialCheckBox.setChecked(initialTrivial);
        urgentCheckBox.setChecked(initialUrgent);
        doneCheckBox.setChecked(initialDone);
    }

    public boolean sameAsInitial() {
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitleE),
                dateEditText = (EditText) findViewById(R.id.editTextDateE);
        CheckBox trivialCheckBox = (CheckBox) findViewById(R.id.checkBoxTrivialE),
                urgentCheckBox = (CheckBox) findViewById(R.id.checkBoxUrgentE),
                doneCheckBox = (CheckBox) findViewById(R.id.checkBoxDoneE);

        return (initialTitle.equals(titleEditText.getText().toString()) &&
                initialDate.equals(dateEditText.getText().toString()) &&
                initialTrivial == trivialCheckBox.isChecked() &&
                initialUrgent == urgentCheckBox.isChecked() &&
                initialDone == doneCheckBox.isChecked());
    }

    public boolean samePosition() {
        CheckBox trivialCheckBox = (CheckBox) findViewById(R.id.checkBoxTrivialE),
                urgentCheckBox = (CheckBox) findViewById(R.id.checkBoxUrgentE),
                doneCheckBox = (CheckBox) findViewById(R.id.checkBoxDoneE);
        return (initialTrivial == trivialCheckBox.isChecked() &&
                initialUrgent == urgentCheckBox.isChecked() &&
                initialDone == doneCheckBox.isChecked());
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
