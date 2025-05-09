import Foundation
import Capacitor
import MetaMapSDK

@objc(MetaMapCapacitorPlugin)
public class MetaMapCapacitorPlugin: CAPPlugin {

    private var output: CAPPluginCall?

    @objc func showMetaMapFlow(_ call: CAPPluginCall) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }

            let clientId = call.getString("clientId") ?? ""
            let flowId = call.getString("flowId") ?? ""
            var metadata = call.getObject("metadata") as? [String: Any] ?? [:]
            metadata["sdk_type"] = "capacitor"

            self.output = call
            MetaMapButtonResult.shared.delegate = self

            print("🚀 [MetaMapCapacitor] Starting flow with:")
            print("   • clientId: \(clientId)")
            print("   • flowId: \(flowId)")
            print("   • metadata: \(metadata)")

            MetaMap.shared.showMetaMapFlow(
                clientId: clientId,
                flowId: flowId,
                metadata: metadata
            )
        }
    }
}

extension MetaMapCapacitorPlugin: MetaMapButtonResultDelegate {
    public func verificationSuccess(identityId: String?, verificationID: String?) {
        let identity = identityId ?? "nil"
        let verification = verificationID ?? "nil"
        print("✅ [MetaMapCapacitor] verificationSuccess")
        print("   • identityId: \(identity)")
        print("   • verificationId: \(verification)")

        output?.resolve([
            "identityId": identity,
            "verificationId": verification,
            "status": "success"
        ])
    }

    public func verificationCancelled(identityId: String?, verificationID: String?) {
        let identity = identityId ?? "nil"
        let verification = verificationID ?? "nil"
        print("❌ [MetaMapCapacitor] verificationCancelled")
        print("   • identityId: \(identity)")
        print("   • verificationId: \(verification)")

        output?.reject(
            "Verification was cancelled by the user",
            "verificationCancelled",
            nil,
            [
                "identityId": identity,
                "verificationId": verification,
                "status": "cancelled"
            ]
        )
    }

    public func verificationCreated(identityId: String?, verificationID: String?) {
        let identity = identityId ?? "nil"
        let verification = verificationID ?? "nil"
        print("🟡 [MetaMapCapacitor] verificationCreated")
        print("   • identityId: \(identity)")
        print("   • verificationId: \(verification)")

        notifyListeners("verificationCreated", data: [
            "identityId": identity,
            "verificationId": verification
        ])
    }
}