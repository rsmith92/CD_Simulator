import java.util.ArrayList;

public class ChangeDirectory {
    // ROOT will act as our global root node
    static Directory ROOT;



    // finds the starting directory and then traverses the directories
    static String mycd(String currDir, String newDir) {
        // split new_dir on the character "/"
        String[] newDirArr = newDir.split("/");
        // currSym initialized as null
        String currSym = null;
        // currDirectory initialized as null
        Directory currDirectory = null;

        // get the Directory from the currDir string, catch the error if it doesnt exist
        try {
            currDirectory = getDirectory(currDir, ROOT);
        }
        catch(NoSuchFieldException e) {
            return currDir + " : No such file or directory.";
        }

        // if there is nothing left in the array OR the first item was "/", currDirectory is the ROOT
        if(newDirArr.length == 0 || newDirArr[0].equals("")) {
            currDirectory = ROOT;
        }

        // remove the holes ("") from the array
        newDirArr = removeHolesLst(newDirArr);

        // traverse the rest of the array, example [gh,..,klm,.]
        for(String i : newDirArr) {
            currSym = i;
            // if currSym == .
            if (currSym.equals(".")) {
                ; // do nothing to currDir
            }

            // else if currSym == ..
            else if (currSym.equals("..")) {
                // change currDirectory to the parent of the previous
                currDirectory = currDirectory.parent;
            }

            // currSym == name
            else {
                // check if currSym is alphanumeric
                if (isAlphaNumeric(currSym)) {

                    // now check if it is a child of the currDirectory, catch the error if not
                    try {
                        currDirectory = currDirectory.findChild(currSym);
                    }
                    catch(NoSuchFieldException e) {
                        return currSym + " : No such file or directory.";
                    }
                }

                // if symbol isnt alphanumeric, return No such file string
                else {
                    return currSym + " : No such file or directory.";
                }
            }
        }
        // finally get the string path of the final directory
        return getPath(currDirectory);
    }



    // finds the string path given a Directory, currDir
    public static String getPath(Directory currDir) {
        String result = "";

        // simplest case, if currDir is root, just return "/"
        if(currDir.name.equals("/")) {
            return "/";
        }

        // loop through directories and build on result until you reach the root, then return result
        while(!(currDir.name.equals("/"))) {
            result = "/" + currDir.name + result;
            currDir = currDir.parent;
        }
        return result;
    }



    // remove all of the holes from the list
    public static String[] removeHolesLst(String[] inputList) {
        ArrayList<String> resultArr = new ArrayList<String>();
        // loop through the list
        for(String i : inputList) {
            // this checks if current string is empty (hole)
            if(i.equals("")) {
                // if empty, continue
                continue;
            }
            // if not empty, add to result list
            resultArr.add(i);
        }
        return resultArr.toArray(new String[0]);
    }



    // get the directory given a currDirPath string
    public static Directory getDirectory(String currDirPath, Directory root) throws NoSuchFieldException {
        // start at root
        Directory currDir = root;
        // turn the string into a list with no holes split on "/"
        String[] dirArr = removeHolesLst(currDirPath.split("/"));

        // step through dirArr directories
        for (String i : dirArr) {
            // at each level look for the next child Directory, error is caught in find child and thrown
            currDir = currDir.findChild(i);
        }
        // return the child if it exists
        return currDir;
    }



    // find the string path of the Directory, if it exists
    public static Boolean isAlphaNumeric(String currString) {
        if(currString.length() == 0) {
            return false;
        }
        // turn the input string into a character array
        char[] charArr = currString.toCharArray();
        // loop through the characters, checking if each is alphanumeric
        for(char i : charArr) {
            // if not alphanumeric, return false
            if((!Character.isDigit(i)) && (!Character.isAlphabetic(i))) {
                return false;
            }
        }
        return true;
    }



    // initialize network of directories
    public static void createNetwork() {
        // update the global ROOT
        ROOT = new Directory("/");
        Directory v1 = new Directory("abc", ROOT);
        Directory v2 = new Directory("def", v1);
        Directory v3 = new Directory("klm", v1);
        Directory v4 = new Directory("gh", v1);
        Directory v5 = new Directory("ghi", v2);
    }

    // for my test cases I used the directory structure from the test cases given in the email:
    //             ------> gh
    // root       |
    //   / ----> abc ----> def ----> ghi
    //            |
    //             ------> klm



    // all of these test cases (and many more) are implemented in the TestJunit.java file
    public static void main(String args[]) {
        createNetwork();

        // test cases from email
        System.out.println( ChangeDirectory.mycd("/", "abc") ); // /abc
        System.out.println( ChangeDirectory.mycd("/abc/def", "ghi") ); // /abc/def/ghi
        System.out.println( ChangeDirectory.mycd("/abc/def", "..") ); // /abc
        System.out.println( ChangeDirectory.mycd("/abc/def", "/abc") ); // /abc
        System.out.println( ChangeDirectory.mycd("/abc/def", "/abc/klm") ); // /abc/klm
        System.out.println( ChangeDirectory.mycd("/abc/def", "../..") ); // /
        System.out.println( ChangeDirectory.mycd("/abc/def", "../../..") ); // /
        System.out.println( ChangeDirectory.mycd("/abc/def", ".") ); // /abc/def
        System.out.println( ChangeDirectory.mycd("/abc/def", "//////") ); // /
        System.out.println( ChangeDirectory.mycd("/abc/def", "......") ); //  ....... : No such file or directory.
        System.out.println( ChangeDirectory.mycd("/abc/def", "../gh///../klm/.") ); // /abc/klm

        // some additional test cases
        System.out.println( ChangeDirectory.mycd("/", ".") ); // /
        System.out.println( ChangeDirectory.mycd("/", "/abc") ); // /abc
        System.out.println( ChangeDirectory.mycd("/abc", ".") ); // /abc
        System.out.println( ChangeDirectory.mycd("/def", ".") ); // /def : No such file or directory.
        System.out.println( ChangeDirectory.mycd("/abc/def", "cba") ); //  cba : No such file or directory.
        System.out.println( ChangeDirectory.mycd("/abc/def", "") ); // /abc/klm
        System.out.println( ChangeDirectory.mycd("/", "/") ); // /
        System.out.println( ChangeDirectory.mycd("/", "..///.////abc/../abc/def/.././gh/..//def/ghi") ); // /abc/def/ghi
    }
}
