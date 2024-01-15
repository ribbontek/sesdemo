# SES Demo

A simple demo app that utilises SpringBoot 3.2, Kotlin 1.9, Java 21, SQS, & SES

The SpringBoot Tests demo how to trigger bounce & complaint emails against a live SES environment

Set up the SES SQS stack using cloudformation
```
# Create the stack
aws cloudformation create-stack --stack-name SesDemoStack --template-body file://ses_queues.yaml --region us-east-1
# Check the stack
aws cloudformation describe-stacks --stack-name SesDemoStack --region us-east-1
# Update the stack
aws cloudformation update-stack --stack-name SesDemoStack --template-body file://ses_queues.yaml --region us-east-1
# delete the stack
aws cloudformation delete-stack --stack-name SesDemoStack --region us-east-1
```

Build & run the application
```
./gradlew clean build -i
```