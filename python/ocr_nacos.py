import os
import logging
from flask import Flask, jsonify, request
import requests
import threading
import time
import json
from paddleocr import PaddleOCR

# 初始化Flask应用
server = Flask(__name__)
server.config['JSON_AS_ASCII'] = False

# 从环境变量读取配置
IP_ADDRESS = os.getenv('IP_ADDRESS', '127.0.0.1')
PORT = os.getenv('PORT', '8085')
NACOS_IP = os.getenv('NACOS_IP', '127.0.0.1')
NACOS_PORT = os.getenv('NACOS_PORT', '8848')
SERVICE_NAME = os.getenv('SERVICE_NAME', 'ocr-service')

# 初始化PaddleOCR服务
ocr = PaddleOCR(use_angle_cls=True)

# 设置日志
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

@server.route('/ocr', methods=["POST"])
def paddle_ocr():
    try:
        data = json.loads(request.data)
        img_url = data["imgUrl"]
        result = ocr.ocr(img_url, cls=True)
        logger.info(f"OCR result for {img_url}: {result}")
        return jsonify(data=result, code=200, message="调用接口成功"), 200
    except ValueError:
        logger.error("ValueError: 请求中的数据格式不正确")
        return jsonify(data=None, code=400, message="请求中的数据格式不正确"), 400
    except FileNotFoundError:
        logger.error("FileNotFoundError: 请求的资源未找到")
        return jsonify(data=None, code=404, message="请求的资源未找到"), 404
    except Exception as e:
        logger.error(f"Exception: {str(e)}")
        return jsonify(data=None, code=500, message="服务器内部错误"), 500

def service_register():
    url = f"http://{NACOS_IP}:{NACOS_PORT}/nacos/v1/ns/instance?serviceName={SERVICE_NAME}&ip={IP_ADDRESS}&port={PORT}"
    res = requests.post(url)
    logger.info(f"向nacos注册中心发起服务注册请求，注册响应状态：{res.status_code}")

def service_beat():
    while True:
        url = f"http://{NACOS_IP}:{NACOS_PORT}/nacos/v1/ns/instance/beat?serviceName={SERVICE_NAME}&ip={IP_ADDRESS}&port={PORT}"
        res = requests.put(url)
        logger.info(f"执行心跳服务，续期服务响应状态：{res.status_code}")
        if res.status_code != 200:
            logger.info(f"注册服务失败，服务响应状态：{res.status_code}")
        time.sleep(5)

if __name__ == "__main__":
    service_register()
    threading.Timer(5, service_beat).start()
    server.run(host='0.0.0.0', port=int(PORT))