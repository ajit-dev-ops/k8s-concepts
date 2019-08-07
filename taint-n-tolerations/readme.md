# Concept

Each node or group of nodes can be tainted with a key:value:effect taint. Such nodes are by default are avaialable for pods to be scheduled unless, 
pod defines a toleration in its spec.

e.g. a deployment

template:
....
 spec:
 ....
```yaml
    tolerations:
        # Make sure istio-cni-node gets scheduled on all nodes.
        - effect: NoSchedule
          operator: Exists
        # Mark the pod as a critical add-on for rescheduling.
        - key: CriticalAddonsOnly
          operator: Exists
        - effect: NoExecute
          operator: Exists
        # all nodes with a taint, where key is workload, this pod can be scheduled, we dont care about value or effect in this case
        - key: workload
          operator: Exists
```
##Defining a taint

kubectl taint node 




### Get all taints of all nodes

```
kubectl get nodes -o json | jq '.items[].spec.taints'

...
[
  {
    "effect": "NoSchedule",
    "key": "workload",
    "value": "api-pods"
  }
]
...
```