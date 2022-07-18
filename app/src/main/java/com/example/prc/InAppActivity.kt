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
                        productDetailsList.log()
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
            purchasesResult.log()
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
/*During account hold, the subscription is not returned by BillingClient.queryPurchasesAsync().*/

/*Warning: Don't remove a subscription from Google Play while any user is still entitled to the content.
Removing content to which a user is entitled results in penalties.*/

/*A cancelled subscription remains visible in the Play Store app until its expiration date.
A user can restore a cancelled subscription before it expires by clicking Resubscribe (previously Restore) in the Subscriptions section in the Google Play Store app.*/

/*If your app relies solely on queryPurchasesAsync() to check whether a user is entitled to a subscription,
then your app should automatically handle grace period, as queryPurchasesAsync() continues to return cancelled purchases before their expiration dates.*/

/*When a user's subscription is paused, the subscription is not returned by queryPurchasesAsync()*/

/*Note: Google Play dynamically extends the expiryTime value until the grace period has expired.*/

/*A user can voluntarily cancel a subscription from the Play Store or have their subscription automatically cancelled if they don't recover after being in account hold.
When a user cancels a subscription they retain access to the content until the end of the current billing cycle.
When the billing cycle ends, access is revoked.*/

/*Note: If a subscription is set to renew on the 29th, 30th, or 31st of the month, in the next February of a non-leap year,
the subscription renewal day is moved to the 28th and continues to renew on the 28th of each month for the duration of the subscription.
Similarly, if a user starts a subscription on March 31st, the subscription renews on April 30th and continues to renew on the 30th of each month.*/

/*The steps to create one-time products and subscriptions are similar.
For each product, you need to provide a unique product ID, a title, a description, and pricing information.
Subscriptions have additional required information,
such as the renewal period, whether you're offering a free trial, and whether the subscription has an introductory offer.*/

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
