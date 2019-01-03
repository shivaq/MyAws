■■■■■■■■■■■■■■■■■■■■■■■■■■ Serverless Application Repository

■■■■■■■■■■■■■■■■■■■■■■■■■■ AWS Lambda の登場人物
▼Lambda のハンドラ
Lambda が呼び出すコード内の関数

▼ event
AWS Lambda がイベントデータをハンドラに渡すためのパラメータ。
辞書型
list, str 型なんかも使える

▼ context
AWS Lambda がランタイム情報を渡すのに使うパラメータ。
LambdaContext 型

▼ RequestResponse
AWS Lambda が、Lambda を呼び出したクライアントに返す戻り値
-------------------------------------------------

■■■■■■■■■■■■■■■■■■■■■■■■■■ Lambda と VPC
デフォルトでは、Lambda が VPC 内のリソースにアクセスすることはできない


▼ VPC を使う場合
・VPC を指定する
・Lambdaが使用する サブネット を指定する
・Lambdaが使用する セキュリティグループを指定する
・インターネットアクセスを行うために、追加設定が必要になる
・追加設定：SGがそれを許可していること
・追加設定：VPCにNATゲートウェイがあること

注意事項
・Lambda 関数に VPC 設定を追加した場合、関数でアクセスできるのは、その VPC 内のリソースのみ
・追加の ENI の使用を開始するペナルティが発生
・ロールには、ENI を作成、記述、削除するためのアクセス許可が必要
・各 ENI には、指定されたサブネットの IP アドレス範囲からプライベート IP アドレスが割り当てられます


■■■■■■■■■■■■■■■■■■■■■■■■■■ バージョニングとエイリアス
細かいことは、いじって確認しろ。
▼ エイリアス
-------------------------------------------------
▼ Lambda エイリアスとは
 Lambda 関数の特定のバージョンに対するポインタのようなもの
 必要に応じた Lambda 関数の新しいバージョンへの移行またはロールバックが容易

▼ 例) PROD エイリアス
1.バージョン 1 を指す PROD という名前のエイリアスを作成
 →PROD エイリアスを使用して Lambda 関数のバージョン 1 を呼び出すこと可能

2.PROD エイリアスをバージョン 2 を指すように再マッピング
 →バージョン 2 を本稼働環境に移行できます。


問題が出た場合は、PROD エイリアスがバージョン 1 を指すように再マッピングすることで、簡単に本稼働バージョンをロールバック
-------------------------------------------------
AutoBackupVer1:
  Type: AWS::Lambda::Version
  Properties:
    FunctionName: !Ref "MyFunction"
    Description: "Something fixed"
AliasForMyApp:
  Type: AWS::Lambda::Alias
  Properties:
    FunctionName: ！Ref "MyFunction"
    FunctionVersion: !GetAtt AutoBackupVer1.Version
    Name: "NameOfThisAlias"
-------------------------------------------------

▼ バージョンの表示のされ方
修飾 ARN
バージョンのサフィックスが付いた関数 ARN
arn:aws:lambda:aws-region:acct-id:function:helloworld:$LATEST

非修飾 ARN
バージョンのサフィックスが付いていない関数 ARN
arn:aws:lambda:aws-region:acct-id:function:helloworld

バージョン発行しない限り、$LATEST バージョン が唯一のLambda関数バージョン
-------------------------------------------------
