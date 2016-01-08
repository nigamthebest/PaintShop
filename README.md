# PaintShop
##Solution for Paint Shop Problem

The Algorithm parses an input file for the number of cases
For Each case it starts allocating colors with GLOSSY as a priority and adds the satisfied customers to a queue "customersQueue"
and keeping the color and finnish codes in a Map "outputColorMap"

When it cant satisfy a customer with the existing colors in "outputColorMap" or any color not in the "outputColorMap".
Then it starts removing previously satisfied customers from the "customersQueue" 
and tries to see if the  "previousCustomer" can be satisfied with another color so as to the unsatisfied customer can be satisfied
if Not possible it marks the case as IMPOSSIBLE 

It also marks the case as IMPOSSIBLE if the it has already traversed the entire queue and the existing customer cannot be satisfied 

 


To test the Algorithm
Simply provide the input file location as argument to the below command

mvn exec:java -Dexec.mainClass="com.paint.shop.PaintMaker" -Dexec.args="{absolute path of the test input file}"
 
You can expect an output as shown below 
 ```
   mvn exec:java -Dexec.mainClass="com.paint.shop.PaintMaker" -Dexec.args="c:\test123.txt"
  [INFO] Scanning for projects...
  [INFO]
  [INFO] Using the builder org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder with a thread count of 1
  [INFO]
  [INFO] ------------------------------------------------------------------------
  [INFO] Building PaintShop 1.0-SNAPSHOT
  [INFO] ------------------------------------------------------------------------
  [INFO]
  [INFO] --- exec-maven-plugin:1.4.0:java (default-cli) @ PaintShop ---
  Case #1  1 0 0 0 0
  Case #2 IMPOSSIBLE
  
  [INFO] ------------------------------------------------------------------------
  [INFO] BUILD SUCCESS
  [INFO] ------------------------------------------------------------------------
  [INFO] Total time: 0.988 s
  [INFO] Finished at: 2016-01-08T13:25:44+02:00
  [INFO] Final Memory: 11M/309M
  [INFO] ------------------------------------------------------------------------
```



