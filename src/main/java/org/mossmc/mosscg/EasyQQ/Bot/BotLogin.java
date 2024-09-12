package org.mossmc.mosscg.EasyQQ.Bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.mossmc.mosscg.EasyQQ.BasicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BotLogin {
    public static Bot bot = null;
    public static List<Long> groupList;
    public static void login() {
        try {
            //Bot配置
            BasicInfo.logger.sendInfo("正在初始化机器人配置文件......");
            //检查机器人是否正在运行避免重复登录
            if (bot != null && bot.isOnline()) bot.close();

            //读取配置文件
            File dataFile = new File(BasicInfo.config.getString("botFile"));
            String qq = BasicInfo.config.getString("botNumber");
            String password = BasicInfo.config.getString("botPassword");
            String groups = BasicInfo.config.getString("botGroups");
            //群组读取
            groupList = new ArrayList<>();
            String[] cut = groups.split(",");
            for (String s : cut) {
                groupList.add(Long.valueOf(s));
                BasicInfo.logger.sendInfo("启用消息群聊：" + s);
            }

            //检查Bot文件夹
            BasicInfo.logger.sendInfo("正在检查机器人数据文件夹......");
            if (!dataFile.exists()) {
                BasicInfo.logger.sendInfo("机器人运行文件目录不存在！正在创建目录");
                if (dataFile.mkdir()) {
                    BasicInfo.logger.sendInfo("成功创建机器人运行文件目录");
                } else {
                    BasicInfo.logger.sendWarn("机器人运行文件目录创建失败！已取消机器人启动！");
                    return;
                }
            }

            //进入构建配置流程
            BotConfiguration botConfiguration = new BotConfiguration() {{
                if (BasicInfo.config.getString("botLog").equals("false")) {
                    noBotLog();
                    BasicInfo.logger.sendInfo("已关闭机器人信息日志显示");
                }if (BasicInfo.config.getString("botNetworkLog").equals("false")) {
                    noNetworkLog();
                    BasicInfo.logger.sendInfo("已关闭机器人网络日志显示");
                }
                switch (BasicInfo.config.getString("botHeartbeat")) {
                    case "1":
                        setHeartbeatStrategy(HeartbeatStrategy.STAT_HB);
                        BasicInfo.logger.sendInfo("机器人心跳策略：STAT_HB");
                        break;
                    case "2":
                        setHeartbeatStrategy(HeartbeatStrategy.REGISTER);
                        BasicInfo.logger.sendInfo("机器人心跳策略：REGISTER");
                        break;
                    case "3":
                        setHeartbeatStrategy(HeartbeatStrategy.NONE);
                        BasicInfo.logger.sendInfo("心跳策略参数设置错误！使用默认机器人心跳策略：NONE");
                        break;
                    default:
                        setHeartbeatStrategy(HeartbeatStrategy.NONE);
                        BasicInfo.logger.sendInfo("机器人心跳策略：NONE");
                        break;
                }
                switch (BasicInfo.config.getString("botProtocol")) {
                    case "1":
                        setProtocol(MiraiProtocol.ANDROID_PHONE);
                        BasicInfo.logger.sendInfo("机器人登陆协议：ANDROID_PHONE");
                        break;
                    case "2":
                        setProtocol(MiraiProtocol.ANDROID_PAD);
                        BasicInfo.logger.sendInfo("机器人登陆协议：ANDROID_PAD");
                        break;
                    case "3":
                        setProtocol(MiraiProtocol.ANDROID_WATCH);
                        BasicInfo.logger.sendInfo("机器人登陆协议：ANDROID_WATCH");
                        break;
                    case "4":
                        setProtocol(MiraiProtocol.IPAD);
                        BasicInfo.logger.sendInfo("机器人登陆协议：IPAD");
                        break;
                    case "5":
                        setProtocol(MiraiProtocol.MACOS);
                        BasicInfo.logger.sendInfo("机器人登陆协议：MACOS");
                        break;
                    default:
                        setProtocol(MiraiProtocol.ANDROID_PHONE);
                        BasicInfo.logger.sendInfo("协议参数设置错误！使用默认机器人登陆协议：ANDROID_PHONE");
                        break;
                }
                BasicInfo.logger.sendInfo("机器人工作目录："+dataFile);
                setWorkingDir(dataFile);
                fileBasedDeviceInfo();
            }};

            //进入登录流程
            BasicInfo.logger.sendInfo("机器人配置完成，开始登录......");
            BasicInfo.logger.sendInfo("机器人账号："+qq);
            if (BasicInfo.config.getString("botLoginType").equals("1")) {
                BasicInfo.logger.sendInfo("正在使用账号密码登录！");
                bot = BotFactory.INSTANCE.newBot(Long.parseLong(qq), password, botConfiguration);
            } else {
                BasicInfo.logger.sendInfo("正在使用扫码登录！");
                bot = BotFactory.INSTANCE.newBot(Long.parseLong(qq), BotAuthorization.byQRCode(), botConfiguration);
            }
            BasicInfo.logger.sendInfo("正在登录中......");
            bot.login();
            BasicInfo.logger.sendInfo("机器人登录完成！");
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
            BasicInfo.logger.sendWarn("QQ机器人启动失败！请检查报错！");
        }
    }
}
