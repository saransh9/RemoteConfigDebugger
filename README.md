# RemoteConfigDebugger

RemoteConfigDebugger simplifies way to change Firebase remote config values locally for debugging instead of going to the firebase console and changing the values.

App Using RemoteConfigDebugger will display a notification through which user would be able to access the RemoteConfigDebuggerActivity to where all the remote config values are displayed and user can select and change any value.

![enter image description here](https://github.com/saransh9/RemoteConfigDebugger/blob/master/assets/screenshot_1.jpg)

![enter image description here](https://github.com/saransh9/RemoteConfigDebugger/blob/master/assets/screenshot_2.jpg)

## Getting Started
Add the following dependency to your module's build.gradle:

Please note that you should add both the `library` and the the `library-no-op` variant to isolate RemoteConfigDebugger from release builds as follows:

    dependencies {
      debugImplementation "com.github.remoteconfigdebugger:library:1.0.0"
      releaseImplementation "com.github.remoteconfigdebugger:library-no-op:1.0.0"
    }

**To start using RemoteConfigDebugger:**

Add following to your manifest

    <provider
      android:name="com.remoteconfigdebugger.provider.RemoteConfigDebuggerInitProvider"
      android:authorities="${application_id}.RemoteConfigDebuggerInitProvider"
      android:exported="false" />

or

Add following to your application class

    RemoteConfigDebugger.initialiseApp(context)

## Usage
To get the value as string use:

    RemoteConfigDebugger.getInstance().getString(KEY_NAME)
To get the value as Boolean use:

    RemoteConfigDebugger.getInstance().getBoolean(KEY_NAME)

To get the value as Double use:

    RemoteConfigDebugger.getInstance().getDouble(KEY_NAME)
To get the value as Long use:


    RemoteConfigDebugger.getInstance().getLong(KEY_NAME)

## License
```
Copyright 2020 Arman Sargsyan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```