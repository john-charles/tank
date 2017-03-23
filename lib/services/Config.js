"use strict";

const fs = require('fs');
const _ = require('lodash');
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

function readFile(filePath) {
    return new Promise(function (resolve, reject) {
        fs.readFile(filePath, function (err, data) {
            if (err) {
                reject(err);
            } else {
                resolve(data);
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
            return Promise.map(config.hostList, function (hostInfo) {
                if(hostInfo.privateKeyFile){
                    return readFile(hostInfo.privateKeyFile).then(function (key) {
                        return _.extend(hostInfo, {privateKey: key});
                    });
                } else {
                    return hostInfo;
                }
            });
        });

    }


};