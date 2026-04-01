package com.msnegi.mycomposesamples.ui.productlist.composables

data class ProductModel(
    var id: String,
    var name: String? = null,
    var price: Double? = null,
    var image: String? = null,
    var description: String? = null,
    var nutrition: String? = null
)
