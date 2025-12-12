package com.msnegi.basiccomponents;

import java.io.InputStream;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter
{
	Context context;
	List<Product> products;

	public GridViewAdapter(Context context, List<Product> items)
	{
		this.context = context;
		this.products = items;
	}

	static class ViewHolder
	{
		ImageView imageView;
		TextView txtTitle;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.grid_item, null);
			
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		Product product = (Product) getItem(position);

		holder.txtTitle.setText(product.gettitle());
		//holder.imageView.setImageResource("images/" + product.getimage());
		
		try
		{
			AssetManager manager = context.getAssets();
			InputStream is = manager.open("images/" + product.getimage());
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			holder.imageView.setImageBitmap(bitmap);
		}
		catch(Exception e)
		{}

		return convertView;
	}

	@Override
	public int getCount()
	{
		return products.size();
	}

	@Override
	public Object getItem(int position)
	{
		return products.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return products.indexOf(getItem(position));
	}
}