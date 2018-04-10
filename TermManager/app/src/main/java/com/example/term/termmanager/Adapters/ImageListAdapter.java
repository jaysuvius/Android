package com.example.term.termmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.term.termmanager.Models.Image;
import com.example.term.termmanager.R;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Image> imageList;

    public ImageListAdapter(Context context, int layout, ArrayList<Image> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View row = convertView;
       ViewHolder holder = new ViewHolder();

       if(row==null){
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           row = inflater.inflate(layout, null);

           holder.txtId = (TextView) row.findViewById(R.id.image_id_TextView);
           holder.imageView = (ImageView) row.findViewById(R.id.image_list_item);
           row.setTag(holder);
       }
       else{
           holder = (ViewHolder) row.getTag();
       }

       Image image = imageList.get(position);

       holder.txtId.setText(String.valueOf(image.getId()));

       holder.imageView.setImageBitmap(image.get_bitMap());


        return row;
    }
}
