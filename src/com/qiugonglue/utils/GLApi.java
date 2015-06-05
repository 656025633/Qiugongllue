package com.qiugonglue.utils;

/**
 * 求攻略API,URL
 * 
 * @author dell
 * 
 */
public class GLApi {

	// 选择你想去的目的地
	public static final String CHOOSEPLACE = "http://www.qiugonglue.com/api/v3/main/recommend?tm=1430235418007&sign=659809a4a449a4ac3119370109912746";

	public static final String TOKYO = "http://www.qiugonglue.com/api/v3/client/index_recommend?client_name=Tokyo&client_version=4.0.37&os_version=17&platform=android&screen_size=720x1280&tm=1430637911922&uuid=862949021272757&sign=c2af06a347674f5a9656545b9d5103dc";

	/**
	 * 推荐------------------------------------
	 */

	// 推荐 —— 当地导游的图片和点击后的宝贝详情页面
	public static final String GUIDE = "http://m.shijieyou.com/mobileJson/recommendByClientName?client_name=HuaHin&latitude=40.037225&longitude=116.368483";

	// 推荐 —— 发现新奇：发现更多，依次 p递增
	public static final String FINDMORE = "http://www.qiugonglue.com/cms/cms_list?p=%s";

	public static final String XINQI = "http://www.qiugonglue.com/api/v3/client/index_recommend?client_name=HuaHin&client_version=4.0.37&os_version=17&platform=android&screen_size=720x1280&tm=1430756540348&uuid=862949021272757&sign=fa758e12e5c15fa621279cbf68223c8d";

	public static final String BANNER = "http://www.qiugonglue.com/api/v3/client/banner_info?client_name=QGLMain&client_version=4.0.37&current_client_name=HuaHin&os_version=17&platform=android&screen_size=720x1280&tm=1430756540543&uuid=862949021272757&sign=322629aabe973737c32a698c9c1e06a5";

	/**
	 * 服务------------------------------------
	 */

	// 服务 —— 整个页面用WebView，用这一个url即可
	public static final String SERVICE = "http://www.qiugonglue.com/api/v3/client/shijieyou_mall?client_name=QGLMain";

	/**
	 * 群聊------------------------------------
	 */
	// 群聊 —— 所有群，左侧地点列表
	public static final String CHATPLACE = "http://www.qiugonglue.com/api/v3/group/city_list?client_name=QGLMain&client_version=4.0.37&os_version=17&platform=android&screen_size=720x1280&tm=1430238564408&uuid=862949021272757&sign=e7cf20f2b649fe5e5ec26c257d8a5396";

	// 群聊 —— 所有群，右侧简介列表
	public static final String CHATBRIEF = "http://www.qiugonglue.com/api/v3/group/group_list?filter_client_name=Taiwan";

	// 我加入的群组
	public static final String FLLOWGROUP = "http://www.qiugonglue.com/api/v3/user/groups?user_id=476445";

	// 消息
	public static final String MESSAGE = "http://www.qiugonglue.com/api/v3/group/info?group_id=";

	// 群组信息
	public static final String GROUP = "";

	/**
	 * 动态------------------------------------
	 */

	// 动态 —— 上方标题
	public static final String DYNAMICBAR = "http://www.qiugonglue.com/api/v3/trends/get_tag_nav?client_name=QGLMain&sign=5b74322fb514dc629c4550743ae8b865";

	// 动态 —— 全部标签的内容
	public static final String DYNAMICBARALL = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=desc&os_version=17&p=1&platform=android&screen_size=720x1280&tm=1430236653232&uuid=862949021272757&sign=380b5834cc6353126c96098dfdaa56ac";

	// 动态 —— 晒旅行标签的内容
	public static final String DYNAMICBARTRAVEL = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=desc&os_version=17&p=1&platform=android&screen_size=720x1280&tag_id=3&tm=1430237003070&uuid=862949021272757&sign=dfa348a16b1f36df1ab11773b216f79";

	// 动态 —— 代购标签的内容
	public static final String DYNAMICBARSHOP = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=desc&os_version=17&p=1&platform=android&screen_size=720x1280&tag_id=8&tm=1430237247383&uuid=862949021272757&sign=d09fa5faa61285963250cbcc0d932d0f";

	// 用户的足迹
	public static final String TAFOOT = "http://www.qiugonglue.com/api/v3/user/been_list?client_name=QGLMain&client_version=4.0.37&os_version=17&p=1&platform=android&screen_size=720x1280&tm=1430927823188&user_id=312998&uuid=862949021272757&sign=fc3373a39f179e0ad33db89cac85c64e";

	// 用户的动态
	public static final String TAGYNAMIC = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=asc&os_version=17&p=1&platform=android&screen_size=720x1280&tm=1430927738957&user_id=312998&uuid=862949021272757&visitor_user_id=476445&sign=e064ec7110e5f6c8b5a635b3f17f902f";

	// 查看用户的信息
	public static final String PERSONMESSAGE = "http://www.qiugonglue.com/api/v3/user/info?client_name=QGLMain&client_version=4.0.37&os_version=17&platform=android&screen_size=720x1280&tm=1430756883229&user_id=472917&uuid=862949021272757&sign=426fa1d38026c1e48ea0374a214771d1";

	// 动态 —— 求伴标签的内容
	public static final String DYNAMICBARPRAY = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=desc&os_version=17&p=1&platform=android&screen_size=720x1280&tag_id=3&tm=1430237003070&uuid=862949021272757&sign=dfa348a16b1f36df1ab11773b216f79";

	// 动态 —— 约饭
	public static final String DYNAMCIFOOD = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=desc&os_version=17&p=1&platform=android&screen_size=720x1280&tag_id=7&tm=1430843441513&uuid=862949021272757&visitor_user_id=476445&sign=e3f066f8ff5989f03ccfa866046754ad";

	// 动态 —— 一起买
	public static final String DYNAMICBUY = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=desc&os_version=17&p=1&platform=android&screen_size=720x1280&tag_id=8&tm=1430843529203&uuid=862949021272757&visitor_user_id=476445&sign=9163406656677f9f6c28a4c6d60e8069";

	/**
	 * 我的------------------------------------
	 */

	// 我的 —— 注册
	public static final String REGISTE = "http://www.qiugonglue.com/api/v3/group/get_token?client_name=QGLMain&client_version=4.0.37&os_version=17&platform=android&screen_size=720x1280&tm=1430237499468&user_id=476446&uuid=862949021272757&sign=59c72c2696ba38985b458563a666e6c5";

	// 我的 —— 个人信息（需要修改参数）
	public static final String MYINFORMATION = "http://www.qiugonglue.com/api/v3/user/info?client_name=QGLMain&client_version=4.0.37&os_version=17&platform=android&screen_size=720x1280&simple=1&tm=1430237499471&user_id=476446&uuid=862949021272757&sign=9f80f814945a39bf280b48a0be6075e8";

	// 我的 —— 获取token
	public static final String MYTOKEN = "http://www.qiugonglue.com/api/v3/group/get_token?client_name=QGLMain&client_version=4.0.37&os_version=17&platform=android&screen_size=720x1280&tm=1430237499705&user_id=476446&uuid=862949021272757&sign=6b8becb85eb63fd7e2ac5a540498b669";

	// 我的动态
	public static final String MINEDONGTAI = "http://www.qiugonglue.com/api/v3/trends/trends_list?client_name=QGLMain&client_version=4.0.37&limit=20&order_by=asc&os_version=17&p=1&platform=android&screen_size=720x1280&tm=1430757279654&user_id=472917&uuid=862949021272757&visitor_user_id=476445&sign=62e0b39384a0974449cd187fbd03a09c";

	// 我的评论
	public static final String MINECOMMENTS = "http://www.qiugonglue.com/api/v3/user/comment_list?client_name=QGLMain&client_version=4.0.37&os_version=17&p=1&platform=android&screen_size=720x1280&tm=1430757345614&user_id=472917&uuid=862949021272757&sign=db7cf0cccaf59b6137277ef06fb28f63";

	// 我的足迹
	public static final String MINEBEENS = "http://www.qiugonglue.com/api/v3/user/been_list?client_name=QGLMain&client_version=4.0.37&os_version=17&p=1&platform=android&screen_size=720x1280&tm=1430757377460&user_id=472917&uuid=862949021272757&sign=9e82508e08feb150df437c5e4bfdd9a8";

	// 暂时没有找到！！！
	// public static final String =
	// "http://www.qiugonglue.com/api/v3/client/init?client_name=QGLMain&client_version=4.0.37&os_version=17&platform=android&push_token=Ao6IevQCZ2HgPZTSIOdNUKvMEjNUEsOTd3hu3JQR602q&screen_size=720x1280&tm=1430233905619&uuid=862949021272757&sign=6c33243672e628abd75bae79bbf8154a";

}
