package com.example.bottomsortmenu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter
{
	private ArrayList<Item> itemList;
	private Activity activity;

	public CustomBaseAdapter(Activity activity, ArrayList<Item> itemList)
	{
		super();
		this.itemList = itemList;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null)
		{
            holder = new ViewHolder();
			convertView = inflator.inflate(R.layout.row_item, null);

            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
            holder.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}
		else
		{
            holder = (ViewHolder) convertView.getTag();
		}

        holder.txtViewTitle.setText(itemList.get(position).getCountry());
        holder.imgViewFlag.setImageResource(itemList.get(position).getFlag());

		return convertView;
	}

	@Override
	public int getCount()
	{
		return itemList.size();
	}

	@Override
	public Item getItem(int position)
	{
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	public static class ViewHolder
	{
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
	}

}
