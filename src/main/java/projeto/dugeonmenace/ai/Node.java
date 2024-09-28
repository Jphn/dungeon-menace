/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.ai;

/**
 *
 * @author natan
 */
public class Node {
    
    Node parent;
    public int col;
    public int row;

    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;
    
    public Node (int col, int row) {
        this.col = col;
        this.row = row;
    }    
}