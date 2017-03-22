"use strict";

const _ = require('lodash');
const Promise = require('bluebird');

module.exports = function (config, localOS, remoteOS) {

    function listPathForHost(hostInfo, path){

        if(_.isFunction(hostInfo.replaceRegex)){
            path = path.replace(hostInfo.replaceRegex, hostInfo.replaceValue);
        }

        return remoteOS.list(hostInfo, path).then(function (listings) {
            return listings.map(function (listing) {
                return {
                    listing,
                    hostInfo
                };
            })
        });
    }

    function printInfo(listings){
        
        listings.forEach(function (listing) {
            console.log(listing);
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

            Promise.all(listPromises);
        }).then(function (listings) {
            return printInfo(_.zip(listings));
        });

    };
};