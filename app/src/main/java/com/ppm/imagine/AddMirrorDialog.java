package com.ppm.imagine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tarda on 06/04/17.
 */


public class AddMirrorDialog extends Activity {
    public static String nombre;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addNombre();


    }


    public void addNombre(){
        final EditText edittext = new EditText(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //TITLE OF DIALOG
        dialog.setTitle("Nombre del espejo");

        //MESSAGE OF DIALOG
        dialog.setMessage("Elige un nombre para identificar a tu espejo");
        dialog.setView(edittext);

        //POSITIVE OPTION
        dialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        nombre = edittext.getText().toString();
                        Toast.makeText(getApplicationContext(),
                                nombre, Toast.LENGTH_SHORT)
                                .show();
                        //La variable nombre es la que contiene el nombre del espejo
                    }
                });

        // NEGATIVE OPTION
        dialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Aqui deberia ir el comportamiento en caso de que
                        Toast.makeText(getApplicationContext(),
                                "Cancelar", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

        // SHOW DIALOG
        dialog.show();
    }



}
