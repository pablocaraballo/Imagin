package com.ppm.imagine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ppm.imagine.User;
import com.ppm.imagine.R;


public class MirrorCreateFragment extends DialogFragment {

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String nombre;
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
                        setNombre(edittext.getText().toString());

                        DatabaseReference db= FirebaseDatabase.getInstance().getReference();

                        DefaultMirror df= new DefaultMirror();
                        df.setmirrorName(getNombre());

                        db.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(df);

                        Log.v("MIRROR_CREATED", getNombre());
                        Toast.makeText(getContext(), "ESPEJO CREADO", Toast.LENGTH_SHORT).show();

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