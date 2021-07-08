import { registerPlugin } from '@capacitor/core';

import type { MatiCapacitorPlugin } from './definitions';

const MatiCapacitor = registerPlugin<MatiCapacitorPlugin>('MatiCapacitor', {});

export * from './definitions';
export { MatiCapacitor };
  
