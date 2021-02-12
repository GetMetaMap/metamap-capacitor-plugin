declare module '@capacitor/core' {
  interface PluginRegistry {
    AposnovmatiMatiCapacitorPlugin: MatiCapacitorPluginPlugin;
  }
}

export interface MatiCapacitorPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
