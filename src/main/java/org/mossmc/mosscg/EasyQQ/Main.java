package org.mossmc.mosscg.EasyQQ;

import org.mossmc.mosscg.EasyQQ.Bot.BotLogin;
import org.mossmc.mosscg.EasyQQ.Command.CommandExit;
import org.mossmc.mosscg.EasyQQ.Command.CommandReload;
import org.mossmc.mosscg.EasyQQ.Web.WebMain;
import org.mossmc.mosscg.MossLib.Command.CommandManager;
import org.mossmc.mosscg.MossLib.Config.ConfigManager;
import org.mossmc.mosscg.MossLib.File.FileCheck;
import org.mossmc.mosscg.MossLib.File.FileDependency;
import org.mossmc.mosscg.MossLib.Object.ObjectLogger;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        FileCheck.checkDirExist("./EasyQQ");
        ObjectLogger logger = new ObjectLogger("./EasyQQ/logs");
        BasicInfo.logger = logger;

        //初始化依赖
        FileDependency.loadDependencyDir("./EasyQQ/dependency","dependency");

        //基础信息输出
        logger.sendInfo("欢迎使用EasyQQ软件");
        logger.sendInfo("软件版本：" + BasicInfo.version);
        logger.sendInfo("软件作者：" + BasicInfo.author);

        //配置读取
        logger.sendInfo("正在读取配置文件......");
        BasicInfo.config = ConfigManager.getConfigObject("./EasyQQ", "config.yml", "config.yml");
        if (!BasicInfo.config.getBoolean("enable")) {
            logger.sendInfo("你还没有完成配置文件的设置哦~");
            logger.sendInfo("快去配置一下吧~");
            logger.sendInfo("配置文件位置：./EasyQQ/config.yml");
            System.exit(0);
        }
        BasicInfo.debug = BasicInfo.config.getBoolean("debug");

        //Bot初始化
        logger.sendInfo("正在初始化Bot模块......");
        BotLogin.login();

        //Http服务初始化
        logger.sendInfo("正在初始化Web模块......");
        WebMain.initWeb();

        //命令行初始化
        CommandManager.initCommand(BasicInfo.logger,true);
        CommandManager.registerCommand(new CommandExit());
        CommandManager.registerCommand(new CommandReload());

        long completeTime = System.currentTimeMillis();
        logger.sendInfo("启动完成！耗时："+(completeTime-startTime)+"毫秒！");
    }

    public static void reloadConfig() {
        BasicInfo.logger.sendInfo("正在重载配置文件......");
        BasicInfo.config = ConfigManager.getConfigObject("./EasyQQ", "config.yml", "config.yml");
        BasicInfo.debug = BasicInfo.config.getBoolean("debug");
        BasicInfo.logger.sendInfo("配置文件重载完成！");
    }
}
