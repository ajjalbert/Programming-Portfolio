
/**
* @author: Andrew Jalbert
* @version: 1.1b
* @filename: Huff.<!-- -->java
* @description: Creates an interactive GUI that allows the user to compress/decompress files via Huffman Coding
*/
public class Huff {

    public static void main(String[] args){
        HuffViewer sv = new HuffViewer("Huffing Coding"); // create a new GUI
        HuffModel hm = new HuffModel(); // create a model for the GUI
        sv.setModel(hm); // set the GUI's model
    }
}
