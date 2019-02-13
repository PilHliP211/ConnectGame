package ai.dataStructs;

/**
 * @author Phillip Byram 
 * The Tree Struture the AI uses to make decisions
 *
 */
public class GameStateTree {

    public StateNode root;

    public GameStateTree(){
        root = new StateNode();
    }
}