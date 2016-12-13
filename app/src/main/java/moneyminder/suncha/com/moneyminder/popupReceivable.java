package moneyminder.suncha.com.moneyminder;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class popupReceivable extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    String name, amount, lentDate, reminderDate, reminderTime, remarks;
    @BindView(R.id.name)
    EditText nameTV;
    @BindView(R.id.amountValue)
    EditText amountTV;
    @BindView(R.id.dateLent)
    TextView lentDateTV;
    @BindView(R.id.reminderInfo)
    TextView reminderInfoTV;
    @BindView(R.id.remarksDetails)
    EditText remarksET;
    @BindView(R.id.changeReminder)
    Button changeReminderButton;
    @BindView(R.id.doneEditing)
    ImageView doneEditing;
    @BindView(R.id.cancelEdit)
    ImageView cancelEdit;
    @BindView(R.id.popupparentlayout)
    RelativeLayout popupparentlayout;
    @BindView(R.id.editLentDate)
    Button editLentDate;
    ImageView editButton;

    KeyListener nameKeylistener;
    KeyListener amountKeyListener;
    KeyListener lentDateKeyListener;
    KeyListener remarksDateKeyListener;
    FragmentManager fragmentManager = getSupportFragmentManager();
    String finalDate;
    String finalReminderDate = null;
    private AlphaAnimation buttonAnimation = new AlphaAnimation(1F, 0.7F);
    private int datePickerID = -1; //If this is 1, then it refers to lend date picker and if it is 2, it refers to reminder date
    //    private String newReminderDate;
    private String newReminderTime;
    private int isReminderActivated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window_receivables);
        ButterKnife.bind(this);

        editButton = (ImageView) findViewById(R.id.editButton);

        nameKeylistener = nameTV.getKeyListener();
        amountKeyListener = amountTV.getKeyListener();
        lentDateKeyListener = lentDateTV.getKeyListener();
        remarksDateKeyListener = remarksET.getKeyListener();

        //lock all the edittexts
        disableEditTexts();

        //get data from previous intent and load the views
        loadViews();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonAnimation);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                startEditing();

            }
        });

        cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonAnimation);
                confirmCancelEditDialog();
            }
        });

        doneEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonAnimation);
                if (finalReminderDate != null && newReminderTime != null) {
//                    isReminderActivated = 1;
                    checkDateOrder(lentDateTV.getText().toString(), finalReminderDate);
                } else {
                    saveAllChanges();
                }
            }
        });
        changeReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editReminder();
            }
        });
    }

    private void saveAllChanges() { //just to call save updates and update views
        saveUpdates();
        disableEditTexts();
        doneEditing.setVisibility(View.INVISIBLE);
        cancelEdit.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.VISIBLE);
        editLentDate.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.editLentDate)
    public void editLentDate() {
        datePickerID = 1;
        datePicker pickDate = new datePicker();
        pickDate.show(fragmentManager, "EDIT LENT DATE");
    }

    private void confirmCancelEditDialog() {
        //Create a dialog to confirm that the edit is to be canceled
        //If yes, set visibility of self to invisible, done to invisible and edit to visible
        //if no, return as was
        //disable editing functionality
    }

    private void saveUpdates() {
        //save updates and refresh views with updated data
        String newName = nameTV.getText().toString();
        String newAmount = amountTV.getText().toString();
        String newLentDate = lentDateTV.getText().toString();
        String newRemarks = remarksET.getText().toString();
        String NewReminderDate = finalReminderDate;
        String NewReminderTime = newReminderTime;


        if (newName.trim().length() == 0 || newAmount.trim().length() == 0 || newLentDate.trim().length() == 0)
            Toast.makeText(getApplicationContext(), R.string.saveError, Toast.LENGTH_SHORT).show();
        if (newRemarks.trim().length() == 0)
            newRemarks = getResources().getString(R.string.noRemarksSet);


        List<ReceivablesModel> receivables = ReceivablesModel.find(ReceivablesModel.class, "name=?", name);
        if (receivables.size() > 0) {
            ReceivablesModel editedReceivable = receivables.get(0);
            editedReceivable.name = newName;
            editedReceivable.lentAmount = newAmount;
            editedReceivable.lentDate = newLentDate;
            editedReceivable.remarks = newRemarks;
            editedReceivable.reminderDate = NewReminderDate;
            editedReceivable.reminderTime = NewReminderTime;
            editedReceivable.isReminderActivated = isReminderActivated;
            editedReceivable.save();
            Toast.makeText(getApplicationContext(), R.string.changesSaved, Toast.LENGTH_SHORT).show();
        }
    }

    private void startEditing() {
        Snackbar.make(popupparentlayout, R.string.editingmodeon, Snackbar.LENGTH_SHORT).show();
        enableEditTexts();
        changeReminderButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        cancelEdit.setVisibility(View.VISIBLE);
        doneEditing.setVisibility(View.VISIBLE);
        editLentDate.setVisibility(View.VISIBLE);
    }

    private void disableEditTexts() {
        nameTV.setKeyListener(null);
        amountTV.setKeyListener(null);
        lentDateTV.setKeyListener(null);
        remarksET.setKeyListener(null);
    }

    private void enableEditTexts() {
        nameTV.setKeyListener(nameKeylistener);
        amountTV.setKeyListener(amountKeyListener);
        lentDateTV.setKeyListener(lentDateKeyListener);
        remarksET.setKeyListener(remarksDateKeyListener);
    }


    private void loadViews() {
        name = getIntent().getStringExtra("name");
        amount = getIntent().getStringExtra("amount");
        lentDate = getIntent().getStringExtra("date");
        isReminderActivated = getIntent().getIntExtra("reminderCheck", -1);
        reminderDate = getIntent().getStringExtra("reminderDate");
        reminderTime = getIntent().getStringExtra("reminderTime");
        remarks = getIntent().getStringExtra("remarks");

        nameTV.setText(name);
        amountTV.setText(amount);
        lentDateTV.setText(lentDate);
        if (isReminderActivated == 1) {
            reminderInfoTV.setText(reminderDate + " " + getResources().getString(R.string.at) + " " + reminderTime);
        } else if (isReminderActivated == 0) {
            reminderInfoTV.setText(getResources().getString(R.string.noReminder));
        } else {
            reminderInfoTV.setText("Error is happens");
        }
        remarksET.setText(remarks);
    }

    private void editReminder() {
        //Call datepicker and time picker and set date and time of reminder to reminderInfoTV
        datePickerID = 2;
        datePicker selectDateFragment = new datePicker();
        selectDateFragment.show(fragmentManager, "EDITED REMINDER DATE");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int monthActual = month + 1;

        StringBuilder tempDate = new StringBuilder();
        tempDate.append(year);
        tempDate.append("-");
        if (monthActual < 10) {
            tempDate.append(0);
        }
        tempDate.append(monthActual);
        tempDate.append("-");
        if (dayOfMonth < 10) {
            tempDate.append(0);
        }
        tempDate.append(dayOfMonth);
        finalDate = tempDate.toString();

        switch (datePickerID) {
            case 1:
                lentDateTV.setText(finalDate);
                break;
            case 2:
                finalReminderDate = finalDate;
                reminderInfoTV.setText(finalDate);
                TimePickerFragment pickTime = new TimePickerFragment();
                pickTime.show(fragmentManager, "REMINDER_TIME");
                isReminderActivated = 1;
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String amOrpm;
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay - 12;
            amOrpm = "pm";
        } else if (hourOfDay == 12) {
            amOrpm = "pm";
        } else
            amOrpm = "am";
        StringBuilder tempReminderTime = new StringBuilder();
        tempReminderTime.append(hourOfDay);
        tempReminderTime.append(":");
        if (minute < 10) {
            tempReminderTime.append(0);
        }
        tempReminderTime.append(minute);
        tempReminderTime.append(" ");
        tempReminderTime.append(amOrpm);
        String actualReminderTime = tempReminderTime.toString();
        newReminderTime = actualReminderTime;
        reminderInfoTV.append(" at " + actualReminderTime);
    }

    //Method that checks if the reminder date is after the lent date or not
    public void checkDateOrder(String lentDate, String reminderDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date lent = simpleDateFormat.parse(lentDate);
            Date reminder = simpleDateFormat.parse(reminderDate);

            if (lent.after(reminder) || lent.equals(reminder)) {
                //Throw a toast asking the user to recheck dates. Reminder date has to be later than lent date
                Toast.makeText(getApplicationContext(), R.string.recheckDates, Toast.LENGTH_SHORT).show();
            } else {
                //WRITE DATA TO DATABASE
                Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
                saveAllChanges();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
