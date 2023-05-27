# 命令

用c语言写出来的，可执行的文件



# Shell中的变量

在linux中，后缀没有任何意义，所以有的文件都不加后缀

## Shebang

 shell脚本中第一行的#!叫shebang

 脚本以#!/bin/bash开头 （指定解析器）

### 第一个Shell脚本 helloworld.sh

```shell
#!/bin/bash   （指定解析器）
echo "hellow world"
```

###  脚本的常用执行方式

1. bash或sh + 脚本的相对路径或绝对路径 （不用赋予脚本+x权限）（重新开了一个进程执行bash命令）

   sh ./helloworld.sh
   bash ./helloworld.sh

2. 采用输入脚本的绝对路径或相对路径执行脚本（必须具有可执行权限+x）（本质是使用当前的bash进程执行命令）

   ./helloworld.sh   (前提是chmod + x helloworld.sh)

3. 在脚本的路径前加“.”或者source

   本质是在当前shell中执行脚本，所有变量存在当前shell中

# 变量

## 常用系统变量：

$HOME 、$PWD、$SHELL、$USER

echo $HOME

## 自定义变量

shell是弱类型语言，不用定义变量类型，直接就可以用，并且都默认为字符串类型

* 直接  变量=值  即可

  A=2

  echo $A

  echo ${A}也可以

  **!! A=2中间不能出现空格**

* 当定义的时候有空格时

  STRING='hello world'

  echo $STRING

* 升级为全局变量

  export 变量名
  
* 单引号不能识别特殊变量，但是双引号可以识别

  ```shell
  will@will-virtual-machine:~$ name='${HOME}'
  will@will-virtual-machine:~$ echo $name
  ${HOME}
  will@will-virtual-machine:~$ name2="${HOME}"
  will@will-virtual-machine:~$ echo $name2
  /home/will
  will@will-virtual-machine:~$ 
  
  ```

* 反引号可以识别命令变量

  ```shell
  will@will-virtual-machine:/opt/shell$ name=`ls`
  will@will-virtual-machine:/opt/shell$ echo $name
  hello.sh make.sh
  will@will-virtual-machine:/opt/shell$ name=ls
  will@will-virtual-machine:/opt/shell$ echo $name
  ls
  ```

## 环境变量

~/.bash_profile：每个用户自己的配置文件，登录时自动加载

~/.bashrc：每个用户自己的配置文件，打开每个终端的时候自动加载

/etc/profile：全局变量，所有用户共享

## 特殊变量

$#：查有多少个参数

$?：上一条命令成功返回0，失败返回非0

$n：$0表示命令名称 $1-9表示第1到第9个参数

$∗：命令的所有参数

$@：命令的所有参数

$* $@区别：$*将参数连带空格当做一个整体，$@将参数之间以空格划分

# bash

bash是一个命令处理器，能直接执行命令，也能从文件中读取linux命令，这个文件称为脚本

**子Shell**

```shell
[root@lys shell]# ps -f
UID        PID  PPID  C STIME TTY          TIME CMD
root      1907 10679  0 12:46 pts/1    00:00:00 ps -f
root     10679 10672  0 09:47 pts/1    00:00:00 -bash
[root@lys shell]# bash
[root@lys shell]# ps -f
UID        PID  PPID  C STIME TTY          TIME CMD
root      1970 10679  0 12:46 pts/1    00:00:00 bash
root      2006  1970  0 12:46 pts/1    00:00:00 ps -f
root     10679 10672  0 09:47 pts/1    00:00:00 -bash
[root@lys shell]# exit
exit
[root@lys shell]# ps -f
UID        PID  PPID  C STIME TTY          TIME CMD
root      2034 10679  0 12:46 pts/1    00:00:00 ps -f
root     10679 10672  0 09:47 pts/1    00:00:00 -bash

```

## bash内置命令

* echo：

  ```shell
  -n 不换行输出
  -e 解析特殊符号
      \n换行
      \r回车
      \t制表符
      \b退格
  ```

  ```shell
  will@will-virtual-machine:~$ echo 你真胖;echo 你好挺可爱
  你真胖
  你好挺可爱
  will@will-virtual-machine:~$ echo -n 你真胖;echo 你挺可爱的
  你真胖你挺可爱的
  will@will-virtual-machine:~$ echo -e "我\n是\n猪"
  我
  是
  猪
  will@will-virtual-machine:~$ echo -e '我\n是\n猪'
  我
  是
  猪
  ```

* printf

  和c中语法差不多

* eval 命令1;命令2

  用一个进程执行多个命令

* exec

  执行命令之后自动exit

* exit

  结束当前进程

* su

  切换进程到指定用户

  ```shell
  su - will：切换到will用户
  sudo su：切换到超级用户
  ```

  

# 运算符

* expr运算式

  * 

  expr + - \\* / % 分别对应加、减、乘、除、取余 

  注意：运算符左右两侧必须有空格

  运算的嵌套

  expr 'expr 2 + 3' \\* 4：先算2+3再算*4

* $[]运算式

  []里面对空格不严格要求

# 条件判断

[ condition ]：注意condition前后有空格

注意：条件非空即为true，[有值]返回true，[]：返回false

## 常用判断条件

* 两个整数之间比较

  -lt (less than)

  -le (less equal)

  -eq (equal)

  -gt (greater than)

  -ge (greater equal)

  -ne(not equal)

  例子：[]中的左括号右边和有括号左边得有空格才行

  ​	![image-20221022104631822](shell.assets/image-20221022104631822.png)

* 判断权限

  -w 文件名：判断文件是否可写

  -r 文件名：判断文件是否可读

  -x 文件名：判断文件是否可执行

* 多条判断

  &&：前一条命令成功的时候才会执行后一条命令

  ||：前一条命令失败之后才会执行下一条

  ![image-20221022105755736](shell.assets/image-20221022105755736.png)

# for循环

for((i=1; i<=100; i++))

do

​	statement

done

例子：

```shell
  3 s=0
  4 for((i=1; i<=100; i++))
  5 do
  6     s=$[$s+$i]
  7 done
  8 echo $s
```



for i in value1, value2, value3

do

done

例子：

```shell
  3 s=0
  4 for i in $*
  5 do
  6     echo "hello $i"
  7 done
```

# read读取控制台输入数据

read(选项)(参数)

选项:

-p:指定读取值时的提示符

-t:指定读取值的等待时间（单位为秒）

```shell
  2 read -t 5 -p "input a name" NAME
  3 echo $NAME
```

在5s内输入一个名字，否则直接结束，输入之后名字会被打印出来

# 函数

## 系统函数

* basename:

basename 绝对路径 [后缀]：拿到某个绝对路径的文件名，如果[后缀]这里写了后缀，则会返回不带后缀的文件名

```shell
 basename /opt/iotek/lsd/io/src/cat.c
```

输出：cat.c

```shell
 basename /opt/iotek/lsd/io/src/cat.c .c
```

输出：cat

* dirname:输出目录名

```shell
dirname /opt/iotek/lsd/io/src/cat.c
```

输出：/opt/iotek/lsd/io/src

## 自定义函数

```shell
  1 #! /bin/bash
  #文件名mybash.sh
  2 function sum()
  3 {
  4     s=0
  5     s=$[ $1+$2 ]
  6     echo $s
  7 }
  8 sum $1 $2
```

控制台输入

mybash.sh 1 2

输出

3

或者：

```shell
  1 #! /bin/bash
  2 function sum()
  3 {
  4     s=0
  5     s=$[ $1+$2 ]
  6     echo $s
  7 }
  8 read -p "input parameter1" P1
  9 read -p "input parameter2" P2
 10 
 11 sum $P1 $P2

```

控制台输入

mybash.sh

然后根据提示，输入 1 2之后

输出

3

# cut

以某种方式切割出某一列

```shell
cut -d " " -f 1 cut.txt
```

-d：以什么字符分割

-f：切割出第几列    -f 3- 意思是查询出第三列以及第三列后面的所有列

上面的命令意思是以空格为分隔符，切割出cut.txt文件中的第一列

# sed

在屏幕输出时，临时改变输出内容，原文件并不会被改变

参数：

* xa 字符串 ：在第x行下面加上某字符串
* /字符串/d：删除包含某字符串的行
* s/wp/ni/g：替换所有的wo为ni（最后的g意思是global全局替换，不加/g只替换第一个）



```shell
sed "2a mei nv" sed.txt
sed "/wo/d" sed.txt
sed "s/wo/ni/g" sed.txt
```

