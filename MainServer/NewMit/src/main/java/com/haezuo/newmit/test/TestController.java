package com.haezuo.newmit.test;

import com.haezuo.newmit.common.Util.Tokenize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "/mainTest";
    }

    @RequestMapping(value = "/okt", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> okt(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> result =  new HashMap<>();

        String text = (String) requestData.get("labelStr");


        List<String> tokens = new Tokenize().GetTokens(text);
        result.put("tokens", tokens);

        return result;
    }
}
