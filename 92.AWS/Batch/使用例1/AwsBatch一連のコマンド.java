// もととなるコンテナImageを pull
docker pull centos

// コンテナImageを確認
docker images

// コンテナの中に入る
docker run -it centos /bin/bash
-------------------------------------------------
■■■■■■■■■■■■  コンテナ内 に入ってやる場合？
//pipコマンドインストール
curl -kl https://bootstrap.pypa.io/get-pip.py | python

//aws-cliインストール
pip install awscli

//boto3インストール
pip install boto3

//gitインストール
yum install -y git

//CodeCommitからcloneして中のrun.shをキックするスクリプト
vi /usr/local/init.sh
---------
#!/bin/bash

CODE_REPO=https://git-codecommit.ap-northeast-1.amazonaws.com/v1/repos/<your repo>
CODE_DIR=/usr/local/test-batch

git clone --config credential.helper='!aws --region ap-northeast-1 codecommit credential-helper $@' --config credential.UseHttpPath=true $CODE_REPO $CODE_DIR

sh /usr/local/test-batch/run.sh
----------
exit

■■■■■■■■■■■■ DockerFile でインストールする場合？
FROM centos:latest
RUN curl -kl https://bootstrap.pypa.io/get-pip.py | python
RUN pip install awscli
RUN pip install boto3
RUN yum install -y git
ADD init.sh /usr/local/
-------------------------------------------------

▼ コンテナImage作成
//作業時のコンテナIDを確認
docker ps -a -n=5


//「test-container」としてコンテナイメージ作成
$ docker commit Container_ID test-container

//コマンドが使えること、ファイルがあることを確認
docker run -it test-container /bin/bash
