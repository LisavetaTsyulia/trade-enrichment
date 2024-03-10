See the [TASK](./TASK.md) file for instructions.

Please document your solution here...

# Approach
After reading the task, I've selected most important features that implementation should have and started 
thinking about most appropriate solution.

I've worked with csv parsing/persisting before using opencsv third party library, so my first implementation
was based on this lib and just making sure positive scenario works on small files.

Next thing to do was making sure implementation can handle huge files, after some searching,
Spring Batch seemed exactly what I need: https://docs.spring.io/spring-batch/reference/spring-batch-intro.html

## Spring Batch

It's primary use case looks like this:
- Reads a large number of records from a database, file, or queue.
- Processes the data in some fashion.
- Writes back data in a modified form.

`Spring Batch automates this basic batch iteration, providing the capability to process similar transactions as a set, typically in an offline environment without any user interaction. Batch jobs are part of most IT projects, and Spring Batch is the only open source framework that provides a robust, enterprise-scale solution.`

Also, Spring Batch documentation covered:
- Scaling and parallel processing scenarios, that is useful for big files: https://docs.spring.io/spring-batch/reference/scalability.html
- other useful things like monitoring and metrics, logging Processing and Failures

## Products handling
First implementation is read products from csv file and put them to Map, 
cause we'll mostly need getting product_name(value) by product_id(value).
This implementation for 100K products was taking to initialize 504 milliseconds

But when running the products in prod env, there might be required some product inserts and updates, so
here we could use either some admin endpoint to affect our products Map. But another case could be store data in some DB 
to avoid restarts.

# How to run

## Run jar file

- download the project
- run ``mvn clean install``
- ``java -jar </path/to/the/application>/target/trade-enrichment-service-0.0.1-SNAPSHOT.jar``
Where </path/to/the/application> is the path to the cloned repository.

## Send request

Following request example was launched from project folder

``curl -F data=@src/test/resources/trades20M.csv  --output enrichedTrades.csv --header 'Content-Type: multipart/form-data' -X POST http://localhost:8080/api/v1/enrich``

## Generation new test files

`/script/main.py`

To launch it, simply change in file sample csv file (from where header and other records as trades would be taken)
 and num (how many times the sample file would be duplicated). Then execute following command in terminal:

``python3 src/test/resources/main.py``

## Check Result lines

To double-check the lines of result file, simply run below command in terminal

``cat enrichedTrades.csv | wc -l``

## H2 db

For executing Spring Batch requires some place to store info about jobs, steps and executions.
While project runs, it's possible to access: http://localhost:8080/h2-console
and check out for example BATCH_STEP_EXECUTION schema, where details about how many lines were read, how long it took

## Metrics of execution

Here aare some gathered time metrics for following config:

- chunk size: 1000 
- thread pool core size: 10
- 100 mil records = 3 min 9 seconds
- 20 mil records = 33.5 seconds

![](./images/h2metrics.png)

# Limitations to the service

## Input file size

the max input file size is set to 5GB

## Handling parallel rest requests

Currently there is only 1 job bean and next rest request can be processed only if job is in completed state.
Here we can go 2 ways:
1) Configure running multiple jobs, here bean scopes could help or further investigating spring batch service and how to configure such scenarious. Here we should then also think about rate limits per IP, making sure we know limits to our application(if file limit is 5GB, how many requests we can handle, etc.).
2) One thing that will happen using 1st option is that response time for requests will be more(if jobs will run in parallel processing data). So another way this service could work is by creating LoadBalancer, that will get requests from users, spawn several instances of this server(Workers), handling one request at a time. And Load Balancer will be aware of what services are busy now and transfer requests to free Worker.

# Improvements to do

- Investigating more into limitation "Handling parallel rest requests", implement 1st option, implement 2nd and check what is the better approach.
- Making ProductMapper implementation based on some storage solution.
- Better exception handling.
- More test coverage.
- More thinking about Thread safety.
- Investigating and implementing Partitioning technique from Spring Batch framework: https://docs.spring.io/spring-batch/reference/scalability.html#partitioning, compare efficiency of application when using partitioning vs partitioning + multi-threaded Step that I use now https://docs.spring.io/spring-batch/reference/scalability.html#multithreadedStep