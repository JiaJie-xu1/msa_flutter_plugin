# msa_flutter_plugin

A new Flutter plugin for MSA SDK.


## How to Use?
- 在pubspec.yaml中添加插件依赖

```
dependencies:
  flutter:
    sdk: flutter
  msa_flutter_plugin: ^1.0.25+1
```

- Add the following imports to your Dart code，example:
```
import 'package:msa_flutter_plugin/msa_flutter_plugin.dart';
...

class _MyHomePageState extends State<MyHomePage> {
  ...
  MSAData _data;

  Future<void> initMSAtate() async {
    MSAData data = await MsaFlutterPlugin.getMsaIdConfigs();
    
    if (!mounted) return;

    setState(() {
      _data = data;
      print("是否支持MSA：${data.isSupport}");
      print("oaid:${data.oaid}");
      print("vaid:${data.vaid}");
      print("aaid:${data.aaid}");
    });
  }
...
}

```
