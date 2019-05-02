#!/bin/bash
./gradlew jsMainClasses && \
    npm i && \
    node -e 'require("./build/classes/kotlin/js/main/kjs-bug.js").foobar.TestData.make()'
