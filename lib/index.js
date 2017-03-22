"use strict";

// Services
const config = new (require('./services')).Config;
const localOS = new (require('./services')).LocalOS;
const remoteOS = new (require('./services')).RemoteOS(config);


module.exports = {
    list: require('./commands').list(config, null, remoteOS)
};