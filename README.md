# CD_Simulator
Unix cd simulator written in Python and Java

Python Simulator
-I was able to fully implement the Python abstract change directory function.<br />
-The file and its unit testing runs from the command line.<br />
-I implemented the abstract directories with a Directory class containing name, children, and parent.<br />
-I decided to implement my_cd with recursion for my Python file so that my two files wouldnt be too similar!<br />
-My test cases use the network that was implied in the email:<br />
            ------> gh<br />
 root      |<br />
  / ----> abc ----> def ----> ghi<br />
           |<br />
            ------> klm<br />
-In order to run the file: python3 change_directory.py<br />
-In order to run the unit tests: python3 test_change_directory.py<br />
<br />
<br />
<br />
Java Simulator<br />
-I was able to fully implement the Java abstract change directory function.<br />
-The file runs from the command line<br />
-I was able to run the Junit testing file from within IntelliJ IDE<br />
-Although the Junit file won't run from the command line, it is very easy to get up and running inside IntelliJ<br />
  -Add the org.junit.jupiter:junit-jupiter:5.6.0-M12 Library to the project structure<br />
  -The run button on the testing file should then work as expected.<br />
-My test cases once again use the network that was implied in the email (see above)<br />
-I implemented the abstract directories with a Directory class containing name, children, and parent.<br />
-I decided to implement mycd without recursion for my Java file.<br />
-In order to run the file: javac ChangeDirectory.java   and then  java ChangeDirectory<br />
