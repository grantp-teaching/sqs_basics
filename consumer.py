﻿# Simple Python SQS consumer
# Peadar Grant

import sys

# must install boto3 to use this (pip3 install boto3)
import boto3

# get QueueUrl from command line
if sys.argv[1] is None:
    print('usage: python3 consumer.py https://queue_url_here/')
    exit
queue_url=sys.argv[1]

# client object for SQS
sqs = boto3.client('sqs')

while True:
    # receive
    response = sqs.receive_message(QueueUrl=queue_url, WaitTimeSeconds=20, MaxNumberOfMessages=1)

    # loop over all messages
    for message in response['Messages']:
        
        # do processing work here (example just prints)
        print("received: " + message['Body'])
        
        # delete once processed
        sqs.delete_message(QueueUrl=queue_url, ReceiptHandle=message['ReceiptHandle'])
        
    
