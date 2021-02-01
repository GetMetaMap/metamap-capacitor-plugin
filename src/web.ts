import { WebPlugin } from '@capacitor/core';
import { MatiCapacitorPluginPlugin } from './definitions';

export class MatiCapacitorPluginWeb extends WebPlugin implements MatiCapacitorPluginPlugin {
  constructor() {
    super({
      name: 'MatiCapacitorPlugin',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const MatiCapacitorPlugin = new MatiCapacitorPluginWeb();

export { MatiCapacitorPlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(MatiCapacitorPlugin);
