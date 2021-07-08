# Mati Capacitor plugin
Mati Capacitor plugin for SDK https://getmati.com

### This is short tutorial to fast implement our SDK into ionic/Capacitor framework https://capacitorjs.com

## First of all install plugin into Capacitor project by command
    npm i @aposnovmati/mati-capacitor-plugin
    
### then rebuild yours project
    ionic build
    
### and update capacitor files
    npx cap sync 

#### EXAMPLE OF APP
your_index.html
  
    <ion-content>
    <ion-button className="matiButtonCss" (click)="showMatiFlow()">Show MatiFlow
    </ion-button>
    </ion-content>
    
 your_index.ts
  
```javascript
import { Component } from '@angular/core';

import { MatiCapacitor } from "@aposnovmati/mati-capacitor-plugin";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  constructor() {}

  showMatiFlow() {
    let metadataParams = { param1: "value1" };
    let registerParams = { clientId: "5c94e3c401ddc6001be83c07", flowId: "5e962a23728ddc001b5937aa", metadata: metadataParams};

    MatiCapacitor.showMatiFlow(registerParams)
      .then( verification => console.log("verification success:" + verification.verificationId))
      .catch(() => console.log("verification cancelled"))
  }
}

```

# Android
### launch android
    ionic capacitor run android

# iOS
### set minimum iOS version in capacitor.config.json
     "ios": {
      "minVersion": "11.4"
    }
    
### launch ios
    ionic capacitor run ios
    
## Still have question? Please ask us in issue tab on GitHub and use our "Examples" folder.

#### Check additional info
npm package https://www.npmjs.com/package/@aposnovmati/mati-capacitor-plugin

capacitor docs https://capacitorjs.com/docs
