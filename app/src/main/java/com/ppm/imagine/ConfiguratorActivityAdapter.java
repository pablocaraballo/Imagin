package com.ppm.imagine;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ConfiguratorActivityAdapter extends RecyclerView.Adapter<ConfiguratorActivityAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    private Widget[] dataSet;
    private ItemClickListener mClickListener;
    private Context context;
    private int numColumns;
    private int numRows;
    private Point sizeDisplay;

    public ConfiguratorActivityAdapter(Context context, Widget[] dataSet, int numColumns, int numRows, Point size) {
        this.context = context;
        this.dataSet = dataSet;
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.sizeDisplay=size;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_configurator_recyclerview_item, parent, false);

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height= sizeDisplay.y/numRows;
        view.setLayoutParams(params);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(dataSet[position] != null) {
                System.out.println("adding widget " + dataSet[position].getName() + " icon:" + dataSet[position].getIcon());
                holder.imageView.setImageResource(dataSet[position].getIcon());
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public Widget[] getDataSet(){ return dataSet; }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Widget temp = dataSet[fromPosition];
        dataSet[fromPosition] = dataSet[toPosition];
        dataSet[toPosition] = temp;

        notifyItemMoved(fromPosition, toPosition);

        // TODO: actualizar la posicion del widget en Firebase!!
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.widgetIcon);
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

interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
}

class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        System.out.println("holder " + viewHolder.getAdapterPosition());
        System.out.println("target " + target.getAdapterPosition());
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
}