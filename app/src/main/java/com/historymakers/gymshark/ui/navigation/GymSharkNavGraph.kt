package com.historymakers.gymshark.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.historymakers.gymshark.ui.products.ProductsRoute
import com.historymakers.gymshark.ui.productDetail.ProductDetailRoute

@Composable
fun GymSharkNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.PRODUCTS,
        modifier = modifier
    ) {
        composable(route = AppRoutes.PRODUCTS) {
            ProductsRoute(
                modifier = modifier,
                onProductClick = { productId ->
                    navController.navigate(AppRoutes.productDetail(productId))
                }
            )
        }
        composable(AppRoutes.PRODUCT_DETAIL) {
            ProductDetailRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}