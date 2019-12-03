package com.example.playodemoproject.datamodule;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.playodemoproject.R;
import com.example.playodemoproject.models.Hit;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {


    private Context context;
    private List<Hit> arrayList;

    public NewsListAdapter(List<Hit> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        final View inflate = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (arrayList.get(position).getTitle() == null || arrayList.get(position).getTitle().equalsIgnoreCase("")) {
            holder.productName.setText("Title");
        } else {
            holder.productName.setText(arrayList.get(position).getTitle());
        }
        if (arrayList.get(position).getTitle() == null || arrayList.get(position).getTitle().equalsIgnoreCase("")) {
            holder.productAuthor.setText("Author");
        } else {
            holder.productAuthor.setText(arrayList.get(position).getAuthor());
        }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).getUrl()!=null){
                    if (!arrayList.get(position).getUrl().equalsIgnoreCase("")) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(arrayList.get(position).getUrl()));
                        context.startActivity(i);
                    }
                }else {
                    Toast.makeText(context, "The Given Url is Empty", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    public void setItemModelList(List<Hit> itemModels) {
        arrayList = itemModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView productAuthor;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productAuthor = itemView.findViewById(R.id.productAuthor);
            productName = itemView.findViewById(R.id.productName);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
