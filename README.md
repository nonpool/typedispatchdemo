# netty4 + protobuf3 最佳实践
1. netty4+protobuf3多类型传输实现
2. 优雅的实现消息分发

## 使用步骤:
1. 先运行`protoGen.bat`生成protobuf实体类，如果是*nix平台直接把bat改成sh即可(项目中已经生成)
2. 运行`HandlerGenerator`生成对应类型`handler`类
3. 在生成的`handler`中填写业务逻辑即可

