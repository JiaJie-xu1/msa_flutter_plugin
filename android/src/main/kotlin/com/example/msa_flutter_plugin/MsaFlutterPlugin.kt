package com.example.msa_flutter_plugin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** MsaFlutterPlugin */
public class MsaFlutterPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var applicationContext: Context
    private lateinit var map: Map<String, Any>

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        onAttachedToEngine(flutterPluginBinding.applicationContext, flutterPluginBinding.binaryMessenger)
    }

    private fun onAttachedToEngine(applicationContext: Context, messenger: BinaryMessenger) {
        this.applicationContext = applicationContext
        channel = MethodChannel(messenger, "msa_flutter_plugin")
        channel.setMethodCallHandler(this)

        try {
            MSAHelper(object : MSAHelper.AppIdsUpdater {
                override fun OnIdsAvalid(
                        isSupport: Boolean,
                        oaid: String,
                        vaid: String,
                        aaid: String
                ) {
                    Log.e("msa", "isSupport:$isSupport  , oaid:$oaid ")

                    map = mapOf("isSupport" to isSupport, "oaid" to oaid, "vaid" to vaid, "aaid" to aaid)
                }

                override fun oError(errorCode: String) {
                    Log.e("msa", "errCode:$errorCode")
                }
            }).getDeviceIds(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val instance = MsaFlutterPlugin()
            instance.onAttachedToEngine(registrar.context(), registrar.messenger())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "getMSAIDConfigs") {
            result.success(map)
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
