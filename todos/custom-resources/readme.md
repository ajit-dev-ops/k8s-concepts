## Custom resources
1. https://coreos.com/operators/
2. https://github.com/operator-framework/getting-started
3. https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/
4. [Custom Controller example, which uses Custom Resources](https://github.com/kubernetes/sample-controller)
5. 

Terms
### Custom resource - metadata for controller its similar to configmap, where CM is accessible as file while CR is via api. CR allws to store and retrieve structured data
### Custom controllers -  logic of code
### Operator - an sdk developed by core os to build custom resources & custom controllers
### Custom resource creation, two ways but once created via any way they are called as custom resources.
    #### via CRDs (custom resource definition)
        no new api servers are added.
    #### via API Aggregation (equires programming)
        subordinate APIServers are added. Aggregated APIs are subordinate APIServers that sit behind the primary API server, which acts as a proxy. This arrangement is called API Aggregation (AA). To users, it simply appears that the Kubernetes API is extended.
