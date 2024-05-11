# 使用基础镜像
FROM paddlecloud/paddleocr:2.6-cpu-latest

# 设置工作目录
WORKDIR /app

# 安装必要的包，使用国内镜像源加速安装
RUN pip install --no-cache-dir -i https://pypi.tuna.tsinghua.edu.cn/simple \
    paddleocr==2.6.1.0 \
    Flask \
    requests

# 将当前目录下的所有文件复制到容器的/app目录下
COPY . /app

# 定义环境变量 替换成你们自己的
ENV IP_ADDRESS=117.72.12.136
ENV PORT=8085
ENV NACOS_IP=127.0.0.1
ENV NACOS_PORT=8848
ENV SERVICE_NAME=ocr-service

# 指定启动命令
CMD ["python3", "./ocr_nacos.py"]