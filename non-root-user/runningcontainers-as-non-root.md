check: for psp to work admission controller must be enabled for kube-api server via vi /etc/kubernetes/manifests/kube-apiserver.yaml
how:
exec in to kusystem's kube api pod 

then run  kube-apiserver -h | grep enable-admission-plugins

and then edit vi /etc/kubernetes/manifests/kube-apiserver.yaml add ,PodSecurityPolicy to enable-admission-plugins 

Not: this will make even the kube-api pod to not shcedule as there if no no psp associated with it at cluster or ks ns level.

error in logs: journalctl  -u kubelet.service --no-pager
``Aug 28 12:55:20 kind-control-plane kubelet[213]: E0828 12:55:20.434689     213 kubelet.go:1647] Failed creating a mirror pod for "kube-apiserver-kind-control-plane_kube-system(6fed86e174b4c7104c3881a87d0fa370)": pods "kube-apiserver-kind-control-plane" is forbidden: unable to validate against any pod security policy: []```


https://kubernetes.io/docs/reference/access-authn-authz/admission-controllers/#how-do-i-turn-on-an-admission-control-plug-in

steps:
1. create a psp restricted
2. create clusterrole or role for ns 
3. create clusterrolebinding or rolebinding for particular SA or whole namespaces
 
 
psp analyzer: https://sysdig.com/blog/enable-kubernetes-pod-security-policy/ 

links:
https://kubernetes.io/docs/tasks/configure-pod-container/security-context/
https://kubernetes.io/docs/reference/command-line-tools-reference/kube-apiserver/
https://github.com/helm/charts/blob/master/stable/prometheus/templates/alertmanager-clusterrolebinding.yaml
https://github.com/helm/charts/blob/master/stable/prometheus/templates/alertmanager-clusterrole.yaml#L9
https://kubernetes.io/docs/concepts/policy/pod-security-policy/#example

---
Run containers as Non-root user

There are two steps involved in this approach.

Dockerfile modification
Modify the Dockerfile of microservices and create user and group as follows 

FROM openjdk:8-jre-alpine
 
#for running container as non root user cds with id 5000
RUN addgroup -g 5001 cds && \
   adduser --system -D -u 5000 -G cds cds
USER 5000


Modify POD deployment
Apply securityContext fsgroup for all the containers and securityContext runAsUser, runAsGroup for each container.



apiVersion: apps/v1
kind: Deployment
metadata:
  name: notification
  namespace: conversion
...
template:
    spec:
      securityContext:
        fsGroup: 2000     # applies to all containers
 
    containers:
      - name: notification
        securityContext:
          allowPrivilegeEscalation: false
          runAsNonRoot: true
 
      - name: metrics-proxy-container
        image: nginx
        securityContext:
          allowPrivilegeEscalation: false
          runAsNonRoot: true
          runAsUser: 101          # applies to specific container. For nginx we use standard 101 non-root nginx user.
          runAsGroup: 101
