import json
from paddleocr import PaddleOCR
from flask import Flask, request, jsonify

# 只需要初始化加载⼀次
ocr = PaddleOCR(use_angle_cls=True)
# 所有的Flask都必须创建程序实例，程序实例是Flask的对象，⼀般情况下⽤如下⽅法实例化
app = Flask(__name__)


@app.route('/ocr', methods=["POST"])
def paddle_ocr():
    try:
        # 获取传入参数
        data = json.loads(request.data)
        # 获取传入的图片路径
        img_url = data["imgUrl"]
        # # 进行ocr识别
        # # 如果不需要检测坐标，那么可以设置
        # # result = ocr.ocr(img_path, cls=True, det=False)
        result = ocr.ocr(img_url, cls=True)
        # 识别结果通过json格式返回
        return jsonify(data=result, code=200, message="调用接口成功"), 200
    except ValueError as e:
        # 处理值错误（比如json解析错误）
        return jsonify(data=None, code=400, message="请求中的数据格式不正确"), 400
    except FileNotFoundError as e:
        # 处理文件未找到错误
        return jsonify(data=None, code=404, message="请求的资源未找到"), 404
    except Exception as e:
        # 捕获其他所有异常
        return jsonify(data=None, code=500, message="服务器内部错误"), 500


# 程序实例⽤run⽅法启动flask集成的web服务器
# 请求的地址为 http://127.0.0.1:8888/ocr
if __name__ == '__main__':
    # 可以返回中⽂字符,否则汉字会以Unicode编码形式返回
    app.config['JSON_AS_ASCII'] = False
    # 接收所有IP的请求,debug=True 代码修改，web容器会重启,端口号为8888
    app.run(host='0.0.0.0', debug=True, port=8888)