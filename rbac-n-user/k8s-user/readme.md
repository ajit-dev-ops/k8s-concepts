this chart is used to create users and their kubeconfigs.
Roles:
- cluster-editor
- namespace-viewer
- namespace-editor 

```bash
helm --namespace kiwis upgrade -i -f ./kiwis-values.yaml --set role=namespace-viewer  --set namespace=test  test-sa-user ./ --debug --dry-run
```
