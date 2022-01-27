import 'dart:async';

import 'package:flutter/services.dart';

class MsaFlutterPlugin {
  static const MethodChannel _channel =
      const MethodChannel('msa_flutter_plugin');

  static Future<MSAData> getMsaIdConfigs() async {
    var result = await _channel.invokeMethod("getMSAIDConfigs");
    var msaData = new MSAData();
    msaData.isSupport = result["isSupport"] ?? false;
    msaData.oaid = result["oaid"] ?? "";
    msaData.aaid = result["aaid"] ?? "";
    msaData.vaid = result["vaid"] ?? "";
    return msaData;
  }
}

/*
 * isSupport :是否支持MSA
 * time ：MSA SDK的初始化时间
 */
class MSAData {
  bool? isSupport;
  String? oaid;
  String? vaid;
  String? aaid;
}
