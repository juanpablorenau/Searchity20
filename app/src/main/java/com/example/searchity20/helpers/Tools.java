package com.example.searchity20.helpers;

import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class Tools {

    //Convertir de String a Date
    public static Date ParseDate(String s)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(s);
        }
        catch (ParseException e)
        {
            System.out.println(e);
        }
        return fechaDate;
    }

    //Obtener la fecha actual
    public static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    //Mostrar un error en pantalla
    public static void showAlert(Context context,String title, String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(error);
        Dialog dialog = builder.create();
        dialog.show();
    }

}
