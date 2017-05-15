package com.ppm.imagine;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ConfiguratorView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurator_view);


        //Button Add Widget
        final FloatingActionButton newWidget = (FloatingActionButton) findViewById(R.id.button_widget);
        newWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfiguratorView.this, R.style.AppTheme);

                View convertView = getLayoutInflater().inflate(R.layout.listview_widget, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Widgets");
                ListView lv = (ListView) convertView.findViewById(R.id.listView_widget);

                WidgetAdapter wa = new WidgetAdapter(ConfiguratorView.this);
                lv.setAdapter(wa);
                alertDialog.show();*/


                startActivity(new Intent(getApplicationContext(),WidgetSelectConfiguratorList.class));



                //---------------------------------------------------------------------------------------------

               /* AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Select");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                Toast.makeText(getApplicationContext(), "You selected: " + items[item],Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/


                //lv=(ListView) findViewById(R.id.listView_widget);


                //Toast.makeText(getApplicationContext(),"Se ha creado un Widget",Toast.LENGTH_SHORT).show();
            }
        });

        //Button Exit to StartMenuActivity
        final FloatingActionButton exit=(FloatingActionButton) findViewById(R.id.button_exit);
        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StartMenuActivity.class));
            }
        });


        /*ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.white));*/
}
}



/*class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(ConfiguratorView configuratorView, String[] widgetName, int[] widgetImages) {
        // TODO Auto-generated constructor stub
        result=widgetName;
        context=configuratorView;
        imageId=widgetImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView widgetN;
        ImageView widgetImg;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.widget_item, null);
        holder.widgetN=(TextView) rowView.findViewById(R.id.name_widget);
        holder.widgetImg=(ImageView) rowView.findViewById(R.id.image_widget);
        holder.widgetN.setText(result[position]);
        holder.widgetImg.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}*/


/*public class Adapter extends BaseAdapter{
    private Context mContext;
    ListAdapter adapter = new ArrayAdapter<String>(
            mContext, R.layout.widget_item) {

        ViewHolder holder;
        Drawable icon;
        int index=0;

        class ViewHolder {
            ImageView icon;
            TextView title;
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            final LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(
                        R.layout.widget_item, null);

                holder = new ViewHolder();
                holder.icon = (ImageView) convertView
                        .findViewById(R.id.icon);
                holder.title = (TextView) convertView
                        .findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                // view already defined, retrieve view holder
                holder = (ViewHolder) convertView.getTag();
            }

                /*for(int i=0; i<3; i++){
                    switch (index){

                        case 0:
                            Drawable drawable = getResources().getDrawable(R.drawable.list_icon); //this is an image from the drawables folder

                            holder.title.setText(items[position]);
                            holder.icon.setImageDrawable(drawable);
                            break;


                    }

            Drawable drawable = getResources().getDrawable(R.drawable.twitter_icon); //this is an image from the drawables folder

            holder.title.setText("twitter");
            holder.icon.setImageDrawable(drawable);

        }








    @Override
    public int getCount() {
        return 0;
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
        return null;
    }
        return convertView;
};


}*/


/*class WidgetConfigItem {
    int image;
    String name;
    Class classe;
    WidgetConfigItem(int i, String n, Class c){
        image = i;
        name = n;
        classe = c;
    }
}*/

/*class WidgetAdapter extends BaseAdapter {
    private Context mContext;

    public WidgetConfigItem[] widgetConfigItems = {
            new  WidgetConfigItem(R.drawable.twitter_icon, "teiter", MainActivity.class),
            new  WidgetConfigItem(R.drawable.twitter_icon, "teiter", MainActivity.class),
            new  WidgetConfigItem(R.drawable.twitter_icon, "teiter", MainActivity.class),
    };

    public WidgetAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return widgetConfigItems.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            view = LayoutInflater.from(mContext).inflate(R.layout.widget_item, null);
        } else {
            view = convertView;
        }
        System.out.println("AAAAAAAA"+widgetConfigItems[position].image);
        ImageView icon=(ImageView) view.findViewById(R.id.image_widget);
        icon.setImageResource(widgetConfigItems[position].image);

        TextView name=(TextView) view.findViewById(R.id.name_widget);
        name.setText(widgetConfigItems[position].name);
        return view;
    }


}*/
