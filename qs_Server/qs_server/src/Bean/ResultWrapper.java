package Bean;

/**
 * 服务器json输出样板
 * <p>
 * code : 服务器响应结果 ,如: 操作成功:200,操作失败:400
 * data : 服务器响应数据
 * <p>
 * 例如:
 * {
 * "code": 200,
 * "data": {
 * "n_imgs": "imgs/1.jpg",
 * "n_date": "1",
 * "n_author": "yangleduo",
 * "fu_jian": "file/1.pdf",
 * "looked_times": 12,
 * "n_content": "这个是新闻内容",
 * "n_title": "新闻1",
 * "n_type": "xw",
 * "n_id": 1
 * }
 * }
 *
 * @param <T> 响应数据数据集
 */
public class ResultWrapper<T> {
    public int code = 400;
    public T data;

    public ResultWrapper(int code, T data) {
        this.code = code;
        this.data = data;
    }
}