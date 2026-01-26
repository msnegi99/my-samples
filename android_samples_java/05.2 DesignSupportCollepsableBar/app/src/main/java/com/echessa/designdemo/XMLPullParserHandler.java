package com.echessa.designdemo;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler
{
	List<Product> products;
	private Product product;
	private String text;

	public XMLPullParserHandler()
	{
		products = new ArrayList<Product>();
	}

	public List<Product> getProducts()
	{
		return products;
	}

	public List<Product> parse(InputStream is)
	{
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		try
		{
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();

			parser.setInput(is, null);

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT)
			{
				String tagname = parser.getName();
				switch (eventType)
				{
					case XmlPullParser.START_TAG:
						if (tagname.equalsIgnoreCase("product"))
						{
							product = new Product();
						}
						break;

					case XmlPullParser.TEXT:
						text = parser.getText();
						break;

					case XmlPullParser.END_TAG:
						if (tagname.equalsIgnoreCase("product"))
						{
							products.add(product);
						}
						else if (tagname.equalsIgnoreCase("image"))
						{
							product.setimage(text);
                            Log.d("MSNEGI", "image" + text);
						}
						else if (tagname.equalsIgnoreCase("title"))
						{
							product.settitle(text);
                            Log.d("MSNEGI", "title" + text);
						}						
						else if (tagname.equalsIgnoreCase("description"))
						{
							product.setdescription(text);
                            Log.d("MSNEGI", "description" + text);
						}
                        else if (tagname.equalsIgnoreCase("price"))
                        {
                            product.setprice(text);
                            Log.d("MSNEGI", "price" + text);
                        }
						break;

					default:
						break;
				}
				eventType = parser.next();
			}

		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return products;
	}
}