# Mati Capacitor plugin – BETA VERSION
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
    <ion-button className="matiButtonCss" (click)="showMatiFLow()">Show MatiFLow
    </ion-button>
    </ion-content>
    
 your_index.ts
  
```javascript
import { Component } from '@angular/core';
import { Plugins } from '@capacitor/core';

const { MatiCapacitorPlugin } = Plugins;

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss']
})

export class Tab1Page {
  constructor() {}

  // the method that starts to show FLOW screens
  showMatiFLow() {
      MatiCapacitorPlugin.showMatiFLow();
  }

  ionViewDidEnter() {
    let metadataParams = { param1: "value1" }; // variable for metadata params
    let registerParams = { clientId: "YOURS_CLIENT_ID", flowId: "YOURS_FLOW_ID", metadata: metadataParams}; // variable for register params
    MatiCapacitorPlugin.setParams(registerParams); // initialization main class

    // methods handle of result
    window.addEventListener('mfKYCLoginSuccess', (verificationId) => {
      console.log("verification success:" + verificationId)
    });

    window.addEventListener('mfKYCLoginCancelled', () => {
      console.log("verification cancelled")
    });
  }

}
```


# Android
### register the plugin in your Activity

Please be sure to be sync with gradle files, press this button

![alt text](https://github.com/GetMati/mati-capacitor-plugin/blob/main/sync_project.png)

```java
import io.mati.plugins.capacitor.MatiCapacitorPlugin;

import java.util.ArrayList;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initializes the Bridge
    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      // Additional plugins you've installed go here
      // Ex: add(TotallyAwesomePlugin.class);
      add(MatiCapacitorPlugin.class);
    }});
  }
}
```
### launch android
    npx cap open android

# iOS

### set minimum iOS version in capacitor.config.json
     "ios": {
      "minVersion": "11.4"
    }
    
### launch ios
    npx cap open ios  
    
## Still have question? Please ask us in issue tab on GitHub and use our "Examples" folder.


#### Check additional info
npm package https://www.npmjs.com/package/@aposnovmati/mati-capacitor-plugin

capacitor docs https://capacitorjs.com/docs
