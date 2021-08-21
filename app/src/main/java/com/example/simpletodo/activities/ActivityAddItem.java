package com.example.simpletodo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletodo.R;
import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

public class ActivityAddItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView doneButton;
    private EditText inputItemText;
    private TextView dateDisplay;
    private TextView heading;
    private Slider slider;

    private AlertDialog dialogPriority;

    private String itemText;
    private int year = 1000;
    private int month = 10;
    private int date = 10;
    private int priority = 4;

//    private Date temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        inputItemText = findViewById(R.id.inputItemText);
        dateDisplay = findViewById(R.id.dateDisplay);
        doneButton = findViewById(R.id.doneButton);
        heading = findViewById(R.id.heading);
        slider = findViewById(R.id.slider);


        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String text = extras.getString("item_text", "");
            year = extras.getInt("item_year", -1);
            month = extras.getInt("item_month", -1);
            date = extras.getInt("item_date", -1);
            priority = extras.getInt("item_priority", 4);

            inputItemText.setText(text);
            dateDisplay.setText(String.format("%d/%d/%d", year, month, date));
            slider.setValue(priority);

            inputItemText.setSelection(text.length());
            inputItemText.requestFocus();

            heading.setText("Edit Task");
        }

        dateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (inputItemText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ActivityAddItem.this, "Text input cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    itemText = inputItemText.getText().toString();


                    intent.putExtra("item_text", itemText);
                    intent.putExtra("item_year", year);
                    intent.putExtra("item_month", month);
                    intent.putExtra("item_date", date);
                    intent.putExtra("item_priority", priority);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

//        priorityDisplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPriorityDialog();
//            }
//        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (inputItemText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ActivityAddItem.this, "Text input cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    itemText = inputItemText.getText().toString();

                    intent.putExtra("item_text", itemText);
                    intent.putExtra("item_year", year);
                    intent.putExtra("item_month", month);
                    intent.putExtra("item_date", date);
                    intent.putExtra("item_priority", priority);

                    if (extras != null && extras.containsKey("item_id")) {
                        int id = extras.getInt("item_id", -1);
                        intent.putExtra("item_id", id);
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                priority = (int) value;
            }
        });

    }



//    private void showPriorityDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddItem.this);
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_priority_selection, null);
//        builder.setView(view);
//
//        dialogPriority = builder.create();
//        dialogPriority.show();
//
//        view.findViewById(R.id.priority_1_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                priority = 1;
//                priorityDisplay.setText(String.valueOf(priority));
//                dialogPriority.dismiss();
//            }
//        });
//        view.findViewById(R.id.priority_2_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                priority = 2;
//                priorityDisplay.setText(String.valueOf(priority));
//                dialogPriority.dismiss();
//            }
//        });
//        view.findViewById(R.id.priority_3_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                priority = 3;
//                priorityDisplay.setText(String.valueOf(priority));
//                dialogPriority.dismiss();
//            }
//        });
//        view.findViewById(R.id.priority_4_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                priority = 4;
//                priorityDisplay.setText(String.valueOf(priority));
//                dialogPriority.dismiss();
//            }
//        });
//
//    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1 + 1;
        date = i2;
        dateDisplay.setText(String.format("%d/%d/%d", year, month, date));
    }
}