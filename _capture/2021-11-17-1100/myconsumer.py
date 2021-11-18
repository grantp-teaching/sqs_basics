import boto3

queue_url = 'https://queue.amazonaws.com/381303118602/labq'

sqs_client = boto3.client('sqs')

while True:
    response = sqs_client.receive_message(QueueUrl=queue_url, WaitTimeSeconds=20, MaxNumberOfMessages=1)
    
    if 'Messages' not in response:
        continue

    for message in response['Messages']:
        print('received: ' + message['Body'])
        # processing takes palce
        sqs_client.delete_message(QueueUrl=queue_url, ReceiptHandle=message['ReceiptHandle'])

