package com.msnegi.basiccomponents;

public class Product
{
	private int image;
	private String title;	
	private String description = "";

	public Product(int image, String title, String description){
		this.image = image;
		this.title = title;
		this.description = description;
	}

	public void setimage(int image)
	{
		this.image = image;
	}

	public int getimage()
	{
		return image;
	}

	public void settitle(String title)
	{
		this.title = title;
	}

	public String gettitle()
	{
		return title;
	}	

	public void setdescription(String description)
	{
		this.description = description;
	}

	public String getdescription()
	{
		return description;
	}
}