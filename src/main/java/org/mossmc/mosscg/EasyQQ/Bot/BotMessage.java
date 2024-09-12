package org.mossmc.mosscg.EasyQQ.Bot;

import net.mamoe.mirai.contact.Group;
import org.mossmc.mosscg.EasyQQ.BasicInfo;

public class BotMessage {
    public static void groupBroadcast(String message) {
        try {
            BasicInfo.logger.sendInfo("发送群组广播消息："+message);
            for (Long groupID : BotLogin.groupList) sendGroupMessage(message,groupID);
            BasicInfo.logger.sendInfo("广播消息发送完成！");
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
            BasicInfo.logger.sendWarn("发送群组广播消息失败："+message);
        }

    }

    public static void sendGroupMessage(String message,Long groupID) throws Exception{
        Group group = BotLogin.bot.getGroup(groupID);
        assert group != null;
        group.sendMessage(message);
        BasicInfo.logger.sendInfo("已向"+groupID+"发送群组消息："+message);
    }
}
