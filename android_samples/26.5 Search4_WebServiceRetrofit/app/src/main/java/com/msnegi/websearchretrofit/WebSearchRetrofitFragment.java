package com.msnegi.websearchretrofit;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.msnegi.websearchretrofit.MainActivity;
//import com.msnegi.websearchretrofit.web.APIServices;
//import com.msnegi.websearchretrofit.web.ServiceCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import com.msnegi.websearchretrofit.web.APIServices;
import com.msnegi.websearchretrofit.web.ServiceCallback;
import java.util.ArrayList;
import java.util.HashMap;

public class WebSearchRetrofitFragment extends Fragment implements ServiceCallback, View.OnClickListener  {

    ImageView activity_search_back,activity_search_cancel;
    EditText search_products_text;
    RecyclerView recyclerView;
    View networkView = null;
    ArrayList<SearchItem> searchlist;
    RecyclerAdapter<SearchItem> recyclerAdapter;
    ProgressBar progressbar;
    FrameLayout search_result_container;
    Context context;
    boolean networkAvailable;
    String searchString = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        searchlist = new ArrayList<>();
        networkAvailable = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_web_search1, container, false);
        activity_search_back = view.findViewById(R.id.activity_search_back);
        activity_search_cancel = view.findViewById(R.id.activity_search_cancel);
        search_products_text = view.findViewById(R.id.search_products_text);
        networkView = view.findViewById(R.id.view_network_issue);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressbar = view.findViewById(R.id.progressbar);
        search_result_container = view.findViewById(R.id.search_result_container);

        recyclerAdapter = new RecyclerAdapter(getActivity(), searchlist, "search", new ItemOnClick() {
            @Override
            public void OnItemClick(Object obj, int pos, int type) {

            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new VerticalItemDecoration(1f));

        search_products_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null && !s.toString().trim().isEmpty() && s.toString().trim().length() > 2){
                    searchString = s.toString();
                    loadList(s.toString());
                }
                networkView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        activity_search_back.setOnClickListener(this);
        activity_search_cancel.setOnClickListener(this);

        search_products_text.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show

        return view;
    }

    private void loadList(String s) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("query", s);
        //----> new ApiUtils().setAsync(true).callService(getActivity(), APIServices.ServiceType.AUTO_SEARCH, params, this);
    }

    @Override
    public void success(@NotNull APIServices.ServiceType serviceType, @NotNull Object result, @NotNull String msg) {
        progressbar.setVisibility(View.GONE);

        try{
            searchlist.clear();
            if(result != null && result instanceof ArrayList){
                for(int i=0; i<((ArrayList) result).size(); i++){
                    searchlist.add(SearchItem.build((JSONObject) ((ArrayList) result).get(i)));
                }
            }

            /*if (searchlist.isEmpty()) {
            if (view_network_issue != null)
                Utilities.showErrorLayout(data_assert!!, "No Data found", false) {}
            }*/

            recyclerAdapter.notifyDataSetChanged();

        }catch (Exception e){
            search_result_container.setVisibility(View.GONE);
            networkAvailable = false;
            toggleNetworkView(!networkAvailable, e.getMessage());
        }
    }

    @Override
    public void fail(@NotNull APIServices.ServiceType serviceType, @NotNull String msg) {
        search_result_container.setVisibility(View.GONE);
        networkAvailable = false;
        toggleNetworkView(!networkAvailable, msg);
    }

    @Override
    public void cancel() {
    }

    private void toggleNetworkView(boolean show, String msg) {
        if (networkView != null) {
            if (show) {
                networkView.setVisibility(View.VISIBLE);
                AppCompatImageView reload = (AppCompatImageView) networkView.findViewById(R.id.network_reload_icon);
                TextView msgText = (TextView) networkView.findViewById(R.id.network_issue_message);
                msgText.setText(msg);
                reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadList(searchString);
                    }
                });
            } else {
                networkView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_search_back:
                ((MainActivity) context).onBackPressed();
                break;
            case R.id.activity_search_cancel:
                search_products_text.setText("");
                break;
        }
    }

    public class VerticalItemDecoration extends RecyclerView.ItemDecoration{
        private float value;
        public VerticalItemDecoration(float value){
            this.value = value;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = (int) value;
            outRect.bottom = (int) value;
        }
    }

    public class HorizontalItemDecoration extends RecyclerView.ItemDecoration{
        private float value;
        public HorizontalItemDecoration(float value){
            this.value = value;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = (int) value;
            outRect.right = (int) value;
        }
    }

    public class DividerItemDecoration extends androidx.recyclerview.widget.DividerItemDecoration {
        private float value;
        public DividerItemDecoration(Context context, int orientation, float value, Drawable drawable){
            super(context, orientation);
            this.value = value;
            this.setDrawable(drawable);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = (int) value;
            outRect.bottom = (int) value;
            outRect.left = (int) value;
            outRect.right = (int) value;
        }
    }

}
