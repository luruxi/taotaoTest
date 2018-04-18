package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
/*
 * 商品信息-来自rest接口服务
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_INFO_URL}")
	private String REST_ITEM_INFO_URL;
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;
	
	/*
	 * 获取商品基本信息
	 */
	@Override
	public ItemInfo getItemById(long itemId) {
		try {
			//通过httpclient调用rest服务查询商品信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_INFO_URL+itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if(taotaoResult.getStatus()==200) {
					ItemInfo item = (ItemInfo) taotaoResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 商品描述
	 */
	@Override
	public String getItemDesc(long itemId) {
		try {
			//通过httpclient调用rest服务查询商品信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_DESC_URL+itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
				if(taotaoResult.getStatus()==200) {
					TbItemDesc item = (TbItemDesc) taotaoResult.getData();
					return item.getItemDesc();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * 商品规格参数
	 */
	@Override
	public String getItemParam(long itemId) {
		try {
			//通过httpclient调用rest服务查询商品信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_PARAM_URL+itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
				if(taotaoResult.getStatus()==200) {
					TbItemParamItem item = (TbItemParamItem) taotaoResult.getData();
					String paramData = item.getParamData();
					List<Map> paramList = JsonUtils.jsonToList(paramData,Map.class);
					StringBuffer sb = new StringBuffer();
					sb.append("<table cellpadding='0' cellspacing='0' width='100%' border='0' class='Ptable'>");
					for(Map item1 : paramList) {
						
						sb.append("<tr>\n");
							sb.append("<th colspan='2' class='tdTitle'>"+item1.get("group")+"</th>\n");
						sb.append("</tr>\n");
						
						List<Map> sub = (List<Map>) item1.get("params");
						for(Map subItem : sub) {
							
						sb.append("<tr>\n");
							sb.append("<td class='tdTitle'>"+subItem.get("k")+"</td>\n");
							sb.append("<td>"+subItem.get("v")+"</td>\n");
						sb.append("</tr>\n");
						
						}
					}
					sb.append("</table>\n");
					return sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
