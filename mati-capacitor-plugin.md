# Mati Capacitor Plugin Usage Guide

This is a tutorial to implement the Mati SDK in the [Ionic Capacitor framework](https://capacitorjs.com/docs).

## Install the Mati Plugin

The following instructions use command line tools to Install the Mati Capacitor plugin to your existing Capacitor application.

1. Use the following CLI to install plugin into Capacitor project.

    ```bash
    npm i @aposnovmati/mati-capacitor-plugin
    ```

1. Build your application.
    ```bash
    ionic build
    ```
1. Update the Capacitor files.
    ```bash
    npx cap sync
    ```

## Add the Mati Button

Add the Mati button to your application's HTML and JavaScript files.

`your_index.html`

```html
    <ion-content>
    <ion-button className="matiButtonCss" (click)="showMatiFlow()">Show MatiFlow
    </ion-button>
    </ion-content>
```

 `your_index.ts`

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

## Launch for Android

Run the following command to launch the application for Android:
```bash
ionic capacitor run android
```

# Launch for iOS
To launch the application for iOS, you need to do the following:

1. Set minimum iOS version in `capacitor.config.json`
    ```json
     "ios": {
      "minVersion": "11.4"
    }
    ```

1. Launch the application for iOS
    ```bash
    ionic capacitor run ios
    ```
