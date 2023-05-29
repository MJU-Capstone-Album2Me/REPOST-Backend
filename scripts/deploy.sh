#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/repost"

JAR_PATH="$PROJECT_ROOT/build/libs/*.jar"
BUILD_JAR=$(ls "$JAR_PATH")
JAR_NAME=$(basename "$BUILD_JAR")

DEPLOY_PATH="$PROJECT_ROOT/"
APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

echo "===== 배포 시작 : $TIME_NOW =====" >> $DEPLOY_LOG
echo "> build 파일 복사: $JAR_NAME 파일 복사" >> $DEPLOY_LOG
cp "$BUILD_JAR" $DEPLOY_PATH

CURRENT_PID=$(pgrep -f "$JAR_NAME")
if [ -z "$CURRENT_PID" ];
then
  echo "현재 실행중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
else
  echo "실행중인 $CURRENT_PID 애플리케이션 종료 " >> $DEPLOY_LOG
  kill -15 "$CURRENT_PID"
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> jar 파일 실행: $DEPLOY_JAR 파일 실행" >> $DEPLOY_LOG
nohup java -jar -Dspring.profiles.active=prod "$DEPLOY_JAR" > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f "$JAR_NAME")
echo " 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
echo "===== 배포 종료 : $TIME_NOW =====" >> $DEPLOY_LOG
