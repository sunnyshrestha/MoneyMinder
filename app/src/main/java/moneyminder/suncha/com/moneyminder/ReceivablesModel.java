package moneyminder.suncha.com.moneyminder;

import com.orm.SugarRecord;

/**
 * Created by MSI on 11/27/2016.
 */

public class ReceivablesModel extends SugarRecord {
    public ReceivablesModel() {

    }

    public String name;
    public String lentDate;
    public String lentAmount;
    public String isReminderActivated;
    public String reminderDate;
    public String reminderTime;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLentDate() {
        return lentDate;
    }

    public void setLentDate(String lentDate) {
        this.lentDate = lentDate;
    }

    public String getLentAmount() {
        return lentAmount;
    }

    public void setLentAmount(String lentAmount) {
        this.lentAmount = lentAmount;
    }

    public String getIsReminderActivated() {
        return isReminderActivated;
    }

    public void setIsReminderActivated(String isReminderActivated) {
        this.isReminderActivated = isReminderActivated;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String remarks;

}
