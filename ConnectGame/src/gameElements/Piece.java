package gameElements;

/**
 * @author Phillip Byram
 * The Pieces placed in a Connect Game
 *
 */
public enum Piece {
    NONE(' '),
    RED('X'),
    BLACK('O');
    
    char character = ' ';
    
    Piece(char character)
    {
        this.character = character;
    }
    
    char getCharacter(){
        return character;
    }
    String prettyName()
    {
        return this.toString().substring(0, 1) + this.toString().toLowerCase().substring(1);
    }

}