java -jar ..\lib\java-cup-11a.jar parser.cup
java -jar ..\lib\JFlex.jar scanner.flex
java -jar ..\lib\JFlex.jar scanner2.flex

::copy parser.java ..\src\syntax\
::copy scanner.java ..\src\syntax\
copy scanner2.java ..\src\syntax\
copy sym.java ..\src\syntax\
