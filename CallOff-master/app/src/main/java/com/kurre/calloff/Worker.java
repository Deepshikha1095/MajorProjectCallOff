package com.kurre.calloff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kurre on 25-09-2016.
 */
public class Worker extends AsyncTask {

    ProgressDialog dialog;
    Context context = null;
    Constants.task task = null;

    public Worker(Context context, Constants.task task) {
        this.context = context;
        this.task = task;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return getData((String) objects[0]);
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setTitle("Contacting Server...");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    public String getData(String urlToFetchData) {
        String pagina = "", devuelve = "No Response from server";
        URL url;
        try {
            url = new URL(urlToFetchData);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            System.out.println("Connection created and opened");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            System.out.println("Starting data read");
            String linea = reader.readLine();
            while (linea != null) {
                pagina += linea;
                linea = reader.readLine();
            }
            reader.close();
            devuelve = pagina;
            conexion.disconnect();
            return devuelve;
        } catch (Exception ex) {
            return devuelve;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        dialog.dismiss();
        String response = (String) result;
        Toast toast = null;
        if (this.task == task.LOGIN) {
            //response = "1";                                                 //For testing purpose making all logins successfull.
            if (response!= null && !response.isEmpty() && response.contains("#")) {
                RecentChat.USER_NAME = response.split("#")[0];
                RecentChat.PHONE_NUMBER = response.split("#")[1];
                MyDatabaseHelper myDbHelper = new MyDatabaseHelper(this.context);
                myDbHelper.userLogin(RecentChat.USER_NAME, RecentChat.PHONE_NUMBER);
                Worker worker = new Worker(this.context, Constants.task.GET_CONTACTS);
                worker.execute(Constants.GET_CONTACTS_URL);
                toast = Toast.makeText(context, Constants.LOGIN_SUCCESS, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(context, Constants.LOGIN_FAILED, Toast.LENGTH_SHORT);
            }
        } else if (this.task == task.REGISTER) {
            //TODO register code add here
            toast = Toast.makeText(context, Constants.REGISTER_SUCCESS, Toast.LENGTH_SHORT);
        } else if (this.task == task.RESET) {
            toast = Toast.makeText(context, Constants.REGISTER_FAILED, Toast.LENGTH_SHORT);
        } else if (this.task == task.GET_CONTACTS) {
            loadContactList(response);
            Intent intent = new Intent(this.context, RecentChat.class);
            this.context.startActivity(intent);
            ((Activity)this.context).finish();
        }
        if (toast != null) toast.show();
    }

    public static void loadContactList(String data) {
        String[] dataArray = data.substring(0, data.length()-1).split(",");

        for (String eachContact : dataArray) {
            String[] contact_details = eachContact.split("#");
            Contact newContact = new Contact(contact_details[0], contact_details[1], contact_details[2]);
            if (!ContactList.lContacts.contains(newContact) && !newContact.phone_number.equals(RecentChat.PHONE_NUMBER)) ContactList.lContacts.add(newContact);
        }
    }

}