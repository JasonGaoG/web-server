#!/bin/bash

source /etc/profile

umask 022

# 获取当前脚本所在路径
CURRENTPATH=$(cd "$(dirname "$0")"; pwd)
cd ${CURRENTPATH}

if [ ! -d ${CURRENTPATH}/dll ];then
    mkdir -p ${CURRENTPATH}/dll 
fi

# 项目根目录下包含多个.jar文件不可用
APP_NAME=$(ls -l |grep .jar$ | awk '{print$9}')
FILEPATH=$CURRENTPATH"/"$APP_NAME

# 设置动态链接库路径
export LD_LIBRARY_PATH=${CURRENTPATH}/dll/:$LD_LIBRARY_PATH

# jvm最小内存、jvm最大内存、每个线程分配内存大小
JAVA_OPTS="-Xms768m -Xmx768m -Xss1m"

# jvm发生OOM时，自动生成DUMP文件
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${CURRENTPATH}/logs/HeapDumpOnOutOfMemoryError.log"

# 使用说明，用来提示输入参数
usage() {
    echo "Usage: sh boot.sh [start|stop|restart|status]"
    exit 1
}

# 检查程序是否在运行
is_exist(){
    # 获取PID
    PID=$(ps -ef |grep ${FILEPATH} | grep -v $0 |grep -v grep |awk '{print $2}')
    # -z "${pid}"判断pid是否存在，如果不存在返回1，存在返回0
    if [ -z "${PID}" ]; then
        # 如果进程不存在返回1
        return 1
    else
        # 进程存在返回0
        return 0
    fi
}

# 定义启动程序函数
start(){
    is_exist
    if [ $? -eq "0" ]; then
        echo "${APP_NAME} is already running, PID=${PID}"
    else
        nohup java $JAVA_OPTS -jar ${FILEPATH} >/dev/null 2>&1 &
    PID=$(echo $!)
        echo "${APP_NAME} start success, PID=$!"
    fi
}

# 停止进程函数
stop(){
    is_exist
    if [ $? -eq "0" ]; then
        kill -9 ${PID}
        echo "${APP_NAME} process stop, PID=${PID}"
    else
        echo "There is not the process of ${APP_NAME}"
    fi
}

# 重启进程函数
restart(){
    stop
    start
}

# 查看进程状态
status(){
    is_exist
    if [ $? -eq "0" ]; then
        echo "${APP_NAME} is running, PID=${PID}"
    else
        echo "There is not the process of ${APP_NAME}"
    fi
}

case $1 in
"start")
    start
    ;;
"stop")
    stop
    ;;
"restart")
    restart
    ;;
"status")
   status
    ;;
    *)
    usage
    ;;
esac
exit 0