package com.xzy.mvc.controller;

import com.xzy.mvc.dto.QueryConditionsDTO;
import com.xzy.mvc.util.Message;
import com.xzy.mvc.util.MessageBox;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xzy
 * @date 2020-12-25 10:14
 * 说明：系统测试接口
 */
@RestController
@RequestMapping("test/")
public class TestController {

    /**
     * get请求处理测试（不含参数）
     *
     * @return -
     */
    @GetMapping("get_test1")
    public Message getTest1() {
        return Message.ok();
    }

    /**
     * get请求处理测试（含参数）
     *
     * @param param - 请求参数
     * @return -
     */
    @GetMapping("get_test2")
    public Message getTest2(@RequestParam Object param) {
        return MessageBox.ok(param);
    }

    /**
     * post请求处理测试
     *
     * @param param - 参数
     * @return -
     */
    @PostMapping("post_test1")
    public Message postTest(@RequestBody Map<String, Object> param) {
        return MessageBox.ok(param);
    }

    /**
     * post请求处理测试
     *
     * @param qc - query conditions
     * @return - query conditions
     */
    @PostMapping("post_test2")
    public Message postTest(@RequestBody QueryConditionsDTO qc) {
        return MessageBox.ok(qc);
    }
}
