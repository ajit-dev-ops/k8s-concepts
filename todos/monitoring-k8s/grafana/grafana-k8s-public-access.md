Temporary setup:

1. create a nginx instance as side car
2. setup basic auth and may be ip restriction
[Restricting Access with HTTP Basic Authentication](https://docs.nginx.com/nginx/admin-guide/security-controls/configuring-http-basic-authentication/)
3. setup tsl/ssl with a self signed cert - todo document the process here for once and all
4. proxy all traffic to localhost 3000
5. Create a K8s sevice of type public LB with ports 443 open
6. 

Example prom queries
sum(kube_pod_container_resource_requests_memory_bytes{namespace="argonautsmunich"} /1024/1024) by (namespace)
conatiner_memory_cache
