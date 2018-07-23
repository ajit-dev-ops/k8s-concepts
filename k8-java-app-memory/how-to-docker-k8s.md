## setting up docker image and deployment resource of kubernetes

### Dockerfile 
- to set default values of memory optimization.
- to make java app aware of memory available to pod/docker.

```bash
#FROM my-repo/app/tomcat:8.0
#FROM tomcat:8.0

ARG JAVA_OPTS=""

#RUN apt-get update -y

#RUN apt-get install wget -y
#RUN apt-get install curl -y
#RUN apt-get install netcat -y
#RUN apt-get install vim -y
#RUN apt-get install less -y


RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./*.war /usr/local/tomcat/webapps/ROOT.war

# setting default options for memory, would be overwritten in k8s manifest definition. 
ENV JAVA_OPTS "$JAVA_OPTS -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"

ENTRYPOINT ["/scripts/docker-entrypoint.sh"]
```

### Deployment.yml 

Container definition would define an environment variable JAVA_OPTS.
The value of it is read from secret my-app-access-info defined. 

```bash
kubectl --insecure-skip-tls-verify create secret generic my-app-access-info \
    --from-literal=java_opt="-Xmx1024m -Xss1024k -XX:ReservedCodeCacheSize=240m -Dspring.profiles.active="$PROFILE_ACTIVE" 
    --from-file=server.xml=server.xml \
    --dry-run -o yaml | kubectl --insecure-skip-tls-verify apply -n $NAMESPACE -f -
```

```yaml

env:
- name: JAVA_OPTS
  valueFrom:
    secretKeyRef:
      name: my-app-access-info
      key: java_opt
```
