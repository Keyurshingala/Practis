package com.example.prc

import android.os.Bundle
import com.android.billingclient.api.*
import com.android.billingclient.api.Purchase.PurchaseState.PENDING
import com.android.billingclient.api.Purchase.PurchaseState.PURCHASED
import com.example.prc.databinding.ActivityInAppBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InAppActivity : Base() {
    lateinit var bind: ActivityInAppBinding


    private lateinit var billingClient: BillingClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityInAppBinding.inflate(layoutInflater)
        setContentView(bind.root)

        billingClient = BillingClient.newBuilder(this)
                .setListener { billingResult, purchases ->
//                PurchasesUpdatedListener
                    "//billingClient To be implemented in a later section.".log()

                }.enablePendingPurchases().build()


        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    "onBillingSetupFinished The BillingClient is ready. You can query purchases here.".log()

                    val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
                            .setProductList(listOf(QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId("product_id_example")
                                    .setProductType(BillingClient.ProductType.SUBS)
                                    .build())
                            ).build()

                    billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
                        // check billingResult
                        "queryProductDetailsAsync process returned productDetailsList".log()

                        val selectedPlan = 0
                        // TODO: start purchase flow
                        val productDetailsParamsList = listOf(BillingFlowParams.ProductDetailsParams.newBuilder()
                                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                .setProductDetails(productDetailsList[selectedPlan])
                                // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                                // for a list of offers that are available to the user
                                .setOfferToken(productDetailsList[selectedPlan].subscriptionOfferDetails!![selectedPlan].offerToken)
                                .build()
                        )

                        val billingFlowParams = BillingFlowParams.newBuilder()
                                .setProductDetailsParamsList(productDetailsParamsList)
                                .build()

                        // Launch the billing flow
                        val billingResult = billingClient.launchBillingFlow(this@InAppActivity, billingFlowParams)

                        billingResult.debugMessage.log()
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                        } else {
                            "not ok".log()
                        }
                    }


                }
            }

            override fun onBillingServiceDisconnected() {
                "// Try to restart the connection on the next request to".log()
                // Google Play by calling the startConnection() method.
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val params = QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS)

        // uses queryPurchasesAsync Kotlin extension function
        CoroutineScope(Dispatchers.IO).launch {

            val purchasesResult = billingClient.queryPurchasesAsync(params.build())
            // check purchasesResult.billingResult
            "onResume process returned purchasesResult.purchasesList, e.g. display the plans user owns".log()
        }
    }

    fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        "onPurchasesUpdated".log()
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            "ok".log()
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            "// Handle an error caused by a user cancelling the purchase flow.".log()
        } else {
            "// Handle any other error codes.".log()
        }
    }


    private fun handlePurchase(purchase: Purchase) {
        purchase.gson().log()
        if (purchase.purchaseState == PURCHASED) {

        } else if (purchase.purchaseState == PENDING) {

        }

        if (purchase.purchaseState == PURCHASED) {
            if (!purchase.isAcknowledged) {

                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken)

                CoroutineScope(Dispatchers.IO).launch {
                    val ackPurchaseResult = withContext(Dispatchers.IO) {
                        billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
                    }
                }
            }
        }
    }
}

/*
class BillingClientWrapper(context: Context) : PurchasesUpdatedListener {
    interface OnQueryProductsListener {
        fun onSuccess(products: List<SkuDetails>)
        fun onFailure(error: Error)
    }
    class Error(val responseCode: Int, val debugMessage: String)
    private val billingClient = BillingClient
            .newBuilder(context)
            .enablePendingPurchases()
            .setListener(this)
            .build()

    fun queryProducts(listener: OnQueryProductsListener) {
        val skusList = listOf("premium_sub_month", "premium_sub_year", "some_inapp")
        queryProductsForType(skusList, BillingClient.SkuType.SUBS) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val products = skuDetailsList ?: mutableListOf()
                queryProductsForType(skusList, BillingClient.SkuType.INAPP) { billingResult, skuDetailsList ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        products.addAll(skuDetailsList ?: listOf())
                        listener.onSuccess(products)
                    } else {
                        listener.onFailure(Error(billingResult.responseCode, billingResult.debugMessage))
                    }
                }
            } else {
                listener.onFailure(Error(billingResult.responseCode, billingResult.debugMessage))
            }
        }
    }
    private fun queryProductsForType(skusList: List<String>, @BillingClient.SkuType type: String, listener: SkuDetailsResponseListener) {
        onConnected {
            billingClient.querySkuDetailsAsync(
                    SkuDetailsParams.newBuilder().setSkusList(skusList).setType(type).build(),
                    listener
            )
        }
    }
    private fun onConnected(block: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                block()
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
    }

}*/
