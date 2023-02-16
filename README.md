# Android-SerialPort
此项目移植于谷歌官方串口库[android-serialport-api](https://code.google.com/archive/p/android-serialport-api/),但该项目仅支持串口名称及波特率，所以在项目的基础上添加支持数据位、数据位、停止位、流控等配置。

[![](https://jitpack.io/v/F1ReKing/Android-SerialPort.svg)](https://jitpack.io/#F1ReKing/Android-SerialPort)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/F1ReKing/Android-SerialPort/blob/master/LICENSE)

## 下载
<a href='https://play.google.com/store/apps/details?id=me.f1reking.serialporttool&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width=200 height=77/></a>

酷安：[https://www.coolapk.com/apk/251882](https://www.coolapk.com/apk/251882)

![](https://raw.githubusercontent.com/F1ReKing/Android-SerialPort/master/3.webp)

## 引入

**Step 1.** Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


**Step 2.** Add the dependency


	dependencies {
	        implementation 'com.github.F1ReKing:Android-SerialPort:1.5.1'
	}


## 使用

### 1. 查询串口列表

```java
SerialPortHelper#getAllDevices();
// 查询串口设备地址列表
SerialPortHelper#getAllDeicesPath();
```

### 2. 配置串口参数

```java
SerialPortHelper#Builder(String port, int baudRate).build(); //支持配置串口号，波特率（默认值115200）
setStopBits(int stopBits); // 支持设置停止位 默认值为2
setDataBits(int dataBits); // 支持设置数据位 默认值为8
setParity(int parity); // 支持设置检验位 默认值为0
setFlowCon(int flowCon); // 支持设置流控 默认值为0
setFlags(int flags); // 支持设置标志 默认值为0，O_RDWR  读写方式打开
```

### 3. 打开串口

```java
SerialPortHelper#open();
```

### 4. 关闭串口

```java
SerialPortHelper#close();
```

### 4. 发送数据

```java
SerialPortHelper#sendBytes(byte[] bytes); // 支持发送byte[]
SerialPortHelper#sendHex(String hex); // 支持发送Hex
SerialPortHelper#sendTxt(String txt); // 支持发送ASCII码
```

### 5. 接收数据

```java
public interface ISerialPortDataListener {
	// 接收数据回调
    void onDataReceived(byte[] bytes);
   	// 发送数据回调
    void onDataSend(byte[] bytes);
}
```

### 6. 回调

```java
//  串口打开状态监听
void setIOpenSerialPortListener(IOpenSerialPortListener IOpenSerialPortListener);

// 串口消息监听
void setISerialPortDataListener(ISerialPortDataListener ISerialPortDataListener);
```
### 7. proguard-rules 
```
-keep class me.f1reking.serialportlib.** {*;}
```

## 版本更新记录

### 1.1

- 优化api
- 支持设置可选参数，并配置默认值

### 1.0

- 基础功能、支持设置串口号、波特率、数据位、校验位、停止位、流控等配置
- 支持发送、接收数据

## License
```
Copyright 2019 F1ReKing. 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


## Stargazers over time

[![Stargazers over time](https://starchart.cc/GeekBugs/Android-SerialPort.svg)](https://starchart.cc/GeekBugs/Android-SerialPort)

