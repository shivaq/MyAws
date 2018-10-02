#!/usr/bin/python
# -*- coding: utf-8 -*-

from pprint import pprint
import json
import os
import boto3
from logging import getLogger, INFO

logger = getLogger()

ec2_client = boto3.client('ec2')
ec2_resource = boto3.resource('ec2')

print('Loading function')

TAG_KEY_RUNNING_OR_STOP = 'RunningOrStop'
TAG_VALUE_RUNNING_OR_STOP = os.environ['RunningOrStop']


def lambda_handler(event, context):
    pprint('Received event: ' + json.dumps(event, indent=2))

    instance_list = ec2_client.describe_instances(Filters=[
        {'Name': 'tag:' + TAG_KEY_RUNNING_OR_STOP, 'Values': ['stop_everynight']}]
    )

    stop_ec2_instances(instance_list)


def stop_ec2_instances(instance_list):
    for Reservations in instance_list["Reservations"]:
        for one_instance in Reservations['Instances']:
            ec2_name = get_instance_tag_value(one_instance, 'Name')
            if get_instance_state(one_instance) == 'stopped':
                print("{0} is already stopped".format(ec2_name))
            elif get_instance_state(one_instance) == 'terminated':
                ec2_id = get_instance_id(one_instance)
                print("{0} ({1})is terminated".format(ec2_name, ec2_id))
                continue
            elif get_instance_state(one_instance) == 'shutting-down':
                print("{0} is shutting down".format(ec2_name))
            else:
                print("Start stopping {0}".format(ec2_name))
                ec2_client.stop_instances(InstanceIds=[get_instance_id(one_instance)])


def get_instance_tag_value(instance_info, key):
    tags = instance_info['Tags']
    for tag in tags:
        if not (key == tag['Key']):
            continue

        return tag['Value']

    return None


def get_instance_id(one_instance):
    return one_instance['InstanceId']


def get_instance_state(one_instance):
    return one_instance['State']['Name']
