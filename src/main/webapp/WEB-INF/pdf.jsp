<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Facture</title>
</head>
<body>
	<object data="<c:out value="${fichierpdf}"/>" type="application/pdf"
		width="100%" height="100%"></object>
</body>
</html>