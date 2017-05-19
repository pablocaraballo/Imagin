package com.ppm.imagine;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class WidgetTimeConfiguratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_time_configurator);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_widgetsTime);
        toolbar.setTitle(getResources().getString(R.string.widgetTimeConfigurator_title));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        ListView listView= (ListView) findViewById(R.id.listViewRegions);
        List<String> listLocales= new ArrayList<String>();

        for (int i=0; i<WidgetTime.zonas.length; i++){

            listLocales.add(WidgetTime.zonas[i]);
            System.out.println(WidgetTime.zonas[i]);

        }

        WidgetTimeConfiguratorListViewAdapter adapter= new WidgetTimeConfiguratorListViewAdapter(this, listLocales);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().setHoraActual(WidgetTime.zonas[position]);
                System.out.println("HOOOOOOOORA: "+ "ITEM: " + WidgetTime.zonas[position] + "/////" +  User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getHoraActual());

            }
        });

    }

    public class WidgetTimeConfiguratorListViewAdapter extends BaseAdapter{

        Context context;
        List<String> llistatLocales;

        public WidgetTimeConfiguratorListViewAdapter(Context context, List<String> items){

            this.context= context;
            this.llistatLocales=items;

        }

        @Override
        public int getCount() {
            return llistatLocales.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (convertView == null) {
                // Create a new view into the list.
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.widget_time_configurator_list_item, parent, false);
            }

            // Set data into the view.
            TextView itemLocaleText = (TextView) rowView.findViewById(R.id.itemLocaleListViewText);

            String item = this.llistatLocales.get(position);
            itemLocaleText.setText(item);

            return rowView;

        }
    }

}


