import Foundation
import Capacitor
import MatiGlobalIDSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(MatiCapacitorPlugin)
public class MatiCapacitorPlugin: CAPPlugin {
    
    @objc func initialization(_ call: CAPPluginCall) {
        MFKYC.register(clientId: call.getString["clientId"], metadata: call.getString["metadata"])
        call.success()
    }
    
    @objc func setParams(_ call: CAPPluginCall) {
        let matiButton = MFKYCButton()
        matiButton.flowId = call.getString("flowId")
        DispatchQueue.main.async { [weak self] in
            guard
                self?.widget.view.superview == nil,
                let widgetView = self?.widget.view else { return }
            let mainView = self?.bridge.viewController.view
            mainView?.addSubview(matiButton)
        }
        call.success()
    }
    
}
