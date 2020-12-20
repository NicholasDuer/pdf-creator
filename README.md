# pdf-creator
A Java PDF creator which builds a PDF from a series of instructions. 
Instructions are read from an input stream. An instruction is either some text
to be added to the pdf or a command. 

The pdf-creator used in the demo main program reads instructions 
from a text file. The supported instructions are: 
* **.paragraph**: starts a new paragraph
* **.fill**: sets indentation to fill for paragraphs, where the last character of a line must end at
  the end of the margin (except for the last line of a paragraph)
* **.nofill**: the default, sets the formatter to regular formatting7
* **.large**: sets the font to large
* **.bold**: sets the font to bold
* **.italic**: sets the font to italic
* **.regular**: resets the font to normal font
* **.indent <number>** indents the current paragraph by the specified amount

Instructions must be placed line-by-line. A sample instruction set can be found 
in src/main/resources/demo-input.txt.

This solution uses iText7 to format the PDFs.

To project is built using Gradle. To build, navigate to the pdf-creator directory.
Then, type the following into your terminal or command prompt:

```
gradlew build
```

A demo run can then be executed by the following command:
```
gradlew run
```
The demo program uses the aforementioned demo-input.txt as the instruction set 
and will create a pdf named demo.pdf. To use a different instruction set, 
create a text file in the src/main/resources folder. Then, use the following command to
provide a new instruction set and pdf name:
```
gradlew run --args="my-input.txt my.pdf"
```
