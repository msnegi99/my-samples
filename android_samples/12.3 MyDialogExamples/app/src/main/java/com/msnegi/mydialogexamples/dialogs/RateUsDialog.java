package com.msnegi.mydialogexamples.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.msnegi.mydialogexamples.R;

public class RateUsDialog  extends DialogFragment {

    ImageView img_cross;
    ConfirmDeleteDialog.MyCallback myCallback;

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
        View view = inflater.inflate(R.layout.dialog_rate_us, container, false);

        Button btnYes = (Button) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallback.onSubmit();
            }
        });

        Button btnNo = (Button) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallback.onCrossImgClick();
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

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
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

    public void openMessagePopup(FragmentManager fragmentManager, String tag, ConfirmDeleteDialog.MyCallback myCallback) {
        if (!isVisible() && !isAdded() && fragmentManager!=null) {
            fragmentManager.beginTransaction();
            show(fragmentManager, tag);
            this.myCallback = myCallback;
        }
    }

    public interface MyCallback {
        void onSubmit();
        void onCrossImgClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}