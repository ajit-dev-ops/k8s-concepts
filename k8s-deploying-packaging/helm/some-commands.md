### List

```bash
brew install kubernetes-helm
helm init # installs tiller service and pod in k8s
helm install --name=zooming-pig stable/mysql
helm upgrade zooming-pig stable/mysql
helm ls
helm delete zooming-pig
helm create mychart
helm get manifest clunky-serval **to see the entire generated YAML**
helm install --debug --dry-run ./mychart

```

### HElm inbuilt objects
1. Release
2. Chart
3. Files
4. Capabilities
5. Template
6. Values
    - helm install -f myvals.yaml ./mychart
    - helm install --set foo=bar ./mychart
    values.yaml is the default, which can be overridden by a parent chart’s values.yaml, which can in turn be overridden by a user-supplied values file, which can in turn be overridden by --set parameters.
    note --set can also override properties of yaml which are actually not templated.
    -- [Default value can be removed by setting its value to null](https://docs.helm.sh/chart_template_guide/#deleting-a-default-key)   
    - [Functions & chaining functions](https://docs.helm.sh/chart_template_guide/#using-the-default-function)
### Links
- https://docs.helm.sh/using_helm/#quickstart-guide
- https://deis.com/blog/2016/getting-started-authoring-helm-charts/
- https://docs.helm.sh/chart_template_guide/#the-chart-template-developer-s-guide


### 
1. todo get secrets from vault
2. 
