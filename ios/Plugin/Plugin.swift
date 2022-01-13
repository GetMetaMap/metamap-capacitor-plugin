import Foundation
import Capacitor
import MatiSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(MatiCapacitorPlugin)
public class MatiCapacitorPlugin: CAPPlugin {
    
    @objc func showMatiFlow(_ call: CAPPluginCall) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            var metadata = call.getObject("metadata") ?? [:]
            metadata["sdk_type"] = "capacitor"
            Mati.shared.showMatiFlow(clientId: call.getString("clientId") ?? "",
                                    flowId: call.getString("flowId") ?? "",
                                    metadata: metadata)
            MatiButtonResult.shared.delegate = self
            call.resolve()
        }
    }
}

extension MatiCapacitorPlugin: MatiButtonResultDelegate {
    public func verificationSuccess(identityId: String?, verificationID: String?) {
        self.bridge?.triggerWindowJSEvent(eventName:  "verificationSuccess", data: identityId ?? "")
        debugPrint("verificationSuccessIdentityId : \(identityId)")
        
        self.bridge?.triggerWindowJSEvent(eventName:  "verificationSuccessVerificationID", data: verificationID ?? "")
        debugPrint("verificationSuccessVerificationID: \(verificationID)")
    }
        
    public func verificationCancelled() {
        self.bridge?.triggerWindowJSEvent(eventName: "verificationCancelled")
        debugPrint("verificationCancelled")
    }
}
