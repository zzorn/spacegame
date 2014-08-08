#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/gradlew desktop:dist

rm $DIR/spacegame.jar

cp $DIR/desktop/build/libs/desktop-1.0.jar $DIR/spacegame.jar

