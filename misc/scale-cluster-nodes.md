# Concept: scale temporarily to allow for scheduling of pods 


You deploy a "dumb/small" pod which doesn't requires cpu or ram.
You set to PodAntiAffinity to not run on the same node where the pod runs.
You control your entire number of nodes in your cluster with just the "replicas" of your deployment
Scale down the replica of this dummy deployment once not needed.

for e.g. 
Spawning a new cluster - now it would take quite some time for nodes to scale and get ready but above can be used to pre-scale cluster with enough nodes. 
during istio upgrade as cni daemonset takes 1 pod on each node, and since we need to restart all pods afterwards some of them do not get started in time and causes outage until the new nodes are ready.

e.g. Deployment:
```yaml

create this deploy with podantiaffinity

create priority classes on commontools ns


```


links:
