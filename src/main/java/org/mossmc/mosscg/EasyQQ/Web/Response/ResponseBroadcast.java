package org.mossmc.mosscg.EasyQQ.Web.Response;

import com.alibaba.fastjson.JSONObject;
import org.mossmc.mosscg.EasyQQ.Bot.BotMessage;

public class ResponseBroadcast {
    /**
     * 基础请求格式（POST）：
     * {
     *     token: "xxx",
     *     type: "broadcast",
     *     message: "xxx"
     * }
     * 基础返回格式：
     * {
     *     "status": true,
     *     ...
     * }
     */
    public static void getResponse(JSONObject data, JSONObject responseData) {
        if (!data.containsKey("message")) return;
        BotMessage.groupBroadcast(data.getString("message"));
        responseData.replace("status",true);
    }
}
