# Android-SerialPort
此项目移植于谷歌官方串口库[android-serialport-api](https://code.google.com/archive/p/android-serialport-api/),但该项目仅支持串口名称及波特率，所以在项目的基础上添加支持数据位、数据位、停止位、流控等配置。

[![](https://jitpack.io/v/F1ReKing/Android-SerialPort.svg)](https://jitpack.io/#F1ReKing/Android-SerialPort)

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
	        implementation 'com.github.F1ReKing:Android-SerialPort:1.0'
	}


## 使用

### 1. 查询串口列表

```java
SerialPortFinder.getDrivers();
```

### 2. 打开串口

```java
SerialPortHelper.openSerialPort(File device, int baudRate, int stopBits, int dataBits, int parity, int flowCon, int flags);
```

### 3. 关闭串口

```java
SerialPortHelper.closeSerialPort();
```

### 4. 发送数据

```java
SerialPortHelper.sendBytes(byte[] bytes); // 支持发送byte[]
SerialPortHelper.sendHex(String hex); // 支持发送Hex
SerialPortHelper.sendTxt(String txt); // 支持发送ASCII码
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



## 版本更新记录

### 1.0

- 基础功能、支持设置串口号、波特率、数据位、校验位、停止位、流控等配置
- 支持发送、接收数据
