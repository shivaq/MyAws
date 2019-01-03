# -*- coding: utf-8 -*-

import boto3
from time import sleep

# S3
BUCKET_NAME = "test-bucket-batch"
TARGET_DIR = "batch"
FILE_CONTENTS = ""

# Timer
MAX_ITERATION = 10
SLEEP_SEC = 60

def PutS3(i):
  s3 = boto3.resource('s3')
  bucket = s3.Bucket(BUCKET_NAME)
  filename = str(i+1)

  obj = bucket.put_object(ACL='private', Body=FILE_CONTENTS, Key=TARGET_DIR + "/" + filename, ContentType='text/plain')
  return str(obj)

def print_count(i):
  print("{}秒経過しました。".format((i+1)*SLEEP_SEC))

if __name__ == '__main__':
  for i in range(MAX_ITERATION):
    sleep(SLEEP_SEC)
    print_count(i)
    PutS3(i)
