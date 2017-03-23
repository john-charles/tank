#!/usr/bin/env node
"use strict";

const path = require('path');
const tankLib = require('../lib');

tankLib.list(path.resolve(process.argv[2]));