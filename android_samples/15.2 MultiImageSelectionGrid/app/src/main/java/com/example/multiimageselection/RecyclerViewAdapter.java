package com.example.multiimageselection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<File> files;
    private List<String> filesStr;
    Context context;

    public RecyclerViewAdapter(ArrayList<File> files, ArrayList<String> filesStr, Context context) {
        this.files = files;
        this.filesStr = filesStr;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout , parent,false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Bitmap mBitmap = null;
        try {
            mBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(files.get(position)));
            if (mBitmap == null)
                mBitmap = getBitmapWithAuthority(Uri.fromFile(files.get(position)));
            ((View_Holder) holder).imageView.setImageBitmap(mBitmap);
        } catch (IOException ie) {
            ((View_Holder) holder).imageView.setImageResource(R.drawable.no_image);
        }

        ((View_Holder) holder).imgdeleteIcon.setId(position);
        ((View_Holder) holder).imgdeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    files.remove(view.getId());
                    filesStr.remove(view.getId());
                    notifyDataSetChanged();
                } catch (NullPointerException ne) {
                } catch (ArrayIndexOutOfBoundsException ne) {
                } catch (IndexOutOfBoundsException iob) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class View_Holder extends RecyclerView.ViewHolder
    {
        ImageView imageView, imgdeleteIcon;

        public View_Holder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            imgdeleteIcon = (ImageView) view.findViewById(R.id.imgdeleteIcon);
        }
    }

    public Bitmap getBitmapWithAuthority(Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                //imageView.setImageBitmap(bmp);
                return bmp;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException ne) {
                }
            }
        }
        return null;
    }

    public void update(ArrayList<File> files, ArrayList<String> filesStr){
        this.files = files;
        this.filesStr = filesStr;
    }
}
