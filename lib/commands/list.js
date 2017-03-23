"use strict";

const _ = require('lodash');
const Promise = require('bluebird');

module.exports = function (config, localOS, remoteOS) {

    function listPathForHost(hostInfo, path){

        if(_.isFunction(hostInfo.replaceRegex)){
            path = path.replace(hostInfo.replaceRegex, hostInfo.replaceValue);
        }

        return remoteOS.list(hostInfo, path);
    }

    function printListing(fileName, stats){

        console.log(fileName, Object.keys(stats).join("|"));


    }

    function printInfo(listings){

        _.forIn(listings, function (value, key) {
            printListing(key, value);
        });

    }

    return function(path){

        return config.getHostList().then(function (hostList) {

            let listPromises = [
                localOS.list(path)
            ];

            hostList.forEach(function (hostInfo) {
                listPromises.push(listPathForHost(hostInfo, path));
            });

            return Promise.all(listPromises);
        }).then(function (listings) {
            let result = {};

            listings.forEach(function (listing) {
                _.merge(result, listing);
            });

            return printInfo(result);
        });

    };
};