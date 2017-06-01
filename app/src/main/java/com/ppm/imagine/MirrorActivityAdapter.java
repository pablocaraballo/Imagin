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

public class MirrorActivityAdapter extends RecyclerView.Adapter<MirrorActivityAdapter.ViewHolder>  {

    private Widget[] mData = new Widget[0];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private int numColumns;
    private Point sizeDisplay;

    // data is passed into the constructor
    public MirrorActivityAdapter(Context context, Widget[] data, int numColumns, Point size) {
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mData = data;
        this.numColumns = numColumns;
        this.sizeDisplay=size;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.griditem_mirror, parent, false);
        ViewGroup.LayoutParams params= view.getLayoutParams();
        params.height= sizeDisplay.y/(mData.length/numColumns);
        view.setLayoutParams(params);


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        boolean hayWidget = false;
        for(int i=0; i<mData.length; i++){
            if(mData[i] != null) {
                if (mData[i].getPosXinMirror() * numColumns + mData[i].getPosYinMirror() == position) {
                    holder.myTextView.addView(mData[i].getView());
                    hayWidget = true;
                }
            }
        }
        if(! hayWidget) {
            TextView textView = new TextView(context);
            textView.setText("Widget" + position);
            holder.myTextView.addView(textView);
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
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

    // convenience method for getting data at click position
    public Widget getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
