package com.msnegi.slidebanner.banner;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.msnegi.dialogbanner.R;
import com.msnegi.slidebanner.banner.pojo.OnItemClick;
import com.msnegi.slidebanner.banner.pojo.OptionItem;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.AppCompatTextView;

public class OptionSliderView extends FrameLayout {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<OptionItem> optionItems = new ArrayList<>();
    private SliderLayout sliderLayout;
    private OnItemClick onItemClick;
    private int itemSpan;
    private View v;

    public OptionSliderView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OptionSliderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public OptionSliderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OptionSliderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(R.layout.view_option_slider, null);
        sliderLayout = v.findViewById(R.id.slider);
        addView(v, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void initialize(List<OptionItem> optionItems, OnItemClick onItemClick, int itemSpan){
        this.optionItems = optionItems;
        this.onItemClick = onItemClick;
        this.itemSpan = itemSpan;
        designSlider();
    }

    public void setBackgroundColor(int color){
        v.setBackgroundColor(color);
    }

    public void setIndicatorColor(int selectedColor, int unselectedColor){
        //sliderLayout.getmIndicator().setIndicatorColor(selectedColor, unselectedColor);
        sliderLayout.getPagerIndicator().setDefaultIndicatorColor(selectedColor, unselectedColor);
    }

    private void designSlider() {
        sliderLayout.removeAllSliders();
        int index = 0;
        while (index < optionItems.size()) {
            if(optionItems.size() > index + itemSpan ) {
                sliderLayout.addSlider(new SliderView(context, optionItems.subList(index, index + itemSpan), onItemClick));
            }else {
                sliderLayout.addSlider(new SliderView(context, optionItems.subList(index, optionItems.size()), onItemClick));
            }
            index += itemSpan;
        }
        sliderLayout.stopAutoCycle();
    }

    class SliderView extends BaseSliderView {
        private List<OptionItem> optionItems = new ArrayList<>();
        private OnItemClick onItemClick;

        protected SliderView(Context context, List<OptionItem> optionItems, OnItemClick onItemClick) {
            super(context);
            this.optionItems = optionItems;
            this.onItemClick = onItemClick;
        }

        @Override
        public View getView() {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for(final OptionItem optionItem : optionItems) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.view_option_slider_item, null);
                ImageView target = v.findViewById(R.id.image);
                AppCompatTextView title = v.findViewById(R.id.title);

                if(optionItem.getImgResource() != -1)
                    target.setImageResource(optionItem.getImgResource());
                title.setText(optionItem.getName());

                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.OnItemClick(optionItem, 0);
                    }
                });
                layout.addView(v, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            }
            bindEventAndShow(layout, null);
            return layout;
        }
    }

}
