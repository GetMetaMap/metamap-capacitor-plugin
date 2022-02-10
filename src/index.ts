import { registerPlugin } from '@capacitor/core';


import type { MatiCapacitorPlugin } from './definitions';
import type { MatiParams } from './definitions';

const MatiCapacitorUnwrapped = registerPlugin<MatiCapacitorPlugin>('MatiCapacitor', {});

const MatiCapacitor = {
    showMatiFlow: function(options: MatiParams): Promise<{ verificationId: string }> {
        const { metadata } = options
        MatiCapacitorUnwrapped.showMatiFlow({...options, metadata: {...metadata, sdkType: "capacitor" }})
    }
}

export * from './definitions';
export { MatiCapacitor };
  
