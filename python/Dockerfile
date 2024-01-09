# 使用基础镜像 paddlecloud/paddleocr:2.6-cpu-latest
FROM paddlecloud/paddleocr:2.6-cpu-latest

# 设置工作目录
WORKDIR /data

# 安装 paddleocr 和 flask，使用国内镜像源加快速度
RUN pip install "paddleocr ==2.6.1.0" -i https://mirror.baidu.com/pypi/simple && \
    pip install flask -i https://pypi.tuna.tsinghua.edu.cn/simple

# 创建存放 web-python 文件的目录
RUN mkdir -p /data/web-python

# 将本地的 ocr.py 复制到容器的 /data/web-python 目录下
COPY ./ocr.py /data/web-python

# 指定启动命令，运行 ocr.py 脚本
CMD ["python3", "/data/web-python/ocr.py"]
