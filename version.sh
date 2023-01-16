#!/bin/bash
# 全局替换, 将pom.xml中的版本一键替换
if [ "$#" -le "1" ]; then
  echo "usage ./version.sh 1.22.0-Edgware-RELEASE 1.23.0-Edgware-SNAPSHPOT"
  exit 1
fi
echo "replace $1 to $2? (Y/N) "
read confirm
if [ "$confirm" == 'Y' ]; then
  find ./ -type f -name 'pom.xml'|xargs sed -i 's/'$1'/'$2'/g'
  echo "=== DONE SUCCESS ==="
fi