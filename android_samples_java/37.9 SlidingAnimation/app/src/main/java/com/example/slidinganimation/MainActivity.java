package com.example.slidinganimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    FrameLayout drawerLL;
    FloatingActionButton fabBtn;
    ListView selectionItem;
    LinearLayout popup;
    boolean isOpen = false;
    boolean isPopupOpen = false;

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    private Button previousbtn, nextbtn;
    private ImageSwitcher imgsw;
    private int[] images = {R.drawable.img1,R.drawable.img2};
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLL = findViewById(R.id.drawerLL);
        ObjectAnimator.ofFloat(drawerLL, "translationX", -220).setDuration(500).start();

        findViewById(R.id.clickbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen) {
                    ObjectAnimator.ofFloat(drawerLL, "translationX", -220).setDuration(500).start();
                    isOpen = false;
                }else {
                    ObjectAnimator.ofFloat(drawerLL, "translationX", 0).setDuration(500).start();
                    isOpen = true;
                }
            }
        });

        fabBtn = findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setVisibility(View.VISIBLE);
                popup.setX((fabBtn.getX() - popup.getMeasuredWidth())+(fabBtn.getMeasuredWidth()/2));
                popup.setY(fabBtn.getY() - popup.getMeasuredHeight());
                popup.setPivotX(popup.getMeasuredWidth());
                popup.setPivotY(popup.getMeasuredHeight());

                if(isPopupOpen) {
                    /*ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(popup,
                            PropertyValuesHolder.ofFloat("scaleX", 0.f),
                            PropertyValuesHolder.ofFloat("scaleY", 0.f));
                    animator.setDuration(250);
                    animator.start();*/

                    popup.animate()
                            .scaleX(0.f)
                            .scaleY(0.f)
                            .setDuration(250)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    Toast.makeText(MainActivity.this,"popup closed",Toast.LENGTH_SHORT).show();
                                }
                            }).start();

                    isPopupOpen = false;
                    fabBtn.animate().rotation(0).setDuration(250).start();
                }else {
                    /*ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(popup,
                            PropertyValuesHolder.ofFloat("scaleX", 1.f),
                            PropertyValuesHolder.ofFloat("scaleY", 1.f));
                    animator.setDuration(250);
                    animator.start();*/

                    popup.animate()
                            .scaleX(1.f)
                            .scaleY(1.f)
                            .setDuration(250)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    Toast.makeText(MainActivity.this,"popup opened",Toast.LENGTH_SHORT).show();
                                }
                            }).start();

                    isPopupOpen = true;
                    fabBtn.animate().rotation(-45).setDuration(220).start();
                }
            }
        });

        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,mobileArray);
        selectionItem = findViewById(R.id.selectionItem);
        selectionItem.setAdapter(adapter);
        //selectionItem.setX(fabBtn.getX());
        //selectionItem.setY(fabBtn.getY());

        popup = findViewById(R.id.popup);
        popup.setX(fabBtn.getX() - popup.getMeasuredWidth());
        popup.setY(fabBtn.getY() - popup.getMeasuredHeight());
        popup.setPivotX(popup.getMeasuredWidth());
        popup.setPivotY(popup.getMeasuredHeight());

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(popup,
                PropertyValuesHolder.ofFloat("scaleX", 0.f),
                PropertyValuesHolder.ofFloat("scaleY", 0.f));
        animator.setDuration(0);
        animator.start();

        popup.animate()
                .scaleX(0.f)
                .scaleY(0.f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Toast.makeText(MainActivity.this,"Animation Ended",Toast.LENGTH_SHORT).show();
                    }
                }).start();

        //----- Text Switcher

        final TextSwitcher tvSwitcher = findViewById(R.id.tvSwitcher);
        tvSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(MainActivity.this);
                t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                t.setTextAppearance(MainActivity.this, android.R.style.TextAppearance_Large);
                return t;
            }
        });

        Animation in = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out);
        tvSwitcher.setInAnimation(in);
        tvSwitcher.setOutAnimation(out);
        tvSwitcher.setCurrentText(String.valueOf("Hello!"));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                tvSwitcher.setText(String.valueOf("Bye!"));
            }
        }, 3000);

        /*ImageSwitcher*/

        previousbtn = (Button)findViewById(R.id.btnPrevious);
        nextbtn = (Button)findViewById(R.id.btnNext);
        imgsw = (ImageSwitcher) findViewById(R.id.imgSw);
        imgsw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imgVw= new ImageView(MainActivity.this);
                imgVw.setImageResource(images[position]);
                return imgVw;
            }
        });
        imgsw.setInAnimation(this, android.R.anim.slide_in_left);
        imgsw.setOutAnimation(this, android.R.anim.slide_out_right);
        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0)
                    position--;
                else if(position<0)
                    position = 0;
                imgsw.setImageResource(images[position]);
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<images.length)
                    position++;
                if(position>=images.length)
                    position = images.length-1;
                imgsw.setImageResource(images[position]);
            }
        });

        /*Drawable Transitions*/

        Drawable[] backgrounds = new Drawable[2];
        backgrounds[0] = getResources().getDrawable(R.drawable.switch_on);
        backgrounds[1] = getResources().getDrawable(R.drawable.switch_off);

        ImageView ivPosition = (ImageView) findViewById(R.id.ivPosition);

        //TransitionDrawable drawable = (TransitionDrawable) ivPosition.getDrawable();
        //drawable.startTransition(500);

        TransitionDrawable crossfader = new TransitionDrawable(backgrounds);
        ivPosition.setImageDrawable(crossfader);
        crossfader.startTransition(3000);

        // make sure the transition occurred
        //crossfader.startTransition(0);

        // reverse transition
        //crossfader.reverseTransition(3000);
    }
}
