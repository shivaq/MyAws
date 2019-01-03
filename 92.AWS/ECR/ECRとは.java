■■■■■■■■■■■■ ECRとは
・コンテナレジストリを使うのに必要なインフラを用意してくれる
・コンテナイメージの転送はHTTPSを使う
・冗長でスケーラブル
・ECS と Docker CLI とを統合


■■■■■■■■■■■■ 料金
▼ ストレージ
・1年間は 月 500MB まで無料
・0.10 USD 1GB/月

▼ データ転送 // ECR からのアウト、ECRへのイン
データ量は、Amazon EC2、Amazon Elastic Container Registry、Amazon EBS、Amazon S3、Amazon Glacier、Amazon RDS、
Amazon SimpleDB、Amazon SQS、Amazon SNS、Amazon DynamoDB、AWS Storage Gateway、Amazon VPC からのデータ転送量の合計

・単一リージョン内 ECR EC2 間転送
無料

・すべてのデータ転送受信 (イン)
無料

・データ転送送信 (アウト)
1 GB まで/月：無料
次の 9.999 TB/月：0.114USD/GB
-------------------------------------------------
