package autoComplete;

import java.util.ArrayList;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
 */
public class PrefixTree {
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void add(String word){

        TreeNode nodeLetter = root; // start at root
        for (int c = 0; c < word.length(); c++){ // for each letter in the word
            char character = word.charAt(c);
            if (!nodeLetter.children.containsKey(character)){
                TreeNode newCharNode = new TreeNode();
                newCharNode.letter = character;
                nodeLetter.children.put(character, newCharNode);
            }
            nodeLetter = nodeLetter.children.get(character); // makes pointer the character
        }
        if (!nodeLetter.isWord){
            nodeLetter.isWord = true;
            size++;
        }
    }
    

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){

        TreeNode nodeLetter = root;
        for (int c = 0; c < word.length(); c++){
            if (!nodeLetter.children.containsKey(word.charAt(c))){
                return false;
            }
            nodeLetter = nodeLetter.children.get(word.charAt(c));
        }
        if (nodeLetter.isWord){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        ArrayList<String> prefixWords = new ArrayList<String>();
        TreeNode nodeLetter = root;
        for (int c = 0; c < prefix.length(); c++){ // gets node to point to the last letter of the prefex
            nodeLetter = nodeLetter.children.get(prefix.charAt(c));
        }
        recursiveWordFinder(nodeLetter, prefix, prefixWords, new StringBuffer());

        //TODO: complete me
        return prefixWords;
    }

    private void recursiveWordFinder(TreeNode localRoot, String prefix, ArrayList<String> prefixWords, StringBuffer prefixWord){
        StringBuffer word = new StringBuffer(prefixWord); // current letters down the list current search
        word.append(String.valueOf(localRoot.letter));
        if (localRoot.isWord){
            prefixWords.add(prefix.substring(0, prefix.length()-1) + word);
        }

        if (localRoot.children == null){ // if no children left, end
            return;
        }

        for (TreeNode newNode : localRoot.children.values()){
            
            recursiveWordFinder(newNode, prefix, prefixWords, word);
        }
        
    }
        

    private void add(StringBuffer prefixWord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
    
}
