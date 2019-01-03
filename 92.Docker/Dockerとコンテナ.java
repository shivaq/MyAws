▼ Dockerとは
・Linux コンテナをもとにしたオープンソースプロジェクト
・Linuxカーネルの、名前空間、コントロールグループを使って、OS上にコンテナを作成
・コンテナ技術は古くからある。なんで Docker が注目されてる？
・ステートレスな環境

▼ コンテナのアーキテクチャ               ここから上がコンテナ
サーバ ＞ ホストOS ＞ Docker エンジン ＞ Bins/Libs > App
■■■■■■■■■■■■ Docker が支持されている理由
▼ 使いやすい
・誰でもラップトップ上にポータブルなアプリをパッケージできる
・パッケージを、パブリック/プライベートクラウド、ベアメタル上で run できる
・マントラは:build once, run anywhere

▼ スピード
・軽い。早い。
・カーネル上で動く箱庭環境に過ぎない。
・使うリソースも fewer
・VM は仮想OSのブートに時間がかかるのに比べて早い
・数秒で Docker コンテナを作成実行できる

▼ Docker Hub の存在
・それはエコシステム
・Docker イメージの App Store みたいなもの
・たくさんあるからほとんど修正する必要もないくらいのイメージが簡単に見つかる

▼ Modularity と スケーラビリティー
・Modularity は、システムのコンポーネントの分離、再結合のしやすさの度合い
・アプリの機能を個々のコンテナに分割しやすくしている
・例）ポスグレを一つのコンテナ、Redisサーバを他のコンテナ、Node.js アプリを他のコンテナ
・Docker は個々のコンテナをリンクしやすくしている。
・個々のコンテナのアップデートやスケーリングも簡単

■■■■■■■■■■■■ Docker のコンポーネント
▼ Docker エンジン
Docker が走るレイヤー
コンテナ、イメージを管理し、ビルドを行う
▼ Docker エンジンの構成物
・Docker Daemon
ホストコンピュータ上で走る

・Docker クライアント

・REST API
Docker デーモンと リモートでやり取りする

▼ Docker Client
エンドユーザーが使う、Docker の UI 的なものもの。
Docker デーモンと communicate して、コマンドを実行させる

▼ Docker Daemon
コマンドを実行する。ホストマシーンの上で走る。
Docker Client は 異なるマシンから、Docker Daemon と Communicate することができる。

■■■■■■■■■■■■ Dockerファイル
・Docker Image をビルドするための指示を設定するファイル
・Dockerファイルの書く指示が、DockerImageに新たなレイヤーを追加する。

docker build docker_file で、イメージをビルドできる
▼ 命令
RUN
ソフトウェアパッケージの インストールなどに使用

EXPOSE 8080
ポートを指定

ENV SOME_ENV
環境変数を設定

■■■■■■■■■■■■ Docker Image
・リードオンリーのテンプレート
・ほしいパッケージ化されたアプリを定義
・上記の依存関係を定義
・起動時に走らせたいプロセスを定義
・リードオンリー

■■■■■■■■■■■■ Union File System ※ レイヤー化されたファイルシステム
・ImageをビルドするのにDockerが使用するもの
・スタックできるファイルシステムのようなもの
・別々のファイルシステム(A.K.A ブランチ)のファイルやディレクトリが、
透過的にオーバーレイされ、単一のファイルシステムを形作る感じ
・オーバーレイしたブランチの、ディレクトリの中身のファイルのうち、同じパスのものは
単一のマージされたディレクトリになる。
・上記結果、レイヤーごとに別個のコピーが生成されることが回避される
・そのかわり、各レイヤーは同じリソースへのポインタが与えられる
・任意のレイヤーを修正する必要がある場合、オリジナルは未修正のままコピーを作成し、それを修正する
・上記のように、ファイルシステムに対し実際は書き込むことはできないが、書き込みができるように見える

▼ レイヤー化することの便益
1.冗長にならない
Imageを使って新規コンテナを作成するたびに、ファイルシステムが改めて生成されることがない
結果、Dockerコンテナのインスタンス化が早い

2.レイヤーの分離
修正が早くなる。Imageを変更するとき、Dockerは変更がなされたレイヤーのみを更新する

■■■■■■■■■■■■ Volume
・Volume はコンテナのデータを担う部分。
・コンテナ生成時にインスタンス化される。
・コンテナのデータの共有と一貫性を実現する
・Data Volume はデフォルトの Union File System から隔離されており、
・ホストのファイルシステムの、普通のファイルやディレクトリとして存在している
・コンテナを削除、更新、リビルドしても、DataVolumeは修正されることなくそのまま。
・Volumeを更新したいときは直接変更を行う。
・コンテナ間でDataVolumeを共有したり、再利用したりできる。

■■■■■■■■■■■■ Dockerコンテナ
・アプリのソフトウェアを、アプリをRUNするために必要なすべてを含めて見えない箱にラップするようなもの
・含まれるもの：OS、アプリのコード、ランタイム、Systemのツール、ライブラリなど
・Docker Image からビルドされる
・DockerImage のリードオンリー ファイルシステムの上に、
  読み書き可能ファイルシステムを追加してコンテナが作成される。
・カーネルなどのブートFS > ベースImage(Debian) > emacs, Apache などの Image > 書込み可能なコンテナ
▼ Docker が コンテナ作成後に行うこと
・NWインターフェイスを作成 ⇒IPアドレスをコンテナに追加
 ⇒コンテナがローカルホストとトークできるようになる。
・Imageを定義するときに指定した、アプリをRUNするためのプロセスを実行する

■■■■■■■■■■■■ コンテナが作成されたらどうなる
どの環境でも、変更を加えることなく RUN できるようになる

■■■■■■■■■■■■ 1.Namespaces
・配下のLinux System に対する、コンテナから見えるもの、アクセスできるものを制限する。
・コンテナをRUNすると、Dockerが、そのコンテナ用の名前空間を生成する。

▼ Namespace の種類
a. NET:
Systemのネットワークスタックを提供
ネットワークデバイス、IPアドレス、ルートテーブル、ポート番号、/proc/net ディレクトリ

b. PID:
プロセスID。
コンテナ自身が見ることができ、やりとりができるスコープのプロセス。
init (PID 1) も含まれる

c. MNT:
コンテナ自身の、Systemのマウントの view を提供。
結果、異なるマウント Namespace のプロセスは、異なるファイルシステム 階層の View を持つ。

d. UTS:
UNIX Timesharing System.
プロセスに、SystemのIDを識別することを可能にする。
UTS によって、コンテナが、自身のhostname、NISドメインネームが、
他のコンテナやホストのSystemと独立したものになることを実現している。

e. IPC:InterProcess Communication
IPC Namespace によって、
各コンテナの中でRUNしているプロセス間の IPC リソースを隔離している。

f. USER:
コンテナ間のユーザーを隔離するために使用するNamespace。
ホストのSystemと比べ、それぞれ異なるスコープのuid, gid レンジを提供する。

結果、プロセスの uid, gid はユーザーのNamespaceの内外で異なったものとなる。
さらに、プロセスが、コンテナ内のroot特権を犠牲にすることなく、
非特権ユーザーをコンテナの外に持つことが可能となる

■■■■■■■■■■■■ 2.コントロールグループ cgroups
Linux カーネルの機能。
プロセスのリソースを隔離し、優先順位付けし、使いみちを決める
(CPU, memory, disk I/O, network, etc.)

こうして、cgroup はコンテナが必要なだけのリソースを使うことを、
また必要に応じて、コンテナが使うことができるリソースに成約を設けることを保証する。
単一のコンテナがリソースを使い切って、System全体をダウンさせることがないことを保証する。

■■■■■■■■■■■■ 3.隔離された Union file systems
