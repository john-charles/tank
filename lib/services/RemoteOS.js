"use strict";

let Promise = require('bluebird');
let Client = require('ssh2').Client;

module.exports = class RemoteOS {

    constructor(config) {

    }

    // TODO: Make this re-use connections.
    // Currently this is just going to POC the ssh code..
    list(hostInfo, path) {

        let dirListing = {};

        function processList(listing){
            dirListing[listing.filename] = {};
            dirListing[listing.filename][hostInfo.title || hostInfo.hostName] = listing.attrs;
        }

        return new Promise(function (resolve, reject) {

            let conn = new Client();

            conn.on('ready', function () {

                conn.sftp(function (err, sftp) {

                    if (err) throw err;
                    sftp.readdir(path, function (err, list) {
                        if (err) {
                            reject(err);
                        } else {
                            list.forEach(processList);
                            resolve(dirListing);
                        }

                        conn.end();
                    });
                });

            }).connect({
                host: hostInfo.hostName,
                port: hostInfo.port || 22,
                username: hostInfo.userName,
                privateKey: hostInfo.privateKey
            });

        });
    }

};