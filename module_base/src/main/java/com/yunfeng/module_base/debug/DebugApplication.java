package com.yunfeng.module_base.debug;

import com.yunfeng.lib.BaseApplication;
import com.yunfeng.module_base.config.ModuleConfig;

/**
 * 组件单独运行时初始化module
 */
public class DebugApplication extends BaseApplication {

    @Override
    public void dependModulesInit() {
        ModuleConfig.INSTANCE.init(this, null);
    }
}
