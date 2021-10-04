def call() {
	sh(label: 'push the container to k8s', script:
         '''
         #!/bin/bash
            
	    if [ -z ${GITCOMMIT} || -z ${APPNAME} || -z ${IMAGE} ]; then echo "one of the require variables is not set" && exit 14 ;fi  
            helm upgrade -i --set applicationManifest.image=${IMAGE}:${GITCOMMIT} ${APPNAME} ./helm
        
	 '''.stripIndent())
	} 
