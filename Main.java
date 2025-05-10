package com.group.oodjAssignment;

import com.group.oodjAssignment.customer.CustomerDashboardForm;
import com.group.oodjAssignment.scheduler.SchedulerDashboardForm;

public class Main {

    public static void main(String[] args) {
        WelcomeForm welcomeForm = WelcomeForm.getInstance();
        welcomeForm.setVisible(true);
        // CustomerDashboardForm form = new CustomerDashboardForm();
        // form.setVisible(true);
    }
}
