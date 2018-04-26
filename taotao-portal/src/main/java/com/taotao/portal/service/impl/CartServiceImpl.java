package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
/*
 * 购物车服务接口
 */
@Service
public class CartServiceImpl implements CartService {
	//rest服务基础url
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	//rest服务获取商品信息url
	@Value("${REST_ITEM_INFO_URL}")
	private String REST_ITEM_INFO_URL;
	
	/*
	 * 购物车添加商品
	 */
	@Override
	public TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		//定义购物车商品对象
		CartItem cartItem = null;
		
		//获取购物车商品列表--cookie
		List<CartItem> cartItemList = getCartItemList(request);
		//判断商品列表中是否有该商品
		for (CartItem cartItem2 : cartItemList) {
			if(cartItem2.getId() == itemId) {
				cartItem2.setNum(cartItem2.getNum()+num);
				cartItem = cartItem2;
				break;
			}
		}
		if(cartItem == null) {
			cartItem = new CartItem();
			//根据商品id查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_INFO_URL+itemId);
			//把json数据转成java对象
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			//判断取值
			if(result.getStatus()==200) {
				TbItem item = (TbItem) result.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage()==null?"":item.getImage().split(",")[0]);
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);
			}
			//添加到购物车列表
			cartItemList.add(cartItem);
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), true);
		return TaotaoResult.ok();
	}

	//获取购物车商品列表--cookie
	private List<CartItem> getCartItemList(HttpServletRequest request){
		//从cookie中取商品列表 
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);//请求  键值  是否编码
		//判断返回结果是否为空
		if(cartJson==null) {
			return new ArrayList<>();
		}
		try {
			//json数据转商品列表
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
		
	}
	
	/*
	 * 获取购物车信息列表
	 */
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItemList = getCartItemList(request);
		return cartItemList;
	}
	
	//更新购物车商品数量
	@Override
	public TaotaoResult updateCartItemNum(long itemId, int num, HttpServletRequest request,
			HttpServletResponse response) {
		//定义购物车商品对象
		CartItem cartItem = null;
		
		//获取购物车商品列表--cookie
		List<CartItem> cartItemList = getCartItemList(request);
		//判断商品列表中是否有该商品
		for (CartItem cartItem2 : cartItemList) {
			if(cartItem2.getId() == itemId) {
				cartItem2.setNum(num);
				cartItem = cartItem2;
				break;
			}
		}
		if(cartItem == null) {
			cartItem = new CartItem();
			//根据商品id查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_INFO_URL+itemId);
			//把json数据转成java对象
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			//判断取值
			if(result.getStatus()==200) {
				TbItem item = (TbItem) result.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage()==null?"":item.getImage().split(",")[0]);
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);
			}
			//添加到购物车列表
			cartItemList.add(cartItem);
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), true);
		return TaotaoResult.ok();
	}
	//删除购物车中的商品
	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		//定义购物车商品对象
		CartItem cartItem = null;
		
		//获取购物车商品列表--cookie
		List<CartItem> cartItemList = getCartItemList(request);
		//判断商品列表中是否有该商品
		for (CartItem cartItem2 : cartItemList) {
			if(cartItem2.getId() == itemId) {
				cartItemList.remove(cartItem2);
				break;
			}
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), true);
		return TaotaoResult.ok();
	}
}
