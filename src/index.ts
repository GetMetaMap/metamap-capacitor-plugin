import { registerPlugin } from '@capacitor/core';


import type { MetaMapCapacitorPlugin } from './definitions';
import type { MetaMapParams } from './definitions';

const MetaMapCapacitorUnwrapped = registerPlugin<MetaMapCapacitorPlugin>('MetaMapCapacitor', {});

const MetaMapCapacitor = {
    showMetaMapFlow: function(options: MetaMapParams):Promise<{ identityId: string, verificationID: string }> {
        const { metadata } = options
        return MetaMapCapacitorUnwrapped.showMetaMapFlow({...options, metadata: {...metadata, sdkType: "capacitor" }})
    },
}

export * from './definitions';
export { MetaMapCapacitor };
  
