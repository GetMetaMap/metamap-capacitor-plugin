---
title: "Capacitor"
excerpt: "Add the MetaMap button to your Capacitor app."
slug: "capacitor-sdk"
category: 61ae8e8dba577a0010791480
---

# MetaMap for Capacitor Usage Guide

This is a guide to implement MetaMap in the [Ionic Capacitor framework](https://capacitorjs.com/docs).

## Capacitor Demo App

You can go to GitHub to download the [MetaMap Capacitor demo app](https://github.com/GetMati/mati-mobile-examples/tree/main/capacitorDemoApp).

## Install MetaMap for Capacitor

The following instructions use command line tools to install MetaMap for Capacitor to your existing Capacitor application.

1. Use the following CLI to install MetaMap for your Capacitor project.

    ```bash
    npm i @avo121/mati-capacitor-plugin
    ```

1. Build your application.
    ```bash
    ionic build
    ```
1. Update the Capacitor files.
    ```bash
    npx cap sync
    ```
    
## How to update Capacitor 3.x.x

npm install @capacitor/cli@latest @capacitor/core@latest @capacitor/ios@latest @capacitor/android@latest --force
    
## How toAdd the MetaMap Button

Add the MetaMap button to your application's HTML and JavaScript files.

`your_index.html`

```html
    <ion-content>
    <ion-button className="metaMapButtonCss" (click)="showMetaMapFlow()">Show MetaMapFlow
    </ion-button>
    </ion-content>
```

 `your_index.ts`

```typescript
import { Component } from '@angular/core';

import { MetaMapCapacitor } from "@avo121/mati-capacitor-plugin";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  constructor() {}

  showMetaMapFlow() {
    let metadataParams = { param1: "value1" };
    let registerParams = { clientId: "YOUR_CLIENT_ID", flowId: "YOUR_FLOW_ID", metadata: metadataParams};

    MetaMapCapacitor.showMetaMapFlow(registerParams)
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
      "minVersion": "12.0"
    }
    ```

1. Launch the application for iOS
    ```bash
    ionic capacitor run ios
    ```
    
 ## Uninstall MetaMap for Capacitor
Use the following CLI to uninstall MetaMap from yours project.

    
    npm uninstall --save npm i @avo121/mati-capacitor-plugin
    
