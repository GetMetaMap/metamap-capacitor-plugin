import Foundation
import Capacitor
import MatiGlobalIDSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(MatiCapacitorPlugin)
public class MatiCapacitorPlugin: CAPPlugin {
    
    private var matiButton: MFKYCButton?
    
    @objc func initialization(_ call: CAPPluginCall) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            MFKYC.register(clientId: call.getString("clientId") ?? "", metadata: call.getObject("metadata") ?? nil)
        }
        call.success()
    }
    
    @objc func setParams(_ call: CAPPluginCall) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            self.matiButton = MFKYCButton()
            self.matiButton?.flowId = call.getString("flowId") ?? ""
            call.success()
        }
    }
    
    @objc func showFlow(_ call: CAPPluginCall) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            if self.matiButton == nil {
                self.matiButton = MFKYCButton()
                self.matiButton?.sendActions(for: .touchUpInside)
            } else {
                self.matiButton?.sendActions(for: .touchUpInside)
            }
            call.success()
        }
    }
}

extension MatiCapacitorPlugin: MFKYCDelegate {
    public func mfKYCLoginSuccess(identityId: String) {
        self.bridge.triggerWindowJSEvent(eventName:  "mfKYCLoginSuccess", data: identityId)
        debugPrint("mfKYCLoginSuccess: \(identityId)")
    }
    
    public func mfKYCLoginCancelled() {
        self.bridge.triggerWindowJSEvent(eventName: "mfKYCLoginCancelled")
        debugPrint("mfKYCLoginCancelled")
    }
}
