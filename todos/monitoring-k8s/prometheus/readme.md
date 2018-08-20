### core dropwizard
https://metrics.dropwizard.io/3.1.0/getting-started/
https://metrics.dropwizard.io/3.1.0/manual/core/

### USING existing dropwizard metrics and converting to prometheus
1. https://www.robustperception.io/exposing-dropwizard-metrics-to-prometheus/
1.1 https://github.com/RobustPerception/java_examples/blob/master/java_dropwizard/src/main/java/io/robustperception/java_examples/JavaDropwizard.java
Problem: [Timer](https://metrics.dropwizard.io/3.1.0/getting-started/#gs-timers) of dropwizard lib exposes both rate and duration and count as well.
its a combinations of Meter, Histograms, counter and duration measurements.
This conversion is not exposing rate and means only the durations. 

Dropwizard would not expose it: https://github.com/prometheus/client_java/issues/166




### push-gateway bundle thing did not work for me.   
https://github.com/dhatim/dropwizard-prometheus  