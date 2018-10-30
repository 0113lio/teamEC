package com.internousdev.i1810c.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.i1810c.dao.CartInfoDAO;
import com.internousdev.i1810c.dao.MCategoryDAO;
import com.internousdev.i1810c.dao.PurchaseHistoryInfoDAO;
import com.internousdev.i1810c.dto.CartInfoDTO;
import com.internousdev.i1810c.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementCompleteAction extends ActionSupport implements SessionAware {

	private Map<String, Object> session;
	private int id;

	public String execute() {
		String result = LOGIN;
		if (!(session.containsKey("mCategoryDtoList"))) {
			session.put("mCategoryDtoList", (new MCategoryDAO()).getMCategoryList());
		}
		if (session.containsKey("logined")) {
			result = ERROR;
			if (session.containsKey("isSettlement")) {
				session.remove("isSettlement");
					@SuppressWarnings("unchecked")
					ArrayList<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList = (ArrayList<PurchaseHistoryInfoDTO>) session.get("purchaseHistoryInfoDtoList");
					CartInfoDAO cartInfoDAO=new CartInfoDAO();
					List<CartInfoDTO> cartInfoDtoList=new ArrayList<CartInfoDTO>();
					cartInfoDtoList=cartInfoDAO.getCartInfoDtoList(String.valueOf(session.get("loginId")));
				if (session.containsKey("purchaseHistoryInfoDtoList")) {
					for (int i = 0; i < purchaseHistoryInfoDtoList.size(); i++) {
						purchaseHistoryInfoDtoList.get(i).setDestinationId(id);
					}
					PurchaseHistoryInfoDAO purchaseHistoryInfoDAO = new PurchaseHistoryInfoDAO();
					int count = 0;
					for (int i = 0; i < purchaseHistoryInfoDtoList.size(); i++) {
						count += purchaseHistoryInfoDAO.regist(String.valueOf(session.get("loginId")),
								purchaseHistoryInfoDtoList.get(i).getProductId(),
								purchaseHistoryInfoDtoList.get(i).getProductCount(),
								purchaseHistoryInfoDtoList.get(i).getDestinationId(),
								purchaseHistoryInfoDtoList.get(i).getSubtotal());
					}
					if (count > 0) {
						session.remove("purchaseHistoryInfoDtoList");
						count = cartInfoDAO.deleteAll(String.valueOf(session.get("loginId")));
						if (count > 0) {
							Iterator<CartInfoDTO> iterator = cartInfoDtoList.iterator();
							if (!(iterator.hasNext())) {
								cartInfoDtoList = null;
							}
							session.put("cartInfoDtoList", cartInfoDtoList);

							int totalPrice = Integer.parseInt(String.valueOf(cartInfoDAO.getTotalPrice(String.valueOf(session.get("loginId")))));
							session.put("totalPrice", totalPrice);
							result = SUCCESS;
						}
					}
				}
			}
		}
		return result;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
