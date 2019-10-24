# Usage : 

Copy the target folder over to any of the folder. You will need to copy all the files and folders.



# Command Line and Args

java -jar PermDataExtraction-final.jar
Usage is permExtractUtil [Testing?(Yes/No)] [Search String] [FileName]


[Testing?(Yes/No)]: If set to 'Yes' It will only retrive 5 records.
[Search String]: Any search string for which it will extract the first 100 records. If there is missing resources it will skip.
[FileName]: Give desired file name of your choice. Remember that it will PostFix the search string to the filename for usability.



# Example Usage:

java -jar PermDataExtraction-final.jar Yes TRI testfile.csv


# Example Output

Testing:Yes
Search String:TRI
Filename:testfile.csv
Size initialiazed:5
Current: https://permid.org/1-4295861160:0
Next: https://permid.org/1-4295903234:0
Row added:549300561UZND4C7B569,Thomson Reuters Corp,http://sws.geonames.org/6251999/,http://sws.geonames.org/6251999/,4295861160

done!
Current: https://permid.org/1-4295903234:1
Next: https://permid.org/1-4295886658:1
Row added:6QVMFZKY1QSOOIHD7Y77,Tri-Continental Corp,http://sws.geonames.org/6252001/,http://sws.geonames.org/6252001/,4295903234

done!
Current: https://permid.org/1-4295886658:2
Next: https://permid.org/1-4295896425:2
Row added:259400EX361KZAXTL620,Triton Development SA,http://sws.geonames.org/798544/,http://sws.geonames.org/798544/,4295886658

done!
Current: https://permid.org/1-4295896425:3
Next: https://permid.org/1-5028045245:3
Row added:213800TIXZSF4ZYI2E58,Tri-Star Resources PLC,http://sws.geonames.org/2635167/,http://sws.geonames.org/2635167/,4295896425

done!
Current: https://permid.org/1-5028045245:4
Next: https://permid.org/1-4296066152:4
Row added:,Tri Pointe Homes Inc,http://sws.geonames.org/6252001/,http://sws.geonames.org/6252001/,5028045245

done!

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

