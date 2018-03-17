package cn.dombro.meetmeeting.common;

import cn.dombro.meetmeeting.model.*;
import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.template.Engine;

import java.util.regex.Pattern;

public class MyConfig extends JFinalConfig {

    public void configConstant(Constants constants) {

        PropKit.use("config.properties");
        constants.setDevMode(true);
    }

    public void configRoute(Routes routes) {

    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {
        //druid 数据源插件
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"),PropKit.get("user"),PropKit.get("password"));
        dp.setDriverClass(PropKit.get("driverClass"));
        dp.set(PropKit.getInt("initialSize"),PropKit.getInt("minIdle"),PropKit.getInt("maxActive"));
        dp.setMaxWait(PropKit.getInt("maxWait"));
        plugins.add(dp);
        //arp 插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        plugins.add(arp);
        arp.setBaseSqlTemplatePath(PathKit.getWebRootPath()+"/WEB-INF");
        arp.addSqlTemplate("sql/all.sql");
        arp.addMapping("user","u_id", User.class);
        arp.addMapping("contact", Contact.class);
        arp.addMapping("meeting","m_id" ,Meeting.class);
        arp.addMapping("request","r_id", Request.class);
        arp.addMapping("message","msg_id", Message.class);
        arp.addMapping("group","g_id",Group.class);
        arp.addMapping("vote","v_id",Vote.class);
        arp.addMapping("voting_result",VotingResult.class);
        arp.addMapping("signin","g_id",Signin.class);

    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {
        handlers.add(new DruidStatViewHandler("/druid"));
    }

    @Override
    public void afterJFinalStart() {

    }
}
