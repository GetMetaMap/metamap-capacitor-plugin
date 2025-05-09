export interface MetaMapParams {
  clientId: string
  flowId: string
  metadata?: object
}
export interface MetaMapCapacitorPlugin {
  showMetaMapFlow(options: MetaMapParams): Promise<{ identityId: string, verificationID: string }>;
}

