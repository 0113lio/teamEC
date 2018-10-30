<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/cart.css">
<link rel="stylesheet" type="text/css" href="./css/i1810c.css">
<link rel="stylesheet" type="text/css" href="./css/purchaseHistory.css">
<title>商品購入履歴</title>
</head>
<body>
<s:include value="header.jsp"/>
<div id="contents">
<h1>商品購入履歴画面</h1>

<s:if test="purchaseHistoryInfoDtoList.size()>0">
<s:form action="DeletePurchaseHistoryAction">
	<s:submit value="削除" class="submit-btn"/>
</s:form>
<table>
	<s:iterator value="purchaseHistoryInfoDtoList">
	<div class="main-content">
		<div class="pro-image"><img src='<s:property value="imageFilePath" />/<s:property value="imageFileName" />'width="40%" height="220px"/></div>

		<div class="pro-text">
			<div class="pro-kana">
				<s:property value="productNameKana" />
			</div>

			<div class="pro-name">
				<s:label value="商品名:" />
				<s:property value="productName" />
			</div>

			<div class="pro-price">
				<s:label value="値段:" />
				<s:property value="price" />円
			</div>

			<div class="comp-info">
				<s:label value="発売会社名:" />
				<s:property value="releaseCompany" /><br>
				<s:label value="発売年月日:" />
				<s:property value="releaseDate" />
			</div>
		</div>
	</div>
	</s:iterator>
</table>
</s:if>
<s:else>
	<h2>商品購入履歴はありません。</h2>
</s:else>
</div>
<s:include value="footer.jsp"/>
</body>
</html>