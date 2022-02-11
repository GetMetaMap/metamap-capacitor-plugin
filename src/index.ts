import { registerPlugin } from '@capacitor/core';


import type { MatiCapacitorPlugin } from './definitions';
import type { MatiParams } from './definitions';

const MatiCapacitorUnwrapped = registerPlugin<MatiCapacitorPlugin>('MatiCapacitor', {});

const MatiCapacitor = {
    showMati: function(options: MatiParams):Promise<{ identityId: string, verificationID: string }> {
        const { metadata } = options
        return MatiCapacitorUnwrapped.showMatiFlow({...options, metadata: {...metadata, sdkType: "capacitor" }})
    },
}

export * from './definitions';
export { MatiCapacitor };
  
