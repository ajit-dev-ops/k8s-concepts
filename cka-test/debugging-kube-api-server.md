Kube api server is run a static pod
pod manifest like all other static pods is located at /etc/kubernetes/manifests/kube-apiserver.yaml

The kube api server is then run as pod in kube-system namespace

## cli
kube-apiserver --enable-admission-plugins=NamespaceLifecycle  --- does not work

help:
kube-apiserver -h | grep enable-admission-plugins


## enable admission controller
edit /etc/kubernetes/manifests/kube-apiserver.yaml in kubernetes master server.


logs:

journalctl  -u kubelet.service --no-pager
