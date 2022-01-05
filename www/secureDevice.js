var exec = require('cordova/exec');

exports.checkDeviceSecure = function(success,error) {
    exec(success, error, "secureDevice", "checkDeviceSecure",[]);
};