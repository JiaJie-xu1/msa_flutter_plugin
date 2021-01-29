#import "MsaFlutterPlugin.h"
#if __has_include(<msa_flutter_plugin/msa_flutter_plugin-Swift.h>)
#import <msa_flutter_plugin/msa_flutter_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "msa_flutter_plugin-Swift.h"
#endif

@implementation MsaFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMsaFlutterPlugin registerWithRegistrar:registrar];
}
@end
