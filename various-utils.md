## copy secret accross name space
kubectl get secret stage-cds-myhybris-cloud --namespace=commontools --export -o yaml | kubectl apply --namespace=argonautsmunich -f -

##
