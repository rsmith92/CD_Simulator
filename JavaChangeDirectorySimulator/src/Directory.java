import java.util.ArrayList;
import java.util.Objects;

// Directory class will represent directories in the abstract network
public class Directory {
    // name of current directory
    String name;
    // represents the previous Directory
    Directory parent = null;
    // represents directories that stem form this one
    ArrayList<Directory> children = new ArrayList<Directory>();

    // constructor for a directory, in our implementation this is for the root node
    public Directory(String name) {
        this.name = name;
        // it is its own parent, this is how we deal with "/.." or anything similar to return "/"
        this.parent = this;
    }

    // constructor for a directory, in our implementation we use this for everything but the root node
    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        // must add the current node to the children of the parent
        parent.children.add(this);
    }


    // look through the list of children directories and find the one with name equal to childName
    public Directory findChild(String childName) throws NoSuchFieldException {
        for(Directory d : this.children) {
            if(d.name.equals(childName)) {
                return d;
            }
        }
        // if the child isnt in the children list, throw NoSuchFieldException
        throw new NoSuchFieldException();
    }
}
