package com.msnegi.recyclerviewpagewise;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.msnegi.recyclerviewpagewise.interfaces.CallBackInterface;
import java.util.ArrayList;

public class PagingListFragment extends Fragment {

    public static final int GET_ITEM_LIST = 3643;
    int [] images = {
            R.drawable.avacado,
            R.drawable.banana,
            R.drawable.chakotha,
            R.drawable.chikku,
            R.drawable.coconut,
            R.drawable.custard_apple,
            R.drawable.dates,
            R.drawable.lychees,
            R.drawable.mango,
            R.drawable.mulberry,
            R.drawable.muskmelon,
            R.drawable.oranges,
            R.drawable.papayas,
            R.drawable.pears,
            R.drawable.star_fruit,
            R.drawable.watermelon
    };

    CallBackInterface callback;
    Context contxt;

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    ArrayList<Data> searchList = new ArrayList<Data>();
    private String searchParam = "";
    private int selStatus = 0;
    private String searchValue = "";
    private int itemID;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
        contxt = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Pagewise Listing");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_paging_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(searchList, contxt,new RecyclerViewCallBack(){
            @Override
            public void onItemClicked(int position) {
                Toast.makeText(getActivity(),searchList.get(position).title, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);  //<--
        recyclerView.setLayoutManager(layoutManager);   //<--

        SpacesItemDecoration decoration = new SpacesItemDecoration(15);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);   //<--

        EditText searchTxt = view.findViewById(R.id.searchTxt);
        Button searchBtn = view.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchTxt.getText().toString().length()>0 && !isStringASpace(searchTxt.getText().toString())){
                    searchList.clear();

                    searchParam = "";
                    selStatus = 0;
                    searchValue = searchTxt.getText().toString().trim();
                    itemID = 0;
                    getItemList(searchParam, selStatus, searchValue, 0);

                }else{
                    Toast.makeText(getActivity(),"Please specify some text",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            int lastItemPosition = firstVisibleItemPosition + visibleItemCount;

            if(lastItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= 10){
                itemID = searchList.get(searchList.size() -1).getItemID();
                getItemList(searchParam, selStatus, searchValue, itemID);
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    public void initViews() {
        searchList.add(new Data(0, "avacado","avacado description", images[0]));
        searchList.add(new Data(1,"banana","banana description",images[1]));
        searchList.add(new Data(2, "chakotha","chakotha description",images[2]));
        searchList.add(new Data(3, "chikku","chikku description",images[3]));
        searchList.add(new Data(4, "coconut","coconut description",images[4]));
        searchList.add(new Data(5, "custard_apple","custard_apple description",images[5]));
        searchList.add(new Data(6, "dates","dates description",images[6]));
        searchList.add(new Data(7, "lychees","lychees description",images[7]));
        searchList.add(new Data(8, "mango","mango description",images[8]));
        searchList.add(new Data(9, "mulberry","mulberry description",images[9]));
        searchList.add(new Data(10, "muskmelon","muskmelon description",images[10]));
        searchList.add(new Data(11, "oranges","oranges description",images[11]));
        searchList.add(new Data(12, "papayas","papayas description",images[12]));
        searchList.add(new Data(13, "pears","pears description",images[13]));
        searchList.add(new Data(14, "star_fruit","star_fruit description",images[14]));
    }

    private void getItemList(String searchParam, int status, String searchvalue, int itemID){
        //KeyBoardManager.dismissSoftKeyboard(activity);
        //-- call service to get the next page
    }

    /*public void onSuccess(ResponseModel response, int tag) {
        if(tag == GET_ITEM_LIST){
            if (response.isSuccess()) {
                if (response.getResults() != null && !response.getResults().isEmpty()) {
                    searchList.addAll(response.getResults());
                    adapter.setAdapterArray(searchList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(),"Data not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(),response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public void onError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    private boolean isStringASpace(String str) {
        boolean isStringASpace = true;
        str = str.trim();
        if (str.length() > 0) {
            isStringASpace = false;
        } else {
            isStringASpace = true;
        }
        return isStringASpace;
    }
}