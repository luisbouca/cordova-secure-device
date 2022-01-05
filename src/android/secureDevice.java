/*
   Copyright 2016 André Vieira

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.outsystemscloud.andrevieira;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;


public class secureDevice extends CordovaPlugin {

    private CallbackContext callback;
    
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        callback = callbackContext;
        switch (action){
            case "checkDeviceSecure":
                checkDevice();
                return true;
            default:
                PluginResult result = new PluginResult(PluginResult.Status.ERROR);
                callback.sendPluginResult(result);
                return false;
        }
    }

    private void checkDevice() {
        boolean isPasscodeSet = isPatternSet(this.cordova.getActivity()) || isPassOrPinSet(this.cordova.getActivity());

        PluginResult result = new PluginResult(PluginResult.Status.OK, isPasscodeSet);
        callback.sendPluginResult(result);
    }

    /**
     * @param context
     * @return true if pattern set, false if not (or if an issue when checking)
     */
    private static boolean isPatternSet(Context context)
    {
        ContentResolver cr = context.getContentResolver();
        try
        {
            // This constant was deprecated in API level 23. 
            // Use KeyguardManager to determine the state and security level of the keyguard. 
            // Accessing this setting from an app that is targeting M or later throws a SecurityException.
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
                int lockPatternEnable = Settings.Secure.getInt(cr, Settings.Secure.LOCK_PATTERN_ENABLED);
                return lockPatternEnable == 1;
            } else {
                return false;
            }
        }
        catch (Settings.SettingNotFoundException e)
        {
            
            return false;
        }
    }

    /**
     * @param context
     * @return true if pass or pin set
     */
    @SuppressLint("NewApi") 
    private static boolean isPassOrPinSet(Context context)
    {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE); //api 16+
        return keyguardManager.isDeviceSecure();
    }

}
