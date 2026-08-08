import { type PluginListenerHandle } from "@capacitor/core";

export interface MetaMapParams {
  clientId: string
  flowId: string
  metadata?: object
}
export interface MetaMapCapacitorPlugin {
  addListener(eventName: 'verificationCreated', listenerFunc: (data: { identityId: string, verificationID: string }) => any): Promise<PluginListenerHandle>;
  showMetaMapFlow(options: MetaMapParams): Promise<{ identityId: string, verificationID: string }>;
}

