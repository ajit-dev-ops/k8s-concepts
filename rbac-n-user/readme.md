. ./kube/create-kube-conf.sh [SERVICE_ACCOUNT_NAME] [NAMESPACE]

An laternate and easy way is
```yaml
apiVersion: rbacmanager.reactiveops.io/v1beta1
kind: RBACDefinition
metadata:
  name: joe-access
rbacBindings:
  - name: joe
    subjects:
      - kind: ServiceAccount
        name: istio-cd-1
        namespace: istio-system
    ClusterRoleBindings:
      - clusterRole: cluster-admin

```
more at: https://github.com/FairwindsOps/rbac-manager
