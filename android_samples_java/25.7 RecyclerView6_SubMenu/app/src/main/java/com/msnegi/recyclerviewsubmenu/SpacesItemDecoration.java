package com.msnegi.recyclerviewsubmenu;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace * 2;
        outRect.right = mSpace * 2;
        outRect.top = mSpace;
        //outRect.bottom = mSpace;

        /*if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace * 2;
            outRect.left = mSpace * 2;
        }

        if (parent.getChildAdapterPosition(view) == 1) {
            outRect.top = mSpace * 2;
            outRect.right = mSpace * 2;
        }

        if(parent.getChildAdapterPosition(view) > 1){
            if(isEven(parent.getChildAdapterPosition(view))){
                outRect.left = mSpace * 2;
            }else{
                outRect.right = mSpace * 2;
            }
        }*/
    }

    public boolean isEven(int pos){
        return (pos%2==0?true:false);
    }
}
