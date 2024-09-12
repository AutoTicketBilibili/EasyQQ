# EasyQQ
一个简单的QQ机器人API

> 需要java环境/参考java8 <br>
> 直接构建出来run.bat运行即可
> 基于mamoe/mirai


怎么使用配置文件写的很清楚了

代码参考如下/Python

```
data = {
      "token": token,
      "type": "broadcast",
      "message": "内容"
}
url = "http://127.0.0.1:9999/API"
sessionTemp = requests.Session()
sessionTemp.post(url, headers=BasicInfo.headers, json=data, timeout=10)
