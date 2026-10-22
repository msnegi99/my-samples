package com.msnegi.dropdownsearch;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.msnegi.dropdownsearch.MainActivity;
import com.msnegi.dropdownsearch.CallBackInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DropDownSearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String[] titles = new String[]
            { "Avacado", "Banana", "Chakotha", "Chikku",
                    "Coconut", "Custard apple", "Dates", "Lychees",
                    "Mango", "Mulberry", "Muskmelon", "Oranges",
                    "Papayas", "Pears", "Star fruit", "Watermelon"};

    public static final String[] descriptions = new String[]
            { "The avocado (Persea americana) is a tree native to Mexico and Central America.",
                    "A banana is an edible fruit, botanically a berry, produced by several kinds of large herbaceous flowering plants in the genus Musa.",
                    "The grapefruit was bred in the 18th century as a cross between a pomelo and an orange.",
                    "Sapota, popularly known in India as chiku, is native to tropical America Most probably to south Mexico or Central America.",
                    "The Coconut tree (Cocos nucifera) is a member of the family Arecaceae (palm family).",
                    "Custard apple, is a common name for a fruit, and the tree which bears it, Annona reticulata.",
                    "Wonderfully sweet, dates are one of the most popular fruits of Arabian Peninsula.",
                    "It is a tropical and subtropical fruit tree native to the Guangdong and Fujian provinces of China.",
                    "The mango is a juicy stone fruit belonging to the genus Mangifera.",
                    "Morus, a genus of flowering plants in the family Moraceae, comprises 10–16 species of deciduous trees commonly known as mulberries.",
                    "Muskmelon (Cucumis melo) is a species of melon that has been developed into many cultivated varieties.",
                    "Juicy and sweet and renowned for its concentration of vitamin C, oranges make the perfect snack.",
                    "The papaya (from Carib via Spanish), papaw or pawpaw is the fruit of the plant Carica papaya.",
                    "The pear is any of several tree and shrub species of genus Pyrus.",
                    "Carambola, also known as starfruit, is the fruit of Averrhoa carambola, a species of tree native to the Philippines, Indonesia, Malaysia, India, Bangladesh.",
                    "Citrullus lanatus is a plant species in the family Cucurbitaceae, a vine-like flowering plant originally from West Africa."};

    public static final Integer[] images =
            { R.drawable.avacado, R.drawable.banana, R.drawable.chakotha, R.drawable.chikku,
                    R.drawable.coconut, R.drawable.custard_apple, R.drawable.dates, R.drawable.lychees,
                    R.drawable.mango, R.drawable.mulberry, R.drawable.muskmelon, R.drawable.oranges,
                    R.drawable.papayas, R.drawable.pears, R.drawable.star_fruit, R.drawable.watermelon};

    ListView listView;
    ArrayList<RowItem> rowItems;
    Spinner spin;
    List<String> fruits = new ArrayList<String>();
    CustomBaseAdapter adapter;


    CallBackInterface callback;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Search on DropDown Sample");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_drop_down_search, container, false);

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < titles.length; i++)
        {
            RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
            rowItems.add(item);
        }

        fruits = Arrays.asList(titles);

        spin = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, fruits);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        spin.setSelection(0);

        if(fruits.size() < 10){
            spin.setOnItemSelectedListener(this);
        }else{
            spin.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        showCompanyDialog(rowItems);
                    }
                    return true;
                }
            });
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private AlertDialog dialog = null;

    public void showCompanyDialog(ArrayList<RowItem> list){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View loginFormView = getLayoutInflater().inflate(R.layout.layout_search_dialog, null);
        builder.setView(loginFormView);

        ListView listView = (ListView) loginFormView.findViewById(R.id.contactlist);
        adapter = new CustomBaseAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        EditText searchText = (EditText) loginFormView.findViewById(R.id.searchtxt);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
}

