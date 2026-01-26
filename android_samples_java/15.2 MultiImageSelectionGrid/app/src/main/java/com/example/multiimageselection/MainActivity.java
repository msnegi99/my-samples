package com.example.multiimageselection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int [] images = {
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image,
            R.drawable.no_image
    };

    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission =  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    int PICK_IMAGE_MULTIPLE = 1;
    ArrayList<String> imagesEncodedList;
    private ArrayList<File> files;
    private ArrayList<String> filesStr;
    TextView imageCountTxt;
    LinearLayout attachments_ll;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permission, PERMISSIONS_REQUEST_CODE);

        files = new ArrayList<>();
        filesStr = new ArrayList<String>();

        imageCountTxt = findViewById(R.id.imageCountTxt);
        attachments_ll = findViewById(R.id.attachments_ll);

        Button imgSelection = findViewById(R.id.imgSelection);
        imgSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(files, filesStr,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false));

        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            try {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        try {
                            File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/Android/media/" + getPackageName() + "/");
                            if (!myDir.exists())
                                myDir.mkdirs();
                            File file = new File(Environment.getExternalStorageDirectory().getPath(), "/Android/media/" + getPackageName() + "/" + "image_" + System.currentTimeMillis() + ".jpg");
                            FileOutputStream out = new FileOutputStream(file);
                            Log.d("dest", file.toString());

                            InputStream in = getContentResolver().openInputStream(uri);
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }

                            in.close();
                            out.close();

                            String localpath = uri.getPath();
                            if (!filesStr.contains(localpath)) {
                                if(files.size() <= 6) {
                                    filesStr.add(localpath);
                                    files.add(file);
                                }else{
                                    Toast.makeText(this, "You can pick only 6 image", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(this, "Removed duplicate image", Toast.LENGTH_LONG).show();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(files.size()>0) renderLayout(files);
                    imageCountTxt.setText("You have picked " + files.size() + " images.");

                } else if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    try {
                        File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/Android/media/" + getPackageName() + "/");
                        if (!myDir.exists())
                            myDir.mkdirs();
                        File file = new File(Environment.getExternalStorageDirectory().getPath(), "/Android/media/" + getPackageName() + "/" + "image_" + System.currentTimeMillis() + ".jpg");
                        FileOutputStream out = new FileOutputStream(file);
                        Log.d("dest", file.toString());

                        InputStream in = getContentResolver().openInputStream(mImageUri);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        in.close();
                        out.close();

                        String localpath = mImageUri.getPath();
                        if (!filesStr.contains(localpath)) {
                            if(files.size() <= 6) {
                                filesStr.add(localpath);
                                files.add(file);
                            }else{
                                Toast.makeText(this, "You can pick only 6 image", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(this, "Removed duplicate image", Toast.LENGTH_LONG).show();
                        }

                        if(files.size()>0) renderLayout(files);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

            adapter.update(files, filesStr);
            adapter.notifyDataSetChanged();
        }
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    ImageView imageView;
    public void renderLayout(List<File> fileFromUri) {
        hideKeyboard(this);
        attachments_ll.removeAllViews();
        for (int i = 0; i < fileFromUri.size(); i++)
        {
            View v = getLayoutInflater().inflate(R.layout.item_attachment, null);
            imageView = (ImageView) v.findViewById(R.id.imgAttached);
            final TextView tv = (TextView) v.findViewById(R.id.tv);
            ImageView imgdeleteIcon = (ImageView) v.findViewById(R.id.imgdeleteIcon);
            tv.setText(String.valueOf(i));
            imgdeleteIcon.setVisibility(View.VISIBLE);
            Bitmap mBitmap = null;
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(fileFromUri.get(i)));
                if (mBitmap == null)
                    mBitmap = getBitmapWithAuthority(Uri.fromFile(fileFromUri.get(i)));
                imageView.setImageBitmap(mBitmap);
            } catch (IOException ie) {
                imageView.setImageResource(R.drawable.no_image);
            }

            imgdeleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        attachments_ll.removeViewAt(Integer.parseInt(tv.getText().toString().trim()));
                        files.remove(Integer.parseInt(tv.getText().toString()));
                        filesStr.remove(Integer.parseInt(tv.getText().toString()));
                        renderLayout(files);
                    } catch (NullPointerException ne) {
                        attachments_ll.removeAllViews();
                    } catch (ArrayIndexOutOfBoundsException ne) {
                        attachments_ll.removeAllViews();
                    } catch (IndexOutOfBoundsException iob) {
                        attachments_ll.removeAllViews();
                    }

                }
            });

            attachments_ll.addView(v);
            imageCountTxt.setText("You have picked " + files.size() + " images.");
        }
    }

    public Bitmap getBitmapWithAuthority(Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bmp);
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
}