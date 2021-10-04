def call() {
    sh(label: 'ECR login and docker push', script:
         '''
         #!/bin/bash
         
           echo "Authenticate with ECR"
            set +x # Don't echo credentials from the login command!
            echo "Building New ECR Image"
            eval $(aws ecr get-login --region ${AWS_REGION} --no-include-email)
            # Enable Debug and Exit immediately 
            set -xe
            echo ${GITCOMMIT}
            docker build  -t ${IMAGE}:${GITCOMMIT} .
            #two push one for master tag other is git commit ID
            docker push ${IMAGE}:${GITCOMMIT}
            docker tag ${IMAGE}:${GITCOMMIT} ${IMAGE}:latest
            docker push ${IMAGE}:latest
         '''.stripIndent())

      }
