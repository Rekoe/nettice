package com.cyfonly.nettice.examples.action;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.cyfonly.nettice.core.BaseAction;
import com.cyfonly.nettice.core.ret.Render;
import com.cyfonly.nettice.core.ret.RenderType;

/**
 * 基本类型参数 测试 demo
 * 
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class DemoAction extends BaseAction {

	// 测试基本参数类型
	@At("/api/")
	public Render primTypeTest(@Param(value = "id", df = "1") Integer id, @Param(value = "proj") String proj, @Param(value = "author") String author) {
		System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
		return new Render(RenderType.TEXT, "Received your primTypeTest request.[from primTypeTest]");
	}

	// 使用 @Namespace 注解
	@At("/nettp/pri/")
	public Render primTypeTestWithNamespace(@Param(value = "id") Integer id, @Param(value = "proj") String proj, @Param(value = "author") String author) {
		System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
		return new Render(RenderType.TEXT, "Received your primTypeTestWithNamespace request.[from primTypeTestWithNamespace]");
	}

}
