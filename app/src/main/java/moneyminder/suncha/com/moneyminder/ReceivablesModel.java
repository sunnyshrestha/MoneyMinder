package moneyminder.suncha.com.moneyminder;

import com.orm.SugarRecord;

/**
 * Created by MSI on 11/27/2016.
 */

public class ReceivablesModel extends SugarRecord {
    String name;
    String lentDate;
    String lentAmount;
    String isReminderActivated;
    String reminderDate;
    String reminderTime;
    String remarks;

    //Default constructor
    public ReceivablesModel(String name, String lentDate, String lentAmount, String isReminderActivated, String reminderDate, String reminderTime, String remarks) {
        this.name = name;
        this.lentDate = lentDate;
        this.lentAmount = lentAmount;
        this.isReminderActivated = isReminderActivated;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.remarks = remarks;
    }

    public ReceivablesModel() {

    }


}
