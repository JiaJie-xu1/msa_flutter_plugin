import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:msa_flutter_plugin/msa_flutter_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  MSAData _data;

  @override
  void initState() {
    super.initState();
    initMSAtate();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initMSAtate() async {
    MSAData data = await MsaFlutterPlugin.getMsaIdConfigs();
    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _data = data;
      print("是否支持oaid：${data.isSupport}");
      print("获取oaid:${data.oaid}");
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('msa oaid: ${_data?.oaid}\n'),
        ),
      ),
    );
  }
}
