package com.msnegi.websearchvolley;


import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

import com.msnegi.websearchvolley.MainActivity;
import com.msnegi.websearchvolley.webservice.WebserviceCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WebSearchVolleyFragment extends Fragment implements WebserviceCallback, TextView.OnEditorActionListener, View.OnClickListener  {

    ImageView activity_search_back,activity_search_cancel;
    EditText search_products_text;
    RecyclerView recyclerView;
    View networkView = null;
    ArrayList<SearchItem> searchlist;
    RecyclerAdapter<SearchItem> recyclerAdapter;
    ProgressBar progressbar;
    String searchString;
    Context context;
    FrameLayout search_result_container;
    boolean networkAvailable;

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
        View view = inflater.inflate(R.layout.fragment_web_search2, container, false);
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

        search_products_text.requestFocus();

        activity_search_back.setOnClickListener(this);
        activity_search_cancel.setOnClickListener(this);
        search_products_text.setOnEditorActionListener(this);

        return view;
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            searchString = textView.getText().toString().trim();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(search_products_text.getWindowToken(), 0);
            if (!searchString.isEmpty())
                getSearchResults(searchString);
            return true;
        }
        return false;
    }

    private void getSearchResults(String s) {
        try {
            JSONObject params = new JSONObject();
            params.put("query", s);
            //---->  new Webservice().fetchDataFromServer(getActivity(), "http://43.224.136.117/cello/rest/V1/product/suggest", params, this);
        }catch (Exception e){}
    }

    @Override
    public void success(Object obj, String message, String URL) {
        progressbar.setVisibility(View.GONE);
        try{
            search_result_container.setVisibility(View.VISIBLE);
            searchlist.clear();
            if(obj != null){
                try {
                    JSONArray jsonArray = ((JSONObject) obj).getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        searchlist.add(SearchItem.build((JSONObject) jsonArray.get(i)));
                    }
                }catch (Exception e){}
            }

            if (searchlist.isEmpty()) {
                if (networkView != null)
                    toggleNetworkView(true, "No Data found");
            }

            recyclerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            search_result_container.setVisibility(View.GONE);
            networkAvailable = false;
            toggleNetworkView(!networkAvailable, e.getMessage());
        }
    }

    @Override
    public void error(String message, String URL) {
        search_result_container.setVisibility(View.GONE);
        networkAvailable = false;
        toggleNetworkView(!networkAvailable, message);
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
                        getSearchResults(searchString);
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
