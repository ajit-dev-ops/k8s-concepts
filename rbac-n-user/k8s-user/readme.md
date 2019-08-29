this chart is used to create users and their kubeconfigs.
Roles:
- cluster-editor
- namespace-viewer
- namespace-editor 


helm template . --set role=namespace-viewer --name=ajit-viewer --set namespace=test
