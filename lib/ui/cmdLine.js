"use strict";

const minimist = require('minimist');

module.exports.getArgs = function () {
    return minimist(process.argv.slice(2));
};