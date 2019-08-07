# Goal: try deploying critical pods such as istio-cni in a namespace other than kube-system

All critical pods were supposed to be deployed in kube-system ns until k8s 1.12
 

## Concept of critical pods and how to mark them as critical


Pods deployed via daemonset or deployments can be marked as critical. Starting from k8s 1.11 they must not be deployed to kube-system NS to achieve this.
They can be deployed to any ns

How to mark pods as critical:

    1. Add following annotation to deployment template spec (this is going to work only uptill k8s 1.15, as it is deprecated

```yaml

  template:
    metadata:
      labels:
        k8s-app: istio-cni-node
      annotations:
        # This, along with the CriticalAddonsOnly toleration below,
        # marks the pod as a critical add-on, ensuring it gets
        # priority scheduling and that its resources are reserved
        # if it ever gets evicted.
        scheduler.alpha.kubernetes.io/critical-pod: ''
```

    2. add priorityClassName to pod spec
```yaml

    template:
    metadata:
      labels:
        k8s-app: istio-cni-node
      annotations:
        # This, along with the CriticalAddonsOnly toleration below,
        # marks the pod as a critical add-on, ensuring it gets
        # priority scheduling and that its resources are reserved
        # if it ever gets evicted.
        scheduler.alpha.kubernetes.io/critical-pod: ''
    spec:
      nodeSelector:
        beta.kubernetes.io/os: linux
      hostNetwork: true
      tolerations:
        # Make sure istio-cni-node gets scheduled on all nodes.
        - effect: NoSchedule
          operator: Exists
        # Mark the pod as a critical add-on for rescheduling.
        - key: CriticalAddonsOnly
          operator: Exists
        - effect: NoExecute
          operator: Exists
      priorityClassName: system-node-critical # only this class is allwoed in kube-system ns
```
## Note: this priority-class-name `system-node-critical` does not work as in k8s code it is checked that, the ns must be kube-system along with priority class name.
so fot now only the above annotation works if one needs to mark a pod critical.

````
func priorityClassPermittedInNamespace(priorityClassName string, namespace string) bool {
	// Only allow system priorities in the system namespace. This is to prevent abuse or incorrect
	// usage of these priorities. Pods created at these priorities could preempt system critical
	// components.
	for _, spc := range scheduling.SystemPriorityClasses() {
		if spc.Name == priorityClassName && namespace != metav1.NamespaceSystem {
			return false
		}
	}
	return true
}
````
https://github.com/kubernetes/kubernetes/pull/65593 

## Error if priorityclassname is tried in another ns
`Error creating: pods "istio-cni-node-" is forbidden: pods with system-node-critical priorityClass is not permitted in istio-system namespace


## defining own priority classes 

check api versions 
```bash
kubectl api-versions # available apis 
kubectl api-resources # list api n resources 
```



````yaml
apiVersion: scheduling.k8s.io/v1beta1 #scheduling.k8s.io/v1
kind: PriorityClass
metadata:
  name: high-priority
value: 1000000
globalDefault: false
description: "This priority class should be used for XYZ service pods only."
---
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    env: test
spec:
  containers:
  - name: nginx
    image: nginx
    imagePullPolicy: IfNotPresent
  priorityClassName: high-priority
---
apiVersion: v1
kind: Pod
metadata:
  name: nginx2
  labels:
    env: test
spec:
  containers:
  - name: nginx
    image: nginx
    imagePullPolicy: IfNotPresent
  priorityClassName: system-node-critical # only this class is allwoed in kube-system ns

````

https://kubernetes.io/docs/concepts/configuration/pod-priority-preemption/#priorityclass

## links
https://kubernetes.io/docs/tasks/administer-cluster/guaranteed-scheduling-critical-addon-pods/
https://kubernetes.io/docs/concepts/policy/pod-security-policy/

https://kubernetes.io/docs/tasks/configure-pod-container/quality-service-pod/
https://kubernetes.io/docs/concepts/workloads/pods/disruptions/
https://kubernetes.io/docs/concepts/configuration/pod-priority-preemption/