package com.ppm.imagine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class WidgetTimeConfiguratorActivity extends AppCompatActivity {

    EditText inputSearch;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_time_configurator);

        //Creación de la toolbar.

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_widgetsTime);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getResources().getString(R.string.widgetTimeConfigurator_title));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Creación del listView y la unión con su adaptador.

        ListView listView= (ListView) findViewById(R.id.listViewRegions);

        adapter = new ArrayAdapter<>(this, R.layout.widget_time_configurator_list_item, R.id.itemLocaleListViewText, WidgetTime.zonas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().setHoraActual(WidgetTime.zonas[position]);
                System.out.println("HOOOOOOOORA: "+ "ITEM: " + WidgetTime.zonas[position] + "/////" +  User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getHoraActual());

                Map<String, Object> newTz = new HashMap<String, Object>();
                newTz.put("horaActual", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getHoraActual());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getName()).updateChildren(newTz);

                startActivity(new Intent(WidgetTimeConfiguratorActivity.this, WidgetSelectConfiguratorList.class));
            }
        });

        inputSearch = (EditText) findViewById(R.id.timezoneInputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                WidgetTimeConfiguratorActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

                WidgetTimeConfiguratorActivity.this.adapter.getFilter().filter(arg0);
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

    }
}


