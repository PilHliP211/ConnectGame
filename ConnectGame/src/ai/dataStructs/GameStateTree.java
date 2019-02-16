package ai.dataStructs;

/**
 * @author Phillip Byram 
 * The Tree Struture the AI uses to make decisions
 *
 */
public class GameStateTree {

    private StateNode root;
    private int depth;

    public GameStateTree(int depth){
        this.depth = depth;
        root = new StateNode();
    }

    /**
     * Given a root node, prune irrelevant paths.
     * @param node the root node that will be pruned
     */
    public static void pruneTree(StateNode node){

    }

    public void createTree()
        for(int i = 0;i< depth;i++)
        {

        }
    }
}