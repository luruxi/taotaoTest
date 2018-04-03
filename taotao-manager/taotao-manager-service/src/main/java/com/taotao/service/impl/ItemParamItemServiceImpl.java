package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemParamItemService;
/*
 * 获取商品的规格
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public String getItemParamByItemId(Long itemId) {
		List<TbItemParamItem> itemParamList = itemParamItemMapper.selectByItemId(itemId);
		if(itemParamList==null || itemParamList.size()==0) {
			return "";
		}
		String paramData = itemParamList.get(0).getParamData();
//		paramData
		List<Map> paramList = JsonUtils.jsonToList(paramData,Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding='0' cellspacing='0' width='100%' border='0' class='Ptable'>");
		for(Map item : paramList) {
			
			sb.append("<tr>\n");
				sb.append("<th colspan='2' class='tdTitle'>"+item.get("group")+"</th>\n");
			sb.append("</tr>\n");
			
			List<Map> sub = (List<Map>) item.get("params");
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
