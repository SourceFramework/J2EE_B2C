package com.taotao.portal.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemBaseInfo;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${HTTPCLIENT_BASE_URL}")
	private String HTTPCLIENT_BASE_URL;
	@Value("${ITEM_BASE_INFO}")
	private String ITEM_BASE_INFO;
	@Value("${ITEM_DESC}")
	private String ITEM_DESC;
	@Value("${ITEM_PARAM}")
	private String ITEM_PARAM;

	@Override
	public ItemBaseInfo getItemBaseInfo(long id) {
		String jsonData = HttpClientUtil.doGet(HTTPCLIENT_BASE_URL + ITEM_BASE_INFO + "/" + id);
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, ItemBaseInfo.class);
		if (taotaoResult.getStatus() == 200) {
			ItemBaseInfo item = (ItemBaseInfo) taotaoResult.getData();
			return item;
		} else {
			return null;
		}
	}

	@Override
	public String getItemDesc(long id) {
		try {
			String jsonData = HttpClientUtil.doGet(HTTPCLIENT_BASE_URL + ITEM_DESC + "/" + id);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbItemDesc.class);
			if (taotaoResult.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
				return itemDesc.getItemDesc();	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemParam(long id) {
		try {
			String jsonData = HttpClientUtil.doGet(HTTPCLIENT_BASE_URL + ITEM_PARAM + "/" + id);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbItemParamItem.class);
			if(taotaoResult.getStatus() == 200){
				TbItemParamItem paramItem = (TbItemParamItem) taotaoResult.getData();
				String paramData = paramItem.getParamData();
				//生成html
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				return sb.toString();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
