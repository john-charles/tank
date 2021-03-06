"use strict";

const fs = require('fs');
const _ = require('lodash');
const path = require('path');
const Promise = require('bluebird');

function readdir(path) {

    return new Promise(function (resolve, reject) {
        fs.readdir(path, function (err, listings) {
            if (err) {
                reject(err);
            } else {
                resolve(listings);
            }
        });
    });

}

function statPath(path){

    return new Promise(function (resolve, reject) {
        fs.stat(path, function (err, stats) {
            if(err){
                reject(err);
            } else {
                resolve(stats);
            }
        });
    });
}

module.exports = class LocalOS {

    list(root){

        let dirListing = {};

        function getStatForFile(fileName){
            return statPath(path.join(root, fileName)).then(function (stat) {
                dirListing[fileName] = {
                    local: stat
                };
            });
        }

        return readdir(root).then(function (entries) {
            let statPromises = [];

            entries.forEach(function (entry) {
                statPromises.push(getStatForFile(entry));
            });

            return Promise.all(statPromises).then(function (stat) {
                return dirListing;
            });
        });
    }

};