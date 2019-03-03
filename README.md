# ConnectGame
CAP5600 Project 1

# Compiling
After extracting the uploaded zip file, navigate to the top level directory that contains \src.
Create a \bin directory.

To compile connectM run the following command with JDK 8 or later (assuming the JDK\bin directory is on your environment path):

`javac -d bin -sourcepath src src\gameElements\Game.java`


# Generating Docs
Navigate to the top level directory that contains \src.
Create a \docs directory.
To generate javadocs run the following command (assuming the JDK\bin directory is on your environment path):

`javadoc -d docs -sourcepath src gameElements ai test`


# Running
After compiling change to the \bin directory. Then, you can run connectM with the following command

`java gameElements.Game `


To specify the size of the board run (this creates a board with width and height 7).

`java gameElements.Game 7`


To specify the size of the board and win condition run (this creates a board with width and height 6 and a win condition of 3).

`java gameElements.Game 6 3`


# Playing
To play a column enter a number from 0 to width and hit enter. The human player is O and the AI is X.
