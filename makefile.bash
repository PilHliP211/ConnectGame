#!/bin/bash

 
# Set filepath variables.
AI_PATH="./ConnectGame/src/ai"
GAME_ELEMS_PATH="./ConnectGame/src/gameElements"
TESTER_PATH="./ConnectGame/src/test"

# Check whether bin and source directories exist.
# If directories exist, remove it so that new compile command 
# doesn't have issues.
if test -d ./bin
then
   rm -r ./bin
   echo -e "\nOld bin files removed"
fi

if test -d ./docs
then 
    rm -r ./docs
    echo -e "\nOld docs files removed"
fi

# Check whether bin and docs directories exist. 
# At this point they should not, so create directories for new
# compiled code and documents.
if !(test -d ./bin)
then
    mkdir ./bin
    echo -e "\nMaking new bin folder"
fi

if !(test -d ./docs)
then
    mkdir ./docs
    echo -e "\nMaking new docs folder"
fi

# Compile project's java code.
# commented because not working yet.
echo -e "\nCompiling Project...\n"
javac -d ./bin -sourcepath ./src $GAME_ELEMS_PATH/Game.java $GAME_ELEMS_PATH/Player.java $GAME_ELEMS_PATH/Board.java $GAME_ELEMS_PATH/QuadraticBoard.java $GAME_ELEMS_PATH/InputPlayer.java $GAME_ELEMS_PATH/Piece.java $GAME_ELEMS_PATH/StandardBoard.java $AI_PATH/ArtificialPlayer.java $AI_PATH/helper/BoardHelpers.java $AI_PATH/helper/Utility.java $AI_PATH/dataStructs/StateNode.java #$TESTER_PATH ArtificialTester.java $TESTER_PATH/RandomTester.java

#javac -d ./bin -sourcepath ./src ./ConnectGame/src/gameElements/Game.java

javadoc -sourcepath ./src -classpath ./src $GAME_ELEMS_PATH/Game.java $GAME_ELEMS_PATH/Player.java $GAME_ELEMS_PATH/Board.java $GAME_ELEMS_PATH/QuadraticBoard.java $GAME_ELEMS_PATH/InputPlayer.java $GAME_ELEMS_PATH/Piece.java $GAME_ELEMS_PATH/StandardBoard.java $AI_PATH/ArtificialPlayer.java $AI_PATH/helper/BoardHelpers.java $AI_PATH/helper/Utility.java $AI_PATH/dataStructs/StateNode.java -d ./docs

