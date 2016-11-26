package moneyminder.suncha.com.moneyminder;


import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.philliphsu.bottomsheetpickers.date.BottomSheetDatePickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.view.View.GONE;
import static moneyminder.suncha.com.moneyminder.R.id.lentDate;

public class ReceivableDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, BottomSheetTimePickerDialog.OnTimeSetListener {
    @BindView(R.id.receivableToolbar)
    Toolbar receivableToolbar;
    @BindView(R.id.lentDateButton)
    Button datePicker;
    @BindView(lentDate)
    EditText lentdate;
    @BindView(R.id.lentDateWrapper)
    TextInputLayout lentDateWrapper; //required to set validation
    @BindView(R.id.reminderSwitch)
    Switch reminderSwitch;
    @BindView(R.id.reminderDetailsWrapper)
    RelativeLayout reminderDetailsWrapper;
    @BindView(R.id.reminderDetails)
    TextView reminderDetails;
    @BindView(R.id.changeReminderButton)
    Button changeReminderButton;
    @BindView(R.id.nameOfLender)
    EditText nameOfLender;
    @BindView(R.id.amountLent)
    EditText amountLent;


    int datePickerID = 0; //If this is 1, then it refers to lend date picker and if it is 2, it refers to reminder date
    String reminderDateChosenbyUser; //The reminder date that the user chooses is assigned to this variable so that we can later check if the reminder date is later than the lent date or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivable_details);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(receivableToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        receivableToolbar.setTitle(R.string.receivable_details);
        receivableToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog();
            }
        });

        changeReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderDate();
            }
        });
    }

    @OnCheckedChanged(R.id.reminderSwitch)
    public void reminderDate() {
        if (reminderSwitch.isChecked()) {
            Calendar now = Calendar.getInstance();
            BottomSheetDatePickerDialog dialog = BottomSheetDatePickerDialog.newInstance(
                    ReceivableDetails.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));
            datePickerID = 2;
            dialog.show(getSupportFragmentManager(), "reminderDate");
        } else {
            reminderDetails.setText(null);
            reminderDetailsWrapper.setVisibility(GONE);
        }
    }

    //Call reminder time after reminder date is set
    public void reminderTime() {
        Calendar nowTime = Calendar.getInstance();
        GridTimePickerDialog timeDialog = GridTimePickerDialog.newInstance(
                ReceivableDetails.this,
                nowTime.get(Calendar.HOUR_OF_DAY),
                nowTime.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(ReceivableDetails.this));
        timeDialog.show(getSupportFragmentManager(), "reminderTime");
    }

    @OnClick(R.id.lentDateButton)
    public void pickDate() {
        Calendar now = Calendar.getInstance();
        BottomSheetDatePickerDialog dialog = BottomSheetDatePickerDialog.newInstance(
                ReceivableDetails.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        datePickerID = 1;
        dialog.show(getSupportFragmentManager(), "lentdate");
    }


    //Create an alert if a user presses the back button (on the toolbar) from the activity
    private void createAlertDialog() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        goBackWithoutSavingFragment goBackDialog = new goBackWithoutSavingFragment();
        goBackDialog.setRetainInstance(true);
        goBackDialog.show(fragmentManager, "backwithoutsaving");
    }

    //Create an alert if a user presses the back button from the activity
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            createAlertDialog();
            //moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //User decides to change the reminder that s/he had set previously


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        switch (datePickerID) {
            case 1:
                lentdate.setText(DateFormat.getDateFormat(this).format(cal.getTime()));
                break;
            case 2:
                reminderTime();
                reminderDetailsWrapper.setVisibility(View.VISIBLE);
                reminderDetails.setText(getResources().getString(R.string.reminderText) + " "+DateFormat.getDateFormat(this).format(cal.getTime()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        reminderDateChosenbyUser=DateFormat.getTimeFormat(this).format(cal.getTime());
        reminderDetails.append(" at " + DateFormat.getTimeFormat(this).format(cal.getTime()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.receivablesmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.saveReceivables: {
                //Step 1: Check if the name filed, amount field, and the lent date field are not empty
                if (nameOfLender.getText().toString().trim().length() == 0 || amountLent.getText().toString().trim().length() == 0 || lentdate.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.saveError, Toast.LENGTH_SHORT).show();
                    break;
                }
                //Step 2: Check if lent date is in proper format
                if (isDateValid(amountLent.getText().toString())) {
                    //Check if the reminder date is set after the lend date or not
                    //But do this only if reminder was picked
                    if(reminderSwitch.isChecked()){
                        checkDateOrder(lentdate.getText().toString(),reminderDateChosenbyUser);                    
                    }    
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), R.string.dateFormatError, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    //Method to check if date is valid
    public boolean isDateValid(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            date = lentdate.getText().toString();
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Method that checks if the reminder date is after the lent date or not
    public void checkDateOrder(String lentDate, String reminderDate) {
        //checks if the followup date is after the meetingDate or not
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date lent = simpleDateFormat.parse(lentDate);
            Date reminder = simpleDateFormat.parse(reminderDate);

            if (lent.after(reminder) || lent.equals(reminder)) {
                //Throw a toast asking the user to recheck dates. Reminder date has to be later than lent date
                Toast.makeText(getApplicationContext(),R.string.recheckDates,Toast.LENGTH_SHORT).show();
            }else{
                //Method to write to database
                //WRITE DATA TO DATABASE
                Toast.makeText(getApplicationContext(), "Now you need to write to database", Toast.LENGTH_SHORT).show();
            }            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
}

//TODO set switch to inflate date and time picker and set reminder
