package com.example.prc

import android.R
import android.os.Bundle
import android.view.View
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.prc.databinding.ActivityTestBinding


class TestKt : Base() {

    lateinit var bind: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.arrowButton.setOnClickListener { view ->
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (bind.hiddenView.visibility == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(bind.baseCardview, AutoTransition())
                bind.hiddenView.visibility = View.GONE
                bind.arrowButton.setImageResource(R.drawable.ic_delete)
            } else {
                TransitionManager.beginDelayedTransition(bind.baseCardview, AutoTransition())
                bind.hiddenView.visibility = View.VISIBLE
                bind.arrowButton.setImageResource(R.drawable.ic_delete)
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        if (hasBluetoothPermission()) {
//            isBluetoothHeadsetConnected().log()
//            isWiredHeadsetConnected().log()
//        }
//    }
//
//    fun hasBluetoothPermission(): Boolean {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) return true
//
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 111)
//            return false
//        }
//        return true
//    }
//
//    fun isWiredHeadsetConnected() = (getSystemService(AUDIO_SERVICE) as AudioManager).isWiredHeadsetOn
//
//    fun isBluetoothHeadsetConnected(): Boolean {
//        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        return (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED)
//    }
}

