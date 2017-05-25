package com.ppm.imagine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class WidgetSelectConfiguratorList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_widget);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar_widgets);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ListView lv = (ListView) findViewById(R.id.listView_widget);
        WidgetAdapter wa = new WidgetAdapter(WidgetSelectConfiguratorList.this);
        lv.setAdapter(wa);


    }

    public static void selectWidget(Switch ok){

        if(ok.isChecked()){
           // startActivity
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(WidgetSelectConfiguratorList.this, ConfiguratorView.class));
    }
}

//Widget Item
class WidgetConfigItem {
    int image;
    String name;
    Class classe;

    WidgetConfigItem(int i, String n, Class c){
        image = i;
        name = n;
        classe = c;
    }
}

//Adapter Widget List
class WidgetAdapter extends BaseAdapter {
    private Context mContext;

    public static WidgetConfigItem[] widgetConfigItems = {

            new  WidgetConfigItem(R.drawable.twitter_icon_scaled, "Twitter", WidgetTwitterLoginActivity.class),
            new  WidgetConfigItem(R.drawable.relojwidgetlogo_scaled, "Hora mundial", WidgetTimeConfiguratorActivity.class),
            new  WidgetConfigItem(R.drawable.timewidgetlogo_scaled, "Meteorología", MainActivity.class),
    };

    public WidgetAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return widgetConfigItems.length;
    }

    public Object getItem(int position) {
        return widgetConfigItems[position];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            view = LayoutInflater.from(mContext).inflate(R.layout.widget_item, null);
        } else {
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               mContext.startActivity(new Intent(mContext,widgetConfigItems[position].classe));
            }
        });

        ImageView icon=(ImageView) view.findViewById(R.id.image_widget);
        icon.setImageResource(widgetConfigItems[position].image);

        TextView name=(TextView) view.findViewById(R.id.name_widget);
        name.setText(widgetConfigItems[position].name);


        return view;
    }


}
