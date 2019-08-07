# e.g. Istio cni pods need to access host network 

````yaml
spec:
      nodeSelector:
        beta.kubernetes.io/os: linux
      hostNetwork: true
````

to allow this, we create psp and assign role to SA of cni pods:
````yaml
apiVersion: policy/v1beta1
kind: PodSecurityPolicy
metadata:
  name: cds-istio-cni
  namespace: istio-system
  annotations:
    seccomp.security.alpha.kubernetes.io/allowedProfileNames: '*'
  labels:
    app: {{ template "cds-istio-pre.name" . }}
    chart: {{ template "cds-istio-pre.chart" . }}
    heritage: {{ .Release.Service }}
    release: {{ .Release.Name }}
spec:
  volumes:
  - '*'
  hostNetwork: true
  runAsUser:
    rule: 'RunAsAny'
  seLinux:
    rule: 'RunAsAny'
  supplementalGroups:
    rule: 'RunAsAny'
  fsGroup:
    rule: 'RunAsAny'
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
   name: cds-istio-cni
   namespace: istio-system
   labels:
     app: {{ template "cds-istio-pre.name" . }}
     chart: {{ template "cds-istio-pre.chart" . }}
     heritage: {{ .Release.Service }}
     release: {{ .Release.Name }}
rules:
 - apiGroups:
   - extensions
   resourceNames:
   - cds-istio-cni
   resources:
   - podsecuritypolicies
   verbs:
   - use
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
   name: cds-istio-cni
   namespace: istio-system
   labels:
     app: {{ template "cds-istio-pre.name" . }}
     chart: {{ template "cds-istio-pre.chart" . }}
     heritage: {{ .Release.Service }}
     release: {{ .Release.Name }}
roleRef:
   apiGroup: rbac.authorization.k8s.io
   kind: ClusterRole
   name: cds-istio-cni
subjects:
 - kind: ServiceAccount
   name: istio-cni
   namespace: istio-system
---

````