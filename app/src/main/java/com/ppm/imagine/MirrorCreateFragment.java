package com.ppm.imagine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.EditText;

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

                        //Mirror mirror= new Mirror(getNombre(), new Configurator("Config1"));

                        User user= new User(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        //user.addMirrorToArray(mirror);

                        db.child("users").child(user.getUid_user()).setValue(user);

                        Log.v("MIRROR_CREATED", getNombre());

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