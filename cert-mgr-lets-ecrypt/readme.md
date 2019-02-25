1. deploy the Cert manager - this deploys some new CRDs 
2. create a certificate issuer issuer resource
    -  create a tachnical aws user for and setup in cert issuer
    -  this is required to complete the ACME challenge that lets encrypt issues, to verify the domain.
3. Create a Certificate resource with, domain and k8 secret name to store cert in it.
4. Use cert secretz in nginx or istio ingress controllers. 

links https://itnext.io/automated-tls-with-cert-manager-and-letsencrypt-for-kubernetes-7daaa5e0cae4