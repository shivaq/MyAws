■■■■■■■■■■■■ ECSとは


■■■■■■■■■■■■ ECS と ELB
・ECS は、ALB リスナールールと ALB リスナーに明示的に依存する必要がある
これにより、リスナーの準備が整う前にサービスが開始されなくなります

Amazon CloudWatch アラームに応じて Amazon ECS サービスをスケールするには、アプリケーションの Auto Scaling AWS::ApplicationAutoScaling::ScalableTarget および AWS::ApplicationAutoScaling::ScalingPolicy リソースを使用します。

複数のターゲット間で受信するアプリケーショントラフィックを分配するために Application Load Balancer を使用する場合には、AWS::ElasticLoadBalancingV2::TargetGroup、AWS::ElasticLoadBalancingV2::Listener、AWS::ElasticLoadBalancingV2::ListenerRule および AWS::ElasticLoadBalancingV2::LoadBalancer リソースを使用します
