package com.ppm.imagine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.Toast;


public class AlertDFrgment extends DialogFragment {
    public static String nombre;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText edittext = new EditText(getContext());
        return new AlertDialog.Builder(getActivity())

                // Set Dialog Title
                .setTitle(R.string.title)
                // Set Dialog Message
                .setMessage(R.string.mssg)
                .setView(edittext)

                // Positive button
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nombre = edittext.getText().toString();

                    }
                })

                // Negative Button
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }
}