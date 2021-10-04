def call(Map config = [:]) {
    sh(label: 'ECR login and docker push', script:
         '''
         #!/bin/bash
         
           echo "Authenticate with ECR"
            set +x 
            echo "Building New ECR Image"
            eval $(aws ecr get-login --region ${config.AWS_REGION} --no-include-email)
             
            set -xe
            echo ${config.GITCOMMIT}
            docker build  -t ${config.IMAGE}:${config.GITCOMMIT} .
            #two push one for master tag other is git commit ID
            docker push ${config.IMAGE}:${config.GITCOMMIT}
            docker tag ${config.IMAGE}:${config.GITCOMMIT} ${config.IMAGE}:latest
            docker push ${config.IMAGE}:latest
         '''.stripIndent())

      }
