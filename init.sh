#! /bin/bash
pw=$(uuidgen | cut -d '-' -f 1)
http POST :50355/resources/users/register uid=Admin password=$pw
http POST :50355/resources/roles roleName=wheel description=坐在轮椅上的狗管理
http POST :50355/resources/users/addRole uid==Admin roleName==wheel

http POST :50355/resources/users/register uid=XiaoMing password=1234
http POST :50355/resources/roles roleName=csuster description=长沙理工大学的学子
http POST :50355/resources/users/addRole uid==Admin roleName==csuster
http POST :50355/resources/users/addRole uid==XiaoMing roleName==csuster

echo "created admin with password $pw"
