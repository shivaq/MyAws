#!/usr/bin/python
# -*- coding: utf-8 -*-

from pprint import pprint
import json
import os
import boto3
# boto3 を使っていても、例外処理には下記を使う
from botocore.exceptions import ClientError
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

ec2_client = boto3.client('ec2')

print('Loading function')

TAG_KEY_TO_EXTRACT = os.environ['TagKeyToExtract']
TAG_VALUE_VALUE_TO_EXTRACT = os.environ['TagValueToExtract']

def lambda_handler(event, context):
    logger.info("Received event: %s" % json.dumps(event))

    instance_list = ec2_client.describe_instances(Filters=[
        {'Name': 'tag:' + TAG_KEY_TO_EXTRACT, 'Values': [TAG_VALUE_VALUE_TO_EXTRACT]}]
    )

    extract_instance_id_list(instance_list)

def extract_instance_id_list(instance_list):
    try:
        for Reservations in instance_list["Reservations"]:
            for one_instance in Reservations['Instances']:
                # Get a name of instances
                ec2_name = get_instance_tag_value(one_instance, 'Name')
                if ec2_name == None:
                    continue
                pprint("Start launching {0}".format(ec2_name))
                pprint(get_instance_id(one_instance))
    except ClientError as e:
        logger.error('Error: %s', e)

def get_instance_tag_value(instance_info, key):
    tags = instance_info['Tags']
    for tag in tags:
        if not (key == tag['Key']):
            continue

        return tag['Value']

    return None

def get_instance_id(one_instance):
    return one_instance['InstanceId']
