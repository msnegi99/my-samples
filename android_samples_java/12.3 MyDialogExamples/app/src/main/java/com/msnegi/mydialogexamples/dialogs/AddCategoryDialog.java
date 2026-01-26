package com.msnegi.mydialogexamples.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.text.InputFilter;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.msnegi.mydialogexamples.R;
import com.msnegi.mydialogexamples.utils.KeyBoardManager;
import com.msnegi.mydialogexamples.utils.StringInput;

import java.util.Objects;

public class AddCategoryDialog  extends DialogFragment {

    ImageView img_cross;
    AddCategoryDialog.MyCallback myCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_category, container, false);

        EditText new_category = view.findViewById(R.id.new_category);
        new_category.setFilters(new InputFilter[]{new StringInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ._-~&0123456789!@#$%^&*+=?"), new InputFilter.LengthFilter(20)});

        Button updateButton = (Button) view.findViewById(R.id.saveNewCategory);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardManager.dismissSoftKeyboard(getActivity());
                if(new_category.getText().toString().trim().length() != 0)
                    myCallback.onSubmit(new_category.getText().toString().trim());
                else
                    Toast.makeText(getContext(),getString(R.string.empty_categories),Toast.LENGTH_SHORT).show();
            }
        });

        img_cross = view.findViewById(R.id.img_cross);
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallback.onCrossImgClick();
            }
        });

        setCancelable(false);
        return view;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout( (int)(size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT );
        window.setGravity( Gravity.CENTER );
        super.onResume();
    }

    public void openMessagePopup(FragmentManager fragmentManager, String tag, AddCategoryDialog.MyCallback myCallback) {
        if (!isVisible() && !isAdded() && fragmentManager!=null) {
            fragmentManager.beginTransaction();
            show(fragmentManager, tag);
            this.myCallback = myCallback;
        }
    }

    public interface MyCallback {
        void onSubmit(String new_category);
        void onCrossImgClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
