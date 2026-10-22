package com.msnegi.mycompscreens.ui.screens.data.model

data class Item(
  val id: Int,
  val title: String,
  val subtitle: String,
  val imageId: Int,
  val source: String = "demo source"
)
