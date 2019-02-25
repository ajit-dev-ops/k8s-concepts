# How to confiure k8s Nginx ingress to Use Custom Error Pages

By default K8s nginx uses a defalt backend to forward any 404 request to this backend pod in case a resource is not found.

Ingress controller can be configured using config map to forward all other error requests to this default backend too.

```yaml
kind: ConfigMap
apiVersion: v1
metadata:
  name: {{ .Release.Name }}
  labels:
    k8s-addon: ingress-nginx.addons.k8s.io
  namespace: commontools
data:
  use-proxy-protocol: "true"
  large-client-header-buffers: "4 32k"
  enable-modsecurity: "true"
  enable-owasp-modsecurity-crs: "true"
  custom-http-errors: 400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,420,422,429,431,500,501,502,503,504,506,507,508,509,510,511,598,599
```

## Setting up a def back end with custom pages
#### Base image is defined at
-  https://github.com/kubernetes/ingress-nginx/blob/master/images/custom-error-pages/main.go
- Now Deafult backend by default is service pages that are in docker image /www folder we will overwrite these pages and add our own pages to this folder in our docker image.

### How is this working:
nginx ingress sends the error code to default backend in `X-CODE` header, which the default backed must decode and then build the static html page file path to return to. 

Dockerfile
```yaml
FROM quay.io/kubernetes-ingress-controller/custom-error-pages-amd64:0.3

COPY ./www /www
```

### ref
1. https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-to-use-custom-error-pages-on-ubuntu-14-04
