#设置镜像使用的基础镜像
FROM openjdk:8u322-jre-buster
# 作者
MAINTAINER niefy <niefy@qq.com>
#设置镜像暴露的端口 这里要与application.properties中的server.port保持一致
EXPOSE 80
#设置容器的挂载卷
VOLUME /tmp
#编译镜像时将springboot生成的jar文件复制到镜像中
ADD target/wx-api.jar  /wx-api.jar
#编译镜像时运行脚本
RUN bash -c 'touch /wx-api.jar'
#容器的入口程序，这里注意如果要指定外部配置文件需要使用-spring.config.location指定配置文件存放目录
ENTRYPOINT ["java","-jar","/wx-api.jar"]
