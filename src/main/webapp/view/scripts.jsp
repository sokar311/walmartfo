<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<fmt:setLocale value = "es_CL" scope="session"/>

	<script src="${ctx}/assets/js/02-jquery-v1.10.2.js"></script>
	<script src="${ctx}/assets/js/04-bootstrap-v3.3.7.js"> </script>
	<script src="${ctx}/assets/js/05-validator-v0.11.9.js"></script>
	
	<script>
		var headerCtrl =angular.module('angularApp', []);
		headerCtrl.controller('HeaderController', function($scope,$http) {

			// Redirecciona al buscador con el campo ingresado
			$scope.buscarParametro = function(parametroBusqueda){
				console.log("param",parametroBusqueda);
				if(parametroBusqueda!=null &&parametroBusqueda!=""){
					window.location.href = '${ctx}/search?phrase='+$scope.parametroBusqueda;
				}
			}
		});
	</script>