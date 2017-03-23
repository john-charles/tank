"use strict";

// Services
const config = new (require('./services')).Config;
const localOS = new (require('./services')).LocalOS;
const remoteOS = new (require('./services')).RemoteOS(config);

const commands = {
    list: require('./commands').list(config, localOS, remoteOS)
};

const cmdLine = require('./ui').cmdLine;
const Controller = require('./ui').Controller;


module.exports = {
    controller: new Controller(cmdLine.getArgs(), commands)
};