# CD_Simulator
Unix cd simulator written in Python and Java

Python Simulator
-I was able to fully implement the Python abstract change directory function.
-The file and its unit testing runs from the command line.
-I implemented the abstract directories with a Directory class containing name, children, and parent.
-I decided to implement my_cd with recursion for my Python file so that my two files wouldnt be too similar!
-My test cases use the network that was implied in the email:
            ------> gh
 root      |
  / ----> abc ----> def ----> ghi
           |
            ------> klm
-In order to run the file: python3 change_directory.py
-In order to run the unit tests: python3 test_change_directory.py



Java Simulator
-I was able to fully implement the Java abstract change directory function.
-The file runs from the command line
-I was able to run the Junit testing file from within IntelliJ IDE
-Although the Junit file won't run from the command line, it is very easy to get up and running inside IntelliJ
  -Add the org.junit.jupiter:junit-jupiter:5.6.0-M12 Library to the project structure
  -The run button on the testing file should then work as expected.
-My test cases once again use the network that was implied in the email (see above)
-I implemented the abstract directories with a Directory class containing name, children, and parent.
-I decided to implement mycd without recursion for my Java file.
-In order to run the file: javac ChangeDirectory.java   and then  java ChangeDirectory
