## JAVA-8-Fixing the memory allocation for JVM 
Java 8 does not look for memory/cpu limits of docker instead of underlaying host. To fix this we pass arguments to JVM when the java application starts.
We would use JAVA_OPTS environment varibale for the purpose.

### Check which options are available to with JVM
java -X
```bash
-Xmixed           mixed mode execution (default)
-Xint             interpreted mode execution only
-Xbootclasspath:<directories and zip/jar files separated by ;>
                      set search path for bootstrap classes and resources
-Xbootclasspath/a:<directories and zip/jar files separated by ;>
                      append to end of bootstrap class path
-Xbootclasspath/p:<directories and zip/jar files separated by ;>
                      prepend in front of bootstrap class path
-Xdiag            show additional diagnostic messages
-Xnoclassgc       disable class garbage collection
-Xincgc           enable incremental garbage collection
-Xloggc:<file>    log GC status to a file with time stamps
-Xbatch           disable background compilation
-Xms<size>        set initial Java heap size.........................
-Xmx<size>        set maximum Java heap size.........................
-Xss<size>        set java thread stack size
-Xprof            output cpu profiling data
-Xfuture          enable strictest checks, anticipating future default
-Xrs              reduce use of OS signals by Java/VM (see documentation)
-Xcheck:jni       perform additional checks for JNI functions
-Xshare:off       do not attempt to use shared class data
-Xshare:auto      use shared class data if possible (default)
-Xshare:on        require using shared class data, otherwise fail.
-XshowSettings    show all settings and continue
-XshowSettings:all         show all settings and continue
-XshowSettings:vm          show all vm related settings and continue
-XshowSettings:properties  show all property settings and continue
-XshowSettings:locale      show all locale related settings and continue
```

### Xmx & Xms options:

The flag Xmx specifies the maximum memory allocation pool for a Java virtual machine (JVM), while Xms specifies the initial memory allocation pool.

This means that your JVM will be started with Xms amount of memory and will be able to use a maximum of Xmx amount of memory. 

For example, starting a JVM like below will start it with 256 MB of memory and will allow the process to use up to 2048 MB of memory:
java -Xms256m -Xmx2048m

The memory flag can also be specified in multiple sizes, such as kilobytes, megabytes, and so on.
-Xmx1024k
-Xmx512m
-Xmx8g

The Xms flag has no default value, and Xmx typically has a default value of 256 MB. A common use for these flags is when you encounter a java.lang.OutOfMemoryError.

When using these settings, keep in mind that these settings are for the JVM's heap, and that the JVM can/will use more memory than just the size allocated to the heap. From Oracle's documentation:

Note that the JVM uses more memory than just the heap. For example Java methods, thread stacks and native handles are allocated in memory separate from the heap, as well as JVM internal data structures.

### Xss flag - set java thread stack size

1mb or 1024k is the default on a 64bit machine.

Get default / current values
 ```bash 
 java -XX:+PrintFlagsFinal -version | grep ThreadStackSize
 ```

Each thread in a Java application has its own stack. The stack is used to hold return addresses, function/method call arguments, etc. 
So if a thread tends to process large structures via recursive algorithms, it may need a large stack for all those return addresses and such.
 With the Sun JVM, you can set that size via that parameter.

You should touch it in either of these two situations:

StackOverflowError (the stack size is greater than the limit), increase the value
OutOfMemoryError: unable to create new native thread (too many threads, each thread has a large stack), decrease it.
The latter usually comes when your Xss is set too large - then you need to balance it (testing!)
 
### ReservedCodeCacheSize & InitialCodeCacheSize 

ReservedCodeCacheSize (and InitialCodeCacheSize) is an option for the (just-in-time) compiler of the Java Hotspot VM. 
Basically it sets the maximum size for the compiler's code cache.

The cache can become full, which results in warnings like the following:

```bash
Java HotSpot(TM) 64-Bit Server VM warning: CodeCache is full. Compiler has been disabled.
Java HotSpot(TM) 64-Bit Server VM warning: Try increasing the code cache size using -XX:ReservedCodeCacheSize=
Code Cache  [0x000000010958f000, 0x000000010c52f000, 0x000000010c58f000)
 total_blobs=15406 nmethods=14989 adapters=362 free_code_cache=835Kb largest_free_block=449792
 
It's much worse when followed by Java HotSpot(TM) Client VM warning: Exception java.lang.OutOfMemoryError occurred dispatching signal SIGINT to handler- the VM may need to be forcibly terminated.
```

When to set this option?

when having Hotspot compiler failures
to reduce memory needed by the JVM (and hence risking JIT compiler failures)
Normally you'd not change this value. I think the default values are quite good balanced because this problems occur on very rare occasions only (in my experince).