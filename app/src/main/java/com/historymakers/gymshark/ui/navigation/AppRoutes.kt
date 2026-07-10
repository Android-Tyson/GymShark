package com.historymakers.gymshark.ui.navigation

object AppRoutes {
    const val PRODUCTS = "products"
    const val PRODUCT_DETAIL = "product_detail/{productId}"

    fun productDetail(productId: String) = "product_detail/$productId"
}