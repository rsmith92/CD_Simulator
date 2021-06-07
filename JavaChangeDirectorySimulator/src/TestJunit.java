import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestJunit {

    @Test
    void test_mycd() {
        // initialize the network of directories
        ChangeDirectory.createNetwork();

        // run the tests now that the directories exist in the environment of change_directory
        assertEquals(ChangeDirectory.mycd("/", "."), "/");
        assertEquals(ChangeDirectory.mycd("/", "abc"), "/abc");
        assertEquals(ChangeDirectory.mycd("/", "/abc"), "/abc");
        assertEquals(ChangeDirectory.mycd("/abc", "."), "/abc");
        assertEquals(ChangeDirectory.mycd("/abc/def", "ghi"), "/abc/def/ghi");
        assertEquals(ChangeDirectory.mycd("/abc/def", ".."), "/abc");
        assertEquals(ChangeDirectory.mycd("/abc/def", "/abc"), "/abc");
        assertEquals(ChangeDirectory.mycd("/abc/def", "/abc/klm"), "/abc/klm");
        assertEquals(ChangeDirectory.mycd("/abc/def", "../.."), "/");
        assertEquals(ChangeDirectory.mycd("/abc/def", "../../.."), "/");
        assertEquals(ChangeDirectory.mycd("/abc/def", "."), "/abc/def");
        assertEquals(ChangeDirectory.mycd("/abc/def", "//////"), "/");
        assertEquals(ChangeDirectory.mycd("/abc/def", "../gh///../klm/."), "/abc/klm");
        assertEquals(ChangeDirectory.mycd("/abc/def", "......"), "...... : No such file or directory.");
        assertEquals(ChangeDirectory.mycd("/abc/def", "cba"), "cba : No such file or directory.");
        assertEquals(ChangeDirectory.mycd("/", "/"), "/");
        assertEquals(ChangeDirectory.mycd("/def", "."), "/def : No such file or directory.");
        assertEquals(ChangeDirectory.mycd("/", "..///.////abc/../abc/def/.././gh/..//def/ghi"), "/abc/def/ghi");
    }

    @Test
    void test_getPath() {
        // initialize the network of directories
        Directory ROOT = new Directory("/");
        Directory v1 = new Directory("abc", ROOT);
        Directory v2 = new Directory("def", v1);
        Directory v3 = new Directory("klm", v1);
        Directory v4 = new Directory("gh", v1);
        Directory v5 = new Directory("ghi", v2);

        assertEquals(ChangeDirectory.getPath(ROOT), "/");
        assertEquals(ChangeDirectory.getPath(v1), "/abc");
        assertEquals(ChangeDirectory.getPath(v2), "/abc/def");
        assertEquals(ChangeDirectory.getPath(v3), "/abc/klm");
        assertEquals(ChangeDirectory.getPath(v4), "/abc/gh");
        assertEquals(ChangeDirectory.getPath(v5), "/abc/def/ghi");
    }

    @Test
    void test_removeHolesLst() {
        String[] input1 = new String[] {};
        String[] input2 = new String[] {""};
        String[] input3 = new String[] {"", "1"};
        String[] input4 = new String[] {"1", ""};
        String[] input5 = new String[] {"1", "", "2"};
        String[] input6 = new String[] {"1", "2", "3"};

        String[] output1 = new String[] {};
        String[] output2 = new String[] {"1"};
        String[] output3 = new String[] {"1", "2"};
        String[] output4 = new String[] {"1", "2", "3"};

        assertArrayEquals(ChangeDirectory.removeHolesLst(input1), output1);
        assertArrayEquals(ChangeDirectory.removeHolesLst(input2), output1);
        assertArrayEquals(ChangeDirectory.removeHolesLst(input3), output2);
        assertArrayEquals(ChangeDirectory.removeHolesLst(input4), output2);
        assertArrayEquals(ChangeDirectory.removeHolesLst(input5), output3);
        assertArrayEquals(ChangeDirectory.removeHolesLst(input6), output4);
    }

    @Test
    void test_getDirectory() {
        // initialize the network of directories
        Directory ROOT = new Directory("/");
        Directory v1 = new Directory("abc", ROOT);
        Directory v2 = new Directory("def", v1);
        Directory v3 = new Directory("klm", v1);
        Directory v4 = new Directory("gh", v1);
        Directory v5 = new Directory("ghi", v2);

        try {
            assertEquals(ChangeDirectory.getDirectory("/", ROOT), ROOT);
            assertEquals(ChangeDirectory.getDirectory("/abc", ROOT), v1);
            assertEquals(ChangeDirectory.getDirectory("/abc/def", ROOT), v2);
            assertEquals(ChangeDirectory.getDirectory("/abc/klm", ROOT), v3);
            assertEquals(ChangeDirectory.getDirectory("/abc/gh", ROOT), v4);
            assertEquals(ChangeDirectory.getDirectory("/abc/def/ghi", ROOT), v5);
        }
        catch(NoSuchFieldException e) {
            ;
        }
    }

    @Test
    void test_isAlphaNumeric() {
        assertFalse(ChangeDirectory.isAlphaNumeric(""));
        assertFalse(ChangeDirectory.isAlphaNumeric("_"));

        assertTrue(ChangeDirectory.isAlphaNumeric("a"));
        assertTrue(ChangeDirectory.isAlphaNumeric("1"));
        assertTrue(ChangeDirectory.isAlphaNumeric("abc"));
        assertTrue(ChangeDirectory.isAlphaNumeric("123"));
        assertTrue(ChangeDirectory.isAlphaNumeric("12ab"));
    }


}
