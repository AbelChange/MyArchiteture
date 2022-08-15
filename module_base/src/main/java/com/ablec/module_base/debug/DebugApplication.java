package com.ablec.module_base.debug;

import com.ablec.lib.BaseApplication;
import com.ablec.module_base.config.ModuleConfig;

/**
 * 组件单独运行时初始化module
 */
public class DebugApplication extends BaseApplication {

    @Override
    public void dependModulesInit() {
        ModuleConfig.INSTANCE.init(this, null);
    }
}
