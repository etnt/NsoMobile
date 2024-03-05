# Notes

## Internet permissions and using HTTP

Make sure to add the INTERNET permission in the Manifest file
and also add the use of cleartext HTTP.

    <uses-permission android:name="android.permission.INTERNET" />
    
        <application
            android:usesCleartextTraffic="true"
            ...


## Problem connecting to localhost (127.0.0.1)

    $ cd ./Library/Android/sdk/platform-tools/
    $ ./adb reverse tcp:9080 tcp:9080

Then later:

    $ adb reverse --remove-all

See [also](https://handstandsam.com/2016/02/01/network-calls-from-android-device-to-laptop-over-usb-via-adb/)


## Adding a new Icon

Download an Icon for Android from the [Google Fonts](https://fonts.google.com/icons?icon.platform=android) page.

It does not come in SVG format so ask Copilot to: `convert the content to proper svg format`.

Then in Android Studio:

* on the `res` directory, open the Vector Asset tool.
* select the svg file you have created
* chose a name such as: ic_<name>

Then from the code it can be referred to as: `R.drawable.ic_<name>` and be turned into
an ImageVector as: `ImageVector.vectorResource(id = R.drawable.ic_<name>)`

See [also](https://developer.android.com/studio/write/vector-asset-studio#svg)


## Adding new data source

* Create a new package under `data` (e.g alarms)
* In that package, create a new `Kotlin data class from JSON` (select from under the New menu)
* In the `domain` package create a corresponding data UI class (e.g AlarmUi)
* In the new package, create a new file with a mapper function (from data to ui class), see AlarmMapper.kt
* In the MainViewModel add the corresponding mutable/observer pair to hold the data
* Add the corresponding network API function in NsoApi.kt
* Create the other functions needed in the MainViewModel (e.g similar to getNsoEts etc...)
* Add a RunRetrofitInstance.kt (similar to e.g packages) and verify that data can be retrieved.
* Add an intent in MainIntent.kt
* Add the handling of that intent in MainViewModel.kt
* Add the new Page to TabPage.kt
* Add corresponding TabPage handling in HomeScreen.kt
* Add refresh handling in MainViewModel.kt
* Add a new menu item in HomeScreen.kt
* Add the new screen composable to view the data (e.g see AlarmsScreen.kt)

## Prepare for release

1. Update the release_notes.json
2. Commit everything and set a tag, e.g:

    git tag -a v0.2.0 -m "Release version 0.2.0"
    git push --tags

 3. Build an APK and upload it to the Github release page.   


## Build an APK for sharing (outside Google Play)

To create an Android App Bundle in Android Studio, follow these steps:

1. Click on the Build menu in the Android Studio toolbar.
2. Select Build Bundle(s) / APK(s).
3. Click on Build Bundle(s)

Android Studio will then build your APK and show a notification when the build is complete. Click on the locate or event log link in the notification to find the APK file. The APK file will typically be located in the app/build/outputs/apk/ directory in your project.

To generate APKs from an Android App Bundle using bundletool, follow these steps:

1. Download the latest [bundletool](https://github.com/google/bundletool/releases)

2. Generate APKs: Open a terminal and navigate to the directory where you downloaded bundletool. Run the following command to generate APKs from your App Bundle (note: replace 1.15.6 with whatever latest version you have downloaded):

    java -jar bundletool-all-1.15.6.jar build-apks --bundle=../AndroidStudioProjects/Nso/app/build/outputs/bundle/debug/app-debug.aab --output=nso_mobile_<version>.apks

3. Extract APKs: The build-apks command generates a .apks file, which is a ZIP file that contains all the APKs for your app. To extract the APKs, you can rename the .apks file to .zip and extract it using any ZIP tool. Unzipping the .apks file is not a necessary step for installing the app on a device. However, unzipping the .apks file can be useful for inspection purposes. The .apks file is a collection of APK files generated from your Android App Bundle. Each APK file in the .apks file represents a different configuration of your app (for example, different screen densities, CPU architectures, languages, etc.). 

4. Install APKs: To install the APKs on a device, you can use the install-apks command (replace /MyApp/my_app.apks with the path to your .apks file):

    java -jar bundletool-all-1.15.6.jar install-apks --apks=nso_mobile_0.2.0.apks


Note: The device needs to be connected to your machine via USB and you need to have USB debugging enabled on the device.

Got some errors here. Needed to specify a device id, which I found as:

    ../Library/Android/sdk/platform-tools/adb devices

Then:

    java -jar bundletool-all-1.15.6.jar install-apks --apks=nso_mobile_0.2.0.apks --device-id=<device-id>

Still didn't find the folder that the output indicated...need to look into this...

