# Usage : 

1. Download the zip folder of the project.
2. Make sure you can browse into the target folder. 
3. You can run the "java -jar" command from the target folder. 

For example, if you are in current folder => D:/Target
Run the cmd command below

d:target/> java -jar PermDataExtraction-final.jar No STANDARD PermDownloadFile.csv

# Command and Args definition

java -jar PermDataExtraction-final.jar [Testing?(Yes/No)] [Search String] [File Name]

Arguments  -
[Testing?(Yes/No)]: If set to 'Yes' It will only extract 5 rows. This can be used for sampling test results.
[Search String]: Any search string for which it will extract the first 100 records. If there is missing resources it will skip.
 Note, you can use URL encoded characters to use SPACE (%20). For example, [Search String] = JP%20MORGAN.
[FileName]: Give desired file name of your choice. Remember that it will PostFix the search string to the filename for usability.

# Additional Comment

- To avoid hitting a concurrency limit with REFINITIV, on purpose there is a one second delay added into the program.
- There is a inhirent MAX limit in REFINITIV (num) for number of entities we can retrive within a single processing call. 
- The program first gets the search results and iterates through all the perm links within the search results. Therefore it will not be possible to extract more than 50 first search results from the utility process.
- There is possible enchancement that can be made to extract more records but for now it will be left unchanged.

# Example Usage:

java -jar PermDataExtraction-final.jar Yes STA testfile.csv

# Example Output

$ java -jar PermDataExtraction-final.jar No JP%20MORGAN test
args passed
===========
Testing:No
Search String:JP%20MORGAN
Filename:test

===Download Count: 50===
Row 1 done.
Row 2 done.
Row 3 done.
Row 4 done.
Row 5 done.
Row 6 done.
Row 7 done.
Row 8 done.
Row 9 done.
Row 10 done.
Row 11 done.
Row 12 done.
Row 13 done.
Row 14 done.
Row 15 done.
Row 16 done.
Row 17 done.
Row 18 done.
Row 19 done.
Row 20 done.
Row 21 done.
Row 22 done.
Row 23 done.
Row 24 done.
Row 25 done.
Row 26 done.
Row 27 done.
Row 28 done.
Row 29 done.
Row 30 done.
Row 31 done.
Row 32 done.
Row 33 done.
Row 34 done.
Row 35 done.
Row 36 done.
Row 37 done.
Row 38 done.
Row 39 done.
Row 40 done.
Row 41 done.
Row 42 done.
Row 43 done.
Row 44 done.
Row 45 done.
Row 46 done.
Row 47 done.
Row 48 done.
Row 49 done.
Row 50 done.


# Output File

D:\git\Java\PermDataExtraction\target>dir
 Volume in drive D is DATA
 Volume Serial Number is B40D-4670

 Directory of D:\git\Java\PermDataExtraction\target

24-10-2019  16:25    <DIR>          .
24-10-2019  16:25    <DIR>          ..
24-10-2019  16:20    <DIR>          classes
24-10-2019  16:14    <DIR>          libs
24-10-2019  16:14    <DIR>          maven-archiver
24-10-2019  16:14    <DIR>          maven-status
24-10-2019  16:20             6,115 PermDataExtraction-final.jar
24-10-2019  15:42    <DIR>          test-classes
24-10-2019  16:04               662 test1.csv.TIR
24-10-2019  16:25               662 testfile.csv.TRI
               3 File(s)          7,439 bytes
               7 Dir(s)  979,930,914,816 bytes free