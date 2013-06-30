# AppWoodoo Android SDK

`v0.6 PRE-RELEASE`

AppWoodoo's Android SDK comes with two projects: the SDK with the source code and an example app. The corresponding folders are 'SDK' and 'Example'

## Install

You can add the SDK to your application either as an SDK or as a Library project.

### As an SDK

Simply drag&drop the AppWoodooSDK.jar file to the libs folder in Eclipse

### As a Library project

In Eclipse, right-click on the project name in the left panel. In the Application properties, navigate to Android, add a new Library, select AppWoodooSDK and click Save.

## Integrating the SDK

1. Get an API key on the website: www.appwoodoo.com, and add some remote settings (for example, set "SPLASH_SCREEN_ENABLED") to "false")

2. Add the following permissions to the AndroidManifest.xml of your app:

```
<uses-permission android:name="android.permission.INTERNET"/>
```

3. Add the takeOff call in your Activity:

```
Woodoo.takeOff("YOUR_API_KEY");
```

4. Receive the Remote Settings in your app:

```
Woodoo.getBooleanForKey("SPLASH_SCREEN_ENABLED");
```

## Advanced functions

* Check weather the settings have arrived from the server, before using them:

```
if (Woodoo.settingsArrived()) {
  Woodoo.getBooleanForKey("SPLASH_SCREEN_ENABLED");
}
```

* Use a WoodooDelegate to be notified when the Remote Settings arrive:

```
public class MainActivity extends Activity implements WoodooDelegate {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Woodoo.takeOffWithCallback("YOUR_API_KEY", delegate);
  }
  
  @Override
  public void woodooArrived(Woodoo.WoodooStatus status) {
    if (status == Woodoo.WoodooStatus.SUCCESS) {
      System.out.println("WOODOO SETTINGS ARRIVED");
    }
  }

}
```


## Lincese

Lincesed under The MIT License (MIT)

Copyright (c) 2013 AppWoodoo ([appwoodoo.com](www.appwoodoo.com))

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
