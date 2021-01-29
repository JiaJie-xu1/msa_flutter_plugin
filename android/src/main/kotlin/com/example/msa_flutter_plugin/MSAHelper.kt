package com.example.msa_flutter_plugin

import android.content.Context
import android.util.Log
import com.bun.miitmdid.core.ErrorCode
import com.bun.miitmdid.core.MdidSdkHelper
import com.bun.miitmdid.interfaces.IIdentifierListener
import com.bun.miitmdid.interfaces.IdSupplier

class MSAHelper(private val _listener: AppIdsUpdater) : IIdentifierListener {

    override fun OnSupport(isSupport: Boolean, _supplier: IdSupplier?) {
        _supplier?.let {
            val oaid = _supplier.oaid
            val vaid = _supplier.vaid
            val aaid = _supplier.aaid

            Log.e("xujj", "OnSupport:$oaid")
            _listener.OnIdsAvalid(isSupport, oaid, vaid, aaid)

        }
    }

    fun getDeviceIds(cxt: Context) {

        val nres: Int = CallFromReflect(cxt)

        when (nres) {
            ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT -> { //不支持的设备
            }
            ErrorCode.INIT_ERROR_LOAD_CONFIGFILE -> { //加载配置文件出错
            }
            ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT -> { //不支持的设备厂商
            }
            ErrorCode.INIT_ERROR_RESULT_DELAY -> { //获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程
            }
            ErrorCode.INIT_HELPER_CALL_ERROR -> { //反射调用出错
            }
        }
        if (nres != ErrorCode.INIT_ERROR_RESULT_DELAY) {
            _listener.oError(nres.toString())
        }
    }

    /*
     * 通过反射调用，解决android 9以后的类加载升级，导至找不到so中的方法
     *
     * */
    private fun CallFromReflect(cxt: Context): Int {
        return MdidSdkHelper.InitSdk(cxt, true, this)
    }

    interface AppIdsUpdater {
        fun OnIdsAvalid(
                isSupport: Boolean,
                oaid: String,
                vaid: String,
                aaid: String
        )

        fun oError(errorCode: String)
    }
}