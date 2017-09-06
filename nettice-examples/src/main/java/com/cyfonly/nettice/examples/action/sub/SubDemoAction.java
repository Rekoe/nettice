package com.cyfonly.nettice.examples.action.sub;

import java.util.List;
import java.util.Map;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.alibaba.fastjson.JSONObject;
import com.cyfonly.nettice.core.BaseAction;
import com.cyfonly.nettice.core.ret.Render;
import com.cyfonly.nettice.core.ret.RenderType;

/**
 * Map/Array/List 类型参数测试 demo
 * 
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class SubDemoAction extends BaseAction {

	// 测试 Map 类型参数
	public Render mapTypeTest(@Param(value = "srcmap") Map<String, String> srcmap) {
		System.out.println("server output srcmap:");
		for (String key : srcmap.keySet()) {
			System.out.println(key + "=" + srcmap.get(key));
		}

		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "Received your Map request.[from mapTypeTest]");

		return new Render(RenderType.JSON, obj.toJSONString());
	}

	// 测试 Array/List 类型参数
	public Render arrayListTypeTest(@Param(value = "ids") Integer[] ids, @Param(value = "names") List<String> names) {
		System.out.println("server output ids:");
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
		}

		System.out.println("server output names：");
		for (String item : names) {
			System.out.println(item);
		}

		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "Received your Array/List request.[from arrayListTypeTest()]");

		return new Render(RenderType.JSON, obj.toJSONString());
	}

	// 使用 @Namespace 注解
	@At("/nettp/array/")
	public Render arrayListTypeTestWithNamespace(@Param(value = "ids") Integer[] ids, @Param(value = "names") List<String> names) {
		System.out.println("server output ids:");
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
		}

		System.out.println("server output names：");
		for (String item : names) {
			System.out.println(item);
		}

		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "Received your Array/List request.[from arrayListTypeTestWithNamespace()]");

		return new Render(RenderType.JSON, obj.toJSONString());
	}

}
