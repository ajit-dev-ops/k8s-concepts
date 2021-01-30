# Apps with istio mtls

## Problem overview:
When istio is deployed with mtls, for pods which are deployed with istio envoy proxy side car, all traffic is hijacked by this proxy. Only mtls pods(with istio proxy) are authenticated and are allowed to communicate to each other. In this scenario following are the challenges:
1. HTTP liveness and readiness probe
   1. They do not work by default as k8s API server which is not able to access porbe endpoints since they are protected by istio mtls proxy
2. Custom Prometheus metrics 
   1. Applications that expose custom metrics, expose them over and /metrics endpoint, this endpoint is then polled by prometheus to collect pod metrics. But since by default this endpoint is also protected by istio mtls proxy, prometheus is not able to access it until it is deployed with istio proxy as well. 

Solutions:
1. Deploy a side car with app, which can proxy liveness/readiness probes and metrics enpoints. Obviously we would not protect this proxy with mtls.
   1. Pros:
      1. Underlaying app is not required to be adapted at all. 
      2. The functional endpoints of underlying app remain mtls protected with no code change. 
   2. Cons:
      1. An additional proxy docker image is required to be maintained
      2. Though exploits very minimal resources, is needed to be deployed along with app container within same pod.
Example  