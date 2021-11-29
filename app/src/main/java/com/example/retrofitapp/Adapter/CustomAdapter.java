package com.example.retrofitapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitapp.Model.RetroPhoto;
import com.example.retrofitapp.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<RetroPhoto> dataList;
    private Context context;

    public CustomAdapter(Context context,List<RetroPhoto> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       // public final View mView;

        public TextView txtTitle;

        private ImageView coverImage;

        ViewHolder(View itemView) {
            super(itemView);
           // mView = itemView;

            txtTitle = (TextView) itemView.findViewById(R.id.title);
            coverImage = (ImageView) itemView.findViewById(R.id.coverImage);
        }
    }

    //usually involve inflating a layout from xml and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Inflate the custom layout
        View contactView = layoutInflater.inflate(R.layout.custom_row_for_recyclerview, parent, false);
        //Return new holder instance
        return new ViewHolder(contactView);
    }

    //Involve populating data into the item through the holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         //get the data model based on position
        RetroPhoto retroPhoto = dataList.get(position);

        //set item view based on your views and data model
        TextView textView = holder.txtTitle;
        textView.setText(retroPhoto.getTitle());

       // holder.txtTitle.setText(dataList.get(position).getTitle());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

    }

    //return the total count of items in the list
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
