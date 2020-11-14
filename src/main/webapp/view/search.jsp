<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><c:set var="ctx" value="${pageContext['request'].contextPath}"/>

<!DOCTYPE html>
<html ng-app="angularApp">
<head>
	<c:import url="./head.jsp" charEncoding="UTF-8"/>
  <title>${tittleURL} - Search results</title>
</head>

<c:import url="header.jsp" charEncoding="UTF-8"/>
	
	<div class="vista vista--resultado-busqueda">

		<div id="js-cargador">
			<div class="cargador">
				<img class="cargador__imagen" src="${ctx}/images/site/loading.gif" alt="loading" style="width: 50px;">
			</div>
		</div>

		<div class="container vista__contenedor">
			<div class="row">
				<div class="col-sm-12">
					<nav class="breadcrumb">
						<ul>
							<li class="breadcrumb__item">
								<a href="${ctx}/home/">home</a>
							</li>
							<c:if test="${!viewOferta}">
							<li class="breadcrumb__item">
								<a  onclick='busqueda(this)'> "${search}"</a>
							</li>
							</c:if>
							<c:if test="${viewOferta}">
							<li class="breadcrumb__item">
								<a  onclick='busqueda(this)'> ofertas</a>
							</li>
							</c:if>
						</ul>
					</nav>
				</div>
			</div>

			<div class="vista__header">
				<div class="vista__identificacion">
					<h2 class="vista__titulo">
						SEARCH RESULTS
					</h2>
					
				</div>
				<p class="vista__descripcion" id="mje_totalResultados">
					No results
				</p> 
			</div>

			<div id="searchAngular" ng-controller="searchController">
				
				<div class="row">

					<div class="col-md-9 col-sm-8">

						<%-- MENSAJE VISTA SIN PRODUCTOS --%>
						<div id="DivNoItems"  style="display: none;">
							<div class="mensaje">
								<p class="mensaje__texto">
									<c:if test="${search != null}">
									Sorry, no results found for <strong>${search}</strong> 😞 
									<br>Please try another search.
									</c:if>
									<c:if test="${search == null}">
									Please try searching for a product.
									</c:if>
								</p>
							</div>
						</div>

						<%-- MINIFICHAS CARGANDO PAGINA --%>
						<ul class="columnas productosNoCargados" id="loadingFacet">
							<c:forEach begin="1" end="${cantidadXPagina}">
								<li  class="columnas__item columnas__item--minifichas">
									<div class="minificha minificha--cargando">
										<div class="minificha__imagen">
											<img class="img-responsive" alt="cargando" src="${ctx}/images/site/cargando.png" > 
										</div>
										<div class="minificha__informacion">
											<div class="minificha__marca"></div>
											<ul class="minificha__nombre-producto">
												<li></li>
												<li></li>
											</ul>
											<div class="minificha__sku"></div>
										</div>

										<div class="minificha__precios">
											<span></span>
											<span></span>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>

						<%-- LISTA DE MINIFICHAS	 --%>
						<ul class="columnas" id="DivItems" style="display: none;">
							<li class="columnas__item columnas__item--minifichas" ng-repeat='product in ${productosBusqueda}'>
								<div class="minificha">

									<div class="minificha__imagen">
										<img ng-if="product.image!=null" alt="{{product.description}}" ng-src="https://{{product.image}}">
										<img ng-if="product.image==null" class="img-responsive" alt="" ng-src="${ctx}/images/site/no-disponible.png" >
									</div>

									<div class="minificha__informacion">
										<h6 class="minificha__marca"> {{product.brand}} </h6>
										<h1 class="minificha__nombre-producto">
											{{product.description}}
										</h1>
										<div class="minificha__codigos">
											<span class="minificha__sku">
												sku: {{product.id}}
											</span>
										</div>
										
										<div class="minificha__disponibilidad minificha__disponibilidad--ok" >
											Stock available
										</div>

										<div ng-if="product.previusPrice != product.price" class="minificha__iconos">
											<img  ng-src="${ctx}/images/iconos/discount.png" alt="50% Discount">
										</div>
									</div>
												
									<div class="minificha__precios">
										<div ng-if="product.previusPrice != product.price" class="minificha__precio-referencia">
											<span>Normal price</span>
											{{product.previusPrice}} 
										</div>
			
										<div ng-if="product.previusPrice == product.price" class="minificha__precio-preferencial minificha__precio-preferencial--solo">
											<span>Price</span>
											{{product.price}} 
										</div>
												
										<div ng-if="product.previusPrice != product.price" class="minificha__precio-preferencial">
											<span>Discount price</span>
											{{product.price}} 
										</div>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

<c:import url="scripts.jsp" charEncoding="UTF-8"/>	

<c:import url="footer.jsp" charEncoding="UTF-8"/>

<script type="text/javascript">

	$(document).ready(function() {	
		angular.element(document.getElementById('searchAngular')).scope().cargadeItemsByUrl2();
	});
	
	headerCtrl.controller('searchController', function($scope,$http) {
		$scope.umbralStock= ${umbralStock!=null?umbralStock:0};

		$scope.totalResultados=0;
		
		$scope.cargadeItemsByUrl2=function(adHistory,upScreen){
			var url = new URL(window.location.href);
			$scope.pagina=url.searchParams.get("pagina")!=null?url.searchParams.get("pagina")-1:0;
			if(${productosBusqueda.length()==0}){
				$("#js-cargador").fadeOut(200);
				$("#loadingFacet").fadeOut(400);
				$("#DivNoItems").fadeIn(300);
				$("#Divpaginacion").fadeOut(300);
				return;
			}
		
			var arrProducts=${productosBusqueda};
						
			$scope.totalResultados=${TotalProductos};
			
			if(arrProducts.length==0){
				$scope.ResultadoItems(0);
			}else{
				$scope.ResultadoItems(1);
			}
			setTimeout(function(){ 					
				$("#divFacets").fadeIn(20);
			}, 200);

		}
		
		
		$scope.ResultadoItems=function(val){
			if(val==null){	//load
	 			$("#DivNoItems").fadeOut(20);
	 			$("#DivItems").fadeOut(20);
				$("#js-cargador").fadeIn(30);
				$("#loadingFacet").fadeIn(20);
			}else if(val==0){	//nocontent
				$("#js-cargador").fadeOut(20);
				$("#loadingFacet").fadeOut(20);
				$("#DivNoItems").fadeIn(20);
//	 			$("#DivItems").fadeOut(300);
				$("#mje_totalResultados").fadeOut(20);
			}else{	//content
				$("#js-cargador").fadeOut(30);
				$("#loadingFacet").fadeOut(20);
	 			$("#DivNoItems").fadeOut(20);
				$("#DivItems").fadeIn(20);
				$scope.mensajeTotalResultados();
				$("#mje_totalResultados").fadeIn(20);

			}
		}
		
		$scope.mensajeTotalResultados=function(){
			$("#mje_totalResultados").html("Found "+$scope.totalResultados+" product(s).");
		}
				
	});
	</script>


</body>
</html>

