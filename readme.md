[![Coverage Status](https://coveralls.io/repos/github/teazaid/growth-rate/badge.svg?branch=master)](https://coveralls.io/github/teazaid/growth-rate?branch=master)

Solution is written is scala thus scala specific features and FP principles are used: 
 - value classes (https://docs.scala-lang.org/overviews/core/value-classes.html)
 These are provide compile time safety and no runtime overhead.
 - all services are stateless
 - no mutability
 - tail recursion is used in order to prevent stack overflow (https://www.scala-lang.org/api/2.12.3/scala/annotation/tailrec.html)
 
 
 Assumptions:
 - if there is only 1 file stats per file it wont be written into resulting file.
 Because there is no explanation how to measure growth rate.
 - growth rate is measured between current record and a previous one(if any).
 1,"2015-03-25 10:00:16.902",1000000
 1,"2015-03-25 12:00:16.902",2000000
 In the example above, growth per hour is 500000, because there is 2 hours difference between records.
 
 - corrupted data is skipped during parsing files
 
 Solution is possible to run via `Runner.scala`
 
 `Runner.scala` is not covered with the tests 
 
