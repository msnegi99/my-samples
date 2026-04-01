package com.msnegi.mycomposesamples.presentation.productlist

import androidx.compose.runtime.Immutable
import com.msnegi.mycomposesamples.ui.productlist.composables.ProductModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
class ProductListViewState(
    val categoryTitle: String = "",
    val productList: ImmutableList<ProductModel> = persistentListOf()
) {
    companion object {
        val Empty = ProductListViewState()
    }
}
