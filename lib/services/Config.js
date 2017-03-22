"use strict";

const fs = require('fs');
const Promise = require('bluebird');

function readConfig(configPath) {
    return new Promise(function (resolve, reject) {
        fs.readFile(configPath, function (err, data) {
            if (err) {
                reject(err);
            } else {
                resolve(JSON.parse(data));
            }
        });
    });
}

module.exports = class Config {

    constructor() {
        this.configData = readConfig(`${process.env.HOME}/.tank-config.json`);
    }

    getHostList() {
        return this.configData.then(function (config) {
            return config.hostList;
        });

    }


};