# Directory class represents each hypothetical directory
class Directory:
    # the init method deals with the root node as well as all others
    def __init__(self, a_name, a_parent):
        # all nodes will start with a given name and a 
        self.name = a_name
        self.children = []

        # this branch is for the root directory initialization
        if isinstance(a_parent, type(None)):
            # root is its own parent. This is how we deal with something like "/..", which should be "/"
            self.parent = self

        # this branch is for all other directories
        else:
            # add the parent to the self.parent field
            self.parent = a_parent
            # add current object to parents children list
            a_parent.children.append(self)

    def __eq__ (self, other):
        # first we check if the other is of type None, this would cause problems 
        if isinstance(other, type(None)):
            return False
        # if it is not None, we check the equality of the three fields. We only check the names of parents for the sake of avoiding endless recursion
        return self.name == other.name and self.parent.name == other.parent.name and self.children == other.children

    # this method finds a child in children list if it exists and returns the Directory
    def findChild(self, child_name):
        # loop throw the children list
        for child in self.children:
            # if the name of the child is correct, return it
            if child.name == child_name:
                return child
        # if no children match, return None
        return None



# finds the starting directory and then calls my_cd_helper to traverse the directories
def my_cd(curr_dir_path, new_dir):
    # split new_dir on the character "/"
    new_dir_list = new_dir.split("/")

    # get rid of holes in new_dir_list
    try:
        while True:
            new_dir_list.remove("") 
    except ValueError:
        pass
    
    # check if starting node is a legal path, if not return no such file string
    if(isinstance(getNode(curr_dir_path, global_root), type(None))):
        return curr_dir_path + " : No such file or directory."

    # here there was nothing after splitting and removing holes
    if new_dir_list == []: 
        return "/"
    
    # if first char is /, change currDir to global_root
    if  new_dir[0] == "/": 
        return my_cd_helper(global_root, new_dir_list)

    # recursively call my_cd_helper
    return my_cd_helper(getNode(curr_dir_path, global_root), new_dir_list)



# traverse the curr_dir_node with the new_dir_list recursively
def my_cd_helper(curr_dir_node, new_dir_list):
    # base case, list has been emptied
    if new_dir_list == []:
        return getPath(curr_dir_node)

    # case of ".", nothing should change in current directory
    elif new_dir_list[0] == ".":
        # pop the first item from the list and recurse
        new_dir_list.pop(0)
        return my_cd_helper(curr_dir_node, new_dir_list)

    # case of "..", go to parent directory
    elif new_dir_list[0] == "..":
        # pop the first item from the list and recurse
        new_dir_list.pop(0)
        return my_cd_helper(curr_dir_node.parent, new_dir_list)

    # else we have a string that we need to look closer at
    else:
        # check if we have an alphanumeric string
        if new_dir_list[0].isalnum():
            # now check if the child is in the current directory children list
            curr_dir_node = curr_dir_node.findChild(new_dir_list[0])

            # if it isnt in the children list, return no file string
            if(isinstance(curr_dir_node, type(None))):
                return new_dir_list[0] + " : No such file or directory."

            # otherwise, pop the item form the list and continue
            new_dir_list.pop(0)
            return my_cd_helper(curr_dir_node, new_dir_list) 

        # if the string isnt alphanumeric, it cant be a file path, so return no such file string
        else:
            return new_dir_list[0] + " : No such file or directory."



# get the Directory node that the curr_dir string points to
def getNode(dir_path, root):
    # start at the root node
    curr_dir_node = root 

    # split dir_path string into a list on character "/""
    dir_path_list = dir_path.split("/") 

    # get rid of holes in dir_path_list
    try:
        while True:
            dir_path_list.remove("")
    except ValueError:
        pass

    # traverse dir_path_list
    for new_path in dir_path_list: 
        # on each level look for the child directory with name new_path
        curr_dir_node = curr_dir_node.findChild(new_path)

        # if it doesnt exist, return None
        if(isinstance(curr_dir_node, type(None))):
            return None

    # finally return the node that we were looking for
    return curr_dir_node



# get the path of the Directory node (as a string)
def getPath(dir_node):
    # simplest case, return the root string
    if dir_node.name == "/":
        return "/"

    # start with the name of the current node and build from that
    result = dir_node.name

    # loop until we reach the root and then we will return
    while True:
        # update node with parent node
        dir_node = dir_node.parent
        # if we reach the root, return
        if dir_node.name == "/":
            return "/" + result 
        # otherwise build on result string and continue looping
        result =  dir_node.name + "/" + result



# initialize network of directories
def create_network():
    abc = Directory("abc", global_root)
    def_ = Directory("def", abc)
    gh = Directory("gh", abc)
    klm = Directory("klm", abc)
    ghi = Directory("ghi", def_)

# for my test cases I used the directory structure from the test cases given in the email: 
#             ------> gh
# root       |
#   / ----> abc ----> def ----> ghi
#            |
#             ------> klm



# global variable for the root directory
global_root = Directory("/", None)


# all of these test cases (and many more) are implemented in the test_change_directory.py file
def main():
    create_network()
    
    # test cases from email
    print(my_cd("/", "abc"))                        # /abc
    print(my_cd("/abc/def", "ghi"))                 # /abc/def/ghi
    print(my_cd("/abc/def", ".."))                  # /abc
    print(my_cd("/abc/def", "/abc"))                # /abc
    print(my_cd("/abc/def", "/abc/klm"))            # /abc/klm
    print(my_cd("/abc/def", "../.."))               # /
    print(my_cd("/abc/def", "../../.."))            # /
    print(my_cd("/abc/def", "."))                   # /abc/def
    print(my_cd("/abc/def", "//////"))              # /
    print(my_cd("/abc/def", "......"))              # ...... : No such file or directory.
    print(my_cd("/abc/def", "../gh///../klm/."))    # /abc/klm

    # some additional test cases
    print(my_cd("/abc/def", "cba"))                 # cba : No such file or directory.
    print(my_cd("/", "."))                          # / 
    print(my_cd("/", "/abc"))                       # /abc
    print(my_cd("/abc", "."))                       # /abc
    print(my_cd("/def", "."))                       # def : No such file or directory.
    print(my_cd("/", "/"))                          # / 
    print(my_cd("/", "..///.////abc/../abc/def/.././gh/..//def/ghi")) # /abc/def/ghi



if __name__ == "__main__":
    main()
