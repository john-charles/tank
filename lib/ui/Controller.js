"use strict";

module.exports = class Controller {

    constructor(cmdArgs, commands){
        this.cmdArgs = cmdArgs;
        this.commands = commands;
    }

    exec(){

        let commandName = this.cmdArgs._[0];
        let command = this.commands[commandName];

        if(!command){

            console.log("Error please specify a subcommand name!\n");
            console.log("Available sub-commands:");

            Object.keys(this.commands).forEach(function (cmdName) {
                console.log(`    ${cmdName}`);
            });

        } else {
            return command.apply(null, this.cmdArgs._.slice(1));
        }
    }

};