import unittest
import change_directory

class Test(unittest.TestCase):
    def test_change_directory(self):
        # initialize the network of directories
        change_directory.create_network()
        
        # run the tests now that the directories exist in the environment of change_directory
        self.assertEqual(change_directory.my_cd("/", "."), "/")
        self.assertEqual(change_directory.my_cd("/abc/def", "../.."), "/")
        self.assertEqual(change_directory.my_cd("/abc/def", "../../.."), "/")
        self.assertEqual(change_directory.my_cd("/abc/def", "//////"), "/")
        self.assertEqual(change_directory.my_cd("/", "abc"), "/abc")
        self.assertEqual(change_directory.my_cd("/", "/abc"), "/abc")
        self.assertEqual(change_directory.my_cd("/abc", "."), "/abc")
        self.assertEqual(change_directory.my_cd("/abc/def", "/abc"), "/abc")
        self.assertEqual(change_directory.my_cd("/abc/def", ".."), "/abc")
        self.assertEqual(change_directory.my_cd("/abc/def", "ghi"), "/abc/def/ghi")
        self.assertEqual(change_directory.my_cd("/abc/def", "/abc/klm"), "/abc/klm")
        self.assertEqual(change_directory.my_cd("/abc/def", "."), "/abc/def")
        self.assertEqual(change_directory.my_cd("/abc/def", "../gh///../klm/."), "/abc/klm")
        self.assertEqual(change_directory.my_cd("/abc/def", "......"), "...... : No such file or directory.")
        self.assertEqual(change_directory.my_cd("/abc/def", "cba"), "cba : No such file or directory.")
        self.assertEqual(change_directory.my_cd("/def", "."), "/def : No such file or directory.")
        self.assertEqual(change_directory.my_cd("/", "/"), "/")
        self.assertEqual(change_directory.my_cd("/", "..///.////abc/../abc/def/.././gh/..//def/ghi"), "/abc/def/ghi")


    def test_my_cd_helper(self):
        # initialize the network of directories
        global_root = change_directory.Directory("/", None)
        abc = change_directory.Directory("abc", global_root)
        def_ = change_directory.Directory("def", abc)
        gh = change_directory.Directory("gh", abc)
        klm = change_directory.Directory("klm", abc)
        ghi = change_directory.Directory("ghi", def_)

        # get coverage on my_cd_helper
        self.assertEqual(change_directory.my_cd_helper(abc, []), "/abc") # empty case 
        self.assertEqual(change_directory.my_cd_helper(abc, ["."]), "/abc") # . case
        self.assertEqual(change_directory.my_cd_helper(abc, [".."]), "/") # .. case
        self.assertEqual(change_directory.my_cd_helper(abc, ["def"]), "/abc/def") # alnum case
        self.assertEqual(change_directory.my_cd_helper(abc, [".ab?"]), ".ab? : No such file or directory.") # not alnum case
        self.assertEqual(change_directory.my_cd_helper(abc, ["cba"]), "cba : No such file or directory.") # not in network case
        
        # more tests on my_cd_helper
        self.assertEqual(change_directory.my_cd_helper(abc, [".", "..", "abc", "def", "ghi", ".."]), "/abc/def")
        self.assertEqual(change_directory.my_cd_helper(global_root, ["..", ".."]), "/")
        self.assertEqual(change_directory.my_cd_helper(def_, [".."]), "/abc")


    def test_get_node(self):
        # initialize the network of directories
        global_root = change_directory.Directory("/", None) 
        abc = change_directory.Directory("abc", global_root)
        def_ = change_directory.Directory("def", abc)
        gh = change_directory.Directory("gh", abc)
        klm = change_directory.Directory("klm", abc)
        ghi = change_directory.Directory("ghi", def_)

        # tests for getNode
        self.assertEqual(change_directory.getNode("/", global_root), global_root) # root
        self.assertEqual(change_directory.getNode("/abc", global_root), abc) # abc
        self.assertEqual(change_directory.getNode("/abc/def", global_root), def_) # def
        self.assertEqual(change_directory.getNode("/abc/gh", global_root), gh) # gh
        self.assertEqual(change_directory.getNode("/abc/klm", global_root), klm) # klm
        self.assertEqual(change_directory.getNode("/abc/def/ghi", global_root), ghi) # ghi


    def test_get_path(self):
        # initialize the network of directories
        global_root = change_directory.Directory("/", None) 
        abc = change_directory.Directory("abc", global_root)
        def_ = change_directory.Directory("def", abc)
        gh = change_directory.Directory("gh", abc)
        klm = change_directory.Directory("klm", abc)
        ghi = change_directory.Directory("ghi", def_)

        # tests for getPath
        self.assertEqual(change_directory.getPath(global_root), "/")
        self.assertEqual(change_directory.getPath(abc), "/abc")
        self.assertEqual(change_directory.getPath(def_), "/abc/def")
        self.assertEqual(change_directory.getPath(gh), "/abc/gh")
        self.assertEqual(change_directory.getPath(klm), "/abc/klm")
        self.assertEqual(change_directory.getPath(ghi), "/abc/def/ghi")


if __name__ == '__main__':
    unittest.main()
