package com.ppm.imagine;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MirrorActivityAdapter extends RecyclerView.Adapter<MirrorActivityAdapter.ViewHolder>  {

    private Widget[] dataSet;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private int numColumns;
    private int numRows;
    private Point sizeDisplay;

    public MirrorActivityAdapter(Context context, Widget[] dataSet, int numColumns, int numRows, Point size) {
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.dataSet = dataSet;
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.sizeDisplay=size;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.griditem_mirror, parent, false);
        ViewGroup.LayoutParams params= view.getLayoutParams();
        params.height= sizeDisplay.y/numRows;
        view.setLayoutParams(params);


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(dataSet[position] != null) {
                System.out.println("adding widget " + dataSet[position].getName());
                holder.myTextView.addView(dataSet[position].getView());
        }else{
            TextView textView = new TextView(context);
            textView.setText("W" + position);
            holder.myTextView.addView(textView);
        }
    }

    @Override
    public int getItemCount() {
        return numColumns*numRows;
    }

    public Widget[] getDataSet(){ return dataSet; }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RelativeLayout myTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (RelativeLayout) itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Widget getItem(int id) {
        return dataSet[id];
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
