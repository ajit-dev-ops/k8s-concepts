
# Creating a plugin - there two ways
1. Simple
    - just create any executable file bash/node/java etc
    - name it kubectl-<plugin_name>
    - put it in a path then exec *kubectl plugin_name ....*
    - plugin name must be second argument
Help:
https://www.youtube.com/watch?v=83ITOTsXsHU


2. Using k8s.io/cli-runtime 

### A samle plug-in to change the ns
https://github.com/kubernetes/sample-cli-plugin

## All other plugins by community:
1. https://github.com/danisla/kubefunc

### using plugins
install krew https://github.com/kubernetes-sigs/krew
list of plugins: https://github.com/kubernetes-sigs/krew-index/tree/master/plugins

### develop your plugin
https://github.com/kubernetes-sigs/krew/blob/master/docs/DEVELOPER_GUIDE.md#installing-plugins-locally
kubectl krew install --manifest=foo.yaml --archive=foo.tar.gz
https://github.com/kubernetes-sigs/krew/blob/master/docs/NAMING_GUIDE.md
tar --exclude='.[^/]*' -zcvf plugins.tar.gz .
tar -zxvf archive_name.tar.gz --untar
shasum -a 256 plugins.tar.gz
kubectl krew install --manifest=./kubectl-plugins/podstail/podstail.yaml --archive=plugins.tar.gz




Some commands:

# Other sililar utilities

## A list of lot of such tools
 https://terminalsare.sexy/
https://github.com/ytdl-org/youtube-dl/blob/master/README.md#readme

## Colored Ls command
1. first install nerd fonts https://github.com/ryanoasis/nerd-fonts/blob/master/readme.md
    1.1 brew tap homebrew/cask-fonts
        brew cask install font-hack-nerd-font
2. Then https://github.com/athityakumar/colorls  gem install colorls
