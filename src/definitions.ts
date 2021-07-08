export interface MatiCapacitorPlugin {
    showMatiFlow(options: object): Promise<{ verificationId: string }>;
  }
  