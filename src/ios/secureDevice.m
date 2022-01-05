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

/********* cordova-secure-device.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <Cordova/CDVViewController.h>
#import "secureDevice.h"
#import <LocalAuthentication/LocalAuthentication.h>

@implementation secureDevice

-(void) checkDeviceSecure:(CDVInvokedUrlCommand*) command{
  LAContext *context = [LAContext new];
  NSError *error;
  BOOL passcodeEnabled = [context canEvaluatePolicy:LAPolicyDeviceOwnerAuthentication error:&error];
  if (passcodeEnabled) {

    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:TRUE];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    // user has their passcode set
  } else {

    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:FALSE];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    // users passcode is not set
  }
}
@end
