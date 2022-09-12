package com.example.prc.in_app_update

import android.content.Intent
import android.os.Bundle
import com.example.prc.Base
import com.example.prc.databinding.ActivityInAppUpdateBinding
import com.example.prc.log
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
//import com.google.android.play.core.ktx.installStatus
//import com.google.android.play.core.ktx.updatePriority


class InAppUpdateActivity : Base() {

    val IN_APP_UPDATE: Int by lazy { 111 }

    lateinit var bind: ActivityInAppUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityInAppUpdateBinding.inflate(layoutInflater)
        setContentView(bind.root)

        checkUpdate()
    }

    override fun onResume() {
        super.onResume()

        AppUpdateManagerFactory.create(this).appUpdateInfo.addOnSuccessListener { updateInfo ->
            if (updateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                showSnack()

            } else if (updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                if (updateInfo.updatePriority() >= 4) {
                    AppUpdateManagerFactory.create(this).startUpdateFlowForResult(
                            updateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            IN_APP_UPDATE
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IN_APP_UPDATE) {
            when (resultCode) {
                RESULT_OK -> "ok".tos()

                RESULT_CANCELED -> "canceled".tos()

                RESULT_IN_APP_UPDATE_FAILED -> checkUpdate()
            }
        }
    }

    private fun checkUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

        appUpdateManager.registerListener(object : InstallStateUpdatedListener {
            override fun onStateUpdate(it: InstallState) {
                when (it.installStatus()) {
                    InstallStatus.DOWNLOADED -> showSnack()
                    InstallStatus.INSTALLED -> appUpdateManager.unregisterListener(this)
                    InstallStatus.FAILED -> "FAILED : + ${it.installErrorCode()}".tosL()
                    InstallStatus.CANCELED -> "CANCELED".tos()
                    InstallStatus.UNKNOWN -> "UNKNOWN".tos()
                    else -> it.log()
                }
            }
        })

        appUpdateManager.appUpdateInfo.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                AppUpdateManagerFactory.create(this).startUpdateFlowForResult(
                        updateInfo,
                        this,
                        AppUpdateOptions.newBuilder(if (updateInfo.updatePriority() >= 4) AppUpdateType.IMMEDIATE else AppUpdateType.FLEXIBLE)
                                .setAllowAssetPackDeletion(true)
                                .build(),
                        IN_APP_UPDATE
                )
            }
        }
    }

    private fun showSnack() {
        Snackbar.make(bind.root, "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE)
                .setAction("RESTART") { AppUpdateManagerFactory.create(this).completeUpdate() }
                .show()
    }

}