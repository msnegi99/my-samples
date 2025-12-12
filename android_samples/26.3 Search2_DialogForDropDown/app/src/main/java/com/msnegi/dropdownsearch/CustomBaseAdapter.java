package com.msnegi.dropdownsearch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdapter extends BaseAdapter implements Filterable
{
	Context context;
	List<RowItem> filterList;
	ArrayList<RowItem> listarray = new ArrayList<RowItem>();
	ArrayList<RowItem> originalArray = new ArrayList<RowItem>();
	ValueFilter valueFilter;
	private LayoutInflater mInflater = null;

	public CustomBaseAdapter(Activity activty, ArrayList<RowItem> list)
	{
		this.context = activty;
		mInflater = activty.getLayoutInflater();
		this.listarray = list;
		this.originalArray = list;
		mInflater = LayoutInflater.from(activty);
	}

	static class ViewHolder
	{
		ImageView imageView;
		TextView txtTitle;
		TextView txtDesc;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
				
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_item, null);
			
			holder = new ViewHolder();
			holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		RowItem rowItem = (RowItem) getItem(position);

		holder.txtDesc.setText(rowItem.getDesc());
		holder.txtTitle.setText(rowItem.getTitle());
		holder.imageView.setImageResource(rowItem.getImageId());

		return convertView;
	}

	@Override
	public int getCount()
	{
		return listarray.size();
	}

	@Override
	public Object getItem(int position)
	{
		return listarray.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return listarray.indexOf(getItem(position));
	}

	@Override
	public Filter getFilter() {
		if (valueFilter == null) {
			valueFilter = new ValueFilter();
		}
		return valueFilter;
	}

	private class ValueFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();

			if (constraint != null && constraint.length() > 0) {
				ArrayList<RowItem> filterList = new ArrayList<RowItem>();
				for (int i = 0; i < listarray.size(); i++) {
					if ((listarray.get(i).getTitle().toUpperCase())
							.contains(constraint.toString().toUpperCase())) {

						RowItem item = new RowItem(listarray.get(i).getImageId(), listarray.get(i).getTitle(), listarray.get(i).getDesc());
						filterList.add(item);
					}
				}
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = originalArray.size();
				results.values = originalArray;
			}
			return results;

		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			listarray = (ArrayList<RowItem>) results.values;
			notifyDataSetChanged();
		}

	}
}