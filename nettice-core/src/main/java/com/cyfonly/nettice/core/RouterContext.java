package com.cyfonly.nettice.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.mvc.annotation.At;
import org.nutz.resource.Scans;

import com.cyfonly.nettice.core.config.ActionWrapper;
import com.cyfonly.nettice.core.config.RouterConfig;
import com.cyfonly.nettice.core.exception.DuplicateActionException;
import com.cyfonly.nettice.core.invocation.ActionInvocation;
import com.cyfonly.nettice.core.invocation.ActionProxy;

/**
 * 路由上下文
 */
public class RouterContext {

	private Map<String, ActionWrapper> actions = new HashMap<String, ActionWrapper>();

	public RouterContext(String configFilePath, String suffix) throws Exception {
		RouterConfig config = RouterConfig.parse(configFilePath);
		initActionMap(config, actions, suffix);
	}

	public RouterContext(String configFilePath) throws Exception {
		RouterConfig config = RouterConfig.parse(configFilePath);
		initActionMap(config, actions, ".action");
	}

	private void initActionMap(RouterConfig config, Map<String, ActionWrapper> actionMap, String suffix) {
		List<String> packages = config.getActionPacages();
		for (String packagee : packages) {
			List<Class<?>> clazzs = Scans.me().scanPackage(packagee);
			for (Class<?> clazz : clazzs) {
				try {
					if (!BaseAction.class.isAssignableFrom(clazz)) {
						continue;
					}
					BaseAction baseAction = Mirror.me(BaseAction.class).born();
					for (Method method : clazz.getDeclaredMethods()) {
						if (method.getModifiers() == Modifier.PUBLIC ) {
							At at = method.getAnnotation(At.class);
							if(Lang.isEmpty(at)) {
								continue;
							}
							String[] actions = at.value();
							if(Lang.isEmptyArray(actions)) {
								continue;
							}
							for(String action : actions) {
								String actionPath = action + method.getName() + suffix;
								if (actionMap.get(actionPath) != null) {
									throw new DuplicateActionException(actionMap.get(actionPath).method, method, actionPath);
								}
								ActionWrapper actionWrapper = new ActionWrapper(baseAction, method, actionPath);
								actionMap.put(actionPath, actionWrapper);
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public ActionWrapper getActionWrapper(String path) {
		return actions.get(path);
	}

	public ActionProxy getActionProxy(ActionWrapper actionWrapper) throws Exception {
		ActionProxy proxy = new ActionProxy();
		ActionInvocation invocation = new ActionInvocation();
		invocation.init(proxy);
		proxy.setActionObject(actionWrapper.actionObject);
		proxy.setMethod(actionWrapper.method);
		proxy.setMethodName(actionWrapper.method.getName());
		proxy.setInvocation(invocation);
		return proxy;
	}
}
